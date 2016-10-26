/**
 * This class is generated by jOOQ
 */
package com.telosws.orm.tables;


import com.telosws.orm.Keys;
import com.telosws.orm.Slbroking;
import com.telosws.orm.tables.records.InsuranceComapnyDetailsRecord;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class InsuranceComapnyDetails extends TableImpl<InsuranceComapnyDetailsRecord> {

	private static final long serialVersionUID = -1362974633;

	/**
	 * The reference instance of <code>slbroking.insurance_comapny_details</code>
	 */
	public static final InsuranceComapnyDetails INSURANCE_COMAPNY_DETAILS = new InsuranceComapnyDetails();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<InsuranceComapnyDetailsRecord> getRecordType() {
		return InsuranceComapnyDetailsRecord.class;
	}

	/**
	 * The column <code>slbroking.insurance_comapny_details.Insurance_Company_ID</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, Integer> INSURANCE_COMPANY_ID = createField("Insurance_Company_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>slbroking.insurance_comapny_details.Policy_Type</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, String> POLICY_TYPE = createField("Policy_Type", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>slbroking.insurance_comapny_details.Policy_Number</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, String> POLICY_NUMBER = createField("Policy_Number", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>slbroking.insurance_comapny_details.Office_Code</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, String> OFFICE_CODE = createField("Office_Code", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>slbroking.insurance_comapny_details.Agent</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, String> AGENT = createField("Agent", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>slbroking.insurance_comapny_details.Endorse_Number</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, String> ENDORSE_NUMBER = createField("Endorse_Number", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>slbroking.insurance_comapny_details.Policy_Start_Date</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, Date> POLICY_START_DATE = createField("Policy_Start_Date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

	/**
	 * The column <code>slbroking.insurance_comapny_details.Policy_End_Date</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, Date> POLICY_END_DATE = createField("Policy_End_Date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

	/**
	 * The column <code>slbroking.insurance_comapny_details.Insurance_company_name</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, String> INSURANCE_COMPANY_NAME = createField("Insurance_company_name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>slbroking.insurance_comapny_details.Insurance_branch_address</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, String> INSURANCE_BRANCH_ADDRESS = createField("Insurance_branch_address", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

	/**
	 * The column <code>slbroking.insurance_comapny_details.Reference</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, String> REFERENCE = createField("Reference", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>slbroking.insurance_comapny_details.Personal_Details_ID</code>.
	 */
	public final TableField<InsuranceComapnyDetailsRecord, Integer> PERSONAL_DETAILS_ID = createField("Personal_Details_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>slbroking.insurance_comapny_details</code> table reference
	 */
	public InsuranceComapnyDetails() {
		this("insurance_comapny_details", null);
	}

	/**
	 * Create an aliased <code>slbroking.insurance_comapny_details</code> table reference
	 */
	public InsuranceComapnyDetails(String alias) {
		this(alias, INSURANCE_COMAPNY_DETAILS);
	}

	private InsuranceComapnyDetails(String alias, Table<InsuranceComapnyDetailsRecord> aliased) {
		this(alias, aliased, null);
	}

	private InsuranceComapnyDetails(String alias, Table<InsuranceComapnyDetailsRecord> aliased, Field<?>[] parameters) {
		super(alias, Slbroking.SLBROKING, aliased, parameters, "its holds the details of insurance company details of insured policy");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<InsuranceComapnyDetailsRecord, Integer> getIdentity() {
		return Keys.IDENTITY_INSURANCE_COMAPNY_DETAILS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<InsuranceComapnyDetailsRecord> getPrimaryKey() {
		return Keys.KEY_INSURANCE_COMAPNY_DETAILS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<InsuranceComapnyDetailsRecord>> getKeys() {
		return Arrays.<UniqueKey<InsuranceComapnyDetailsRecord>>asList(Keys.KEY_INSURANCE_COMAPNY_DETAILS_PRIMARY, Keys.KEY_INSURANCE_COMAPNY_DETAILS_POLICY_NUMBER, Keys.KEY_INSURANCE_COMAPNY_DETAILS_ENDORSE_NUMBER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<InsuranceComapnyDetailsRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<InsuranceComapnyDetailsRecord, ?>>asList(Keys.PERSONAL_DETAILS_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InsuranceComapnyDetails as(String alias) {
		return new InsuranceComapnyDetails(alias, this);
	}

	/**
	 * Rename this table
	 */
	public InsuranceComapnyDetails rename(String name) {
		return new InsuranceComapnyDetails(name, null);
	}
}
