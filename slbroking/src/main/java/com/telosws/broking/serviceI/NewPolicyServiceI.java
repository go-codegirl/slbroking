package com.telosws.broking.serviceI;

import java.util.List;

import com.telosws.broking.beans.PersonalDetailsBean;
import com.telosws.orm.tables.pojos.AmountDetails;
import com.telosws.orm.tables.pojos.InsuranceComapnyDetails;
import com.telosws.orm.tables.pojos.PolicyDetails;
import com.telosws.orm.tables.pojos.PolicyDocuments;
import com.telosws.orm.tables.pojos.PolicyType;

/**
 * 
 * @author Harish Kalepu
 *
 */
public interface NewPolicyServiceI {
	public PersonalDetailsBean savePersonalDeatils(PersonalDetailsBean personalDetailsBean) throws Exception;
	public InsuranceComapnyDetails saveInsuranceDetails(InsuranceComapnyDetails insuranceComapnyDetails) throws Exception;
	public PolicyDetails savePolicyDetails(PolicyDetails policyDetails) throws Exception;
	public AmountDetails saveAmountDetails(AmountDetails amountDetails) throws Exception;
	public List<PolicyType> getPolicyTypes() throws Exception;
	public int uploadDocuments(PolicyDocuments policyDocuments) throws Exception;
	public List<PolicyDocuments> fetchDocuments(String docType,Integer personId) throws Exception;
}
