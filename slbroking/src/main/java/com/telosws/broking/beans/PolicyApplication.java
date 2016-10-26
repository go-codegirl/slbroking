/**
 * 
 */
package com.telosws.broking.beans;

import java.io.Serializable;

import com.telosws.orm.tables.pojos.Address;
import com.telosws.orm.tables.pojos.AmountDetails;
import com.telosws.orm.tables.pojos.DocumentsType;
import com.telosws.orm.tables.pojos.InsuranceComapnyDetails;
import com.telosws.orm.tables.pojos.PersonalDetails;
import com.telosws.orm.tables.pojos.PolicyDetails;
import com.telosws.orm.tables.pojos.PolicyDocuments;
import com.telosws.orm.tables.pojos.PolicyType;

/**
 * @author Harish Kalepu
 *
 */
public class PolicyApplication implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Address address;
	private PersonalDetails personalDetails;
	private InsuranceComapnyDetails insuranceComapnyDetails;
	private PolicyDetails policyDetails;
	private AmountDetails amountDetails;
	private PolicyDocuments policyDocuments;
	private DocumentsType documentsType;
	private PolicyType policyType;
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public PersonalDetails getPersonalDetails() {
		return personalDetails;
	}
	public void setPersonalDetails(PersonalDetails personalDetails) {
		this.personalDetails = personalDetails;
	}
	public InsuranceComapnyDetails getInsuranceComapnyDetails() {
		return insuranceComapnyDetails;
	}
	public void setInsuranceComapnyDetails(InsuranceComapnyDetails insuranceComapnyDetails) {
		this.insuranceComapnyDetails = insuranceComapnyDetails;
	}
	public PolicyDetails getPolicyDetails() {
		return policyDetails;
	}
	public void setPolicyDetails(PolicyDetails policyDetails) {
		this.policyDetails = policyDetails;
	}
	public AmountDetails getAmountDetails() {
		return amountDetails;
	}
	public void setAmountDetails(AmountDetails amountDetails) {
		this.amountDetails = amountDetails;
	}
	public PolicyDocuments getPolicyDocuments() {
		return policyDocuments;
	}
	public void setPolicyDocuments(PolicyDocuments policyDocuments) {
		this.policyDocuments = policyDocuments;
	}
	public DocumentsType getDocumentsType() {
		return documentsType;
	}
	public void setDocumentsType(DocumentsType documentsType) {
		this.documentsType = documentsType;
	}
	public PolicyType getPolicyType() {
		return policyType;
	}
	public void setPolicyType(PolicyType policyType) {
		this.policyType = policyType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
