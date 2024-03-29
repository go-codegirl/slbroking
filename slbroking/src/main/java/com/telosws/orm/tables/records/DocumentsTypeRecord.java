/**
 * This class is generated by jOOQ
 */
package com.telosws.orm.tables.records;


import com.telosws.orm.tables.DocumentsType;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * it holds doc types
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DocumentsTypeRecord extends UpdatableRecordImpl<DocumentsTypeRecord> implements Record2<Integer, String> {

	private static final long serialVersionUID = 1286756945;

	/**
	 * Setter for <code>slbroking.documents_type.Doc_Type_ID</code>.
	 */
	public void setDocTypeId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>slbroking.documents_type.Doc_Type_ID</code>.
	 */
	public Integer getDocTypeId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>slbroking.documents_type.Doc_Type_Name</code>.
	 */
	public void setDocTypeName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>slbroking.documents_type.Doc_Type_Name</code>.
	 */
	public String getDocTypeName() {
		return (String) getValue(1);
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
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, String> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, String> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return DocumentsType.DOCUMENTS_TYPE.DOC_TYPE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return DocumentsType.DOCUMENTS_TYPE.DOC_TYPE_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getDocTypeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getDocTypeName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocumentsTypeRecord value1(Integer value) {
		setDocTypeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocumentsTypeRecord value2(String value) {
		setDocTypeName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocumentsTypeRecord values(Integer value1, String value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached DocumentsTypeRecord
	 */
	public DocumentsTypeRecord() {
		super(DocumentsType.DOCUMENTS_TYPE);
	}

	/**
	 * Create a detached, initialised DocumentsTypeRecord
	 */
	public DocumentsTypeRecord(Integer docTypeId, String docTypeName) {
		super(DocumentsType.DOCUMENTS_TYPE);

		setValue(0, docTypeId);
		setValue(1, docTypeName);
	}
}
