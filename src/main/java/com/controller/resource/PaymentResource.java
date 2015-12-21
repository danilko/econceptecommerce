
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
package com.controller.resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.controller.model.ProductShipmentAddress;
import com.controller.model.ProductShoppingCart;
import com.controller.payment.PaymentClient;
import com.controller.shipment.ProductShipmentClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Payment Service")
@Produces("application/json; charset=UTF-8")
@Path("/payment")
public class PaymentResource {
    private static Logger LOGGER = LoggerFactory.getLogger(PaymentResource.class);

    @GET
    @Path("/execute")
    @Consumes("application/json")
    @ApiOperation(value = "Test API", notes = "Test API")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Session ID not found")})
    public Response paymentApi(@QueryParam("paymentId") String paymentId, @QueryParam("PayerID") String payerId,
                               @QueryParam("token") String token) {

        // http://<return_url>?paymentId=<PAYMENT_ID>&PayerID=<PAYER_ID>

        try {
            PaymentClient paymentClient = new PaymentClient();
            paymentClient.executePayment(paymentId, payerId);
            return Response.ok().build();

        } catch (Exception exception) {
            LOGGER.error("Error in calculating payment", exception);
            return Response.serverError().entity("{\"message\":\"internal server error\"}").build();
        }

    }

    @POST
    @Path("/productShipment")
    @Consumes("application/json")
    @ApiOperation(value = "Test API", notes = "Test API")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Session ID not found")})
    public Response testAPI(List<ProductShipmentAddress> addresses) {
        ProductShipmentClient productShipmentClient = new ProductShipmentClient();
        productShipmentClient.create();
        try {
            return Response.ok().entity("{\"shippingId\":\""
                    + productShipmentClient.getShipping(productShipmentClient.getAddress(addresses.get(0)),
                    productShipmentClient.getAddress(addresses.get(1)), productShipmentClient.getParcel())
                    + "\"}").build();

        } catch (Exception exception) {
            LOGGER.error("Error in calculating shippment", exception);
            return Response.serverError().entity("{\"message\":\"internal server error\"}").build();

        }

    }

    @POST
    @Path("/productShipment/addressValidation")
    @Consumes("application/json")
    @ApiOperation(value = "Test API", notes = "Test API")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Session ID not found")})
    public Response getAddressValidation(ProductShipmentAddress address) {
        ProductShipmentClient productShipmentClient = new ProductShipmentClient();
        productShipmentClient.create();
        try {
            return Response.ok()
                    .entity("{\"addressStatus\":\"" + productShipmentClient.validateAddress(address) + "\"}").build();

        } catch (Exception exception) {
            LOGGER.error("Error in calculating shippment", exception);
            return Response.serverError().entity("{\"message\":\"internal server error\"}").build();

        }

    }

    @POST
    @Path("/checkout")
    @Consumes("application/json")
    @ApiOperation(value = "Test API", notes = "Test API")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Session ID not found")})
    public Response getPaypalPayment(ProductShoppingCart productShoppingCart,
                                     @QueryParam("recaptcha") String recaptcha) {

        try {
            HttpClient client = HttpClientBuilder.create().build();

            URIBuilder builder = new URIBuilder(System.getenv("GOOGLE_RECAPTCHA_API_ENDPOINT"));
            builder.setParameter("secret", System.getenv("GOOGLE_RECAPTCHA_API_SECRET"));
            builder.setParameter("response", recaptcha);
            LOGGER.error(
                    "RECAPTCHA: " + recaptcha);

            HttpPost httpPost = new HttpPost(builder.build());

            HttpResponse httpResponse = client.execute(httpPost);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode captchaResponse = objectMapper.readTree(result.toString());
                LOGGER.error(
                        "RECAPTCHA RESPONSE: " + result.toString());
                if (captchaResponse.get("success").asBoolean(false)) {
                    PaymentClient paymentClient = new PaymentClient();

                    return Response.ok()
                            .entity("{\"paypalHref\":\"" + paymentClient.createPayment(productShoppingCart) + "\"}")
                            .build();
                } else {
                    LOGGER.error(
                            "Error in validating reCaptcha payment: " + captchaResponse.get("error-codes").asText());
                }
            }
            LOGGER.error("Error in validating reCaptcha payment");
            return Response.serverError().entity("{\"message\":\"internal server error\"}").build();

        } catch (Exception exception) {
            LOGGER.error("Error in calculating payment", exception);
            return Response.serverError().entity("{\"message\":\"internal server error\"}").build();
        }

    }

    @GET
    @Path("/productShipment/rating/{objectId}")
    @ApiOperation(value = "Test API", notes = "Test API")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Session ID not found")})
    public Response getShippingRate(@PathParam("objectId") String objectId) {
        ProductShipmentClient productShipmentClient = new ProductShipmentClient();
        productShipmentClient.create();
        try {
            return Response.ok().entity(productShipmentClient.getShippingRate(objectId)).build();

        } catch (Exception exception) {
            LOGGER.error("Error in calculating shippment", exception);
            return Response.serverError().entity("{\"message\":\"internal server error\"}").build();

        }

    }

}
