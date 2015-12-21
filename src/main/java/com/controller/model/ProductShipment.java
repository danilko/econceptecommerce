
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
public class ProductShipment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5377478820801684991L;
	private String shipmentReferenceId;
	private String shipmentProvider;
	private String shipmentProviderImage75;
	private String shipmentProviderImage200;
	private String shipmentProviderServiceLevelName;
	private String shipmentProviderDurationTerms;

	private double shipmentAmount;
	private String shipmentAmountCurrency;

	private double shipmentInsuranceAmount;
	private String shipmentInsuranceAmountCurrency;

	public String getShipmentProvider() {
		return shipmentProvider;
	}

	public void setShipmentProvider(String shipmentProvider) {
		this.shipmentProvider = shipmentProvider;
	}

	public String getShipmentProviderImage75() {
		return shipmentProviderImage75;
	}

	public void setShipmentProviderImage75(String shipmentProviderImage75) {
		this.shipmentProviderImage75 = shipmentProviderImage75;
	}

	public String getShipmentProviderImage200() {
		return shipmentProviderImage200;
	}

	public void setShipmentProviderImage200(String shipmentProviderImage200) {
		this.shipmentProviderImage200 = shipmentProviderImage200;
	}

	public String getShipmentProviderServiceLevelName() {
		return shipmentProviderServiceLevelName;
	}

	public void setShipmentProviderServiceLevelName(String shipmentProviderServiceName) {
		this.shipmentProviderServiceLevelName = shipmentProviderServiceName;
	}

	public String getShipmentProviderDurationTerms() {
		return shipmentProviderDurationTerms;
	}

	public void setShipmentProviderDurationTerms(String shipmentProviderDurationTerms) {
		this.shipmentProviderDurationTerms = shipmentProviderDurationTerms;
	}

	public double getShipmentAmount() {
		return shipmentAmount;
	}

	public void setShipmentAmount(double shipmentAmount) {
		this.shipmentAmount = shipmentAmount;
	}

	public String getShipmentAmountCurrency() {
		return shipmentAmountCurrency;
	}

	public void setShipmentAmountCurrency(String shipmentAmountCurrency) {
		this.shipmentAmountCurrency = shipmentAmountCurrency;
	}

	public double getShipmentInsuranceAmount() {
		return shipmentInsuranceAmount;
	}

	public void setShipmentInsuranceAmount(double shipmentInsuranceAmount) {
		this.shipmentInsuranceAmount = shipmentInsuranceAmount;
	}

	public String getShipmentInsuranceAmountCurrency() {
		return shipmentInsuranceAmountCurrency;
	}

	public void setShipmentInsuranceAmountCurrency(String shipmentInsuranceAmountCurrency) {
		this.shipmentInsuranceAmountCurrency = shipmentInsuranceAmountCurrency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(shipmentAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((shipmentAmountCurrency == null) ? 0 : shipmentAmountCurrency.hashCode());
		temp = Double.doubleToLongBits(shipmentInsuranceAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((shipmentInsuranceAmountCurrency == null) ? 0 : shipmentInsuranceAmountCurrency.hashCode());
		result = prime * result + ((shipmentProvider == null) ? 0 : shipmentProvider.hashCode());
		result = prime * result
				+ ((shipmentProviderDurationTerms == null) ? 0 : shipmentProviderDurationTerms.hashCode());
		result = prime * result + ((shipmentProviderImage200 == null) ? 0 : shipmentProviderImage200.hashCode());
		result = prime * result + ((shipmentProviderImage75 == null) ? 0 : shipmentProviderImage75.hashCode());
		result = prime * result
				+ ((shipmentProviderServiceLevelName == null) ? 0 : shipmentProviderServiceLevelName.hashCode());
		result = prime * result + ((shipmentReferenceId == null) ? 0 : shipmentReferenceId.hashCode());
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
		if (!(obj instanceof ProductShipment)) {
			return false;
		}
		ProductShipment other = (ProductShipment) obj;
		if (Double.doubleToLongBits(shipmentAmount) != Double.doubleToLongBits(other.shipmentAmount)) {
			return false;
		}
		if (shipmentAmountCurrency == null) {
			if (other.shipmentAmountCurrency != null) {
				return false;
			}
		} else if (!shipmentAmountCurrency.equals(other.shipmentAmountCurrency)) {
			return false;
		}
		if (Double.doubleToLongBits(shipmentInsuranceAmount) != Double
				.doubleToLongBits(other.shipmentInsuranceAmount)) {
			return false;
		}
		if (shipmentInsuranceAmountCurrency == null) {
			if (other.shipmentInsuranceAmountCurrency != null) {
				return false;
			}
		} else if (!shipmentInsuranceAmountCurrency.equals(other.shipmentInsuranceAmountCurrency)) {
			return false;
		}
		if (shipmentProvider == null) {
			if (other.shipmentProvider != null) {
				return false;
			}
		} else if (!shipmentProvider.equals(other.shipmentProvider)) {
			return false;
		}
		if (shipmentProviderDurationTerms == null) {
			if (other.shipmentProviderDurationTerms != null) {
				return false;
			}
		} else if (!shipmentProviderDurationTerms.equals(other.shipmentProviderDurationTerms)) {
			return false;
		}
		if (shipmentProviderImage200 == null) {
			if (other.shipmentProviderImage200 != null) {
				return false;
			}
		} else if (!shipmentProviderImage200.equals(other.shipmentProviderImage200)) {
			return false;
		}
		if (shipmentProviderImage75 == null) {
			if (other.shipmentProviderImage75 != null) {
				return false;
			}
		} else if (!shipmentProviderImage75.equals(other.shipmentProviderImage75)) {
			return false;
		}
		if (shipmentProviderServiceLevelName == null) {
			if (other.shipmentProviderServiceLevelName != null) {
				return false;
			}
		} else if (!shipmentProviderServiceLevelName.equals(other.shipmentProviderServiceLevelName)) {
			return false;
		}
		if (shipmentReferenceId == null) {
			if (other.shipmentReferenceId != null) {
				return false;
			}
		} else if (!shipmentReferenceId.equals(other.shipmentReferenceId)) {
			return false;
		}
		return true;
	}

	public String getShipmentReferenceId() {
		return shipmentReferenceId;
	}

	public void setShipmentReferenceId(String shipmentReferenceId) {
		this.shipmentReferenceId = shipmentReferenceId;
	}
}
