/**
 * This class is generated by jOOQ
 */
package com.telosws.orm.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


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
public class PolicyType implements Serializable {

	private static final long serialVersionUID = 970847480;

	private Integer policyTypeId;
	private String  policyTypeName;

	public PolicyType() {}

	public PolicyType(PolicyType value) {
		this.policyTypeId = value.policyTypeId;
		this.policyTypeName = value.policyTypeName;
	}

	public PolicyType(
		Integer policyTypeId,
		String  policyTypeName
	) {
		this.policyTypeId = policyTypeId;
		this.policyTypeName = policyTypeName;
	}

	public Integer getPolicyTypeId() {
		return this.policyTypeId;
	}

	public void setPolicyTypeId(Integer policyTypeId) {
		this.policyTypeId = policyTypeId;
	}

	public String getPolicyTypeName() {
		return this.policyTypeName;
	}

	public void setPolicyTypeName(String policyTypeName) {
		this.policyTypeName = policyTypeName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("PolicyType (");

		sb.append(policyTypeId);
		sb.append(", ").append(policyTypeName);

		sb.append(")");
		return sb.toString();
	}
}