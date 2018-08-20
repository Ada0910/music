package com.music.list;

import java.util.ArrayList;

import com.music.thread.PlayerThread;


	
	final public class ThreadList {

		private static ArrayList<PlayerThread> list;
		
		

		static{
			if (list==null) {
				list=new ArrayList<PlayerThread>();
			}
		}
		
		public static void add(PlayerThread player){
			
			list.add(player);
		}
		
		public static ArrayList<PlayerThread> getList() {
			return list;
		}
		

}
