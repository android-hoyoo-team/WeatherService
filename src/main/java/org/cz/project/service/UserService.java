package org.cz.project.service;

import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.Users;


public interface UserService {

	Users save( Users u);
	void delete( Users u);
	void update( Users u);
	Users findByName(String name);
	Users findById(int id);
	void updataStatus(Users u,int status);
	Users findByName(String name,String password);
	QueryResult<Users> findAll(int page, int rows, String sort, String order);
	Users findByPhoneNum(String phoneNum);
	Users findByOauthToken(String oauthToken);
}
