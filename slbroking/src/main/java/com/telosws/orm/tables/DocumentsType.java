/**
 * This class is generated by jOOQ
 */
package com.telosws.orm.tables;


import com.telosws.orm.Keys;
import com.telosws.orm.Slbroking;
import com.telosws.orm.tables.records.DocumentsTypeRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class DocumentsType extends TableImpl<DocumentsTypeRecord> {

	private static final long serialVersionUID = 2132524432;

	/**
	 * The reference instance of <code>slbroking.documents_type</code>
	 */
	public static final DocumentsType DOCUMENTS_TYPE = new DocumentsType();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<DocumentsTypeRecord> getRecordType() {
		return DocumentsTypeRecord.class;
	}

	/**
	 * The column <code>slbroking.documents_type.Doc_Type_ID</code>.
	 */
	public final TableField<DocumentsTypeRecord, Integer> DOC_TYPE_ID = createField("Doc_Type_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>slbroking.documents_type.Doc_Type_Name</code>.
	 */
	public final TableField<DocumentsTypeRecord, String> DOC_TYPE_NAME = createField("Doc_Type_Name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * Create a <code>slbroking.documents_type</code> table reference
	 */
	public DocumentsType() {
		this("documents_type", null);
	}

	/**
	 * Create an aliased <code>slbroking.documents_type</code> table reference
	 */
	public DocumentsType(String alias) {
		this(alias, DOCUMENTS_TYPE);
	}

	private DocumentsType(String alias, Table<DocumentsTypeRecord> aliased) {
		this(alias, aliased, null);
	}

	private DocumentsType(String alias, Table<DocumentsTypeRecord> aliased, Field<?>[] parameters) {
		super(alias, Slbroking.SLBROKING, aliased, parameters, "it holds doc types");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<DocumentsTypeRecord, Integer> getIdentity() {
		return Keys.IDENTITY_DOCUMENTS_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<DocumentsTypeRecord> getPrimaryKey() {
		return Keys.KEY_DOCUMENTS_TYPE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<DocumentsTypeRecord>> getKeys() {
		return Arrays.<UniqueKey<DocumentsTypeRecord>>asList(Keys.KEY_DOCUMENTS_TYPE_PRIMARY, Keys.KEY_DOCUMENTS_TYPE_DOC_TYPE_NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocumentsType as(String alias) {
		return new DocumentsType(alias, this);
	}

	/**
	 * Rename this table
	 */
	public DocumentsType rename(String name) {
		return new DocumentsType(name, null);
	}
}