package com.music.model;

import java.awt.Font;

/*
 * 歌曲music的数据模型
 */
public class MusicModel {

	private String id; // 歌曲的id
	private String name;// 歌曲的名字
	private String author;// 歌曲的作者
	private String path;// 歌曲的路径
	private String length;// 歌曲的长度

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

}
