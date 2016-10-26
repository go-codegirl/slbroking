package com.telosws.broking.beans;

import java.io.Serializable;
/**
 * 
 * @author hkalepu
 *
 */

import com.telosws.orm.tables.pojos.Address;
import com.telosws.orm.tables.pojos.PersonalDetails;
public class PersonalDetailsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private PersonalDetails personalDetails;
	private Address address;
	public PersonalDetails getPersonalDetails() {
		return personalDetails;
	}
	public void setPersonalDetails(PersonalDetails personalDetails) {
		this.personalDetails = personalDetails;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
