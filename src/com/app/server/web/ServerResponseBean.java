package com.app.server.web;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

public class ServerResponseBean {
	private int RETURN_CODE;
	private JSON RETURN_OBJ;

	public ServerResponseBean(int RETURN_CODE, JSON RETURN_OBJ) {
		this.RETURN_CODE = RETURN_CODE;

		if (RETURN_OBJ == null && RETURN_CODE!= 200) {

			this.RETURN_OBJ = JSONObject.fromObject("{\"msg\":\"error occur\"}");
		}else{
			this.RETURN_OBJ = RETURN_OBJ;
		}
	}

	public JSON getRETURN_OBJ() {
		return RETURN_OBJ;
	}

	public void setRETURN_OBJ(JSON rETURN_OBJ) {
		RETURN_OBJ = rETURN_OBJ;
	}

	public int getRETURN_CODE() {
		return RETURN_CODE;
	}

	public void setRETURN_CODE(int rETURN_CODE) {
		RETURN_CODE = rETURN_CODE;
	}

}
