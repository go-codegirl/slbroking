package com.telosws.broking.daoI;

import java.util.List;

import com.telosws.orm.tables.pojos.Onlineusers;
import com.telosws.orm.tables.pojos.User;

/**
 * 
 * @author Harish Kalepu
 *
 */

public interface UserDaoI {
	public User searchByEmail(String emailAddress) throws Exception;
	public boolean saveLoggedinUser(Onlineusers onlineUsers) throws Exception;
	public List<Onlineusers> getAllOnlineUsers() throws Exception;
	public boolean deleteOnlineUser(Onlineusers onlineusers)throws Exception;
	public boolean removeOnlineUsers(List<Onlineusers> onlineusers) throws Exception;
	public int updateUser(User user) throws Exception;
}
