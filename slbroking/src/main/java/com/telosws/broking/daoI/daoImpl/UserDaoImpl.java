package com.telosws.broking.daoI.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.telosws.broking.daoI.UserDaoI;
import com.telosws.orm.tables.pojos.Onlineusers;
import com.telosws.orm.tables.pojos.User;
import com.telosws.orm.tables.records.OnlineusersRecord;
import com.telosws.orm.tables.records.UserRecord;

/**
 * 
 * @author Harish Kalepu
 *
 */
@Repository
public class UserDaoImpl implements UserDaoI{
		
	@Autowired
	DSLContext create;
	
	@Override
	public User searchByEmail(String emailAddress) throws Exception {
		User user = create.selectFrom(com.telosws.orm.tables.User.USER).where(com.telosws.orm.tables.User.USER.PRIMARY_EMAIL.eq(emailAddress))
					.fetchOne().into(com.telosws.orm.tables.pojos.User.class);
		return  user;
	}
	
	@Override
	public boolean saveLoggedinUser(Onlineusers onlineUsers) throws Exception {
		OnlineusersRecord record = create.newRecord(com.telosws.orm.tables.Onlineusers.ONLINEUSERS, onlineUsers);
		record.store();
		return record.getId()>0?true:false;
	}

	@Override
	public List<Onlineusers> getAllOnlineUsers() throws Exception {
		Result<Record> userRecord = create.select().from(com.telosws.orm.tables.Onlineusers.ONLINEUSERS).fetch();
		List<Onlineusers> userLookups = new ArrayList<Onlineusers>();
		for (Record r : userRecord) {
			OnlineusersRecord records = (OnlineusersRecord) r;
			Onlineusers lookup = new Onlineusers();
			lookup.setPrimaryEmail(records.getPrimaryEmail());
			lookup.setId(records.getId());
			lookup.setLogintime(records.getLogintime());
			userLookups.add(lookup);
		}
		System.out.println(userLookups);
		return userLookups;
	}

	@Override
	public boolean deleteOnlineUser(Onlineusers onlineusers) throws Exception {
		OnlineusersRecord record = create.fetchOne(com.telosws.orm.tables.Onlineusers.ONLINEUSERS, com.telosws.orm.tables.Onlineusers.ONLINEUSERS.PRIMARY_EMAIL.eq(onlineusers.getPrimaryEmail()));
		return record != null ? record.delete()>0?true:false : false;
	}

	@Override
	public boolean removeOnlineUsers(List<Onlineusers> onlineusers) throws Exception {
		try{
			for(Onlineusers onlineuser : onlineusers){
				OnlineusersRecord record = create.fetchOne(com.telosws.orm.tables.Onlineusers.ONLINEUSERS, com.telosws.orm.tables.Onlineusers.ONLINEUSERS.ID.eq(onlineuser.getId()));
				record.delete();
			}
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	@Override
	public int updateUser(User user) throws Exception {
		try {
		    UserRecord record = create.fetchOne(com.telosws.orm.tables.User.USER, com.telosws.orm.tables.User.USER.ID.eq(user.getId()));
		    record.setFirstName(user.getFirstName());
		    record.setMiddleName(user.getMiddleName());
		    record.setLastName(user.getLastName());
		    record.setPrimaryEmail(user.getPrimaryEmail());
		    record.setPrimaryPhone(user.getPrimaryPhone());
		    record.setPassword(user.getPassword());
		    record.setStatus(user.getStatus());
			record.setComments(user.getComments());
			return record.update();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
}
