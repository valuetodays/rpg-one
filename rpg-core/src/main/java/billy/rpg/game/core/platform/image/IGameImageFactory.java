package billy.rpg.game.core.platform.image;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import billy.rpg.game.core.platform.Platform;
import billy.rpg.game.core.util.CoreUtil;

public class IGameImageFactory {
    public static Bitmap getBitmap(String path) {
        if (path.charAt(0) == '/') {
            path = path.substring(1);
        }
        Context context = Platform.getCtx();
        AssetManager assets = context.getAssets();
        Bitmap bitmap = null;
        try {
            AssetFileDescriptor assetFileDescriptor = assets.openNonAssetFd(path);
            FileInputStream is = assetFileDescriptor.createInputStream();
            if (is != null) {
                bitmap = BitmapFactory.decodeStream(is);
//                Log.i(TAG, bitmap.getWidth() + ",");
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap == null) {
            throw new NullPointerException("no bitmap in specific path: " + path);
        }
        return bitmap;
    }

    public static IGameImage getImage(String path) {
        IGameImage iGameImage = null;
        if (Platform.getType() == Platform.Type.ANDROID) {
            Bitmap bitmap = getBitmap(path);
            iGameImage = new AndroidImage();
            iGameImage.setImageData(bitmap);
        } else if (Platform.getType() == Platform.Type.DESKTOP) {
            try {
                BufferedImage image = ImageIO.read(new File(CoreUtil.getResourcePath(path)));
                iGameImage = new DesktopImage();
                iGameImage.setImageData(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (iGameImage == null) {
            throw new NullPointerException("no image: " + path);
        }
        return iGameImage;
    }

    public static IGameImage createImage(int width, int height, int type4byteAbgr) {
        IGameImage iGameImage = null;
        if (Platform.getType() == Platform.Type.ANDROID) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
            bitmap.setPixel(100, 100, Color.WHITE);
            iGameImage = new AndroidImage();
            iGameImage.setImageData(bitmap);
        } else if (Platform.getType() == Platform.Type.DESKTOP) {
            BufferedImage paint = new BufferedImage(
                    width,
                    height,
                    type4byteAbgr);
            iGameImage = new DesktopImage();
            iGameImage.setImageData(paint);
        }
        if (iGameImage == null) {
            throw new NullPointerException("error createImage");
        }
        return iGameImage;
    }
}
