package com.app.server.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.app.server.model.ActiveCodeModel;
import com.app.server.model.TopicModel;
import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.TopicBean;

public class VerifySMSCodeAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String phonenum = request.getParameter("phonenum");
		String code = request.getParameter("code");
		System.err.println("code="+code);

		
		if (code == null || code.trim().equals("")) {
			return new ServerResponseBean(1, null);
		}
		
		ActiveCodeModel activeCode = entityQueryFactory
				.createQuery(ActiveCodeModel.class)
				.eq("phonenum",phonenum, true).get();
		
//		HttpServletRequest req = (HttpServletRequest)request;
//		HttpSession session = req.getSession();
//		Object sessionCode = session.getAttribute("code");
		if(activeCode == null){
			return new ServerResponseBean(2, null);
		}
		
		Date now = new Date();
		
		
		if(now.after(activeCode.getEndtime())){
			return new ServerResponseBean(3, null);
		}
		
		String codeString =activeCode.getCode();
		if(code.equalsIgnoreCase(codeString)){
			activeCode.setPass(1);
			entityPersist.saveOrUpdate(activeCode);
			return new ServerResponseBean(200, null);
		}
		return new ServerResponseBean(4, null);
	}

}
