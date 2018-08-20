package com.music.list;

import java.util.ArrayList;

import com.music.view.MusicView;

/*
 * 返回一个界面组件的列表
 */
public class ViewList {
	private static ArrayList<MusicView> list;

	static{
		if (list==null) {
			list=new ArrayList<MusicView>();
		}
	}
	
	//添加
	public static void add(MusicView v){
		list.add(v);
	}
	
	public static ArrayList<MusicView> getList() {
		return list;
	}
}
