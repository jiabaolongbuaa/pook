package com.app.server.constant;

/**
 * 
 * Server Response Code for Clients
 * 
 * @author Simon
 *
 */
public class ResponseCode {

	private ResponseCode(){
		throw new AssertionError("Never init a util class");
	}
	
	
	/**
	 * 
	 * NOTE: codes from 200 to 999 are remained 
	 * 
	 */
	public static final int POOK_SUCCESS = 200;
	
	
	public static final int POOK_NOT_LOGGED_IN = 1301;
	public static final int POOK_TOO_MANY_REQUESTS = 1302;
	public static final int POOK_PARAM_ERROR = 1303;
	
	
	
	//FRIENDS RELATED
	public static final int FRIEND_FRIENDID_EMPTY = 3001;
	public static final int FRIEND_FRIENDID_LESS_THAN_ZERO = 3002;
	public static final int FRIEND_FRIENDID_NOT_EXITS = 3003;
	public static final int FRIEND_FRIENDID_ALREADY_FRIENDS = 3004;
	public static final int FRIEND_FRIENDID_NOT_FRIEND = 3005;
	public static final int FRIEND_FRIENDID_ALREADY_IN_BLACKLIST = 3006;
	public static final int FRIEND_FRIENDID_NOT_IN_BLACKLIST = 3007;
	public static final int FRIEND_FRIENDID_FORMAT_ERROR = 3008;
	
	
	
	
	
}
