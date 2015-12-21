
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

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSpecification implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 3075294014446278441L;

    @JsonProperty("productSerialNumber")
    private String productSerialNumber;

    @JsonProperty("productSpecificationName")
    private String productSpecificationName;
    @JsonProperty("productSpecificationValue")
    private String productSpecificationValue;

    public String getProductSerialNumber() {
        return productSerialNumber;
    }

    public void setProductSerialNumber(String productSerialNumber) {
        this.productSerialNumber = productSerialNumber;
    }

    public String getProductSpecificationName() {
        return productSpecificationName;
    }

    public void setProductSpecificationName(String productSpecificationKey) {
        this.productSpecificationName = productSpecificationKey;
    }

    public String getProductSpecificationValue() {
        return productSpecificationValue;
    }

    public void setProductSpecificationValue(String productSpecificationValue) {
        this.productSpecificationValue = productSpecificationValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productSerialNumber == null) ? 0 : productSerialNumber.hashCode());
        result = prime * result + ((productSpecificationName == null) ? 0 : productSpecificationName.hashCode());
        result = prime * result + ((productSpecificationValue == null) ? 0 : productSpecificationValue.hashCode());
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
        if (!(obj instanceof ProductSpecification)) {
            return false;
        }
        ProductSpecification other = (ProductSpecification) obj;
        if (productSerialNumber == null) {
            if (other.productSerialNumber != null) {
                return false;
            }
        } else if (!productSerialNumber.equals(other.productSerialNumber)) {
            return false;
        }
        if (productSpecificationName == null) {
            if (other.productSpecificationName != null) {
                return false;
            }
        } else if (!productSpecificationName.equals(other.productSpecificationName)) {
            return false;
        }
        if (productSpecificationValue == null) {
            if (other.productSpecificationValue != null) {
                return false;
            }
        } else if (!productSpecificationValue.equals(other.productSpecificationValue)) {
            return false;
        }
        return true;
    }


}
