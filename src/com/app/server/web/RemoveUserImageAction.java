package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.UserImageModel;

public class RemoveUserImageAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String imageId = request.getParameter("imageId");
		UserImageModel model = entityQueryFactory
				.createQuery(UserImageModel.class)
				.eq("id", Integer.valueOf(imageId), false).get();
		entityPersist.remove(model);
		return new ServerResponseBean(200, null);
	}

}
