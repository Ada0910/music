package com.music.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.css.RGBColor;

import com.music.list.ViewList;
import com.music.input.FileInput;
import com.music.input.DirInput;
import com.music.list.MusicList;
import com.music.list.ThreadList;
import com.music.model.ListModel;
import com.music.model.MusicModel;
import com.music.thread.PlaySettingThread;
import com.music.thread.PlayerThread;
import com.music.util.List_File;

public class MusicView extends JFrame implements MouseListener, ActionListener, WindowListener {

	// 成员方法
	private JButton stopButton, playButton, delButton, nextButton, preButton, addButton;
	private JTable jTable;
	private PlayerThread player;
	private JPanel[] jPanels;
	private MusicList list;
	private JScrollPane jScrollPane;
	private JRootPane jRootPane;
	private ListModel model;
	private JSlider jSliderSound;
	private JSlider jSliderBarProgress;
	private FileInput fileInput;
	private DirInput dirInput;
	private JMenuBar jMenuBar;
	private JMenu jMenu; // 打开
	private JMenuItem folder;
	private JComboBox jComboBox;
	private JLabel label, soundLable, playing;
	private int height = 50, width = 50;

	// 构造方法，main函数一初始化就调用这个方法
	public MusicView() {
		if (ViewList.getList().size() == 0) {
			Open();
		}
	}

	// 打开的方法
	private void Open() {
		// 设置标题栏的图片和文字
		Image image = this.getToolkit().getImage("image/hi.jpg");// 设置图标
		this.setIconImage(image);
		this.setTitle("Ada音乐播放器");

		// 设置背景的内容
		ImageIcon icon = new ImageIcon("image/music.gif");
		JLabel lab = new JLabel(icon); // 将图片放入到label中

		// 获得内容面板
		Container cp = this.getContentPane();

		
		jMenuBar = new JMenuBar();// 一级菜单
		jMenu = new JMenu("文件");
		folder = new JMenuItem("打开...");
		jMenuBar.add(jMenu);
		jMenu.add(folder);
		setJMenuBar(jMenuBar);

		// 引入图标
		ImageIcon playIcon = new ImageIcon("image/play.png");
		playIcon.setImage(playIcon.getImage().getScaledInstance(height, width, Image.SCALE_DEFAULT));
		ImageIcon stopIcon = new ImageIcon("image/stop.png");
		stopIcon.setImage(stopIcon.getImage().getScaledInstance(height, width, Image.SCALE_DEFAULT));
		ImageIcon preIcon = new ImageIcon("image/上一曲.png");
		preIcon.setImage(preIcon.getImage().getScaledInstance(height, width, Image.SCALE_DEFAULT));
		ImageIcon nextIcon = new ImageIcon("image/下一曲.png");
		nextIcon.setImage(nextIcon.getImage().getScaledInstance(height, width, Image.SCALE_DEFAULT));
		ImageIcon delIcon = new ImageIcon("image/删除.png");
		delIcon.setImage(delIcon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		ImageIcon soundIcon = new ImageIcon("image/声音.png");
		soundIcon.setImage(soundIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		ImageIcon addIcon = new ImageIcon("image/添加.png");
		addIcon.setImage(addIcon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		ImageIcon playingIcon = new ImageIcon("image/playing.png");
		playingIcon.setImage(playingIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

		// 属性的实例化
		playButton = new JButton(playIcon);
		stopButton = new JButton(stopIcon);
		preButton = new JButton(preIcon);
		nextButton = new JButton(nextIcon);
		delButton = new JButton(delIcon);
		label = new JLabel(playingIcon);
		soundLable = new JLabel(soundIcon);
		addButton = new JButton(addIcon);
		playing = new JLabel(playingIcon);

		// 创建面板
		JPanel southJPanel = new JPanel();
		JPanel leftJPanel = new JPanel();
		JPanel topJPanel = new JPanel();
		JPanel centerJPanel = new JPanel();
		JPanel southJPanel2 = new JPanel();
		JPanel southJPanel3 = new JPanel();
		JPanel leftPanel2 = new JPanel();
		JPanel rightJPanel = new JPanel();

		playButton.setPreferredSize(new Dimension(50, 50));// 设置按钮的大小
		playButton.setBorderPainted(false); // 设置按钮的边框为空
		playButton.setContentAreaFilled(false); // 设置按钮透明
		stopButton.setPreferredSize(new Dimension(50, 50));
		stopButton.setBorderPainted(false);
		stopButton.setContentAreaFilled(false);
		preButton.setPreferredSize(new Dimension(50, 50));
		preButton.setBorderPainted(false);
		preButton.setContentAreaFilled(false);
		nextButton.setPreferredSize(new Dimension(50, 50));
		nextButton.setBorderPainted(false);
		nextButton.setContentAreaFilled(false);
		delButton.setPreferredSize(new Dimension(40, 40));
		delButton.setBorderPainted(false);
		delButton.setContentAreaFilled(false);
		soundLable.setPreferredSize(new Dimension(30, 30));
		addButton.setPreferredSize(new Dimension(40, 40));
		addButton.setBorderPainted(false);
		addButton.setContentAreaFilled(false);
		playing.setPreferredSize(new Dimension(30, 30));
		
		

		// 设置字体样式
		jMenu.setFont(new Font("宋体", Font.BOLD, 20));
		folder.setFont(new Font("宋体", Font.BOLD, 18));
		jSliderBarProgress = new JSlider(); // 播放进度条
		jSliderBarProgress.setValue(0);
		jSliderBarProgress.setEnabled(false);
		jSliderBarProgress.setPreferredSize(new Dimension(450, 20));

		// 左边表单的实现
		model = new ListModel(); // 添加表
		jTable = new JTable(model);
		jTable.setOpaque(false);
		jTable.setRowHeight(30);
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable.setShowHorizontalLines(false);
		jTable.setSelectionBackground(new Color(33, 99, 204));
		jScrollPane = new JScrollPane(jTable);
		jScrollPane.setOpaque(false);
		jScrollPane.getViewport().setOpaque(false);

		// 音量进度条
		jSliderSound = new JSlider();// 实例化
		jSliderSound.setValue(0);// 初始化
		jSliderSound.setPreferredSize(new Dimension(120, 20));// 设置滚动条长度
		jSliderSound.addChangeListener(new ChangeListener() {// 添加监听事件
			public void stateChanged(ChangeEvent evt) {
				if (ThreadList.getList().size() != 0) {
					ThreadList.getList().get(0).getVolume().setValue((float) jSliderSound.getValue());
				}

			}
		});

		// 顺序播放发按钮
		String[] v = { "顺序播放", "随机播放", "单曲循环", "列表循环", "单曲播放" };
		jComboBox = new JComboBox(v);
		//获得音乐列表
		jPanels = new JPanel[list.getList().size()];
		for (int i = 0; i < list.getList().size(); i++) {
			MusicModel music = list.getList().get(i);
			JPanel jPanel = new MyJPanelView(music);
			JLabel jLabel = new JLabel(music.getName(), SwingConstants.CENTER);
			jLabel.setSize(300, 10);
			jLabel.setHorizontalTextPosition(JLabel.CENTER);
			jPanel.setBackground(Color.WHITE);
			jPanels[i] = jPanel;
			jPanel.addMouseListener(this);
			jPanel.add(jLabel);

		}

		centerJPanel.setLayout(new BorderLayout());
		leftJPanel.setLayout(new BorderLayout());
		southJPanel2.setLayout(new GridLayout(1, 2));
		leftPanel2.setLayout(new GridLayout(1, 2));
		southJPanel.setLayout(new FlowLayout());
		rightJPanel.setLayout(new GridLayout());

		cp.add(southJPanel, BorderLayout.SOUTH);
		cp.add(topJPanel, BorderLayout.NORTH);
		cp.add(leftJPanel, BorderLayout.WEST);
		cp.add(centerJPanel, BorderLayout.CENTER);
		leftPanel2.add(jComboBox);
		leftPanel2.add(soundLable);
		leftPanel2.add(jSliderSound);
		rightJPanel.add(preButton);
		rightJPanel.add(playButton);
		rightJPanel.add(nextButton);
		rightJPanel.add(stopButton);
		southJPanel.add(leftPanel2);
		southJPanel.add(rightJPanel);
		centerJPanel.add(lab, BorderLayout.CENTER);
		centerJPanel.add(southJPanel3, BorderLayout.SOUTH);
		leftJPanel.add(southJPanel2, BorderLayout.SOUTH);
		leftJPanel.add(jScrollPane, BorderLayout.CENTER);
		southJPanel2.add(addButton);
		southJPanel2.add(delButton);
		southJPanel3.add(label);
		southJPanel3.add(jSliderBarProgress);

		leftJPanel.setPreferredSize(new Dimension(280, 500));
		leftPanel2.setPreferredSize(new Dimension(280, 50));
		rightJPanel.setPreferredSize(new Dimension(670, 50));
		southJPanel.setPreferredSize(new Dimension(600, 65));// 设置窗体的大小
		topJPanel.setPreferredSize(new Dimension(600, 50));

		// 设置背景颜色
		southJPanel.setBackground(new Color(234, 234, 234));
		topJPanel.setBackground(Color.black);
		leftJPanel.setBackground(new Color(237, 237, 237));
		southJPanel2.setBackground(new Color(237, 237, 237));
		southJPanel3.setBackground(new Color(237, 237, 237));
		centerJPanel.setBackground(new Color(204, 204, 255));

		// 添加边界
		southJPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		southJPanel2.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		southJPanel3.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		centerJPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));

		// 添加监听器
		addButton.addMouseListener(this);
		delButton.addMouseListener(this);
		playButton.addMouseListener(this);
		stopButton.addMouseListener(this);
		preButton.addMouseListener(this);
		nextButton.addMouseListener(this);
		folder.addActionListener(this);
		jComboBox.addActionListener(this);
		jTable.addMouseListener(this);
		this.addWindowListener(this);

		// 设置窗体的属性
		this.setLocation(400, 200);
		this.setSize(971, 600);
		this.setResizable(false);// 设置窗体是否可以拉伸
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	// 实现监听器事件
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == folder) {

			if (dirInput == null)
				dirInput = new DirInput(this);

			dirInput.open();

			File s = dirInput.getFile();

			if (s != null) {

				addmusic(s.getAbsolutePath());
			}

		} else if (e.getSource() == jComboBox) {
			// 顺序播放 (默认)default 随机rand 单曲循环 onecircle 列表循环 morecircle 单曲播放 one

			if (ThreadList.getList().size() != 0) {
				player = ThreadList.getList().get(0);
			} else {
				player = new PlayerThread(jSliderSound, jSliderBarProgress);
				ThreadList.getList().add(player);
			}

			String[] s = { "default", "rand", "onecircle", "morecircle", "one" };

			PlaySettingThread.setMode(s[jComboBox.getSelectedIndex()]);

		}
	}

	// 鼠标点击事件
	public void mouseClicked(MouseEvent e) {

		// 添加按钮
		if (e.getSource() == addButton) {// 添加mp3文件
			if (fileInput == null)
				fileInput = new FileInput(this);// 创建文件输入流
			fileInput.open();// 调用FileInput的方法
			// 返回选择的文件
			File[] s = fileInput.getFiles();
			// 将文件添加到播放列表中
			ArrayList<MusicModel> musiclist = MusicList.getList();

			if (s != null) {
				for (int i = 0; i < s.length; i++) {
					MusicModel music = new MusicModel();
					music.setId(musiclist.size() + "");
					music.setName(s[i].getName());
					music.setPath(s[i].getAbsolutePath());
					musiclist.add(music);
					jTable.setModel(new ListModel());
				}
			}
		} else

		if (e.getSource() == playButton) {

			if (player == null) {// 开始
				player = new PlayerThread(jSliderSound, jSliderBarProgress);
				player.setMusic(MusicList.getList().get(0));
				jTable.setRowSelectionInterval(0, 0);
				ThreadList.add(player);
				player.start();
			} else {// 继续
				if (ThreadList.getList().size() != 0) {
					player = ThreadList.getList().get(0);
				}

				String s = player.isPaused() ? "暂停" : "播放";
				playButton.setText(s);
				player.Pause();
			}

		} else if (e.getSource() == stopButton) {
			if (ThreadList.getList().size() != 0) {
				player = ThreadList.getList().get(0);
			}
			if (player != null) {
				player.stopplay();
				player = null;
				playButton.setText("播放");
			}

		} else if (e.getSource() == preButton) {// 上一首
			premusic();

		} else if (e.getSource() == nextButton) {// 下一首
			nextmusic();
		} else if (e.getSource() == delButton) {

			delmusic();

		} else if (e.getSource() == jTable && e.getClickCount() == 2) {// 双击

			clickmusic();

		}

	}

	private void clickmusic() {
		// 双击Jtable
	

		int rowNum = this.jTable.getSelectedRow();
		System.out.println(rowNum);
		if (rowNum == -1) {
			JOptionPane.showMessageDialog(this, "你没有选择一项");
			return;
		}
		ArrayList<PlayerThread> list = ThreadList.getList();

		if (list.size() == 0) {

			player = new PlayerThread(jSliderSound, jSliderBarProgress);
			player.setMusic(MusicList.getList().get(rowNum));
			ThreadList.add(player);
			playButton.setText("暂停");
			player.start();
		} else {
			list.get(0).stopplay();
			list.clear();
			player = new PlayerThread(jSliderSound, jSliderBarProgress);
			player.setMusic(MusicList.getList().get(rowNum));
			playButton.setText("暂停");
			list.add(player);
			player.start();
		}
	}

	private void delmusic() {
		int rowNum = this.jTable.getSelectedRow();

		MusicList.getList().remove(rowNum);

		System.out.println(MusicList.getList().size());

		jTable.setModel(new ListModel());

		ArrayList<PlayerThread> list = ThreadList.getList();
		player = new PlayerThread(jSliderSound, jSliderBarProgress);
		if (list.size() != 0) {
			list.get(0).stopplay();
			list.clear();

			if (rowNum == 0) {// 第一个
				
				jTable.setRowSelectionInterval(0, 0);
				player.setMusic(MusicList.getList().get(rowNum));

			} else if (rowNum == MusicList.getList().size()) {// 最后一个
			

				jTable.setRowSelectionInterval(rowNum - 2, rowNum - 1);
				player.setMusic(MusicList.getList().get(rowNum - 1));

			} else {
			

				jTable.setRowSelectionInterval(rowNum - 1, rowNum);
				player.setMusic(MusicList.getList().get(rowNum));

			}
			list.add(player);
			player.start();
		}
	}

	private void premusic() {
		System.out.println("上一首");

		ArrayList<PlayerThread> list = ThreadList.getList();

		int id = Integer.parseInt(list.get(0).getMusic().getId());

		if (id != 0) {
			if (id == 1) {
				jTable.setRowSelectionInterval(0, 0);
			} else {
				jTable.setRowSelectionInterval(id - 2, id - 1);
			}
	

			list.get(0).stopplay();
			list.clear();

			player = new PlayerThread(jSliderSound, jSliderBarProgress);

			player.setMusic(MusicList.getList().get(id - 1));
	

			playButton.setText("暂停");
			list.add(player);
			player.start();
		}
	}

	private void nextmusic() {
		System.out.println("下一首");
		ArrayList<PlayerThread> list = ThreadList.getList();
		int id = Integer.parseInt(list.get(0).getMusic().getId());

		System.out.println(id);
		if (id != MusicList.getList().size() - 1) { // 122

			jTable.setRowSelectionInterval(id, id + 1); // 123条

			list.get(0).stopplay();
			list.clear();

			player = new PlayerThread(jSliderSound, jSliderBarProgress);

			player.setMusic(MusicList.getList().get(id + 1));
			System.out.println(id + 1);

			playButton.setText("暂停");
			list.add(player);
			player.start();
		}
	}

	// 添加音乐的
	private void addmusic(String path) {// 增加mp3文件夹

		// 获得音乐列表
		ArrayList<MusicModel> musiclist = MusicList.getList();

		// 实例化一个List_File类
		List_File fm = new List_File();
		// 将文件路径下的音乐文件以列表的形式获取
		ArrayList<String[]> FileList = fm.serachFiles(path);

		for (int i = 0; i < FileList.size(); i++) {
			MusicModel music = new MusicModel();
			music.setId(musiclist.size() + "");
			String[] s = (String[]) FileList.get(i);

			music.setName(s[0]);
			music.setPath(s[1]);
			musiclist.add(music);
		}

		// 将获取的列表添加到表格中去
		jTable.setModel(new ListModel());

	}

	public JTable getTable() {
		return jTable;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
