package com.music.list;

import java.util.ArrayList;

import com.music.model.MusicModel;

/*
 * 音乐列表的创建和添加
 */
final public class MusicList {

	private static ArrayList<MusicModel> list;

	static {

		if (list == null) {
			list = new ArrayList<MusicModel>();
		}
	}

	//添加音乐
	public static void add(MusicModel music) {

		list.add(music);
	}

	// 获得音乐列表
	public static ArrayList<MusicModel> getList() {
		return list;
	}

	// 获得音乐的歌曲id
	public static MusicModel get(int id) {
		return list.get(id);
	}

}