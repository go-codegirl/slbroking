/**
 * This class is generated by jOOQ
 */
package com.telosws.orm.tables;


import com.telosws.orm.Keys;
import com.telosws.orm.Slbroking;
import com.telosws.orm.tables.records.OnlineusersRecord;

import java.sql.Timestamp;
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
public class Onlineusers extends TableImpl<OnlineusersRecord> {

	private static final long serialVersionUID = -1775830482;

	/**
	 * The reference instance of <code>slbroking.onlineusers</code>
	 */
	public static final Onlineusers ONLINEUSERS = new Onlineusers();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<OnlineusersRecord> getRecordType() {
		return OnlineusersRecord.class;
	}

	/**
	 * The column <code>slbroking.onlineusers.ID</code>. The PRIMARY AUTO INCREMENT identifier
	 */
	public final TableField<OnlineusersRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "The PRIMARY AUTO INCREMENT identifier");

	/**
	 * The column <code>slbroking.onlineusers.PRIMARY_EMAIL</code>. The primary email address of this registred user
	 */
	public final TableField<OnlineusersRecord, String> PRIMARY_EMAIL = createField("PRIMARY_EMAIL", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "The primary email address of this registred user");

	/**
	 * The column <code>slbroking.onlineusers.LOGINTIME</code>. The datetime when record was created
	 */
	public final TableField<OnlineusersRecord, Timestamp> LOGINTIME = createField("LOGINTIME", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "The datetime when record was created");

	/**
	 * Create a <code>slbroking.onlineusers</code> table reference
	 */
	public Onlineusers() {
		this("onlineusers", null);
	}

	/**
	 * Create an aliased <code>slbroking.onlineusers</code> table reference
	 */
	public Onlineusers(String alias) {
		this(alias, ONLINEUSERS);
	}

	private Onlineusers(String alias, Table<OnlineusersRecord> aliased) {
		this(alias, aliased, null);
	}

	private Onlineusers(String alias, Table<OnlineusersRecord> aliased, Field<?>[] parameters) {
		super(alias, Slbroking.SLBROKING, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<OnlineusersRecord, Integer> getIdentity() {
		return Keys.IDENTITY_ONLINEUSERS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<OnlineusersRecord> getPrimaryKey() {
		return Keys.KEY_ONLINEUSERS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<OnlineusersRecord>> getKeys() {
		return Arrays.<UniqueKey<OnlineusersRecord>>asList(Keys.KEY_ONLINEUSERS_PRIMARY, Keys.KEY_ONLINEUSERS_PRIMARY_EMAIL_UNIQUE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Onlineusers as(String alias) {
		return new Onlineusers(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Onlineusers rename(String name) {
		return new Onlineusers(name, null);
	}
}
