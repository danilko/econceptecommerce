/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 * <p/>
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the
 * following conditions:
 * <p/>
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.controller.payment;

import com.controller.dao.ProductDAO;
import com.controller.model.*;
import com.controller.shipment.ProductShipmentClient;
import com.paypal.api.payments.*;
import com.paypal.core.ConfigManager;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.shippo.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PaymentClient {

    @Inject
    ProductDAO  productDAO;

    private static Logger LOGGER = LoggerFactory.getLogger(PaymentClient.class);

    public void executePayment(String paymentId, String payerId) throws PayPalRESTException {

        Properties properties = new Properties();
        properties.setProperty("http.ConnectionTimeOut", "5000");

        properties.setProperty("http.Retry", "1");
        properties.setProperty("http.ReadTimeOut", "30000");
        properties.setProperty("http.MaxConnection", "100");
        properties.setProperty("http.UseProxy", "false");

        properties.setProperty("service.EndPoint", System.getenv("PAYPAL_API_SERVICE_ENDPOINT"));
        properties.setProperty("clientID", System.getenv("PAYPAL_API_CLIENT_ID"));
        properties.setProperty("clientSecret", System.getenv("PAYPAL_API_CLIENT_SECRET"));

        Payment.initConfig(properties);

        OAuthTokenCredential tokenCredential = new OAuthTokenCredential(
                ConfigManager.getInstance().getValue("clientID"), ConfigManager.getInstance().getValue("clientSecret"));

        String accessToken = tokenCredential.getAccessToken();

        Payment payment = Payment.get(accessToken, paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment newPayment = payment.execute(accessToken, paymentExecution);

        List<Link> linkList = newPayment.getLinks();

        String paymentHref = null;

        for (Link link : linkList) {
            LOGGER.debug("PAYMENT EXECUTE LINK:" + link.getRel() + "" + link.getHref());

        }

    }

    public String createPayment(ProductShoppingCart productShoppingCart)
            throws PayPalRESTException, AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException, RequestTimeoutException {
        ProductShipmentClient productShipmentClient = new ProductShipmentClient();
        productShipmentClient.create();

        ProductShipmentAddress shipmentAddress = productShipmentClient
                .getProductSAhipmmentAddressByShipmentReferenceId(
                        productShoppingCart.getProductShipmentReferenceId());

        List<ProductShoppingCartItem> items = productShoppingCart.getProductShoppingCartList();
        ProductShipment productShipment = productShipmentClient
                .getProductShipment(productShoppingCart.getProductShipmentRatingReferenceId());

        String linel = shipmentAddress.getAddressStreet1();

        if (StringUtils.isNotBlank(shipmentAddress.getAddressStreet2())) {
            linel = linel + "," + shipmentAddress.getAddressStreet2();
        }

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setLine1(linel);
        shippingAddress.setCity(shipmentAddress.getAddressCity());
        shippingAddress.setCountryCode(shipmentAddress.getAddressCountry());
        shippingAddress.setPostalCode(shipmentAddress.getAddressZip());
        shippingAddress.setState(shipmentAddress.getAddressState());
        shippingAddress.setRecipientName(shipmentAddress.getAddressName());

        double total = 0.0;
        int quantity = 0;

        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        List<Item> shoppingCartItemList = new ArrayList<Item>();
        for (ProductShoppingCartItem productShoppingCartItem : items) {
            Product product = productDAO
                    .getProductByProductSerialNumber(productShoppingCartItem.getProductSerialNumber());
            total = total
                    + Double.parseDouble(productShoppingCartItem.getProductQuantity()) * product.getProductPrice();
            LOGGER.debug("PAYMENT_CLIENT: total = " + total + "quantity: "
                    + Double.parseDouble(productShoppingCartItem.getProductQuantity()) + ", price: "
                    + product.getProductPrice());
            Item item = new Item();
            item.setName(product.getProductName());
            item.setPrice(decimalFormat.format(product.getProductPrice()));
            item.setQuantity(productShoppingCartItem.getProductQuantity());
            item.setCurrency("USD");
            quantity = quantity + 1;
            shoppingCartItemList.add(item);
        }

        AmountDetails amountDetails = new AmountDetails();
        amountDetails.setSubtotal(decimalFormat.format(total));

        // 10% Tax
        amountDetails.setTax(decimalFormat.format(total * 0.1));

        total = total + (total * 0.1);
        LOGGER.debug("PAYMENT_CLIENT: total = " + decimalFormat.format(total));
        // Shipment = Shipment of single item x quantity
        amountDetails.setShipping(decimalFormat.format(productShipment.getShipmentAmount() * quantity));
        total = total + (productShipment.getShipmentAmount() * quantity);
        LOGGER.debug("PAYMENT_CLIENT: total = " + decimalFormat.format(total));
        Amount amount = new Amount();
        amount.setTotal(decimalFormat.format(total));
        amount.setCurrency("USD");
        amount.setDetails(amountDetails);

        ItemList itemList = new ItemList();
        itemList.setShippingAddress(shippingAddress);
        itemList.setItems(shoppingCartItemList);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("This is the payment transaction description.");
        transaction.setItemList(itemList);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(System.getenv("CONTROLLER_URI") + "/rest/payment/execute");
        redirectUrls.setCancelUrl(System.getenv("CONTROLLER_URI"));

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        Properties properties = new Properties();
        properties.setProperty("http.ConnectionTimeOut", "5000");

        properties.setProperty("http.Retry", "1");
        properties.setProperty("http.ReadTimeOut", "30000");
        properties.setProperty("http.MaxConnection", "100");
        properties.setProperty("http.UseProxy", "false");

        properties.setProperty("service.EndPoint", System.getenv("PAYPAL_API_SERVICE_ENDPOINT"));
        properties.setProperty("clientID", System.getenv("PAYPAL_API_CLIENT_ID"));
        properties.setProperty("clientSecret", System.getenv("PAYPAL_API_CLIENT_SECRET"));

        Payment.initConfig(properties);

        OAuthTokenCredential tokenCredential = new OAuthTokenCredential(
                ConfigManager.getInstance().getValue("clientID"), ConfigManager.getInstance().getValue("clientSecret"));

        String accessToken = tokenCredential.getAccessToken();

        Payment createdPayment = payment.create(accessToken);

        List<Link> linkList = createdPayment.getLinks();

        String paymentHref = null;

        for (Link link : linkList) {
            if ("approval_url".equalsIgnoreCase(link.getRel())) {
                paymentHref = link.getHref();
            }
        }

        return paymentHref;
    }
}
