
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
package com.controller.shipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.controller.model.ProductShipment;
import com.controller.model.ProductShipmentAddress;
import com.shippo.Shippo;
import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.shippo.exception.RequestTimeoutException;
import com.shippo.model.Address;
import com.shippo.model.Parcel;
import com.shippo.model.Rate;
import com.shippo.model.RateCollection;
import com.shippo.model.Shipment;

public class ProductShipmentClient {

    private String shippoAPIKey = System.getenv("SHIPPO_CLIENT_API_KEY");

    public void create() {
        Shippo.setApiKey(shippoAPIKey);
    }

    public Map<String, Object> getAddress(ProductShipmentAddress address)
            throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        Map<String, Object> addressMapTo = new HashMap<String, Object>();
        addressMapTo.put("object_purpose", "PURCHASE");
        addressMapTo.put("name", address.getAddressName());
        addressMapTo.put("company", address.getAddressCompany());
        addressMapTo.put("street1", address.getAddressStreet1());
        addressMapTo.put("street_no", address.getAddressStreetNo());
        addressMapTo.put("street_2", address.getAddressStreet2());
        addressMapTo.put("city", address.getAddressCity());
        addressMapTo.put("zip", address.getAddressZip());
        addressMapTo.put("state", address.getAddressState());
        addressMapTo.put("country", address.getAddressCountry());
        addressMapTo.put("email", address.getAddressEmail());

        return addressMapTo;
    }

    public String validateAddress(ProductShipmentAddress productShipmentAddress)
            throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        Address addressFrom = Address.create(getAddress(productShipmentAddress), Shippo.getApiKey());

        return addressFrom.getObjectState();
    }

    public Map<String, Object> getParcel() {

        Map<String, Object> parcel = new HashMap<String, Object>();
        parcel.put("length", "5");
        parcel.put("width", "5");
        parcel.put("height", "5");
        parcel.put("distance_unit", "cm");
        parcel.put("weight", "2");
        parcel.put("mass_unit", "lb");
        parcel.put("template", "");
        parcel.put("metadata", "Customer ID 123456");

        return parcel;

    }

    public ProductShipmentAddress getProductSAhipmmentAddressByShipmentReferenceId(String ShipmentReferenceId)
            throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        ProductShipmentAddress productShipmentAddress = new ProductShipmentAddress();

        Shipment shipment = Shipment.retrieve(ShipmentReferenceId, Shippo.getApiKey());
        Address address = Address.retrieve(shipment.getAddressTo().toString(), Shippo.getApiKey());

        productShipmentAddress.setAddressStreet1(address.getStreet1().toString());
        productShipmentAddress.setAddressStreet2(address.getStreet2().toString());

        productShipmentAddress.setAddressStreetNo(address.getStreetNo().toString());
        productShipmentAddress.setAddressCompany(address.getCompany().toString());
        productShipmentAddress.setAddressName(address.getName().toString());
        productShipmentAddress.setAddressCity(address.getCity().toString());
        productShipmentAddress.setAddressState(address.getState().toString());
        productShipmentAddress.setAddressZip(address.getZip().toString());

        productShipmentAddress.setAddressCountry(address.getCountry().toString());

        return productShipmentAddress;

    }

    public String getShipping(Map<String, Object> addressMapFrom, Map<String, Object> addressMapTo,
                              Map<String, Object> parcelItem) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException, RequestTimeoutException {

        Address addressFrom = Address.create(addressMapFrom, Shippo.getApiKey());
        Address addressTo = Address.create(addressMapTo, Shippo.getApiKey());

        Parcel parcel = Parcel.create(parcelItem, Shippo.getApiKey());

        Map<String, Object> shipmentMap = new HashMap<String, Object>();
        shipmentMap.put("object_purpose", "PURCHASE");
        shipmentMap.put("address_from", addressFrom.getObjectId());
        shipmentMap.put("address_to", addressTo.getObjectId());
        shipmentMap.put("parcel", parcel.getObjectId());
        shipmentMap.put("insurance_amount", "100");
        shipmentMap.put("insurance_currency", "USD");
        shipmentMap.put("currency", "USD");
        shipmentMap.put("metadata", "Customer ID 12345");
        shipmentMap.put("submission_type", "DROPOFF");
        Shipment shipment = Shipment.create(shipmentMap);

        return shipment.getObjectId();
    }

    public List<ProductShipment> getShippingRate(String objectId) throws AuthenticationException,
            InvalidRequestException, APIConnectionException, APIException, RequestTimeoutException {

        List<ProductShipment> shipmentList = new ArrayList<ProductShipment>();

        RateCollection rateCollection = Shipment.getShippingRatesSync(objectId);

        List<Rate> rates = rateCollection.getData();
        for (Rate rate : rates) {
            ProductShipment productShipment = new ProductShipment();

            productShipment.setShipmentReferenceId(rate.getObjectId());
            productShipment.setShipmentProvider(rate.getProvider().toString());
            productShipment.setShipmentProviderImage75(rate.getProviderImage75().toString());
            productShipment.setShipmentProviderImage200(rate.getProvider_image_200().toString());
            productShipment.setShipmentProviderServiceLevelName(rate.getServicelevelName().toString());
            productShipment.setShipmentProviderDurationTerms(rate.getDuration_terms().toString());
            productShipment.setShipmentAmount(Double.parseDouble(rate.getAmount().toString()));
            productShipment.setShipmentAmountCurrency(rate.getCurrency().toString());
            productShipment.setShipmentInsuranceAmount(Double.parseDouble(rate.getInsuranceAmount().toString()));
            productShipment.setShipmentInsuranceAmountCurrency(rate.getInsuranceCurrency().toString());

            shipmentList.add(productShipment);
        }
        return shipmentList;
    }

    public ProductShipment getProductShipment(String objectId) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException, RequestTimeoutException {

        Rate rate = Rate.retrieve(objectId, Shippo.getApiKey());

        ProductShipment productShipment = new ProductShipment();

        productShipment.setShipmentReferenceId(rate.getObjectId());
        productShipment.setShipmentProvider(rate.getProvider().toString());
        productShipment.setShipmentProviderImage75(rate.getProviderImage75().toString());
        productShipment.setShipmentProviderImage200(rate.getProvider_image_200().toString());
        productShipment.setShipmentProviderServiceLevelName(rate.getServicelevelName().toString());
        productShipment.setShipmentProviderDurationTerms(rate.getDuration_terms().toString());
        productShipment.setShipmentAmount(Double.parseDouble(rate.getAmount().toString()));
        productShipment.setShipmentAmountCurrency(rate.getCurrency().toString());
        productShipment.setShipmentInsuranceAmount(Double.parseDouble(rate.getInsuranceAmount().toString()));
        productShipment.setShipmentInsuranceAmountCurrency(rate.getInsuranceCurrency().toString());

        return productShipment;
    }
}
