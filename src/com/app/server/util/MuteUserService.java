package com.app.server.util;

public class MuteUserService implements Runnable {
	public MuteUserService(int friendId,int userId){
		this.friendId = friendId;
		this.userId = userId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	
	private int friendId;
	private int userId;

}
