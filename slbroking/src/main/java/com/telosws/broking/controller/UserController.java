package com.telosws.broking.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telosws.broking.serviceI.UserServiceI;
import com.telosws.broking.util.Encryptor;
import com.telosws.broking.util.GenericResponse;
import com.telosws.broking.util.GetProperties;
import com.telosws.orm.tables.pojos.Onlineusers;
import com.telosws.orm.tables.pojos.User;

/**
 * 
 * @author Harish Kalepu
 *
 */
@CrossOrigin
@RestController
public class UserController {
	
	private final static Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserServiceI userServiceI;
	
    private VelocityEngine velocityEngine;

	@Autowired
	private GetProperties getProperties;
	
	@Autowired
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = "application/json")
	public GenericResponse authenticate(@RequestBody User user) {
		GenericResponse res = new GenericResponse();
		try {
			User userFetched = userServiceI.fetchUserByEmail(user.getPrimaryEmail());
			if (!(userFetched == null || userFetched.getId() == null || userFetched.getId().intValue() <= 0)) {
				if (Encryptor.decrypt(null, null, userFetched.getPassword()).equals(user.getPassword())) {
					Onlineusers onlineUsers = new Onlineusers();
					onlineUsers.setPrimaryEmail(user.getPrimaryEmail());
					Date date = new Date();
					onlineUsers.setLogintime(new Timestamp(date.getTime()));
					boolean flag = userServiceI.saveLoggedinUser(onlineUsers);
					res.setStatus("success");
					res.setMsg("Authentication Successful");
					res.setResponseObject(userFetched);
				} else {
					res.setStatus("error");
					res.setMsg("Authentication Failed! The user Id and Password do not match.");
				}
			} else {
				res.setStatus("error");
				res.setMsg("User Not Found.");
			}
		} catch (Exception ex) {
			res.setStatus("error");
			res.setMsg("Invalid username or password");
			// res.setMsg(ex.getMessage());
		}
		return res;
	}
	
	@RequestMapping(value = "/getAllOnlineUsers", method = RequestMethod.GET)
	public List<Onlineusers> getAllOnlineUsers() {
		try {
			return userServiceI.getAllOnlineUsers();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/deleteOnlineUser", method = RequestMethod.POST, consumes = "application/json")
	public GenericResponse deleteOnlineUser(@RequestBody Onlineusers onlineusers) {
		GenericResponse genericResponse=new GenericResponse();
		try {

			userServiceI.deleteOnlineUser(onlineusers);
			genericResponse.setStatus("success");

		} catch (Exception ex) {


		}
		return genericResponse;
	}
	
	@RequestMapping(value = "/removeOnlineUsers", method = RequestMethod.GET)
	public GenericResponse removeOnlineUsers() {
		GenericResponse genericResponse=new GenericResponse();
		try {
			Boolean flag = userServiceI.removeOnlineUsers();
			genericResponse.setResponseObject(flag);
			genericResponse.setMsg("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return genericResponse;
	}
	
	@RequestMapping(value = "/userByEmail", method = RequestMethod.GET)
	public GenericResponse fetchUserByEmail(@RequestParam(value = "emailAddress") String emailAddress) {
		GenericResponse res = new GenericResponse();
		try {
			User user = userServiceI.fetchUserByEmail(emailAddress);
			if (user != null) {
				res.setStatus("success");
				res.setMsg("User Found.");
				res.setResponseObject(user);

			} else {
				res.setStatus("error");
				res.setMsg("User Not Found.");
			}
		} catch (Exception ex) {
			res.setStatus("error");
			res.setMsg(ex.getMessage());
		}
		return res;
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, consumes = "application/json")
	public GenericResponse updateUser(@RequestBody User user) {
		GenericResponse res = new GenericResponse();
		try {
			userServiceI.updateUser(user);
			res.setStatus("success");

		} catch (Exception ex) {

			res.setStatus("error");
			res.setMsg("Error while updating user profile.");
		}
		return res;
	}
}
