package com.app.server.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.app.server.model.TestDeviceModel;
import com.app.server.model.UserInfoModel;
import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSObject;
import com.dd.plist.PropertyListParser;

/**
 * 获取激活码
 * 
 * @author simon
 * 
 */
public class GetUDIDAction extends AbstractAction {

	static Logger logger = Logger.getLogger(GetUDIDAction.class.getName());

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		
		try {
			InputStream input = request.getInputStream();
			String content = inputStream2String(input);
			int start = content.indexOf("<?xml");
			int end = content.lastIndexOf("</plist>")
					+ new String("</plist>").length();

			content = content.substring(start, end);
			// System.err.println("================================================");
			// System.err.println(content);
			// System.err.println("================================================");
			try {
				// File file = new File("Info.plist");
				NSDictionary rootDict = (NSDictionary) PropertyListParser
						.parse(content.getBytes());
				String udid = rootDict.objectForKey("UDID").toString();
				String product = rootDict.objectForKey("PRODUCT").toString();
				String version = rootDict.objectForKey("VERSION").toString();
				TestDeviceModel deviceModel = entityQueryFactory
						.createQuery(TestDeviceModel.class)
						.eq("udid", udid, true).get();
				if (deviceModel != null) {
					return new ServerResponseBean(1, null);
				}
				deviceModel = new TestDeviceModel();
				deviceModel.setProduct(product);
				deviceModel.setUdid(udid);
				deviceModel.setVersion(version);

				entityPersist.saveOrUpdate(deviceModel);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ActivationCodeModel model = new ActivationCodeModel();
		// UUID uuid =UUID.randomUUID();
		// String uuidStr = uuid.toString().replaceAll("-", "");
		// model.setCode(uuidStr);
		// entityPersist.saveOrUpdate(model);
		//
		// Map<String,String> result = new HashMap<String,String> ();
		// result.put("code", uuidStr);
		//
		// JSONObject obj = JSONObject.fromObject(result);

		
		return new ServerResponseBean(200, null);

	}

	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}
}