package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.app.server.da.IEntityPersist;
import com.app.server.da.IEntityQueryFactory;

public abstract class AbstractAction implements Controller {
	@Autowired
	@Qualifier("entityQueryFactory")
	protected IEntityQueryFactory entityQueryFactory;
	@Autowired
	@Qualifier("entityPersist")
	protected IEntityPersist entityPersist;

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {

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
