package com.music.input;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

/**
 *class FileInput 获得目录歌曲文件
 */
public class DirInput
{
   private JFileChooser fdialog=null;
   private File f;
   private JFrame jf;
   public DirInput(JFrame jf)
   {    
       this.jf=jf;
       fdialog=new JFileChooser("D://网易云音乐//download");
       fdialog.setFileFilter(new FileFilter() {
		
		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "目录";
		}
		
		@Override
		public boolean accept(File f) {
			// TODO Auto-generated method stub
			if(f.isDirectory()) return true;
			return false;
		}
	});
       fdialog.setAcceptAllFileFilterUsed(false);
       fdialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
       fdialog.setMultiSelectionEnabled(false);
      
   }
   public void open()
   {
      f=null;
      int result = fdialog.showOpenDialog(jf);
      if(result == fdialog.APPROVE_OPTION){
    	  
    	  f=fdialog.getSelectedFile();
    	  
      }
   }
   public File getFile()
   {
   		return f;
   }
   
}
