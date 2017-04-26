package com.rupeng.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * @author 如鹏网(www.rupeng.com)
 * 
 */
public class GameCore
{

	private static boolean isStarted = false;
	private static GameDialog gameFrame;

	public static String getVersion()
	{
		//1.0.3版本：把“sprite、text、image”编号不存在的异常改成了System.err打印，这样可以避免多线程环境中的并发问题
		//1.0.4版本：GameDialog.currentPressedKeyCode改成volatile
		return "1.0.4";
	}

	/**
	 * 启动游戏线程，只能调用一次
	 * 
	 * @param runnable
	 */
	public static void start(Runnable runnable)
	{
		if (isStarted)
		{
			return;
		}

		if (ClassLoader.getSystemClassLoader().getResource("") == null)
		{
			throw new RuntimeException("游戏不能以jar打包方式运行");
		}

		// 尝试切换为windows风格的LookAndFeel，如果不支持，也不报错
		try
		{
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException ex)
		{
		} catch (InstantiationException ex)
		{
		} catch (IllegalAccessException ex)
		{
		} catch (UnsupportedLookAndFeelException ex)
		{
		}
		isStarted = true;
		gameFrame = new GameDialog(runnable);
		gameFrame.setVisible(true);
	}

	private static GameDialog getGameFrame()
	{
		if (gameFrame == null)
		{
			throw new RuntimeException(
					"游戏引擎未启动，确认是否已经提前调用了GameCore.start。不要直接在main方法中调用除了GameCore.start之外的其他方法");
		}
		return gameFrame;
	}

	/**
	 * 清屏
	 */
	public static void clear()
	{
		getGameFrame().clear();
	}

	/**
	 * 退出
	 */
	public static void exit()
	{
		System.exit(0);
	}

	/**
	 * 暂停ms毫秒
	 * 
	 * @param ms
	 */
	public static void pause(int ms)
	{
		GameUtils.pause(ms);
	}

	/**
	 * 设置游戏标题
	 * 
	 * @param title
	 */
	public static void setGameTitle(final String title)
	{
		getGameFrame().setGameTitle(title);
	}

	/**
	 * 加载背景图片
	 * 
	 * @param imgName
	 */
	public static void loadBgView(String imgName)
	{
		String imgPath = GameUtils.mapPath("Images/" + imgName);
		getGameFrame().loadBgView(imgPath);
	}

	/**
	 * 弹出消息框
	 * 
	 * @param msg
	 */
	public static void alert(final Object msg)
	{
		getGameFrame().alert(msg);
	}

	/**
	 * 弹出询问对话框
	 * 
	 * @param msg
	 * @return
	 */
	public static boolean confirm(final Object msg)
	{
		return getGameFrame().confirm(msg);
	}

	/**
	 * 弹出输入对话框
	 * 
	 * @param value
	 *            显示的默认值
	 * @param msg
	 *            提示消息
	 * @return
	 */
	public static String input(final Object value, final Object msg)
	{
		InputBoxResult result = getGameFrame().input(value, msg);
		if (result.getDialogResult())
		{
			return result.getValue();
		} else
		{
			return null;
		}
	}

	/**
	 * 播放音乐（异步）
	 * 
	 * @param soundName
	 *            音乐文件名（必须放到Sounds包下）
	 * @param repeat
	 *            是否重复播放
	 */
	public static void playSound(String soundName, boolean repeat)
	{
		getGameFrame().playSound(soundName, repeat);
	}

	/**
	 * 关闭播放的音乐
	 * 
	 * @param soundName
	 */
	public static void closeSound(String soundName)
	{
		getGameFrame().closeSound(soundName);
	}

	/**
	 * 按键的键码，可以和KeyEvent.vk_***比较
	 * 
	 * @return
	 */
	public static int getPressedKeyCode()
	{
		return getGameFrame().getPressedKeyCode();
	}

	/**
	 * 判断某个按键是否被按下了
	 * 
	 * @param keyCode
	 *            键码，来自于KeyEvent.vk_***
	 * @return
	 */
	public static boolean isKeyDown(int keyCode)
	{
		return keyCode == getPressedKeyCode();
	}

	/**
	 * 创建只读文本
	 * 
	 * @param txtNum
	 *            编号
	 * @param text
	 *            默认的文字
	 */
	public static void createText(int txtNum, String text)
	{
		getGameFrame().createText(txtNum, text);
	}

	/**
	 * 设置只读文本的文字
	 * 
	 * @param txtNum
	 *            文本编号
	 * @param text
	 *            显示的文字内容
	 */
	public static void setText(int txtNum, String text)
	{
		getGameFrame().setText(txtNum, text);
	}

	/**
	 * 设置文本的颜色
	 * 
	 * @param txtNum
	 *            文本编号
	 * @param color
	 *            颜色
	 */
	public static void setTextColor(final int txtNum, final Color color)
	{
		getGameFrame().setTextColor(txtNum, color);
	}

	/**
	 * 设置文字的字体大小
	 * 
	 * @param txtNum
	 *            文本编号
	 * @param size
	 *            字体大小
	 */
	public static void setTextFontSize(final int txtNum, final int size)
	{
		getGameFrame().setTextFontSize(txtNum, size);
	}

	/**
	 * 得到文本的字体大小
	 * 
	 * @param txtNum
	 * @return
	 */
	public static int getTextFontSize(final int txtNum)
	{
		return getGameFrame().getTextFontSize(txtNum);
	}

	/**
	 * 得到文本控件的大小
	 * 
	 * @param textNum
	 * @return
	 */
	public static Dimension getTextSize(final int textNum)
	{
		return getGameFrame().getTextSize(textNum);
	}

	public static int getTextWidth(final int textNum)
	{
		Dimension size = getTextSize(textNum);
		return size.width;
	}

	public static int getTextHeight(final int textNum)
	{
		Dimension size = getTextSize(textNum);
		return size.height;
	}

	/**
	 * 设置文字的位置
	 * 
	 * @param labelNum
	 * @param x
	 * @param y
	 */
	public static void setTextPosition(final int labelNum, final int x,
			final int y)
	{
		getGameFrame().setTextPosition(labelNum, x, y);
	}

	/**
	 * 得到文字的位置
	 * 
	 * @param num
	 * @return
	 */
	public static Point getTextPosition(final int num)
	{
		return getGameFrame().getTextPosition(num);
	}

	public static Point getTextPositionOnScreen(final int num)
	{
		return getGameFrame().getTextPositionOnScreen(num);
	}

	public static int getTextX(final int num)
	{
		Point pos = getTextPosition(num);
		return pos.x;
	}

	public static int getTextY(final int num)
	{
		Point pos = getTextPosition(num);
		return pos.y;
	}

	/**
	 * 隐藏文字
	 * 
	 * @param labelNum
	 */
	public static void hideText(final int labelNum)
	{
		getGameFrame().hideText(labelNum);
	}

	/**
	 * 显示文字
	 * 
	 * @param labelNum
	 */
	public static void showText(final int labelNum)
	{
		getGameFrame().showText(labelNum);
	}

	public static void removeText(final int labelNum)
	{
		getGameFrame().removeText(labelNum);
	}
	
	/**
	 * 创建精灵
	 * 
	 * @param spriteName
	 *            精灵包的名字（精灵的包必须放到Sprites包下）
	 * @param num
	 *            编号
	 */
	public static void createSprite(final int num, final String spriteName)
	{
		getGameFrame().createSprite(num, spriteName);
	}

	/**
	 * 播放精灵的动作
	 * 
	 * @param spriteNum
	 *            精灵编号
	 * @param animateName
	 *            动作名字
	 * @param repeat
	 *            是否重复播放
	 */
	public static void playSpriteAnimate(final int spriteNum,
			final String animateName, final boolean repeat)
	{
		getGameFrame().playSpriteAnimate(spriteNum, animateName, repeat);
	}

	/**
	 * 设置精灵的坐标
	 * 
	 * @param spriteNum
	 * @param x
	 * @param y
	 */
	public static void setSpritePosition(final int spriteNum, final int x,
			final int y)
	{
		getGameFrame().setSpritePosition(spriteNum, x, y);
	}

	/**
	 * 得到精灵的坐标
	 * 
	 * @param spriteNum
	 * @return
	 */
	public static Point getSpritePosition(final int spriteNum)
	{
		return getGameFrame().getSpritePosition(spriteNum);
	}

	/**
	 * 得到精灵在屏幕上的坐标
	 * 
	 * @param spriteNum
	 * @return
	 */
	public static Point getSpritePositionOnScreen(final int spriteNum)
	{
		return getGameFrame().getSpritePositionOnScreen(spriteNum);
	}

	public static int getSpriteX(final int spriteNum)
	{
		Point pos = getSpritePosition(spriteNum);
		return pos.x;
	}

	public static int getSpriteY(final int spriteNum)
	{
		Point pos = getSpritePosition(spriteNum);
		return pos.y;
	}

	/**
	 * 获得精灵的尺寸
	 * 
	 * @param spriteNum
	 * @return
	 */
	public static Dimension getSpriteSize(final int spriteNum)
	{
		return getGameFrame().getSpriteSize(spriteNum);
	}

	public static int getSpriteWidth(final int spriteNum)
	{
		Dimension size = getSpriteSize(spriteNum);
		return size.width;
	}

	public static int getSpriteHeight(final int spriteNum)
	{
		Dimension size = getSpriteSize(spriteNum);
		return size.height;
	}

	/**
	 * 隐藏精灵
	 * 
	 * @param spriteNum
	 */
	public static void hideSprite(final int spriteNum)
	{
		getGameFrame().hideSprite(spriteNum);
	}

	/**
	 * 显示精灵
	 * 
	 * @param spriteNum
	 */
	public static void showSprite(final int spriteNum)
	{
		getGameFrame().showSprite(spriteNum);
	}
	
	public static void removeSprite(final int spriteNum)
	{
		getGameFrame().removeSprite(spriteNum);
	}

	/**
	 * 设置精灵是否做X轴内容的翻转
	 * 
	 * @param spriteNum
	 * @param flipX
	 */
	public static void setSpriteFlipX(final int spriteNum, final boolean flipX)
	{
		getGameFrame().setSpriteFlipX(spriteNum, flipX);
	}

	/**
	 * 设置精灵是否做Y轴内容的翻转
	 * 
	 * @param spriteNum
	 * @param flipY
	 */
	public static void setSpriteFlipY(final int spriteNum, final boolean flipY)
	{
		getGameFrame().setSpriteFlipY(spriteNum, flipY);
	}

	/**
	 * 创建图片
	 * 
	 * @param num
	 *            编号
	 */
	public static void createImage(final int num)
	{
		getGameFrame().createImage(num, null);
	}

	/**
	 * 创建图片
	 * 
	 * @param num
	 *            编号
	 * @param imgName
	 *            图片的文件名（必须放在Images包下）
	 */
	public static void createImage(final int num, final String imgName)
	{
		if (imgName == null || imgName.trim().length() <= 0)
		{
			getGameFrame().createImage(num, null);
		} else
		{
			String imgPath = GameUtils.mapPath("Images/" + imgName);
			getGameFrame().createImage(num, imgPath);
		}
	}

	/**
	 * 设置显示的图片文件名
	 * 
	 * @param num
	 *            编号
	 * @param imgName
	 *            图片的文件名（必须放在Images包下）
	 */
	public static void setImageSource(final int num, final String imgName)
	{
		String imgPath = GameUtils.mapPath("Images/" + imgName);
		getGameFrame().setImageSource(num, imgPath);
	}

	/**
	 * 设置图片的坐标
	 * 
	 * @param num
	 * @param x
	 * @param y
	 */
	public static void setImagePosition(final int num, final int x, final int y)
	{
		getGameFrame().setImagePosition(num, x, y);
	}

	/**
	 * 获得图片的坐标
	 * 
	 * @param num
	 * @return
	 */
	public static Point getImagePosition(final int num)
	{
		return getGameFrame().getImagePosition(num);
	}

	public static Point getImagePositionOnScreen(final int num)
	{
		return getGameFrame().getImagePositionOnScreen(num);
	}

	public static int getImageX(final int num)
	{
		Point pos = getImagePosition(num);
		return pos.x;
	}

	public static int getImageY(final int num)
	{
		Point pos = getImagePosition(num);
		return pos.x;
	}

	/**
	 * 得到图片的大小
	 * 
	 * @param num
	 * @return
	 */
	public static Dimension getImageSize(final int num)
	{
		return getGameFrame().getImageSize(num);
	}

	public static int getImageWidth(final int num)
	{
		Dimension size = getImageSize(num);
		return size.width;
	}

	public static int getImageHeight(final int num)
	{
		Dimension size = getImageSize(num);
		return size.height;
	}

	/**
	 * 隐藏图片
	 * 
	 * @param num
	 */
	public static void hideImage(final int num)
	{
		getGameFrame().hideImage(num);
	}

	/**
	 * 显示图片
	 * 
	 * @param num
	 */
	public static void showImage(final int num)
	{
		getGameFrame().showImage(num);
	}
	
	public static void removeImage(final int num)
	{
		getGameFrame().removeImage(num);
	}

	/**
	 * 得到游戏窗口的大小
	 * 
	 * @return
	 */
	public static Dimension getGameSize()
	{
		final Dimension size = new Dimension();
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				size.setSize(getGameFrame().getSize());
			}
		});
		return size;
	}

	public static int getGameWidth()
	{
		Dimension size = getGameSize();
		return size.width;
	}

	public static int getGameHeight()
	{
		Dimension size = getGameSize();
		return size.height;
	}

	/**
	 * 设置游戏窗口的大小
	 * 
	 * @param width
	 * @param height
	 */
	public static void setGameSize(int width, int height)
	{
		final Dimension size = new Dimension(width, height);
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				getGameFrame().setSize(size);
				// 刷新，保证一直在屏幕居中
				getGameFrame().setLayout(null);
				getGameFrame().setLocationRelativeTo(null);
			}
		});
	}

	/**
	 * 在单独的线程中异步执行runnable
	 * 
	 * @param runnable
	 */
	public static void asyncRun(Runnable runnable)
	{
		Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * 得到一个对象的类型名（不包含包）
	 * 
	 * @param obj
	 */
	public static void showTypeName(Object obj)
	{
		if (obj == null)
		{
			alert("null");
			return;
		}
		GameCore.alert(obj.getClass().getName());
	}

	public static void addMouseListener(final MouseListener l)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				getGameFrame().addMouseListener(new MouseListener() {
					
					public void mouseReleased(final MouseEvent e)
					{
						asyncRun(new Runnable() {

							public void run()
							{
								l.mouseReleased(e);
							}
						});						
					}
					
					public void mousePressed(final MouseEvent e)
					{
						asyncRun(new Runnable() {

							public void run()
							{
								l.mousePressed(e);
							}
						});								
					}
					
					public void mouseExited(final MouseEvent e)
					{
						asyncRun(new Runnable() {

							public void run()
							{
								l.mouseExited(e);
							}
						});								
					}
					
					public void mouseEntered(final MouseEvent e)
					{
						asyncRun(new Runnable() {

							public void run()
							{
								l.mouseEntered(e);
							}
						});							
					}
					
					public void mouseClicked(final MouseEvent e)
					{
						asyncRun(new Runnable() {

							public void run()
							{
								l.mouseClicked(e);
							}
						});							
					}
				});
			}
		});
	}

	public static void addMouseMotionListener(final MouseMotionListener l)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				getGameFrame().addMouseMotionListener(new MouseMotionListener() {
					
					public void mouseMoved(final MouseEvent e)
					{
						asyncRun(new Runnable() {

							public void run()
							{
								l.mouseMoved(e);
							}
						});							
					}
					
					public void mouseDragged(final MouseEvent e)
					{
						asyncRun(new Runnable() {

							public void run()
							{
								l.mouseDragged(e);
							}
						});								
					}
				});
			}
		});
	}

	public static void addKeyListener(final KeyListener l)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				getGameFrame().addKeyListener(new KeyListener() {

					public void keyTyped(final KeyEvent e)
					{
						//要运行在单独的线程中，否则keyTyped等中进行长时间UI操作会造成卡死
						//比如按键触发精灵弹跳，只会卡一会，然后精灵不动，因为向上向下的UI操作都没有显示
						asyncRun(new Runnable() {

							public void run()
							{
								l.keyTyped(e);
							}
						});
					}

					public void keyReleased(final KeyEvent e)
					{
						asyncRun(new Runnable() {

							public void run()
							{
								l.keyReleased(e);
							}
						});
					}

					public void keyPressed(final KeyEvent e)
					{
						asyncRun(new Runnable() {

							public void run()
							{
								l.keyPressed(e);
							}
						});

					}
				});
			}
		});
	}

	private static final Random random = new Random();

	/**
	 * 取[low,high)之间的随机整数
	 * 
	 * @param low
	 *            取的最低值（能取）
	 * @param high
	 *            取的最高值（不能取）
	 * @return
	 */
	public static int rand(int low, int high)
	{
		synchronized (random)
		{
			if (high < low)
			{
				throw new IllegalArgumentException("low不能大于high");
			}
			return low + random.nextInt(high - low);
		}
	}

	// 定时器的列表
	private static List<Timer> timers = new ArrayList<Timer>();

	/**
	 * 创建一个定时器，每隔millSec毫秒执行一次runnable的run方法
	 * 
	 * @param runnable
	 * @param millSec
	 * @return 定时器Id
	 */
	public static synchronized int setInterval(final Runnable runnable,
			int millSec)
	{
		Timer timer = new Timer(millSec, new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				asyncRun(runnable);//要异步去运行
			}
		});
		timer.start();
		timers.add(timer);
		return timers.size() - 1;// 用timer在timers集合中的序号做id，因为clearInterval只是stop，并不remove，因此id不会重复
	}

	/**
	 * 停止定时器
	 * 
	 * @param intervalId
	 *            定时器的id
	 */
	public static synchronized void clearInterval(int intervalId)
	{
		Timer timer = timers.get(intervalId);
		if (timer == null)
		{
			throw new IllegalArgumentException("不存在的定时器Id" + intervalId);
		}
		timer.stop();
	}

    public static void removeAllNoNameText() {
        getGameFrame().removeAllNoNameText();
    }
    public static void removeNoNameText(String text) {
        getGameFrame().removeNoNameText(text);
    }

    public static void createNoNameTextWithPos(String text, int x, int y) {
        getGameFrame().createText(text, x, y);
    }

    public static void createNoNameImageWithPos(String imageName, int x, int y) {
        String imgPath = GameUtils.mapPath("Images/" + imageName);
        getGameFrame().createImage(imgPath, x, y);
    }

    public static void removeAllNoNameImage() {
        getGameFrame().removeAllNoNameImage();
    }
    
    public static void showMenu() {
        getGameFrame().showMenu();
    }
}
