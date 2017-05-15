package com.rupeng.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.billy.rpg.game.scriptParser.display.GuiDisplay;

class GameDialog extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(GameDialog.class);
    
    private BufferedImage bgImage;
	private Thread gameThread;
	private GuiDisplay guiDisplay; 
	
    @Override
    public void paint(Graphics g) {
//        super.paint(g); // this cause screen-blink
        guiDisplay.display(g);
        GameUtils.pause(50);
    }
    
    private HashMap<String, AsyncAudioPlayer> audioPlayersMap = new HashMap<String, AsyncAudioPlayer>();

	//使用volatile，避免某些情况下getPressedKeyCode读取的是拷贝的问题(这样按键读取的一直是-1，即使按下某个键)
	private volatile int currentPressedKeyCode = -1;// 当前的按的按键

	public Container getGameContainer()
	{
		return this.getContentPane();
	}
	
	public GameDialog(final Runnable runnable)
	{
		setLayout(null);
		setSize(1000, 600);
		setLocationRelativeTo(null);//窗口居中
		setResizable(false);
		setGameTitle("GAME TITLE HERE");
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)
			{
				super.keyPressed(e);
				currentPressedKeyCode = e.getKeyCode();
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				super.keyReleased(e);
				resetKeyCode();
			}
		});

		PaintableCanvas bgCanvas = new PaintableCanvas();
		bgCanvas.setLocation(0, 0);
		bgCanvas.setSize(getSize());
		bgCanvas.setLayout(null);//如果不写这个，那么在任务栏切换窗口的时候，所有子元素的Y坐标都会变为0
		setContentPane(bgCanvas);
		
		bgCanvas.setPaintListener(new PaintListener() {

			public void paintComponent(Graphics g)
			{
				if (bgImage != null)
				{
					g.drawImage(bgImage, 0, 0, GameDialog.this);
				}
			}
		});

		gameThread = new Thread(new Runnable() {
			public void run() 	{
				try {
					runnable.run();
				} catch(Exception ex) {
					ex.printStackTrace();
				}  finally {//无论是否发生异常，run执行结束后游戏都退出
				//如果游戏主逻辑中出现异常，run就会出现异常。但是子线程中如果异常，则程序仍然会继续运行
					System.exit(0);
				}
			}
		});
		gameThread.setDaemon(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e)
			{
				bgImage = new BufferedImage(GameDialog.this.getWidth(),
						GameDialog.this.getHeight(),
						BufferedImage.TYPE_INT_ARGB);
				gameThread.start();
			}

			@Override
			public void windowClosing(WindowEvent e)
			{
				GameCore.exit();
			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
				super.windowDeactivated(e);
				resetKeyCode();
				// 加入按下一个按键的时候触发了msgBox，那么就只有keyPressed没有keyReleased
				// 因此在弹出窗口导致的windowDeactivated中重置按键键码
			}
		});
		
		guiDisplay = new GuiDisplay();
	}



    private void resetKeyCode()
	{
		currentPressedKeyCode = -1;// 按键复位清空
	}

	public int getPressedKeyCode()
	{
		return this.currentPressedKeyCode;
	}

	private void autoSizeLabel(JLabel label)
	{
		Font font = label.getFont();
		FontMetrics fontMetrics = label.getFontMetrics(font);
		int width = fontMetrics.stringWidth(label.getText());
		int height = fontMetrics.getHeight();
		label.setSize(width, height);
	}

	public void createText(final int txtNum, final String text)
	{
		GameUtils.invokeAndWait(new Runnable() {
			public void run()
			{
				if (findLabelByNum(txtNum) != null)
				{
					throw new IllegalArgumentException("编号为" + txtNum
							+ "的文本已经存在，不能重复创建");
				}

				JLabel label = new JLabel();
				label.setName(txtNum + "");
				label.setFont(new Font("宋体", Font.BOLD, 30));
				label.setText(text);				
				label.setForeground(Color.BLACK);
				label.setVisible(true);
				GameDialog.this.getGameContainer().add(label);
				
				autoSizeLabel(label);
			}
		});

	}

	public void setText(final int txtNum, final String text)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				JLabel label = findLabelByNum(txtNum);
				if (label == null)
				{
					LOG.info("Text编号" + txtNum + "找不到");
					return;
				}
				label.setText(text);
				autoSizeLabel(label);
			}
		});
	}

	JLabel findLabelByName(String text)
    {
        for (Component component : this.getGameContainer().getComponents())
        {
            if (component instanceof JLabel)
            {
                JLabel label = (JLabel) component;
                if (!(label instanceof PictureBox)
                        && label.getName().equals(text))
                {
                    return label;
                }
            }
        }
        return null;
    }
	
	
	JLabel findLabelByNum(int txtNum)
	{
	    return findLabelByName(txtNum + "");
	}

	public void setTextColor(final int labelNum, final Color color)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				JLabel label = findLabelByNum(labelNum);
				if (label == null)
				{
					LOG.info("Text编号" + labelNum + "找不到");
					return;
				}
				label.setForeground(color);
				autoSizeLabel(label);
			}
		});
	}

	public void setTextFontSize(final int labelNum, final int size)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				JLabel label = findLabelByNum(labelNum);
				if (label == null)
				{
					LOG.info("Text编号" + labelNum + "找不到");
					return;
				}
				Font oldFont = label.getFont();
				Font newFont = new Font(oldFont.getFontName(), oldFont
						.getStyle(), size);
				label.setFont(newFont);
				autoSizeLabel(label);
			}
		});
	}

	public int getTextFontSize(final int labelNum)
	{
		final ObjectWrapper value = new ObjectWrapper();
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				JLabel label = findLabelByNum(labelNum);
				if (label == null)
				{
					LOG.info("Text编号" + labelNum + "找不到");
					return;
				}
				Font oldFont = label.getFont();
				value.setValue(oldFont.getSize());
			}
		});
		return (Integer)value.getValue();
	}
	
	public Dimension getTextSize(final int textNum)
	{
		final Dimension size = new Dimension();
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				JLabel label = findLabelByNum(textNum);
				if (label == null)
				{
					LOG.info("Text编号" + textNum + "找不到");
					return;
				}
				size.setSize(label.getSize());
			}
		});
		return size;
	}
	
	public void setTextPosition(final int labelNum, final int x, final int y)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				JLabel label = findLabelByNum(labelNum);
				if (label == null)
				{
					LOG.info("Text编号" + labelNum + "找不到");
					return;
				}
				label.setLocation(x, y);
			}
		});
	}

	public Point getTextPosition(final int num)
	{
		final Point pos = new Point();
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				JLabel label = findLabelByNum(num);
				if (label == null)
				{
					LOG.info("Text编号" + num + "找不到");
					return;
				}
				pos.setLocation(label.getLocation());
			}
		});
		return pos;
	}
	
	public Point getTextPositionOnScreen(final int num)
	{
		final Point pos = new Point();
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				JLabel label = findLabelByNum(num);
				if (label == null)
				{
					LOG.info("Text编号" + num + "找不到");
					return;
				}
				pos.setLocation(label.getLocationOnScreen());
			}
		});
		return pos;
	}

	public void hideText(final int labelNum)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				JLabel label = findLabelByNum(labelNum);
				if (label == null)
				{
					LOG.info("Text编号" + labelNum + "找不到");
					return;
				}
				label.setVisible(false);
				autoSizeLabel(label);
			}
		});
	}
	
	public void removeText(final int txtNum)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				JLabel label = findLabelByNum(txtNum);
				if (label == null)
				{
					LOG.info("Text编号" + txtNum + "找不到");
					return;
				}
				label.setVisible(false);
				getGameContainer().remove(label);
			}
		});
	}

	public void showText(final int labelNum)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				JLabel label = findLabelByNum(labelNum);
				if (label == null)
				{
					LOG.info("Text编号" + labelNum + "找不到");
					return;
				}
				label.setVisible(true);
				autoSizeLabel(label);
			}
		});
	}

	public void createSprite(final int num,final String spriteName)
	{
		GameUtils.invokeAndWait(new Runnable() {
			public void run()
			{
				if (findSpriteViewByNum(num) != null)
				{
					throw new IllegalArgumentException("编号为" + num
							+ "的Sprite已经存在，不能重复创建");
				}
				SpriteView view = new SpriteView(spriteName);
				getGameContainer().add(view);
				view.setTag(num);
			}
		});
	}

	// / <summary>
	// / 查找编号为numToFind的精灵
	// / </summary>
	// / <param name="numToFind"></param>
	// / <returns></returns>
	SpriteView findSpriteViewByNum(int numToFind)
	{
		for (Component ctrl : getGameContainer().getComponents())
		{
			if(ctrl instanceof SpriteView)
			{
				SpriteView spriteView = (SpriteView) ctrl;
				int num = (Integer) spriteView.getTag();
				if (num == numToFind)
				{
					return spriteView;
				}
			}
		}
		return null;
	}

	public void playSpriteAnimate(final int spriteNum,
			final String animateName, final boolean repeat)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				SpriteView spriteView = findSpriteViewByNum(spriteNum);
				if (spriteView == null)
				{
					LOG.info("找不到编号为" + spriteNum+ "的精灵");
					return;
				}
				spriteView.playAnimateAsync(animateName, repeat);

			}
		});
	}

	public void setSpritePosition(final int spriteNum, final int x, final int y)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				SpriteView spriteView = findSpriteViewByNum(spriteNum);
				if (spriteView == null)
				{
					LOG.info("找不到编号为" + spriteNum+ "的精灵");
					return;
				}
				spriteView.setLocation(x, y);
			}
		});
	}

	public Point getSpritePosition(final int spriteNum)
	{
		final Point pos = new Point();
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				SpriteView spriteView = findSpriteViewByNum(spriteNum);
				if (spriteView == null)
				{
					LOG.info("找不到编号为" + spriteNum+ "的精灵");
					return;
				}
				pos.setLocation(spriteView.getLocation());
			}
		});
		return pos;
	}
	
	public Point getSpritePositionOnScreen(final int spriteNum)
	{
		final Point pos = new Point();
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				SpriteView spriteView = findSpriteViewByNum(spriteNum);
				if (spriteView == null)
				{
					LOG.info("找不到编号为" + spriteNum+ "的精灵");
					return;
				}
				pos.setLocation(spriteView.getLocationOnScreen());
			}
		});
		return pos;
	}

	public Dimension getSpriteSize(final int spriteNum)
	{
		final Dimension size = new Dimension();
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				SpriteView spriteView = findSpriteViewByNum(spriteNum);
				if (spriteView == null)
				{
					LOG.info("找不到编号为" + spriteNum+ "的精灵");
					return;
				}
				size.setSize(spriteView.getSize());
			}
		});
		return size;
	}

	public void hideSprite(final int spriteNum)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				SpriteView spriteView = findSpriteViewByNum(spriteNum);
				if (spriteView == null)
				{
					LOG.info("找不到编号为" + spriteNum+ "的精灵");
					return;
				}
				spriteView.setVisible(false);
			}
		});
	}
	
	public void removeSprite(final int spriteNum)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				SpriteView spriteView = findSpriteViewByNum(spriteNum);
				if (spriteView == null)
				{
					LOG.info("找不到编号为" + spriteNum+ "的精灵");
					return;
				}
				spriteView.setVisible(false);
				spriteView.stop();
				getGameContainer().remove(spriteView);				
			}
		});
	}

	public void showSprite(final int spriteNum)
	{
		GameUtils.invokeAndWait(new Runnable() {
			public void run()
			{
				SpriteView spriteView = findSpriteViewByNum(spriteNum);
				if (spriteView == null)
				{
					LOG.info("找不到编号为" + spriteNum+ "的精灵");
					return;
				}
				spriteView.setVisible(true);
			}
		});
	}

	public void setSpriteFlipX(final int spriteNum, final boolean flipX)
	{
		GameUtils.invokeAndWait(new Runnable() {
			public void run()
			{
				SpriteView spriteView = findSpriteViewByNum(spriteNum);
				if (spriteView == null)
				{
					LOG.info("找不到编号为" + spriteNum+ "的精灵");
					return;
				}
				spriteView.setFlipX(flipX);
			}
		});
	}

	public void setSpriteFlipY(final int spriteNum, final boolean flipY)
	{
		GameUtils.invokeAndWait(new Runnable() {
			public void run()
			{
				SpriteView spriteView = findSpriteViewByNum(spriteNum);
				if (spriteView == null)
				{
					LOG.info("找不到编号为" + spriteNum+ "的精灵");
					return;
				}
				spriteView.setFlipY(flipY);
			}
		});
	}

	public void loadBgView(String imgPath)
	{
		try
		{
			if(!new File(imgPath).exists())
			{
				throw new FileNotFoundException("文件"+imgPath+"不存在");
			}
			this.bgImage = ImageIO.read(new File(imgPath));
		} catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		this.repaint();
	}

	public void clear()
	{
		GameUtils.invokeAndWait(new Runnable() {
			public void run()
			{
				getGameContainer().removeAll();
				bgImage = new BufferedImage(GameDialog.this.getWidth(),
						GameDialog.this.getHeight(),
						BufferedImage.TYPE_INT_ARGB);
				repaint();
			}
		});
	}

	public void alert(final Object msg)
	{
		GameUtils.invokeAndWait(new Runnable() {
			public void run()
			{
				JOptionPane.showMessageDialog(GameDialog.this, msg);
			}
		});
	}

	public boolean confirm(final Object msg)
	{
		final InputBoxResult result = new InputBoxResult();
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				boolean r = JOptionPane.showConfirmDialog(GameDialog.this, msg,
						"", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
				result.setDialogResult(r);
			}
		});
		return result.getDialogResult();
	}

	public InputBoxResult input(final Object value, final Object msg)
	{
		final InputBoxResult result = new InputBoxResult();
		GameUtils.invokeAndWait(new Runnable() {
			public void run()
			{
				String returnValue = JOptionPane.showInputDialog(
						GameDialog.this, msg, value);
				result.setValue(returnValue);
				result.setDialogResult(true);
			}
		});
		return result;
	}

	public void playSound(String soundName, boolean repeat)
	{
		String soundPath = GameUtils.mapPath("Sounds/" + soundName);
		if(!new File(soundPath).exists())
		{
			throw new RuntimeException("文件"+soundPath+"不存在");
		}
		//播放器缓存起来，提高以后的加载速度
		AsyncAudioPlayer player = audioPlayersMap.get(soundPath);
		if (player == null)
		{
			player = new AsyncAudioPlayer(soundPath, repeat);
			audioPlayersMap.put(soundPath, player);
		}

		player.playAsync();
	}

	public void closeSound(String soundName)
	{
		String soundPath = GameUtils.mapPath("Sounds/" + soundName);
		AsyncAudioPlayer player = audioPlayersMap.get(soundPath);
		if (player != null)
		{
			player.close();
		}
	}

	public void createImage(final int num, final String imgSrc) {
		GameUtils.invokeAndWait(new Runnable() {
			public void run() {
				if (findPictureBoxByNum(num) != null) {
					throw new IllegalArgumentException("编号为" + num
							+ "的图片已经存在，不能重复创建");
				}

				PictureBox picBox = new PictureBox();
				if (imgSrc != null && imgSrc.trim().length() > 0) {
					try {
						picBox.setImagePath(imgSrc);
					} catch (FileNotFoundException e) {
						throw new RuntimeException("图片找不到" + imgSrc, e);
					} catch (IOException e) {
						throw new RuntimeException("图片找不到" + imgSrc, e);
					}
				}
				picBox.setName(num + "");
				picBox.setLocation(10, 10);
				picBox.setVisible(true);
				GameDialog.this.getGameContainer().add(picBox);
			}
		});
	}

    public void createImage(final String imageName, final int x, final int y) {
        GameUtils.invokeAndWait(new Runnable() {
            public void run() {
                PictureBox picBox = new PictureBox();
                if (imageName != null && imageName.trim().length() > 0) {
                    try {
                        picBox.setImagePath(imageName);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException("图片找不到" + imageName, e);
                    } catch (IOException e) {
                        throw new RuntimeException("图片找不到" + imageName, e);
                    }
                }
                picBox.setName("nonameimg" + UUID.randomUUID().toString());
                picBox.setLocation(x, y);
                picBox.setVisible(true);
                GameDialog.this.getGameContainer().add(picBox);
            }
        });
    }
    
    
	public void setImageSource(final int num, final String imgSrc)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				PictureBox picBox = findPictureBoxByNum(num);
				if (picBox == null)
				{
					LOG.info("编号为" + num + "的Image找不到");
					return;
				}
				try
				{
					picBox.setImagePath(imgSrc);
				} catch (FileNotFoundException e)
				{
					throw new RuntimeException("图片找不到" + imgSrc, e);
				} catch (IOException e)
				{
					throw new RuntimeException("图片找不到" + imgSrc, e);
				}
			}
		});
	}

	PictureBox findPictureBoxByNum(int num)
	{
		for (Component component : this.getGameContainer().getComponents())
		{
			if (component instanceof PictureBox
					&& !(component instanceof SpriteView))
			{
				PictureBox picBox = (PictureBox) component;
				// SpriteView也是继承自PictureBox
				if (picBox.getName() != null
						&& picBox.getName().equals(num + "")
						&& !(picBox instanceof SpriteView))
				{
					return picBox;
				}
			}
		}
		return null;
	}

	public void setImagePosition(final int num, final int x, final int y)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				PictureBox picBox = findPictureBoxByNum(num);
				if (picBox == null)
				{
					LOG.info("编号为" + num + "的Image找不到");
					return;
				}
				picBox.setLocation(x, y);
				
			}
		});
	}

	public Point getImagePosition(final int num)
	{
		final Point pos = new Point();
		GameUtils.invokeAndWait(new Runnable() {
			public void run()
			{
				PictureBox label = findPictureBoxByNum(num);
				if (label == null)
				{
					LOG.info("编号为" + num + "的Image找不到");
					return;
				}
				pos.setLocation(label.getLocation());
			}
		});
		return pos;
	}
	
	public Point getImagePositionOnScreen(final int num)
	{
		final Point pos = new Point();
		GameUtils.invokeAndWait(new Runnable() {
			public void run()
			{
				PictureBox label = findPictureBoxByNum(num);
				if (label == null)
				{
					LOG.info("编号为" + num + "的Image找不到");
					return;
				}
				pos.setLocation(label.getLocationOnScreen());
			}
		});
		return pos;
	}

	public Dimension getImageSize(final int imgNum)
	{
		final Dimension size = new Dimension();
		GameUtils.invokeAndWait(new Runnable() {
			public void run()
			{
				PictureBox img = findPictureBoxByNum(imgNum);
				if (img == null)
				{
					LOG.info("编号为" + imgNum + "的Image找不到");
					return;
				}
				size.setSize(img.getSize());
			}
		});
		return size;
	}

	public void hideImage(final int num)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				PictureBox picBox = findPictureBoxByNum(num);
				if (picBox == null)
				{
					LOG.info("编号为" + num + "的Image找不到");
					return;
				}
				picBox.setVisible(false);
			}
		});
	}
	
	public void removeImage(final int num)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				PictureBox picBox = findPictureBoxByNum(num);
				if (picBox == null)
				{
					LOG.info("编号为" + num + "的Image找不到");
					return;
				}
				picBox.setVisible(false);
				getGameContainer().remove(picBox);
			}
		});
	}

	public void showImage(final int num)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				PictureBox picBox = findPictureBoxByNum(num);
				if (picBox == null)
				{
					LOG.info("编号为" + num + "的Image找不到");
					return;
				}
				picBox.setVisible(true);
			}
		});
	}

	public void setGameTitle(final String title)
	{
		GameUtils.invokeAndWait(new Runnable() {

			public void run()
			{
				GameDialog.this.setTitle(title);
			}
		});
	}

    public void removeAllNoNameText() {
        List<JLabel> list2del = new ArrayList<JLabel>();
        for (Component component : this.getGameContainer().getComponents()) {
            if (component instanceof JLabel)
            {
                JLabel label = (JLabel) component;
                if (!(label instanceof PictureBox)
                        && label.getName().startsWith("nonname"))
                {
                    list2del.add(label);
                }
            }
        }
        for (JLabel label : list2del) {
            label.setVisible(false);
            getGameContainer().remove(label);
        }
    }

    public void removeNoNameText(final String text) {
        GameUtils.invokeAndWait(new Runnable() {

            public void run()
            {
                JLabel label = findLabelByName("nonname" + text);
                if (label == null)
                {
                    LOG.info("Text" + text + "");
                    return;
                }
                label.setVisible(false);
                getGameContainer().remove(label);
            }
        });
    }

    public void createText(final String text, final int x, final int y)
    {
        GameUtils.invokeAndWait(new Runnable() {
            public void run()
            {
                          
                JLabel label = new JLabel();
                label.setName("nonname" + UUID.randomUUID().toString());
                label.setFont(new Font("黑体", Font.BOLD, 30));
                label.setText(text);                
                label.setForeground(Color.BLACK);
                label.setVisible(true);
                GameDialog.this.getGameContainer().add(label);
                label.setLocation(x, y);
                autoSizeLabel(label);
            }
        });
        
    }

    public void removeAllNoNameImage() {
        List<JLabel> list2del = new ArrayList<JLabel>();
        for (Component component : this.getGameContainer().getComponents()) {
            if (component instanceof JLabel)
            {
                JLabel label = (JLabel) component;
                if (label instanceof PictureBox
                        && label.getName() != null && label.getName().startsWith("nonameimg"))
                {
                    list2del.add(label);
                }
            }
        }
        for (JLabel label : list2del) {
            label.setVisible(false);
            getGameContainer().remove(label);
        }
    }

    public void showMenu() {
        
    }

}
