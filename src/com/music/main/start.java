package com.music.main;

import javax.swing.UIManager;

import com.music.list.ViewList;
import com.music.view.MusicView;

//启动页面

public class start {

	public static void main(String[] args) {
		if (ViewList.getList().size()==0) {
			
		 MusicView v=new MusicView();
		 
		 ViewList.add(v);
		 
		 //设置风格
			try
	    	{
				//这是把外观设置成你所使用的平台的外观,也就是你这个程序在哪个平台运行,显示的窗口,对话框外观将是哪个平台的外观.
	    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        }catch(Exception exception){
	        	exception.printStackTrace();
	        }
		 
		}
		
	}
}
