package com.billy.rpg.game.screen;

import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.rupeng.game.GameUtils;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * to show animation (animation, magic )
 * 
 * @author liulei
 *
 */
public class AnimationScreen extends BaseScreen {
    private List<Image> images = new ArrayList<Image>();
    private int currentFrameIndex = 0;// 当前帧的序号
    private boolean isRepeat = true;// 是否重复播放
    private String spriteName;// 精灵的名字
    private File spritePath;// 精灵文件的目录
    private int no;
    
    /**
     * 
     * @param name
     * @param no number of animation
     * @param repeat repeat or not
     */
    public AnimationScreen(String name, int no, boolean repeat) {
        this.spriteName = name;
        if (StringUtils.isEmpty(name)) {
            spriteName = "skill";
        }
        this.no = no;
        isRepeat = repeat;

        addImages();
    }
    
    private void addImages() {
        try {
            String packageName = "Sprites/"+spriteName + "/" + no+"/";
            URL rootURL = GameUtils.class.getClassLoader().getResource(packageName);
            if(rootURL==null) {
                throw new RuntimeException("找不到精灵"+spriteName
                        +"下名字为"+no+"的动作");
            }
            String[] pngFiles;
                pngFiles = GameUtils.listResources(packageName, ".png");
            Arrays.sort(pngFiles,new FileNameIntComparator());// 根据文件名排序(0.png,1.png,02.png,.....11.png,12.png)
            
            spritePath = new File(GameUtils.mapPath("Sprites/" + spriteName));
            if (!spritePath.exists()) {
                throw new RuntimeException("名字为"+spriteName+"的精灵没有找到");
            }
            
            for (String pngUrl : pngFiles) {
                images.add(ImageIO.read(new FileInputStream(spritePath + "/" + no + "/" + pngUrl)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }

    private long cnt = 0;
    
    @Override
    public void update(long delta) {
        cnt += delta;
        if (cnt > 200) { //
            cnt = 0;
            currentFrameIndex++;
        }
        
        if (currentFrameIndex >= images.size()) {
            if (isRepeat) {
                currentFrameIndex = 0;
                cnt = 0;
            } else {
                GameFrame.getInstance().popScreen();
//                MainFrame.getInstance().changeScreen(1);
            }
        }
    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        Image image = images.get(currentFrameIndex);
        
        BufferedImage paint = new BufferedImage(
                image.getWidth(null), 
                image.getHeight(null), 
                BufferedImage.TYPE_4BYTE_ABGR);

        Graphics g = paint.getGraphics();
        g.setColor(new Color(255, 255, 255, 255));
//        g.clearRect(0, 0, image.getWidth(null), image.getHeight(null));
//        g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
        g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
        gameCanvas.drawBitmap(paint, 400, 240);
    }

    @Override
    public void onKeyDown(int key) {
        GameFrame.getInstance().changeScreen(1);
    }

    @Override
    public void onKeyUp(int key) {
        
    }
    
    

   
    @Override
    public boolean isPopup() {
        return true;
    }




    private class FileNameIntComparator implements Comparator<String> {
        
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

