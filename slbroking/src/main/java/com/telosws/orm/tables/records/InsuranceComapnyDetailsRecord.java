/**
 * This class is generated by jOOQ
 */
package com.telosws.orm.tables.records;


import com.telosws.orm.tables.InsuranceComapnyDetails;

import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;


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
public class InsuranceComapnyDetailsRecord extends UpdatableRecordImpl<InsuranceComapnyDetailsRecord> implements Record12<Integer, String, String, String, String, String, Date, Date, String, String, String, Integer> {

	private static final long serialVersionUID = -1578077790;

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Insurance_Company_ID</code>.
	 */
	public void setInsuranceCompanyId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Insurance_Company_ID</code>.
	 */
	public Integer getInsuranceCompanyId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Policy_Type</code>.
	 */
	public void setPolicyType(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Policy_Type</code>.
	 */
	public String getPolicyType() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Policy_Number</code>.
	 */
	public void setPolicyNumber(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Policy_Number</code>.
	 */
	public String getPolicyNumber() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Office_Code</code>.
	 */
	public void setOfficeCode(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Office_Code</code>.
	 */
	public String getOfficeCode() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Agent</code>.
	 */
	public void setAgent(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Agent</code>.
	 */
	public String getAgent() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Endorse_Number</code>.
	 */
	public void setEndorseNumber(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Endorse_Number</code>.
	 */
	public String getEndorseNumber() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Policy_Start_Date</code>.
	 */
	public void setPolicyStartDate(Date value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Policy_Start_Date</code>.
	 */
	public Date getPolicyStartDate() {
		return (Date) getValue(6);
	}

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Policy_End_Date</code>.
	 */
	public void setPolicyEndDate(Date value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Policy_End_Date</code>.
	 */
	public Date getPolicyEndDate() {
		return (Date) getValue(7);
	}

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Insurance_company_name</code>.
	 */
	public void setInsuranceCompanyName(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Insurance_company_name</code>.
	 */
	public String getInsuranceCompanyName() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Insurance_branch_address</code>.
	 */
	public void setInsuranceBranchAddress(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Insurance_branch_address</code>.
	 */
	public String getInsuranceBranchAddress() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Reference</code>.
	 */
	public void setReference(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Reference</code>.
	 */
	public String getReference() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>slbroking.insurance_comapny_details.Personal_Details_ID</code>.
	 */
	public void setPersonalDetailsId(Integer value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>slbroking.insurance_comapny_details.Personal_Details_ID</code>.
	 */
	public Integer getPersonalDetailsId() {
		return (Integer) getValue(11);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record12 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row12<Integer, String, String, String, String, String, Date, Date, String, String, String, Integer> fieldsRow() {
		return (Row12) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row12<Integer, String, String, String, String, String, Date, Date, String, String, String, Integer> valuesRow() {
		return (Row12) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.INSURANCE_COMPANY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.POLICY_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.POLICY_NUMBER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.OFFICE_CODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.AGENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.ENDORSE_NUMBER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field7() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.POLICY_START_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field8() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.POLICY_END_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.INSURANCE_COMPANY_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.INSURANCE_BRANCH_ADDRESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field11() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.REFERENCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field12() {
		return InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS.PERSONAL_DETAILS_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getInsuranceCompanyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getPolicyType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getPolicyNumber();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getOfficeCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getAgent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getEndorseNumber();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value7() {
		return getPolicyStartDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value8() {
		return getPolicyEndDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getInsuranceCompanyName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getInsuranceBranchAddress();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value11() {
		return getReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value12() {
		return getPersonalDetailsId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value1(Integer value) {
		setInsuranceCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value2(String value) {
		setPolicyType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value3(String value) {
		setPolicyNumber(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value4(String value) {
		setOfficeCode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value5(String value) {
		setAgent(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value6(String value) {
		setEndorseNumber(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value7(Date value) {
		setPolicyStartDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value8(Date value) {
		setPolicyEndDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value9(String value) {
		setInsuranceCompanyName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value10(String value) {
		setInsuranceBranchAddress(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value11(String value) {
		setReference(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord value12(Integer value) {
		setPersonalDetailsId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetailsRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, Date value7, Date value8, String value9, String value10, String value11, Integer value12) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		value11(value11);
		value12(value12);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached InsuranceComapnyDetailsRecord
	 */
	public InsuranceComapnyDetailsRecord() {
		super(InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS);
	}

	/**
	 * Create a detached, initialised InsuranceComapnyDetailsRecord
	 */
	public InsuranceComapnyDetailsRecord(Integer insuranceCompanyId, String policyType, String policyNumber, String officeCode, String agent, String endorseNumber, Date policyStartDate, Date policyEndDate, String insuranceCompanyName, String insuranceBranchAddress, String reference, Integer personalDetailsId) {
		super(InsuranceComapnyDetails.INSURANCE_COMAPNY_DETAILS);

		setValue(0, insuranceCompanyId);
		setValue(1, policyType);
		setValue(2, policyNumber);
		setValue(3, officeCode);
		setValue(4, agent);
		setValue(5, endorseNumber);
		setValue(6, policyStartDate);
		setValue(7, policyEndDate);
		setValue(8, insuranceCompanyName);
		setValue(9, insuranceBranchAddress);
		setValue(10, reference);
		setValue(11, personalDetailsId);
	}
}
