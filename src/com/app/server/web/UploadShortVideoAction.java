package com.app.server.web;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.app.server.model.UserImageModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.ConfigSingleton;
import com.app.server.web.bean.ConstantBean;
import com.app.server.web.bean.ImageBean;
import com.app.server.web.bean.UserImageBean;

/**
 * 
 * 上传小视频接口
 * 
 * @author Simon
 *
 */
public class UploadShortVideoAction extends AbstractAction {

	static Logger logger = Logger.getLogger(UploadShortVideoAction.class
			.getName());

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		UserInfoModel userId = user.get();

		ConstantBean config = ConfigSingleton.getInstance();
		String SAVE_FOLDER = config.getUserImageFolder();
		String LINK_HEADER = config.getUserImageLink();

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

		String fileName = "";
		fileItemIterator = fileItemList.iterator();
		while (fileItemIterator.hasNext()) {
			FileItem fileItem = fileItemIterator.next();
			if (!fileItem.isFormField()) {
				fileName = UUID.randomUUID() + ".mp4";
				File file = new File(SAVE_FOLDER, fileName.toLowerCase());
				try {
					fileItem.write(file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		ImageBean bean = new ImageBean();
		bean.setLink(LINK_HEADER + fileName);

		return new ServerResponseBean(200, JSONObject.fromObject(bean));
	}

}
