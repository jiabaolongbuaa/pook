package com.app.server.util;

import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.app.server.web.bean.ConstantBean;

public class OpenfireRegistrationAgent {

	// jbl
	private static final String INSERT_REGISTRATION = "INSERT INTO ofUser(username, plainPassword, encryptedPassword, "
			+ "name, email, creationDate,modificationDate) VALUES (?,?,?,?,?,?,?)";
	private static final String UPDATE_PASSWORD = "UPDATE ofUser SET plainPassword =? , encryptedPassword=? WHERE username=?";
	private static final String LOAD_REGISTRATION = "SELECT jid, transportType, username, password, nickname, registrationDate, lastLogin "
			+ "FROM ofGatewayRegistration WHERE registrationID=?";
	private static final String SET_LAST_LOGIN = "UPDATE ofGatewayRegistration SET lastLogin=? WHERE registrationID=?";
	private static final String SET_PASSWORD = "UPDATE ofGatewayRegistration SET password=? WHERE registrationID=?";
	private static final String SET_USERNAME = "UPDATE ofGatewayRegistration SET username=? WHERE registrationID=?";
	private static final String SET_NICKNAME = "UPDATE ofGatewayRegistration SET nickname=? WHERE registrationID=?";

	private String username;
	private String plainPassword;
	private String encryptedPassword;
	private String name;
	private String email;
	private String creationDate;
	private String modificationDate;

	// private boolean disconnectedMode = false;

	public OpenfireRegistrationAgent(String username, String plainPassword,
			String name, String email, String creationDate) {
		this.username = username.replaceAll("@", "\\\\40");
		this.plainPassword = plainPassword;
		this.name = name;
		this.email = email;
		this.creationDate = creationDate;

	}
	public OpenfireRegistrationAgent(String username, String plainPassword) {
		this.username = username.replaceAll("@", "\\\\40");
		this.plainPassword = plainPassword;
	}

	String getKeyString() {

		String keyString = null;
		Connection conn = null;
		Statement pstmt = null;
		ResultSet rs = null;
		String url = null;
		String user = null;
		String password = null;
		String sql = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			ConstantBean constant =ConfigSingleton.getInstance();
			url = constant.getOpenfireMySQLUrl();
			user =constant.getOpenfireMySQLUsername();
			password=constant.getOpenfireMySQLPassword();
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			pstmt = conn.createStatement();
			rs=pstmt.executeQuery("SELECT * FROM ofProperty WHERE name =\"passwordKey\"");
			while (rs.next()) {
				keyString = rs.getString("propValue");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return keyString;

	}

	Blowfish getCipher() {
		Blowfish cipher = null;
		// Get the password key, stored as a database property. Obviously,
		// protecting your database is critical for making the
		// encryption fully secure.
		String keyString;
		try {
			keyString = getKeyString();
			// if (keyString == null) {
			// keyString = StringUtils.randomString(15);
			// // JiveGlobals.setProperty("passwordKey", keyString);
			// // Check to make sure that setting the property worked. It won't
			// // work,
			// // for example, when in setup mode.
			// if (!keyString.equals(JiveGlobals.getProperty("passwordKey"))) {
			// return null;
			// }
			// }
			cipher = new Blowfish(keyString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipher;
	}

	String encryptPassword(String password) {

		Blowfish cipher = getCipher();
		if (cipher == null) {
			throw new UnsupportedOperationException();
		}
		//System.err.println("cipher.encryptString(password)::"+cipher.encryptString(password));
		return cipher.encryptString(password);

	}

	public void updatePassword(){
		if (plainPassword!=null) {
			encryptedPassword = encryptPassword(plainPassword);
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		String url = null;
		String user = null;
		String password = null;
		String sql = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}

		try {
			ConstantBean constant =ConfigSingleton.getInstance();
			url =constant.getOpenfireMySQLUrl();
			user = constant.getOpenfireMySQLUsername();
			password = constant.getOpenfireMySQLPassword();
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			pstmt = conn.prepareStatement(UPDATE_PASSWORD);
			pstmt.setString(1, plainPassword);
			pstmt.setString(2, encryptedPassword);
			// The password is stored in encrypted form for improved
			// security.
			pstmt.setString(3, username);
//			pstmt.setString(4, name);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// �ر���ݿ�
		try {
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertRegistration() {

		if (plainPassword!=null) {
			encryptedPassword = encryptPassword(plainPassword);
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		String url = null;
		String user = null;
		String password = null;
		String sql = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // ����mysq��
		} catch (ClassNotFoundException e) {
			System.out.println("����ش���");
			e.printStackTrace();// ��ӡ������ϸ��Ϣ
		}

		try {
			ConstantBean constant =ConfigSingleton.getInstance();
			url =constant.getOpenfireMySQLUrl();
			user = constant.getOpenfireMySQLUsername();
			password = constant.getOpenfireMySQLPassword();
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			pstmt = conn.prepareStatement(INSERT_REGISTRATION);
			pstmt.setString(1, username);
			pstmt.setString(2, plainPassword);
			// The password is stored in encrypted form for improved
			// security.
			pstmt.setString(3, encryptedPassword);
//			pstmt.setString(4, name);
			if (name != null) {		
				pstmt.setString(4, name);
			} else {
				pstmt.setNull(4, Types.VARCHAR);
			}
			if (email != null) {
				pstmt.setString(5, email);
			} else {
				pstmt.setNull(5, Types.VARCHAR);
			}
			pstmt.setString(6, creationDate);
			pstmt.setString(7, creationDate);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
