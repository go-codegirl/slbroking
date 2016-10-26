package com.telosws.broking.serviceI.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.telosws.broking.daoI.UserDaoI;
import com.telosws.broking.serviceI.UserServiceI;
import com.telosws.orm.tables.pojos.Onlineusers;
import com.telosws.orm.tables.pojos.User;

/**
 * 
 * @author Harish Kalepu
 *
 */
@Service
public class UserServiceImpl implements UserServiceI{
	
	@Autowired
	private UserDaoI userDaoI;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public User fetchUserByEmail(String emailAddress) throws Exception {
		User user = userDaoI.searchByEmail(emailAddress);
		return user;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public boolean saveLoggedinUser(Onlineusers onlineUsers) throws Exception {
		boolean flag = userDaoI.saveLoggedinUser(onlineUsers);
		return flag;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public List<Onlineusers> getAllOnlineUsers() throws Exception {
		return userDaoI.getAllOnlineUsers();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public boolean deleteOnlineUser(Onlineusers onlineusers) throws Exception {
		boolean flag = userDaoI.deleteOnlineUser(onlineusers);
		return flag;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public boolean removeOnlineUsers() throws Exception {
		boolean flag=true;
		List<Onlineusers> onlineusers = userDaoI.getAllOnlineUsers();
		if(onlineusers != null && onlineusers.size()>0) {
			List<Onlineusers> users = new ArrayList<Onlineusers>();
			//getting current Timestamp
			Timestamp timestamp = new Timestamp(new Date().getTime());
			for(Onlineusers onlineuser : onlineusers){
				if((timestamp.getTime()-onlineuser.getLogintime().getTime())> 900000){
					users.add(onlineuser);
				}
			}
			flag = userDaoI.removeOnlineUsers(users);
		}
		return flag;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public int updateUser(User user) throws Exception {
		int status = userDaoI.updateUser(user);
		if(status < 1) {
			throw new Exception("User Update failed.");
		}
		return status;
	}
}
