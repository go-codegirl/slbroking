package com.telosws.broking.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.telosws.broking.beans.PersonalDetailsBean;
import com.telosws.broking.serviceI.NewPolicyServiceI;
import com.telosws.broking.util.GenericResponse;
import com.telosws.broking.util.GetProperties;
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

@CrossOrigin
@RestController
public class NewPolicyController {
	
	private final static Logger LOG = LoggerFactory.getLogger(NewPolicyController.class);
	
	@Autowired
	private NewPolicyServiceI newPolicyServiceI;
	
    private VelocityEngine velocityEngine;

	@Autowired
	private GetProperties getProperties;
	
	@Autowired
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	@RequestMapping(value = "/savePersonalDetails", method = RequestMethod.POST, consumes = "application/json")
	public GenericResponse savePersonalDetails(@RequestBody PersonalDetailsBean personalDetailsBean) {
		LOG.debug("********** savePersonalDetails(@RequestBody PersonalDetails personalDetails) Starting********");
		GenericResponse response = new GenericResponse();
		try {
			personalDetailsBean = newPolicyServiceI.savePersonalDeatils(personalDetailsBean);
			response.setStatus("Success");
			response.setResponseObject(personalDetailsBean);
		} catch(Exception ex) {
			ex.printStackTrace();
			LOG.error("Error while saving personal details", ex);
			response.setMsg("Error while saving personal details data.");
			response.setStatus("Failure");
		}
		LOG.debug("********** savePersonalDetails(@RequestBody PersonalDetails personalDetails) Ending********");
		return response;
	}
	
	@RequestMapping(value = "/saveInsuranceDetails", method = RequestMethod.POST, consumes = "application/json")
	public GenericResponse saveInsuranceDetails(@RequestBody InsuranceComapnyDetails insuranceComapnyDetails) {
		LOG.debug("********** saveInsuranceDetails(@RequestBody InsuranceComapnyDetails insuranceComapnyDetails) Starting********");
		GenericResponse response = new GenericResponse();
		try {
			insuranceComapnyDetails = newPolicyServiceI.saveInsuranceDetails(insuranceComapnyDetails);
			response.setStatus("Success");
			response.setResponseObject(insuranceComapnyDetails);
		} catch(Exception ex) {
			ex.printStackTrace();
			LOG.error("Error while saving personal details", ex);
			response.setMsg("Error while saving insurance company details data.");
			response.setStatus("Failure");
		}
		LOG.debug("********** saveInsuranceDetails(@RequestBody InsuranceComapnyDetails insuranceComapnyDetails) Ending********");
		return response;
	}
	
	@RequestMapping(value = "/savePolicyDetails", method = RequestMethod.POST, consumes = "application/json")
	public GenericResponse savePolicyDetails(@RequestBody PolicyDetails policyDetails) {
		LOG.debug("********** savePolicyDetails(@RequestBody PolicyDetails policyDetails) Starting********");
		GenericResponse response = new GenericResponse();
		try {
			policyDetails = newPolicyServiceI.savePolicyDetails(policyDetails);
			response.setStatus("Success");
			response.setResponseObject(policyDetails);
		} catch(Exception ex) {
			ex.printStackTrace();
			LOG.error("Error while saving personal details", ex);
			response.setMsg("Error while saving insurance company details data.");
			response.setStatus("Failure");
		}
		LOG.debug("********** savePolicyDetails(@RequestBody PolicyDetails policyDetails) Ending********");
		return response;
	}
	
	@RequestMapping(value = "/saveAmountDetails", method = RequestMethod.POST, consumes = "application/json")
	public GenericResponse saveAmountDetails(@RequestBody AmountDetails amountDetails) {
		LOG.debug("********** saveAmountDetails(@RequestBody AmountDetails amountDetails) Starting********");
		GenericResponse response = new GenericResponse();
		try {
			amountDetails = newPolicyServiceI.saveAmountDetails(amountDetails);
			response.setStatus("Success");
			response.setResponseObject(amountDetails);
		} catch(Exception ex) {
			ex.printStackTrace();
			LOG.error("Error while saving personal details", ex);
			response.setMsg("Error while saving insurance company details data.");
			response.setStatus("Failure");
		}
		LOG.debug("********** saveAmountDetails(@RequestBody AmountDetails amountDetails) Ending********");
		return response;
	}
	
	@RequestMapping(value = "/getPolicyTypes", method = RequestMethod.GET)
	public GenericResponse getPolicyTypes() {
		LOG.debug("********** getPolicyTypes() Starting********");
		GenericResponse response = new GenericResponse();
		try {
			List<PolicyType> policyTypeList = newPolicyServiceI.getPolicyTypes();
			response.setStatus("Success");
			response.setResponseObject(policyTypeList);
		} catch(Exception ex) {
			ex.printStackTrace();
			LOG.error("Error while saving personal details", ex);
			response.setMsg("Error while getting policy types data.");
			response.setStatus("Failure");
		}
		LOG.debug("********** getPolicyTypes() Ending********");
		return response;
	}
	
	@RequestMapping(value = "/uploadDocuments", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public GenericResponse uploadDocuments(@RequestBody PolicyDocuments policyDocuments) {
		GenericResponse res = new GenericResponse();;
		try {
			int result = newPolicyServiceI.uploadDocuments(policyDocuments);
			if(result != 0) {
				res.setStatus("success");
				res.setResponseObject(result);
			}
			else {
				res.setStatus("unsuccess");
			}
		} catch (Exception ex) {
			LOG.error("Exception when adding or updating Vault", ex);
			res.setStatus("error");
			res.setMsg(ex.getMessage());
		}
		return res;
	}
	
	@RequestMapping(value = "/fetchDocuments", method = RequestMethod.GET )
	@ResponseBody
	public GenericResponse fetchDocuments(@RequestParam(value="docType") String docType,@RequestParam(value="personId") Integer personId) {
		GenericResponse res = new GenericResponse();;
		try {
			List<PolicyDocuments> policyDocuments = new ArrayList<PolicyDocuments>();
			policyDocuments = newPolicyServiceI.fetchDocuments(docType,personId);
			if(policyDocuments.size() > 0) {
				res.setStatus("success");
				res.setResponseObject(policyDocuments);
			} else {
				res.setStatus("unsuccess");
				res.setResponseObject(policyDocuments);
			}
		} catch (Exception ex) {
			LOG.error("Exception when adding or updating Vault", ex);
			res.setStatus("error");
			res.setMsg(ex.getMessage());
		}
		return res;
	}
}
