
/**
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 *
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductShipmentAddress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2629825243772253526L;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressCity == null) ? 0 : addressCity.hashCode());
		result = prime * result + ((addressCompany == null) ? 0 : addressCompany.hashCode());
		result = prime * result + ((addressCountry == null) ? 0 : addressCountry.hashCode());
		result = prime * result + ((addressEmail == null) ? 0 : addressEmail.hashCode());
		result = prime * result + ((addressName == null) ? 0 : addressName.hashCode());
		result = prime * result + ((addressState == null) ? 0 : addressState.hashCode());
		result = prime * result + ((addressStreet1 == null) ? 0 : addressStreet1.hashCode());
		result = prime * result + ((addressStreet2 == null) ? 0 : addressStreet2.hashCode());
		result = prime * result + ((addressStreetNo == null) ? 0 : addressStreetNo.hashCode());
		result = prime * result + ((addressZip == null) ? 0 : addressZip.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductShipmentAddress))
			return false;
		ProductShipmentAddress other = (ProductShipmentAddress) obj;
		if (addressCity == null) {
			if (other.addressCity != null)
				return false;
		} else if (!addressCity.equals(other.addressCity))
			return false;
		if (addressCompany == null) {
			if (other.addressCompany != null)
				return false;
		} else if (!addressCompany.equals(other.addressCompany))
			return false;
		if (addressCountry == null) {
			if (other.addressCountry != null)
				return false;
		} else if (!addressCountry.equals(other.addressCountry))
			return false;
		if (addressEmail == null) {
			if (other.addressEmail != null)
				return false;
		} else if (!addressEmail.equals(other.addressEmail))
			return false;
		if (addressName == null) {
			if (other.addressName != null)
				return false;
		} else if (!addressName.equals(other.addressName))
			return false;
		if (addressState == null) {
			if (other.addressState != null)
				return false;
		} else if (!addressState.equals(other.addressState))
			return false;
		if (addressStreet1 == null) {
			if (other.addressStreet1 != null)
				return false;
		} else if (!addressStreet1.equals(other.addressStreet1))
			return false;
		if (addressStreet2 == null) {
			if (other.addressStreet2 != null)
				return false;
		} else if (!addressStreet2.equals(other.addressStreet2))
			return false;
		if (addressStreetNo == null) {
			if (other.addressStreetNo != null)
				return false;
		} else if (!addressStreetNo.equals(other.addressStreetNo))
			return false;
		if (addressZip == null) {
			if (other.addressZip != null)
				return false;
		} else if (!addressZip.equals(other.addressZip))
			return false;
		return true;
	}

	public String getAddressZip() {
		return addressZip;
	}

	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}

}
