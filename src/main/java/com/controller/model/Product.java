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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4820385498279685293L;

    @JsonProperty("productSerialNumber")
    @Id
    private String productSerialNumber;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("productPrice")
    private double productPrice;

    @JsonProperty("productDescription")
    private String productDescription;

    @JsonProperty("productSpecificationList")
    private List<ProductSpecification> productSpecificationList;

    @JsonProperty("productImageList")
    private List<String> productImageList;

    public List<ProductSpecification> getProductSpecificationList() {
        return productSpecificationList;
    }

    public void setProductSpecificationList(List<ProductSpecification> productSpecificationList) {
        this.productSpecificationList = productSpecificationList;
    }

    public List<String> getProudctImageList() {
        return productImageList;
    }

    public void setProudctImageList(List<String> proudctImageList) {
        this.productImageList = proudctImageList;
    }

    public String getProductSerialNumber() {
        return productSerialNumber;
    }

    public void setProductSerialNumber(String productSerialNumber) {
        this.productSerialNumber = productSerialNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productDescription == null) ? 0 : productDescription.hashCode());
        result = prime * result + ((productName == null) ? 0 : productName.hashCode());
        long temp;
        temp = Double.doubleToLongBits(productPrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((productSerialNumber == null) ? 0 : productSerialNumber.hashCode());
        result = prime * result + ((productSpecificationList == null) ? 0 : productSpecificationList.hashCode());
        result = prime * result + ((productImageList == null) ? 0 : productImageList.hashCode());
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
        if (!(obj instanceof Product)) {
            return false;
        }
        Product other = (Product) obj;
        if (productDescription == null) {
            if (other.productDescription != null) {
                return false;
            }
        } else if (!productDescription.equals(other.productDescription)) {
            return false;
        }
        if (productName == null) {
            if (other.productName != null) {
                return false;
            }
        } else if (!productName.equals(other.productName)) {
            return false;
        }
        if (Double.doubleToLongBits(productPrice) != Double.doubleToLongBits(other.productPrice)) {
            return false;
        }
        if (productSerialNumber == null) {
            if (other.productSerialNumber != null) {
                return false;
            }
        } else if (!productSerialNumber.equals(other.productSerialNumber)) {
            return false;
        }
        if (productSpecificationList == null) {
            if (other.productSpecificationList != null) {
                return false;
            }
        } else if (!productSpecificationList.equals(other.productSpecificationList)) {
            return false;
        }
        if (productImageList == null) {
            if (other.productImageList != null) {
                return false;
            }
        } else if (!productImageList.equals(other.productImageList)) {
            return false;
        }
        return true;
    }

}
