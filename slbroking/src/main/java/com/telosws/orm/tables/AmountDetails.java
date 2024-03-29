/**
 * This class is generated by jOOQ
 */
package com.telosws.orm.tables;


import com.telosws.orm.Keys;
import com.telosws.orm.Slbroking;
import com.telosws.orm.tables.records.AmountDetailsRecord;

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
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AmountDetails extends TableImpl<AmountDetailsRecord> {

	private static final long serialVersionUID = -618465342;

	/**
	 * The reference instance of <code>slbroking.amount_details</code>
	 */
	public static final AmountDetails AMOUNT_DETAILS = new AmountDetails();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<AmountDetailsRecord> getRecordType() {
		return AmountDetailsRecord.class;
	}

	/**
	 * The column <code>slbroking.amount_details.Amount_ID</code>.
	 */
	public final TableField<AmountDetailsRecord, Integer> AMOUNT_ID = createField("Amount_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>slbroking.amount_details.Gross_Premium_Amount</code>.
	 */
	public final TableField<AmountDetailsRecord, Double> GROSS_PREMIUM_AMOUNT = createField("Gross_Premium_Amount", org.jooq.impl.SQLDataType.DOUBLE.nullable(false), this, "");

	/**
	 * The column <code>slbroking.amount_details.Terrorism_Premium_Amount</code>.
	 */
	public final TableField<AmountDetailsRecord, Double> TERRORISM_PREMIUM_AMOUNT = createField("Terrorism_Premium_Amount", org.jooq.impl.SQLDataType.DOUBLE.nullable(false), this, "");

	/**
	 * The column <code>slbroking.amount_details.Service_Tax</code>.
	 */
	public final TableField<AmountDetailsRecord, Double> SERVICE_TAX = createField("Service_Tax", org.jooq.impl.SQLDataType.DOUBLE.nullable(false), this, "");

	/**
	 * The column <code>slbroking.amount_details.Service_Tax_Amount</code>.
	 */
	public final TableField<AmountDetailsRecord, Double> SERVICE_TAX_AMOUNT = createField("Service_Tax_Amount", org.jooq.impl.SQLDataType.DOUBLE.nullable(false), this, "");

	/**
	 * The column <code>slbroking.amount_details.Net_Premium_Amount</code>.
	 */
	public final TableField<AmountDetailsRecord, Double> NET_PREMIUM_AMOUNT = createField("Net_Premium_Amount", org.jooq.impl.SQLDataType.DOUBLE.nullable(false), this, "");

	/**
	 * The column <code>slbroking.amount_details.Comission_Rate</code>.
	 */
	public final TableField<AmountDetailsRecord, Double> COMISSION_RATE = createField("Comission_Rate", org.jooq.impl.SQLDataType.DOUBLE.nullable(false), this, "");

	/**
	 * The column <code>slbroking.amount_details.Comission_Rate_Amount</code>.
	 */
	public final TableField<AmountDetailsRecord, Double> COMISSION_RATE_AMOUNT = createField("Comission_Rate_Amount", org.jooq.impl.SQLDataType.DOUBLE.nullable(false), this, "");

	/**
	 * The column <code>slbroking.amount_details.Collection_Date</code>.
	 */
	public final TableField<AmountDetailsRecord, Date> COLLECTION_DATE = createField("Collection_Date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

	/**
	 * The column <code>slbroking.amount_details.Personal_Detail_ID</code>.
	 */
	public final TableField<AmountDetailsRecord, Integer> PERSONAL_DETAIL_ID = createField("Personal_Detail_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>slbroking.amount_details.Policy_Number</code>.
	 */
	public final TableField<AmountDetailsRecord, String> POLICY_NUMBER = createField("Policy_Number", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * Create a <code>slbroking.amount_details</code> table reference
	 */
	public AmountDetails() {
		this("amount_details", null);
	}

	/**
	 * Create an aliased <code>slbroking.amount_details</code> table reference
	 */
	public AmountDetails(String alias) {
		this(alias, AMOUNT_DETAILS);
	}

	private AmountDetails(String alias, Table<AmountDetailsRecord> aliased) {
		this(alias, aliased, null);
	}

	private AmountDetails(String alias, Table<AmountDetailsRecord> aliased, Field<?>[] parameters) {
		super(alias, Slbroking.SLBROKING, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<AmountDetailsRecord, Integer> getIdentity() {
		return Keys.IDENTITY_AMOUNT_DETAILS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<AmountDetailsRecord> getPrimaryKey() {
		return Keys.KEY_AMOUNT_DETAILS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<AmountDetailsRecord>> getKeys() {
		return Arrays.<UniqueKey<AmountDetailsRecord>>asList(Keys.KEY_AMOUNT_DETAILS_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<AmountDetailsRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<AmountDetailsRecord, ?>>asList(Keys.PER_DETAILS_ID, Keys.POLICY_NUM_FK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AmountDetails as(String alias) {
		return new AmountDetails(alias, this);
	}

	/**
	 * Rename this table
	 */
	public AmountDetails rename(String name) {
		return new AmountDetails(name, null);
	}
}
