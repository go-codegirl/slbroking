/**
 * This class is generated by jOOQ
 */
package com.telosws.orm.tables.records;


import com.telosws.orm.tables.Address;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * it holds the address details of insureds
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AddressRecord extends UpdatableRecordImpl<AddressRecord> implements Record8<Integer, String, String, String, String, String, String, String> {

	private static final long serialVersionUID = 696784227;

	/**
	 * Setter for <code>slbroking.address.Address_ID</code>.
	 */
	public void setAddressId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>slbroking.address.Address_ID</code>.
	 */
	public Integer getAddressId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>slbroking.address.Address</code>.
	 */
	public void setAddress(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>slbroking.address.Address</code>.
	 */
	public String getAddress() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>slbroking.address.Address_Line_1</code>.
	 */
	public void setAddressLine_1(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>slbroking.address.Address_Line_1</code>.
	 */
	public String getAddressLine_1() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>slbroking.address.Address_Line_2</code>.
	 */
	public void setAddressLine_2(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>slbroking.address.Address_Line_2</code>.
	 */
	public String getAddressLine_2() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>slbroking.address.City</code>.
	 */
	public void setCity(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>slbroking.address.City</code>.
	 */
	public String getCity() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>slbroking.address.State</code>.
	 */
	public void setState(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>slbroking.address.State</code>.
	 */
	public String getState() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>slbroking.address.Zip_Postal_Code</code>.
	 */
	public void setZipPostalCode(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>slbroking.address.Zip_Postal_Code</code>.
	 */
	public String getZipPostalCode() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>slbroking.address.Country</code>.
	 */
	public void setCountry(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>slbroking.address.Country</code>.
	 */
	public String getCountry() {
		return (String) getValue(7);
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
	// Record8 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<Integer, String, String, String, String, String, String, String> fieldsRow() {
		return (Row8) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<Integer, String, String, String, String, String, String, String> valuesRow() {
		return (Row8) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Address.ADDRESS.ADDRESS_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Address.ADDRESS.ADDRESS_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Address.ADDRESS.ADDRESS_LINE_1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return Address.ADDRESS.ADDRESS_LINE_2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Address.ADDRESS.CITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return Address.ADDRESS.STATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return Address.ADDRESS.ZIP_POSTAL_CODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field8() {
		return Address.ADDRESS.COUNTRY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getAddressId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getAddress();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getAddressLine_1();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getAddressLine_2();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getCity();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getState();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getZipPostalCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value8() {
		return getCountry();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AddressRecord value1(Integer value) {
		setAddressId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AddressRecord value2(String value) {
		setAddress(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AddressRecord value3(String value) {
		setAddressLine_1(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AddressRecord value4(String value) {
		setAddressLine_2(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AddressRecord value5(String value) {
		setCity(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AddressRecord value6(String value) {
		setState(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AddressRecord value7(String value) {
		setZipPostalCode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AddressRecord value8(String value) {
		setCountry(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AddressRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached AddressRecord
	 */
	public AddressRecord() {
		super(Address.ADDRESS);
	}

	/**
	 * Create a detached, initialised AddressRecord
	 */
	public AddressRecord(Integer addressId, String address, String addressLine_1, String addressLine_2, String city, String state, String zipPostalCode, String country) {
		super(Address.ADDRESS);

		setValue(0, addressId);
		setValue(1, address);
		setValue(2, addressLine_1);
		setValue(3, addressLine_2);
		setValue(4, city);
		setValue(5, state);
		setValue(6, zipPostalCode);
		setValue(7, country);
	}
}
