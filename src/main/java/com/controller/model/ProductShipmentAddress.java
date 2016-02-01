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

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductShipmentAddress implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2629825243772253526L;

    @Id
    private String address_id;
    private String addressName;
    private String addressCompany;
    private String addressStreet1;
    private String addressStreet2;
    private String addressStreetNo;
    private String addressCity;
    private String addressZip;
    private String addressCountry;
    private String addressEmail;
    private String addressState;

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressCompany() {
        return addressCompany;
    }

    public void setAddressCompany(String addressCompany) {
        this.addressCompany = addressCompany;
    }

    public String getAddressStreet1() {
        return addressStreet1;
    }

    public void setAddressStreet1(String addressStreet1) {
        this.addressStreet1 = addressStreet1;
    }

    public String getAddressStreet2() {
        return addressStreet2;
    }

    public void setAddressStreet2(String addressStreet2) {
        this.addressStreet2 = addressStreet2;
    }

    public String getAddressStreetNo() {
        return addressStreetNo;
    }

    public void setAddressStreetNo(String addressStreetNo) {
        this.addressStreetNo = addressStreetNo;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(String addressEmail) {
        this.addressEmail = addressEmail;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductShipmentAddress that = (ProductShipmentAddress) o;

        if (address_id != null ? !address_id.equals(that.address_id) : that.address_id != null) return false;
        if (addressName != null ? !addressName.equals(that.addressName) : that.addressName != null) return false;
        if (addressCompany != null ? !addressCompany.equals(that.addressCompany) : that.addressCompany != null)
            return false;
        if (addressStreet1 != null ? !addressStreet1.equals(that.addressStreet1) : that.addressStreet1 != null)
            return false;
        if (addressStreet2 != null ? !addressStreet2.equals(that.addressStreet2) : that.addressStreet2 != null)
            return false;
        if (addressStreetNo != null ? !addressStreetNo.equals(that.addressStreetNo) : that.addressStreetNo != null)
            return false;
        if (addressCity != null ? !addressCity.equals(that.addressCity) : that.addressCity != null) return false;
        if (addressZip != null ? !addressZip.equals(that.addressZip) : that.addressZip != null) return false;
        if (addressCountry != null ? !addressCountry.equals(that.addressCountry) : that.addressCountry != null)
            return false;
        if (addressEmail != null ? !addressEmail.equals(that.addressEmail) : that.addressEmail != null) return false;
        return addressState != null ? addressState.equals(that.addressState) : that.addressState == null;

    }

    @Override
    public int hashCode() {
        int result = address_id != null ? address_id.hashCode() : 0;
        result = 31 * result + (addressName != null ? addressName.hashCode() : 0);
        result = 31 * result + (addressCompany != null ? addressCompany.hashCode() : 0);
        result = 31 * result + (addressStreet1 != null ? addressStreet1.hashCode() : 0);
        result = 31 * result + (addressStreet2 != null ? addressStreet2.hashCode() : 0);
        result = 31 * result + (addressStreetNo != null ? addressStreetNo.hashCode() : 0);
        result = 31 * result + (addressCity != null ? addressCity.hashCode() : 0);
        result = 31 * result + (addressZip != null ? addressZip.hashCode() : 0);
        result = 31 * result + (addressCountry != null ? addressCountry.hashCode() : 0);
        result = 31 * result + (addressEmail != null ? addressEmail.hashCode() : 0);
        result = 31 * result + (addressState != null ? addressState.hashCode() : 0);
        return result;
    }

    public String getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

}
