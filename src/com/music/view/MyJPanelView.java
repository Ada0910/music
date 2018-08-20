package com.music.view;

import javax.swing.JPanel;

import com.music.model.MusicModel;

/*
 * 每首歌的音乐模型
 */

public class MyJPanelView extends JPanel {

	private MusicModel music;

	public MusicModel getMusic() {
		return music;
	}

	public void setMusic(MusicModel music) {
		this.music = music;
	}

	public MyJPanelView(MusicModel music) {
		super();
		this.music = music;
	}
	
	
}
