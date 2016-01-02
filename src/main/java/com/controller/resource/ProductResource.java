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

import com.controller.dao.ProductDAO;
import com.controller.model.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Api(value = "Product Service")
@Produces("application/json; charset=UTF-8")
@Path("/product")
public class ProductResource {

    @Inject
    private ProductDAO productDAO;

    @GET
    @Path("/_all")
    @ApiOperation(value = "Return all products", notes = "List of products", response = Product.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Session ID not found")
    })
    public Response getProducts() {

        return Response.ok().entity(productDAO.getAllProducts()).build();
    }

    @GET
    @Path("/{productSerialNumber}")
    @ApiOperation(value = "Return all products", notes = "List of products", response = Product.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Session ID not found")
    })
    public Response getProductBySerialNumber(@PathParam("productSerialNumber") String productSerialNumber) {

        return Response.ok().entity(productDAO.getProductByProductSerialNumber(productSerialNumber)).build();
    }
}
