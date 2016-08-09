package com.app.server.web;

import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.app.server.model.ActiveCodeModel;
import com.app.server.model.PushSettingModel;
import com.app.server.model.UserImageModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.MD5Util;
import com.app.server.util.OpenfireRegistrationAgent;
import com.app.server.util.PatternValidation;
import com.app.server.util.RandomStringGenerator;
import com.app.server.util.SMSSerice;
import com.app.server.util.SMSService;
import com.app.server.util.StringUtils;

public class SendSMSAction extends AbstractNoneStateAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String phoneNum = request.getParameter("phone");
		if (phoneNum == null ) {
			return new ServerResponseBean(0x0001, null);
		}
		//验证手机号码
		if (!PatternValidation.isMobileNO(phoneNum)) {
			return new ServerResponseBean(0x0002, null);
		}
		
		ActiveCodeModel model = entityQueryFactory
				.createQuery(ActiveCodeModel.class)
				.eq("phonenum", phoneNum, true).get();
		
		Date now = new Date();
		
		if(model != null){
			//if(now.before(model.getResendtime())){
				return new ServerResponseBean(3, null);
			//}
		}else{
			model = new ActiveCodeModel();
			model.setPhonenum(phoneNum);
		}
		model.setEndtime(new Date(now.getTime() + 30* 60* 1000));
		model.setResendtime(new Date(now.getTime() + 1* 60* 1000));
		

		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();

		
		//生成6位验证码
		String code = RandomStringGenerator.get(4);
		
		boolean sendSeccessful=SMSService.sendSMS(phoneNum, code);
		if(sendSeccessful){
			
			model.setCode(code);
			entityPersist.saveOrUpdate(model);
			session.setAttribute("phonenum", phoneNum);
			session.setAttribute("code", code);
		}else{
			return new ServerResponseBean(4, null);
		}
		
		
		ServerResponseBean returnObj = new ServerResponseBean(200, null);
		return returnObj;
	}

}
