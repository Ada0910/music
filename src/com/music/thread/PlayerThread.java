package com.music.thread;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JSlider;
import javax.swing.JTable;

import com.music.list.MusicList;
import com.music.list.ThreadList;
import com.music.list.ViewList;
import com.music.model.MusicModel;

/*
 * 主要功能实现
 */

public class PlayerThread extends Thread {

	private JSlider jSliderBarProgress; // 播放进度的滑块条
	private JSlider jSliderSound; // 声音大小的滑块条
	private PlayerThread player;//
	private MusicModel music;// 音乐列表模型

	private boolean paused = false; // 暂停，播放
	private boolean over = false; // 开始，结束
	private long time = 0; // 时间
	private boolean isNext = true;// 是否自动播放下一曲

	AudioInputStream audioInputStream = null;
	SourceDataLine line = null;
	private FloatControl volume = null;
	Object lock = new Object();

	// 返回进度条组件
	public PlayerThread(JSlider jSliderSound, JSlider jSliderBarProgress) {
		super();
		this.jSliderSound = jSliderSound;
		this.jSliderBarProgress = jSliderBarProgress;
	}

	// 获得声音的进度
	public JSlider getjSliderSound() {
		return jSliderSound;
	}

	// 设置声音进度条
	public void setjSliderSound(JSlider jSliderSound) {
		this.jSliderSound = jSliderSound;
	}

	public MusicModel getMusic() {
		return music;
	}

	public void setMusic(MusicModel music) {
		this.music = music;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public FloatControl getVolume() {
		return volume;
	}

	// 开始
	public void startplay() {
		over = false;
	}

	// 停止

	public void stopplay() {
		over = true;
	}

	// 暂停
	public void userPressedPause() {
		paused = true;
	}

	// 继续
	public void userPressedPlay() {
		synchronized (lock) {
			paused = false;
			lock.notifyAll();
		}

	}

	public void Pause() {
		if (paused) {
			synchronized (lock) {
				paused = false;
				lock.notifyAll();
			}
		} else {
			paused = true;
		}
	}

	// 设置声音进度条
	public void setVolume() {
		if (line != null) {
			if (line.isControlSupported(FloatControl.Type.MASTER_GAIN))// MaSTER_GAIN表示某一行上总音量的控件
			{
				jSliderSound.setEnabled(true);
				volume = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
				jSliderSound.setMinimum((int) volume.getMinimum());// 获得所允许的最小值
				jSliderSound.setMaximum((int) volume.getMaximum());// 获得所允许的最大值
				// 设置当前控件的值
				volume.setValue((float) (volume.getMinimum() + (4 * (volume.getMaximum() - volume.getMinimum())) / 5));
			}
		} else {
			volume = null;
			jSliderSound.setEnabled(false);
		}
	}

	// 播放线程
	public void run() {
		AudioInputStream in = null;

		try {
			// 1.获得文件的路径得到文件
			File file = new File(music.getPath());
			try {
				// 2.音频文件输入流
				in = AudioSystem.getAudioInputStream(file);
			} catch (UnsupportedAudioFileException | IOException e) {

				nextmusic();
			}
			/*
			 * 3，音频文件格式 public AudioFormat (float sampleRate, - 每秒的样本数 int
			 * sampleSizeInBits,每个样本中的位数 ;int channels, 声道数（单声道 1 个，立体声 2 个）
			 * boolean signed, 指示数据是有符号的，还是无符号的 boolean bigEndian指示是否以
			 * big-endian 字节顺序存储单个样本中的数据（false 意味着 little-endian）
			 */
			AudioFormat baseFormat = in.getFormat();
			AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
					baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

			// 此格式声音的编码类型
			if (baseFormat.getEncoding() == AudioFormat.Encoding.PCM_UNSIGNED
					|| baseFormat.getEncoding() == AudioFormat.Encoding.ULAW
					|| baseFormat.getEncoding() == AudioFormat.Encoding.ALAW
					|| baseFormat.getEncoding() == AudioFormat.Encoding.PCM_SIGNED) {

				// getSampleRate()样本速率,getSampleSizeInBits获取样本的大小
				time = (file.length() * 8000000)
						/ ((int) (decodedFormat.getSampleRate() * baseFormat.getSampleSizeInBits()));
				/*
				 * System.out.println("时间"+time);//52989353
				 */ } else {
				int bitrate = 0;
				if (baseFormat.properties().get("bitrate") != null) {
					// 取得播放速度(单位位每秒)
					bitrate = (int) ((Integer) (baseFormat.properties().get("bitrate")));
					if (bitrate != 0)
						time = (file.length() * 8000000) / bitrate;
					/* System.out.println("时间2"+time); */
				}

			}

			audioInputStream = AudioSystem.getAudioInputStream(decodedFormat, in);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open();
			setVolume();
			jSliderBarProgress.setMaximum((int) time);
			jSliderBarProgress.setValue(0);
			if (line != null) {
				line.open(decodedFormat);
				byte[] data = new byte[4096];
				int nBytesRead;
				synchronized (lock) {
					while ((nBytesRead = audioInputStream.read(data, 0, data.length)) != -1) {
						// System.out.println(line.getMicrosecondPosition());
						while (paused) {
							if (line.isRunning()) {
								line.stop();
								
							}
							try {
								lock.wait();
							
							} catch (InterruptedException e) {
							}
						}
						if (!line.isRunning() && !over) {
							
							line.start();

						}

						if (over && line.isRunning()) {
							jSliderBarProgress.setValue(0);
							isNext = false;
							line.drain();
							line.stop();
							line.close();
						}

						jSliderBarProgress.setValue((int) line.getMicrosecondPosition());
						line.write(data, 0, nBytesRead);
					}

					// 根据播放模式选择下一首歌
					nextmusic();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (audioInputStream != null) {
				try {
					audioInputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	// 歌曲的播放模式
	private void nextmusic() {
		String mode = PlaySettingThread.getMode();// 默认单曲播放

		if (isNext && !mode.equals("one")) {// 单曲播放就不执行
			int nextid = 0;// 将要播放的id
			int currentid = Integer.parseInt(this.music.getId());
			// 到最后一曲是返回
			if (mode.equals("default") && (currentid == MusicList.getList().size() - 1)) {
				return;
			}

			/* 随机播放 */
			if (mode.equals("rand")) {
				Random random = new Random();
				nextid = Math.abs(random.nextInt()) % MusicList.getList().size();
			} else
			// 单曲循环
			if (mode.equals("onecircle")) {
				nextid = currentid;
			} else
			// 顺序播放
			if (mode.equals("default") && !(currentid == MusicList.getList().size() - 1)) {
				nextid = currentid + 1;
			} else
			// 列表循环
			if (mode.equals("morecircle")) {

				nextid = (currentid == MusicList.getList().size() -1) ? 00 : currentid + 1;
			}
			JTable jTable = ViewList.getList().get(0).getTable();
			if (nextid == 0) {// 第一个
				// 选择从 0 到 01 之间（包含两端）的行
				jTable.setRowSelectionInterval(0, 0);

			} else {
				jTable.setRowSelectionInterval(nextid - 1, nextid);
			}
			this.stopplay();
			ThreadList.getList().clear();// 清除
			player = new PlayerThread(jSliderSound, jSliderBarProgress);
			player.setMusic(MusicList.getList().get(nextid));
			ThreadList.getList().add(player);
			player.start();
		}
	}

}
