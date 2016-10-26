package com.telosws.broking.daoI.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.telosws.broking.beans.PersonalDetailsBean;
import com.telosws.broking.constants.SLBrokingConstants;
import com.telosws.broking.daoI.NewPolicyDaoI;
import com.telosws.orm.tables.pojos.Address;
import com.telosws.orm.tables.pojos.AmountDetails;
import com.telosws.orm.tables.pojos.InsuranceComapnyDetails;
import com.telosws.orm.tables.pojos.PersonalDetails;
import com.telosws.orm.tables.pojos.PolicyDetails;
import com.telosws.orm.tables.pojos.PolicyDocuments;
import com.telosws.orm.tables.pojos.PolicyType;
import com.telosws.orm.tables.records.AddressRecord;
import com.telosws.orm.tables.records.AmountDetailsRecord;
import com.telosws.orm.tables.records.InsuranceComapnyDetailsRecord;
import com.telosws.orm.tables.records.PersonalDetailsRecord;
import com.telosws.orm.tables.records.PolicyDetailsRecord;
import com.telosws.orm.tables.records.PolicyDocumentsRecord;
import com.telosws.orm.tables.records.PolicyTypeRecord;

/**
 * 
 * @author Harish Kalepu
 *
 */
@Repository
public class NewPolicyDaoImpl implements NewPolicyDaoI{
	
	@Autowired
	DSLContext create;
	
	@Override
	public PersonalDetailsBean savePersonalDeatils(PersonalDetailsBean personalDetailsBean) throws Exception {
		try{
			Address address = personalDetailsBean.getAddress();
			PersonalDetails personalDetails = personalDetailsBean.getPersonalDetails();
			AddressRecord addressRecord = create.newRecord(com.telosws.orm.tables.Address.ADDRESS, personalDetailsBean.getAddress());
			int result = addressRecord.store();
			if(result>0) {
				address.setAddressId(addressRecord.getAddressId());
				personalDetails.setAddressId(addressRecord.getAddressId());
				PersonalDetailsRecord personalDetailsRecord = create.newRecord(com.telosws.orm.tables.PersonalDetails.PERSONAL_DETAILS, personalDetails);
				result = personalDetailsRecord.store();
				if(result>0) {
					personalDetails.setPersonalId(personalDetailsRecord.getPersonalId());
					personalDetailsBean.setAddress(address);
					personalDetailsBean.setPersonalDetails(personalDetails);
					return personalDetailsBean;
				} else {
					throw new RuntimeException("Error while saving personal details");
				}
			} else {
				throw new RuntimeException("Error while saving Address details");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public InsuranceComapnyDetails saveInsuranceDetails(InsuranceComapnyDetails insuranceComapnyDetails) throws Exception {
		InsuranceComapnyDetailsRecord insuranceComapnyDetailsRecord = create.newRecord(com.telosws.orm.tables.InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS, insuranceComapnyDetails);
		int result = insuranceComapnyDetailsRecord.store();
		if(result>0)
			insuranceComapnyDetails.setInsuranceCompanyId(insuranceComapnyDetailsRecord.getInsuranceCompanyId());
		return insuranceComapnyDetails;
	}
	
	@Override
	public PolicyDetails savePolicyDetails(PolicyDetails policyDetails) throws Exception {
		PolicyDetailsRecord policyDetailsRecord = create.newRecord(com.telosws.orm.tables.PolicyDetails.POLICY_DETAILS, policyDetails);
		int result = policyDetailsRecord.store();
		if(result>0)
			policyDetails.setPolicyId(policyDetailsRecord.getPolicyId());
		return policyDetails;
	}
	
	@Override
	public AmountDetails saveAmountDetails(AmountDetails amountDetails) throws Exception {
		AmountDetailsRecord amountDetailsRecord = create.newRecord(com.telosws.orm.tables.AmountDetails.AMOUNT_DETAILS, amountDetails);
		int result = amountDetailsRecord.store();
		if(result>0)
			amountDetails.setAmountId(amountDetailsRecord.getAmountId());
		return amountDetails;
	}
	
	@Override
	public List<PolicyType> getPolicyTypes() throws Exception {
		Result<Record> policyTypeRecords = create.select().from(com.telosws.orm.tables.PolicyType.POLICY_TYPE).fetch();
		List<PolicyType> policyTypeList = new ArrayList<PolicyType>();
		for(Record record: policyTypeRecords){
			PolicyTypeRecord policyTypeRecord = (PolicyTypeRecord)record;
			PolicyType policyType = new PolicyType();
			policyType.setPolicyTypeId(policyTypeRecord.getPolicyTypeId());
			policyType.setPolicyTypeName(policyTypeRecord.getPolicyTypeName());
			policyTypeList.add(policyType);
		}
		return policyTypeList;
	}
	
	@Override
	public int uploadDocuments(PolicyDocuments policyDocuments) throws Exception {
		int documentId = 0;
		if (policyDocuments.getDocumentId() == null) {
			PolicyDocumentsRecord policyDocumentsRecord = create.newRecord(com.telosws.orm.tables.PolicyDocuments.POLICY_DOCUMENTS, policyDocuments);
			if (policyDocumentsRecord != null) {
				policyDocumentsRecord.store();
				documentId = policyDocumentsRecord.getDocumentId();
			}
			return documentId;
		}
		return 0;
	}

	@Override
	public List<PolicyDocuments> fetchDocuments(String docType,Integer personId) throws Exception {
		List<PolicyDocuments> policyDocumentsList =  new ArrayList<PolicyDocuments>();
		try{
			Integer docTypeId = null;
			if(docType.equals(SLBrokingConstants.POLICY_DOCUMENTS))
				docTypeId = 1;
			else if(docType.equals(SLBrokingConstants.CLAIMS_DOCUMENTS))
				docTypeId = 2;
			else if(docType.equals(SLBrokingConstants.ECARDS_DOCUMENTS))
				docTypeId = 3;
			else if(docType.equals(SLBrokingConstants.MANDATE_DOCUMENTS))
				docTypeId = 4;
			else if(docType.equals(SLBrokingConstants.POLICY_BROCHURE))
				docTypeId = 5;
			else if(docType.equals(SLBrokingConstants.OTHER_DOCUMENTS))
				docTypeId = 6;
			if(personId != null) {
				policyDocumentsList = create.selectFrom(com.telosws.orm.tables.PolicyDocuments.POLICY_DOCUMENTS).where(com.telosws.orm.tables.PolicyDocuments.POLICY_DOCUMENTS.DOC_TYPE_ID.eq(docTypeId))
						.and(com.telosws.orm.tables.PolicyDocuments.POLICY_DOCUMENTS.PERSON_DET_ID.eq(personId))
						.fetch().into(com.telosws.orm.tables.pojos.PolicyDocuments.class);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return policyDocumentsList;
	}
}
