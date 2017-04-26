package com.rupeng.game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

class PictureBox extends JLabel
{
	private boolean flipX = false;
	private boolean flipY = false;

	
	
	public boolean isFlipX()
	{
		return flipX;
	}

	/**
	 * 设置y轴镜像反转
	 * 
	 * @param flipX
	 */
	public void setFlipX(boolean flipX)
	{
		this.flipX = flipX;
		repaint();
	}

	public boolean isFlipY()
	{
		return flipY;
	}

	/**
	 * 设置x轴镜像反转
	 * 
	 * @param flipY
	 */
	public void setFlipY(boolean flipY)
	{
		this.flipY = flipY;
		repaint();
	}

	private static final long serialVersionUID = 1L;
	private Image image;

	private Object tag;

	public void setImageStream(InputStream inStream) throws IOException
	{
		image = ImageIO.read(inStream);
		setSize(image.getWidth(this), image.getHeight(this));// 自动调整控件大小
		repaint();
	}

	public void setImageFile(File imgFile) throws FileNotFoundException,
			IOException
	{
		InputStream inStream = null;
		try
		{
			inStream = new FileInputStream(imgFile);
			setImageStream(inStream);
		} finally
		{
			GameUtils.closeQuietly(inStream);
		}
	}

	public void setImagePath(String imgPath) throws FileNotFoundException,
			IOException
	{
		setImageFile(new File(imgPath));
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (null == image)
		{
			return;
		}
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		//thanks to http://www.javaworld.com/article/2077530/learn-java/java-tip-32--you-ll-flip-over-java-images----literally-.html
		if (isFlipX() && !isFlipY())//X轴翻转，水平翻转
		{
			g.drawImage(image, w, 0, 0, h, 0, 0, w, h, null);
		} else if (isFlipY() && !isFlipX())//Y轴翻转，垂直翻转
		{
			g.drawImage(image, 0, h, w, 0, 0, 0, w, h, null);
			
		} else if (isFlipY() && isFlipX())//同时X轴和Y轴翻转
		{
			g.drawImage(image, 0, h, w, 0, 0, 0, w, h, this);
		} else//不翻转
		{
			g.drawImage(image, 0, 0, this);
		}

	}

	public Object getTag()
	{
		return this.tag;
	}

	public void setTag(Object tag)
	{
		this.tag = tag;
	}
}
