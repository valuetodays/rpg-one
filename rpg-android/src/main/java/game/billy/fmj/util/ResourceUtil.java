package game.billy.fmj.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;

public class ResourceUtil {
    private final String TAG = getClass().getSimpleName();

    /**
     *
     * @param context context
     * @param path 不以/开头
     */
    public static Bitmap getBitmap(Context context, String path) {
        AssetManager assets = context.getAssets();
        Bitmap bitmap = null;
        try {
//            InputStream is = assets.open("test-tile1.png"); // 打开/assets/下的文件
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
}
