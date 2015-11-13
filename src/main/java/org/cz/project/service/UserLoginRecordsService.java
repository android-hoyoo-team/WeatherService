package org.cz.project.service;

import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.Users;
import org.cz.project.entity.table.UsersLoginRecords;


public interface UserLoginRecordsService {

	UsersLoginRecords save( UsersLoginRecords u);
	void delete( UsersLoginRecords u);
	void update( UsersLoginRecords u);
//	Users findByName(String name);
	UsersLoginRecords findById(int id);
	/**
	 * 查找用户最后的登陆信息
	 * @param userId
	 * @return
	 */
	UsersLoginRecords findLastByUserId(int userId);
	/**
	 * 查找用户昨天最后的登陆的记录
	 * @param userId
	 * @return
	 */
	UsersLoginRecords findLastYesterdayByUserId(int userId);
	
//	void updataStatus(Users u,int status);
//	Users findByName(String name,String password);
	QueryResult<UsersLoginRecords> findAll(int page, int rows, String sort, String order);
//	Users findByPhoneNum(String phoneNum);
	UsersLoginRecords findLastTodayByUserId(int userId);
}
