package com.music.input;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

/**
 * 打开文件的功能，默认返回.mp3
 */
public class FileInput {
	private JFileChooser fdialog = null;//对话框
	private File f[];
	private String name[];
	private JFrame jf;

	public FileInput(JFrame jf) {
		this.jf = jf;
		fdialog = new JFileChooser();
		fdialog.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return ".mp3";
			}

			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				if (f.isDirectory())
					return true;
				return f.getName().endsWith(".mp3");
			}

		});
		//确定是否在AcceptAll FileFilter可选择的筛选器列表中用作可用选项
		fdialog.setAcceptAllFileFilterUsed(false);
		//选择文件和目录
		fdialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//设置文件选择器以允许多个文件选择
		fdialog.setMultiSelectionEnabled(true);

	}

	public void open() {
		f = null;
		int result = fdialog.showOpenDialog(jf);
		
		//如果选择了批准（是，确定），则返回值		
		if (result == fdialog.APPROVE_OPTION) {
			
			//返回被选择的文件
			f = fdialog.getSelectedFiles();

		}
	}

	//返回文件的名字
	public String[] getFileNames() {
		name = null;
		if (f != null) {
			name = new String[f.length];
			for (int i = 0; i < f.length; i++)
				name[i] = f[i].getPath();
		}
		return name;
	}

	//获得文件
	public File[] getFiles() {
		return f;
	}

}
