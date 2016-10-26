/**
 * This class is generated by jOOQ
 */
package com.telosws.orm.tables.pojos;


import java.io.Serializable;
import java.sql.Date;

import javax.annotation.Generated;


/**
 * its holds the details of insurance company details of insured policy
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class InsuranceComapnyDetails implements Serializable {

	private static final long serialVersionUID = 1376452192;

	private Integer insuranceCompanyId;
	private String  policyType;
	private String  policyNumber;
	private String  officeCode;
	private String  agent;
	private String  endorseNumber;
	private Date    policyStartDate;
	private Date    policyEndDate;
	private String  insuranceCompanyName;
	private String  insuranceBranchAddress;
	private String  reference;
	private Integer personalDetailsId;

	public InsuranceComapnyDetails() {}

	public InsuranceComapnyDetails(InsuranceComapnyDetails value) {
		this.insuranceCompanyId = value.insuranceCompanyId;
		this.policyType = value.policyType;
		this.policyNumber = value.policyNumber;
		this.officeCode = value.officeCode;
		this.agent = value.agent;
		this.endorseNumber = value.endorseNumber;
		this.policyStartDate = value.policyStartDate;
		this.policyEndDate = value.policyEndDate;
		this.insuranceCompanyName = value.insuranceCompanyName;
		this.insuranceBranchAddress = value.insuranceBranchAddress;
		this.reference = value.reference;
		this.personalDetailsId = value.personalDetailsId;
	}

	public InsuranceComapnyDetails(
		Integer insuranceCompanyId,
		String  policyType,
		String  policyNumber,
		String  officeCode,
		String  agent,
		String  endorseNumber,
		Date    policyStartDate,
		Date    policyEndDate,
		String  insuranceCompanyName,
		String  insuranceBranchAddress,
		String  reference,
		Integer personalDetailsId
	) {
		this.insuranceCompanyId = insuranceCompanyId;
		this.policyType = policyType;
		this.policyNumber = policyNumber;
		this.officeCode = officeCode;
		this.agent = agent;
		this.endorseNumber = endorseNumber;
		this.policyStartDate = policyStartDate;
		this.policyEndDate = policyEndDate;
		this.insuranceCompanyName = insuranceCompanyName;
		this.insuranceBranchAddress = insuranceBranchAddress;
		this.reference = reference;
		this.personalDetailsId = personalDetailsId;
	}

	public Integer getInsuranceCompanyId() {
		return this.insuranceCompanyId;
	}

	public void setInsuranceCompanyId(Integer insuranceCompanyId) {
		this.insuranceCompanyId = insuranceCompanyId;
	}

	public String getPolicyType() {
		return this.policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getPolicyNumber() {
		return this.policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getOfficeCode() {
		return this.officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getAgent() {
		return this.agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getEndorseNumber() {
		return this.endorseNumber;
	}

	public void setEndorseNumber(String endorseNumber) {
		this.endorseNumber = endorseNumber;
	}

	public Date getPolicyStartDate() {
		return this.policyStartDate;
	}

	public void setPolicyStartDate(Date policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

	public Date getPolicyEndDate() {
		return this.policyEndDate;
	}

	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
	}

	public String getInsuranceCompanyName() {
		return this.insuranceCompanyName;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}

	public String getInsuranceBranchAddress() {
		return this.insuranceBranchAddress;
	}

	public void setInsuranceBranchAddress(String insuranceBranchAddress) {
		this.insuranceBranchAddress = insuranceBranchAddress;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Integer getPersonalDetailsId() {
		return this.personalDetailsId;
	}

	public void setPersonalDetailsId(Integer personalDetailsId) {
		this.personalDetailsId = personalDetailsId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("InsuranceComapnyDetails (");

		sb.append(insuranceCompanyId);
		sb.append(", ").append(policyType);
		sb.append(", ").append(policyNumber);
		sb.append(", ").append(officeCode);
		sb.append(", ").append(agent);
		sb.append(", ").append(endorseNumber);
		sb.append(", ").append(policyStartDate);
		sb.append(", ").append(policyEndDate);
		sb.append(", ").append(insuranceCompanyName);
		sb.append(", ").append(insuranceBranchAddress);
		sb.append(", ").append(reference);
		sb.append(", ").append(personalDetailsId);

		sb.append(")");
		return sb.toString();
	}
}
