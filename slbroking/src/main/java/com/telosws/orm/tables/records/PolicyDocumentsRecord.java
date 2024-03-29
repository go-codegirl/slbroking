/**
 * This class is generated by jOOQ
 */
package com.telosws.orm.tables.records;


import com.telosws.orm.tables.PolicyDocuments;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * it holds the documents of insureds related to policy
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PolicyDocumentsRecord extends UpdatableRecordImpl<PolicyDocumentsRecord> implements Record9<Integer, String, Integer, String, Timestamp, String, Integer, String, byte[]> {

	private static final long serialVersionUID = 1119357697;

	/**
	 * Setter for <code>slbroking.policy_documents.Document_ID</code>.
	 */
	public void setDocumentId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>slbroking.policy_documents.Document_ID</code>.
	 */
	public Integer getDocumentId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>slbroking.policy_documents.Document_Name</code>.
	 */
	public void setDocumentName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>slbroking.policy_documents.Document_Name</code>.
	 */
	public String getDocumentName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>slbroking.policy_documents.Doc_Type_ID</code>.
	 */
	public void setDocTypeId(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>slbroking.policy_documents.Doc_Type_ID</code>.
	 */
	public Integer getDocTypeId() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>slbroking.policy_documents.Comments</code>.
	 */
	public void setComments(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>slbroking.policy_documents.Comments</code>.
	 */
	public String getComments() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>slbroking.policy_documents.Uploaded_Date</code>.
	 */
	public void setUploadedDate(Timestamp value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>slbroking.policy_documents.Uploaded_Date</code>.
	 */
	public Timestamp getUploadedDate() {
		return (Timestamp) getValue(4);
	}

	/**
	 * Setter for <code>slbroking.policy_documents.Status</code>.
	 */
	public void setStatus(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>slbroking.policy_documents.Status</code>.
	 */
	public String getStatus() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>slbroking.policy_documents.Person_Det_ID</code>.
	 */
	public void setPersonDetId(Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>slbroking.policy_documents.Person_Det_ID</code>.
	 */
	public Integer getPersonDetId() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>slbroking.policy_documents.Policy_Number</code>.
	 */
	public void setPolicyNumber(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>slbroking.policy_documents.Policy_Number</code>.
	 */
	public String getPolicyNumber() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>slbroking.policy_documents.Document</code>.
	 */
	public void setDocument(byte[] value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>slbroking.policy_documents.Document</code>.
	 */
	public byte[] getDocument() {
		return (byte[]) getValue(8);
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
	// Record9 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row9<Integer, String, Integer, String, Timestamp, String, Integer, String, byte[]> fieldsRow() {
		return (Row9) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row9<Integer, String, Integer, String, Timestamp, String, Integer, String, byte[]> valuesRow() {
		return (Row9) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return PolicyDocuments.POLICY_DOCUMENTS.DOCUMENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return PolicyDocuments.POLICY_DOCUMENTS.DOCUMENT_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return PolicyDocuments.POLICY_DOCUMENTS.DOC_TYPE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return PolicyDocuments.POLICY_DOCUMENTS.COMMENTS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field5() {
		return PolicyDocuments.POLICY_DOCUMENTS.UPLOADED_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return PolicyDocuments.POLICY_DOCUMENTS.STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field7() {
		return PolicyDocuments.POLICY_DOCUMENTS.PERSON_DET_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field8() {
		return PolicyDocuments.POLICY_DOCUMENTS.POLICY_NUMBER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<byte[]> field9() {
		return PolicyDocuments.POLICY_DOCUMENTS.DOCUMENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getDocumentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getDocumentName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getDocTypeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getComments();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value5() {
		return getUploadedDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getPersonDetId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value8() {
		return getPolicyNumber();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] value9() {
		return getDocument();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PolicyDocumentsRecord value1(Integer value) {
		setDocumentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PolicyDocumentsRecord value2(String value) {
		setDocumentName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PolicyDocumentsRecord value3(Integer value) {
		setDocTypeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PolicyDocumentsRecord value4(String value) {
		setComments(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PolicyDocumentsRecord value5(Timestamp value) {
		setUploadedDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PolicyDocumentsRecord value6(String value) {
		setStatus(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PolicyDocumentsRecord value7(Integer value) {
		setPersonDetId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PolicyDocumentsRecord value8(String value) {
		setPolicyNumber(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PolicyDocumentsRecord value9(byte[] value) {
		setDocument(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PolicyDocumentsRecord values(Integer value1, String value2, Integer value3, String value4, Timestamp value5, String value6, Integer value7, String value8, byte[] value9) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PolicyDocumentsRecord
	 */
	public PolicyDocumentsRecord() {
		super(PolicyDocuments.POLICY_DOCUMENTS);
	}

	/**
	 * Create a detached, initialised PolicyDocumentsRecord
	 */
	public PolicyDocumentsRecord(Integer documentId, String documentName, Integer docTypeId, String comments, Timestamp uploadedDate, String status, Integer personDetId, String policyNumber, byte[] document) {
		super(PolicyDocuments.POLICY_DOCUMENTS);

		setValue(0, documentId);
		setValue(1, documentName);
		setValue(2, docTypeId);
		setValue(3, comments);
		setValue(4, uploadedDate);
		setValue(5, status);
		setValue(6, personDetId);
		setValue(7, policyNumber);
		setValue(8, document);
	}
}
