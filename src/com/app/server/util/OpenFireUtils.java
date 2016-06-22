package com.app.server.util;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.igniterealtime.restclient.RestApiClient;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.entity.UserEntities;
import org.igniterealtime.restclient.entity.UserEntity;

public class OpenFireUtils {

	public static boolean createUser(String username, String email,
			String password) {

		// Basic HTTP Authentication
		AuthenticationToken authenticationToken = new AuthenticationToken(
				"admin", "nopassword");
		// Set Openfire settings (9090 is the port of Openfire Admin Console)
		RestApiClient restApiClient = new RestApiClient(
				"http://101.201.108.194", 9090, authenticationToken);

		// Request all available users

		// Create a new user (username, name, email, passowrd). There are more
		// user settings available.
		UserEntity userEntity = new UserEntity(username, username, email,
				password);
		Response res = restApiClient.createUser(userEntity);

		System.err.println(res.getStatus());
		System.err.println(res.getStatusInfo());

		// 201 means created
		if (res.getStatus() == 201) {
			return true;
		}

		return false;
	}

	public static void main(String[] args) {
		// Basic HTTP Authentication
		AuthenticationToken authenticationToken = new AuthenticationToken(
				"admin", "nopassword");
		// Set Openfire settings (9090 is the port of Openfire Admin Console)
		RestApiClient restApiClient = new RestApiClient(
				"http://101.201.108.194", 9090, authenticationToken);

		// Request all available users
		restApiClient.getUsers();

		// Create a new user (username, name, email, passowrd). There are more
		// user settings available.
		UserEntity userEntity = new UserEntity("testUsername", "testName",
				"test@email.com", "p4ssw0rd");
		restApiClient.createUser(userEntity);

	}
}
