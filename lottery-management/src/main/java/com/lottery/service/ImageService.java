package com.lottery.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
	private static final String DATE_TIME_FORMAT = "yyMMddHHmmssSSS";
	
	@Value("${lottery.image.basedir}")
	private String imageBaseFolder;
	
	public String store(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename(); 
		String suffixName = fileName.substring(fileName.lastIndexOf('.'));
		fileName = getFileName() + suffixName; 
		String path = getDynamicDirName() + File.separator + fileName;
		File dest = new File(imageBaseFolder + File.separator + path);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		file.transferTo(dest);
		
		return getDynamicDirName() + "/" + fileName;
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
