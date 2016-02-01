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

package com.controller.model;

import javax.persistence.Entity;
import java.util.List;

public class ProductShoppingCart {

    private String productShipmentReferenceId;
    private String productShipmentRatingReferenceId;

    private  List<ProductShoppingCartItem> productShoppingCartList;

    public String getProductShipmentRatingReferenceId() {
        return productShipmentRatingReferenceId;
    }

    public void setProductShipmentRatingReferenceId(String productShipmentRatingReferenceId) {
        this.productShipmentRatingReferenceId = productShipmentRatingReferenceId;
    }

    public List<ProductShoppingCartItem> getProductShoppingCartList() {
        return productShoppingCartList;
    }

    public void setProductShoppingCartList(List<ProductShoppingCartItem> productShoppingCartList) {
        this.productShoppingCartList = productShoppingCartList;
    }

    public String getProductShipmentReferenceId() {
        return productShipmentReferenceId;
    }

    public void setProductShipmentReferenceId(String productShipmentReferenceId) {
        this.productShipmentReferenceId = productShipmentReferenceId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((productShipmentRatingReferenceId == null) ? 0 : productShipmentRatingReferenceId.hashCode());
        result = prime * result + ((productShipmentReferenceId == null) ? 0 : productShipmentReferenceId.hashCode());
        result = prime * result + ((productShoppingCartList == null) ? 0 : productShoppingCartList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ProductShoppingCart)) {
            return false;
        }
        ProductShoppingCart other = (ProductShoppingCart) obj;
        if (productShipmentRatingReferenceId == null) {
            if (other.productShipmentRatingReferenceId != null) {
                return false;
            }
        } else if (!productShipmentRatingReferenceId.equals(other.productShipmentRatingReferenceId)) {
            return false;
        }
        if (productShipmentReferenceId == null) {
            if (other.productShipmentReferenceId != null) {
                return false;
            }
        } else if (!productShipmentReferenceId.equals(other.productShipmentReferenceId)) {
            return false;
        }
        if (productShoppingCartList == null) {
            if (other.productShoppingCartList != null) {
                return false;
            }
        } else if (!productShoppingCartList.equals(other.productShoppingCartList)) {
            return false;
        }
        return true;
    }
}
