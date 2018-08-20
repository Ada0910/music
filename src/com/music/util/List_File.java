package com.music.util;

import java.io.File;
import java.util.ArrayList;

public class List_File {
	String dir = "";

	String temp = "";
	//数组info
	private String[] info;

	//列表
	private ArrayList<String[]> FileList = null;

	public ArrayList<String[]> serachFiles(String dir) {
		//获得输入流
		File root = new File(dir);

		//返回某个目录下所有文件和目录的绝对路径，返回的是File数组
		File[] filesOrDirs = root.listFiles();

		FileList = new ArrayList<String[]>();

		//遍历这个文件列表
		for (int i = 0; i < filesOrDirs.length; i++) {

			File file = filesOrDirs[i];

			if (file.isFile() && isMusic(file.getName())) {
				info = new String[2];
				info[0] = file.getName();
				info[1] = file.getAbsolutePath();
				FileList.add(info);
			}
		}

		return FileList;

	}

	// 判断是否为音乐文件

	public boolean isMusic(String s) {

		String type = s.substring(s.indexOf(".") + 1, s.length());
		if ("mp3".equals(type)||"mav".equals(type)||"wav".equals(type)||"wma".equals(type)) {
			
			return true;
		}
		return false;

	}
}
