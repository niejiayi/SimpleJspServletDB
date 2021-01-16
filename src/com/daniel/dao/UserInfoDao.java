package com.daniel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.daniel.model.UserInfo;
import com.daniel.util.DbUtil;

public class UserInfoDao {

	private Connection connection;

	public UserInfoDao() {
		connection = DbUtil.getConnection();
	}

	public void addUserInfo(UserInfo userInfo) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into userinfo(id,name,address,edu,sex,age) values (?, ?, ?, ?, ?, ? )");
			// Parameters start with 1
			int id = userInfo.getId();
			if(id == 0) {
				UUID uuid = UUID.randomUUID();
				String str = uuid.toString();
				String uuidStr = str.replace("-", "");
				id = Integer.parseInt(uuidStr);
			}
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, userInfo.getName());
			preparedStatement.setString(3, userInfo.getAddress());
			preparedStatement.setInt(4, userInfo.getEdu());
			preparedStatement.setInt(5, userInfo.getSex());
			preparedStatement.setInt(6, userInfo.getAge());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUser(int id) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from users where id=?");
			// Parameters start with 1
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateUser(UserInfo userInfo) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update userinfo set name=?, address=?, edu=?, sex=?, age=?" +
							"where id=?");
			// Parameters start with 1
			preparedStatement.setString(1, userInfo.getName());
			preparedStatement.setString(2, userInfo.getAddress());
			preparedStatement.setInt(3, userInfo.getEdu());
			preparedStatement.setInt(4, userInfo.getSex());
			preparedStatement.setInt(5, userInfo.getAge());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<UserInfo> getAllUsers() {
		List<UserInfo> users = new ArrayList<UserInfo>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from users");
			while (rs.next()) {
				UserInfo userinfo = new UserInfo();
				userinfo.setId(rs.getInt("id"));
				userinfo.setName(rs.getString("name"));
				userinfo.setAddress(rs.getString("address"));
				userinfo.setAge(rs.getInt("age"));
				userinfo.setEdu(rs.getInt("edu"));
				users.add(userinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
	
	public UserInfo getUserById(int id) {
		UserInfo userinfo = new UserInfo();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from users where id=?");
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				userinfo.setId(rs.getInt("id"));
				userinfo.setName(rs.getString("name"));
				userinfo.setAddress(rs.getString("address"));
				userinfo.setAge(rs.getInt("age"));
				userinfo.setEdu(rs.getInt("edu"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userinfo;
	}
}
