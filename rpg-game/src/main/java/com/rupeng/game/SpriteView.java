package com.rupeng.game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.Timer;

class SpriteView extends PictureBox {
    private static final long serialVersionUID = 1L;
    private List<File> currentFrames = new ArrayList<File>();// 当前精灵、当前动作的帧图片路径
	private int currentFrameIndex = 0;// 当前帧的序号
	private boolean isRepeat;// 是否重复播放

	private String spriteName;// 精灵的名字

	private File spritePath;// 精灵文件的目录
	private Timer playTimer;// 播放动画用的定时器

	public SpriteView(String spriteName) {
		this.spriteName = spriteName;

		spritePath = new File(GameUtils.mapPath("Sprites/" + spriteName));
		if(!spritePath.exists())
		{
			throw new RuntimeException("名字为"+spriteName+"的精灵没有找到");
		}
		this.playTimer = new Timer(200, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				playTimer_Tick();
			}
		});
	}

	// / <summary>
	// / 获得帧动画中尺寸最大的图片的尺寸
	// / </summary>
	// / <param name="files"></param>
	// / <returns></returns>
	static Dimension getMaxPicSize(List<File> files) throws IOException {
		// 以宽高中的最大值为准
		int maxWidth = 0, maxHeight = 0;
		for (File file : files) {
			BufferedImage img = ImageIO.read(file);
			if (img.getWidth() > maxWidth) {
				maxWidth = img.getWidth();
			}
			if (img.getHeight() > maxHeight) {
				maxHeight = img.getHeight();
			}
		}
		return new Dimension(maxWidth, maxHeight);
	}

	void playTimer_Tick() {
		if (currentFrameIndex <= currentFrames.size() - 1) {
			File imgFile = currentFrames.get(currentFrameIndex);
			try {
				setImageFile(imgFile);
			} catch (FileNotFoundException e) {
				System.out.println("没有找到图片" + imgFile);
				return;
			} catch (IOException e) {
				System.out.println("加载图片" + imgFile + "失败，" + e.getMessage());
				return;
			}
		}

		// 切换为下一张图片，供下次Timer播放
		if (currentFrameIndex < currentFrames.size() - 1) {
			currentFrameIndex++;
		} else if (isRepeat) {
			currentFrameIndex = 0;// 如果播放到最后一张，则重新播放第一张
		}
	}
	
	public void stop()
	{
		playTimer.stop();
	}

	public void playAnimateAsync(String animateName, boolean repeat) {
		try {
			doPlayAnimateAsync(animateName, repeat);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void doPlayAnimateAsync(String animateName, boolean repeat)
			throws IOException {
		this.isRepeat = repeat;

		playTimer.stop();
		currentFrames.clear();
		currentFrameIndex = 0;

		String packageName = "Sprites/"+spriteName + "/" + animateName+"/";
		URL rootURL = GameUtils.class.getClassLoader().getResource(packageName);
		if(rootURL==null)
		{
			throw new RuntimeException("找不到精灵"+spriteName
					+"下名字为"+animateName+"的动作");
		}
		String[] pngFiles = GameUtils.listResources(packageName, ".png");
		Arrays.sort(pngFiles,new FileNameIntComparator());// 根据文件名排序(0.png,1.png,02.png,.....11.png,12.png)
		
		File animateDir = new File(spritePath,animateName);
		for (String pngUrl : pngFiles) {
			currentFrames.add(new File(animateDir,pngUrl));
		}

		Dimension maxSize = getMaxPicSize(currentFrames);// 获得帧动画中尺寸最大的图片作为控件的大小
		setSize(maxSize);

		playTimer.start();
	}
	
	private class FileNameIntComparator implements Comparator<String>
	{
		
		private int tryParseInt(String s)
		{
			Pattern pattern = Pattern.compile("(\\d+)",Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(s);
			if(matcher.find())//提取第一个匹配的数字
			{
				String group =matcher.group();
				return Integer.parseInt(group);
			}
			else
			{
				return 0;
			}
		}

		public int compare(String o1, String o2)
		{						
			return tryParseInt(o1)-tryParseInt(o2);
		}
		
	}
}
