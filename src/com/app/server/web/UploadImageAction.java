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

import com.app.server.model.UserInfoModel;
import com.app.server.util.ConfigSingleton;
import com.app.server.web.bean.ConstantBean;
import com.app.server.web.bean.ImageBean;
import com.app.server.web.bean.UserInfoBean;

/**
 * 上传图片
 * 
 * @author Simon
 *
 */
public class UploadImageAction extends AbstractAction {
	public static String SAVE_FOLDER;
	public static String LINK_HEADER;

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		ConstantBean config = ConfigSingleton.getInstance();
		SAVE_FOLDER = config.getUserImageFolder();
		LINK_HEADER = config.getUserImageLink();

		UserInfoModel userId = user.get();

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
		System.out.println("fileItemList.size()" + fileItemList.size());
		Iterator<FileItem> fileItemIterator = fileItemList.iterator();

		Date now = new Date();

		fileItemIterator = fileItemList.iterator();
		while (fileItemIterator.hasNext()) {
			FileItem fileItem = fileItemIterator.next();

			String filename = String.valueOf(userId.getId()) + "_"
					+ +now.getTime() + ".jpg";
			if (!fileItem.isFormField()) {

				File file = new File(SAVE_FOLDER, filename);
				try {
					fileItem.write(file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		ImageBean bean = new ImageBean();
		bean.setLink(LINK_HEADER + String.valueOf(userId.getId()) + "_"
				+ now.getTime() + ".jpg");
		JSONObject returnObject = JSONObject.fromObject(bean);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnObject);
		return returnObj;
	}

}
