package com.telosws.broking.beans;

import java.io.Serializable;

public class UploadRenewalsBean implements Serializable {

	private static final long serialVersionUID = 1L;
    private Integer SNO;
	
	private String Name;
	private String Department;
	private String RenewalSerialNumber;
	private Double Amount;
	private String Status;
	private Integer InsuredPhoneno;
	
	
	private String PolicyNo;
	private String InsurerCompany;
	private String OfficeCode;
	private String RenewalDate;
	private String RenewalCompany;
	private String RenewalOfficeCode;
	private Integer RenewalPolicyNumber;
	private String AgentName;
	
	
	public Integer getSNO() {
		return SNO;
	}

	public void setSNO(Integer sNO) {
		SNO = sNO;
	}


	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getRenewalSerialNumber() {
		return RenewalSerialNumber;
	}

	public void setRenewalSerialNumber(String renewalSerialNumber) {
		RenewalSerialNumber = renewalSerialNumber;
	}


	public Double getAmount() {
		return Amount;
	}

	public void setAmount(Double amount) {
		Amount = amount;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	public Integer getInsuredPhoneno() {
		return InsuredPhoneno;
	}

	public void setInsuredPhoneno(Integer insuredPhoneno) {
		InsuredPhoneno = insuredPhoneno;
	}

	

	public String getPolicyNo() {
		return PolicyNo;
	}

	public void setPolicyNo(String policyNo) {
		PolicyNo = policyNo;
	}

	public String getInsurerCompany() {
		return InsurerCompany;
	}

	public void setInsurerCompany(String insurerCompany) {
		InsurerCompany = insurerCompany;
	}

	public String getOfficeCode() {
		return OfficeCode;
	}

	public void setOfficeCode(String officeCode) {
		OfficeCode = officeCode;
	}

	public String getRenewalDate() {
		return RenewalDate;
	}

	public void setRenewalDate(String renewalDate) {
		RenewalDate = renewalDate;
	}

	public String getRenewalCompany() {
		return RenewalCompany;
	}

	public void setRenewalCompany(String renewalCompany) {
		RenewalCompany = renewalCompany;
	}

	public String getRenewalOfficeCode() {
		return RenewalOfficeCode;
	}

	public void setRenewalOfficeCode(String renewalOfficeCode) {
		RenewalOfficeCode = renewalOfficeCode;
	}

	public Integer getRenewalPolicyNumber() {
		return RenewalPolicyNumber;
	}

	public void setRenewalPolicyNumber(Integer renewalPolicyNumber) {
		RenewalPolicyNumber = renewalPolicyNumber;
	}

	public String getAgentName() {
		return AgentName;
	}

	public void setAgentName(String agentName) {
		AgentName = agentName;
	}


}
