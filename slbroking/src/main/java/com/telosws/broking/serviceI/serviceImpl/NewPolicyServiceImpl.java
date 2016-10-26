package com.telosws.broking.serviceI.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.telosws.broking.beans.PersonalDetailsBean;
import com.telosws.broking.daoI.NewPolicyDaoI;
import com.telosws.broking.serviceI.NewPolicyServiceI;
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
@Service
public class NewPolicyServiceImpl implements NewPolicyServiceI{
	
	@Autowired
	private NewPolicyDaoI newPolicyDaoI;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public PersonalDetailsBean savePersonalDeatils(PersonalDetailsBean personalDetailsBean) throws Exception {
		personalDetailsBean = newPolicyDaoI.savePersonalDeatils(personalDetailsBean);
		return personalDetailsBean;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public InsuranceComapnyDetails saveInsuranceDetails(InsuranceComapnyDetails insuranceComapnyDetails) throws Exception {
		insuranceComapnyDetails = newPolicyDaoI.saveInsuranceDetails(insuranceComapnyDetails);
		return insuranceComapnyDetails;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public PolicyDetails savePolicyDetails(PolicyDetails policyDetails) throws Exception {
		policyDetails = newPolicyDaoI.savePolicyDetails(policyDetails);
		return policyDetails;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public AmountDetails saveAmountDetails(AmountDetails amountDetails) throws Exception {
		amountDetails = newPolicyDaoI.saveAmountDetails(amountDetails);
		return amountDetails;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public List<PolicyType> getPolicyTypes() throws Exception {
		return newPolicyDaoI.getPolicyTypes();
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public int uploadDocuments(PolicyDocuments policyDocuments) throws Exception {
		return newPolicyDaoI.uploadDocuments(policyDocuments);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public List<PolicyDocuments> fetchDocuments(String docType,Integer personId) throws Exception {
		return newPolicyDaoI.fetchDocuments(docType,personId);
	}
}
