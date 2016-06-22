package com.app.server.web;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.app.server.model.UserImageModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.ConfigSingleton;
import com.app.server.web.bean.ConstantBean;
import com.app.server.web.bean.UserImageBean;

public class UploadUserImageAction extends AbstractAction {
	public static String SAVE_FOLDER;
	public static String LINK_HEADER;

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		ConstantBean config =ConfigSingleton.getInstance();
		SAVE_FOLDER = config.getUserImageFolder();
		LINK_HEADER = config.getUserImageLink();
		
		File tempDir = new File(SAVE_FOLDER);
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1 * 1024 * 1024);
		factory.setRepository(tempDir);
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(100 * 1024 * 1024);
		sfu.setSizeMax(200 * 1024 * 1024);
		sfu.setHeaderEncoding("UTF-8");
		List<FileItem> fileItemList = null;
		try {
			fileItemList = sfu.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<FileItem> fileItemIterator = fileItemList.iterator();
		String userId = "";
		String imageId = "";
		while (fileItemIterator.hasNext()) {
			FileItem fileItem = fileItemIterator.next();
			if (fileItem.getFieldName().equalsIgnoreCase("userId")) {
				userId = fileItem.getString();
			}
			if (fileItem.getFieldName().equalsIgnoreCase("imageId")) {
				imageId = fileItem.getString();
			}

		}
		UserInfoModel userInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class)
				.eq("id", Integer.valueOf(userId), false).get();

		String fileName = "";
		fileItemIterator = fileItemList.iterator();
		while (fileItemIterator.hasNext()) {
			FileItem fileItem = fileItemIterator.next();
			if (!fileItem.isFormField()) {
				fileName = String.valueOf(new Date().getTime()) + ".jpg";
				File file = new File(SAVE_FOLDER, fileName);
				try {
					fileItem.write(file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		UserImageModel savedModel = null;
		if (imageId == null || imageId.trim().equals("")) {
			savedModel = new UserImageModel();
		} else {
			savedModel = entityQueryFactory.createQuery(UserImageModel.class)
					.eq("id", Integer.valueOf(imageId), false).get();
		}

		savedModel.setImagePath(LINK_HEADER + fileName);
		savedModel.setUserInfoModel(userInfoModel);

		entityPersist.saveOrUpdate(savedModel);
		return new ServerResponseBean(200,
				JSONObject.fromObject(new UserImageBean(savedModel)));
	}

}
