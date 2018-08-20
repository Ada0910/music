package com.music.model;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.music.list.MusicList;

public class ListModel extends AbstractTableModel {

	// Vector 可实现自动增长的对象数组
	Vector rowData, columnData;
	JTable jTable = null;

	JScrollPane jScrollPane = null;


	private MusicList list;


	public ListModel() {
		columnData = new Vector();
		// 设置列名
		String musicString= "歌曲列表";
		/*musicString.setFont(new Font("宋体", Font.BOLD, 20));*/
		columnData.add(musicString);
		

		rowData = new Vector();

		// 遍历音乐列表，加入到列之中，
		for (int i = 0; i < list.getList().size(); i++) {
			Vector line = new Vector();
			String num = i < 10 ? "0" + (i + 1) : (i + 1) + "";
			line.add(num + "  " + list.getList().get(i).getName());
			rowData.add(line);
		}
	}

	// 返回音乐列表中的行数
	@Override
	public int getRowCount() {
		return this.rowData.size();
	}

	// 返回音乐模型中的列数
	@Override
	public int getColumnCount() {
		return this.columnData.size();
	}

	// 返回columnIndex 和 rowIndex 位置的单元格值
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return ((Vector) this.rowData.get(rowIndex)).get(columnIndex);
	}

	// 返回 columnIndex 位置的列的名称
	public String getColumnName(int arg0) {
		// TODO Auto-generated method stub
		return (String) this.columnData.get(arg0);
	}

}
