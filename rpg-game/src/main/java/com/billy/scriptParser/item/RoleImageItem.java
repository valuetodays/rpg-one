package com.billy.scriptParser.item;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.billy.scriptParser.bean.LoaderBean;
import com.billy.scriptParser.loader.image.IImageLoader;
import com.rupeng.game.GameUtils;

public class RoleImageItem implements IImageLoader, IItem {
    private Image roleFull1;
    
    
    @Override
    public List<LoaderBean> load() throws Exception {
        loadRole();   // TODO change to loadImage(); ??
        return null;
    }
    
    private boolean loaded = false;
    
    private void loadRole() {
        if (loaded) {
            return ;
        }
        String imgPath = GameUtils.mapPath("Images/") + "/";
        
        try {
        	roleFull1 = ImageIO.read(new FileInputStream(imgPath + "role_full_1.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        loaded = true;
    }

    public Image getRoleFull1() {
        return roleFull1;
    }
    

}
