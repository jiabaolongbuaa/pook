package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.app.server.da.IEntityPersist;
import com.app.server.da.IEntityQueryFactory;
import com.app.server.model.UserInfoModel;

public abstract class AbstractAction implements Controller {
	@Autowired
	@Qualifier("entityQueryFactory")
	protected IEntityQueryFactory entityQueryFactory;
	@Autowired
	@Qualifier("entityPersist")
	protected IEntityPersist entityPersist;

	protected ThreadLocal<UserInfoModel> user = new ThreadLocal<UserInfoModel>() {

		@Override
		protected UserInfoModel initialValue() {
			return null;
		}
	};
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {

		HttpSession session = arg0.getSession(false);
		if (session != null) {
			UserInfoModel userId = (UserInfoModel) session.getAttribute("user");

			if (userId != null) {

				userId = entityQueryFactory.createQuery(UserInfoModel.class)
						.eq("id", userId.getId(), true).get();
				user.set(userId);

				session.setAttribute("user", userId);

			}
		}
		
		arg0.setCharacterEncoding("UTF-8");
		ServerResponseBean returnObj = this.processAndReturnJSONString(arg0,
				arg1);
		arg1.setCharacterEncoding("UTF-8");
		arg1.setContentType("application/json");
		arg1.getWriter().print(JSONObject.fromObject(returnObj));
		return null;
	}

	public abstract ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response);
}
