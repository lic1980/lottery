package com.lottery.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.zxing.WriterException;
import com.lottery.util.QRCodeUtils;

@Service
public class QRCodeService {
	private static final String DATE_TIME_FORMAT = "yyMMddHHmmssSSS";
	
	@Value("${agent.qrcode.basedir}")
	private String qrBaseFolder;
	@Value("${lottery.site.address}")
	private String siteAddress;
	
	public String createQRCodeImg(long agentId) throws IOException, WriterException {
		String ext = "jpg";
		String fileName = getFileName() + "." + ext; 
		String dynamicDirName = getDynamicDirName();
		String dir = qrBaseFolder + File.separator + dynamicDirName;
		String url = "http://" + siteAddress + "/register?agentId=" + agentId;
		QRCodeUtils.createQrCode(url, dir, fileName, ext);
		return dynamicDirName + "/" + fileName + "." + ext;
	}
	
	public static String getDynamicDirName(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return format.format(date);
	}
	
	public String getFileName() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		return dateFormat.format(new Date());
	}
}
