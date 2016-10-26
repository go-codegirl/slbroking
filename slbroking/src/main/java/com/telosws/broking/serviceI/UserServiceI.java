package com.telosws.broking.serviceI;

import java.util.List;

import com.telosws.orm.tables.pojos.Onlineusers;
import com.telosws.orm.tables.pojos.User;

/**
 * 
 * @author Harish Kalepu
 *
 */
public interface UserServiceI {
	public User fetchUserByEmail(String emailAddress) throws Exception;
	public boolean saveLoggedinUser(Onlineusers onlineUsers) throws Exception;
	public List<Onlineusers> getAllOnlineUsers() throws Exception;
	public boolean deleteOnlineUser(Onlineusers onlineusers) throws Exception;
	public boolean removeOnlineUsers() throws Exception;
	public int updateUser(User user) throws Exception;
}
