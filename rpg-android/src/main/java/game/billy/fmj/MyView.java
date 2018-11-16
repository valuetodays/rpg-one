package game.billy.fmj;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import billy.rpg.resource.map.MapMetaData;

public class MyView extends View {
    private static final String TAG = MyView.class.getSimpleName();

    private Paint paint;
    private float cx = 100f;
    private float cy = 100f;
    private Bitmap bitmap = null;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //画笔
        paint = new Paint();
        //设置颜色
        paint.setColor(Color.parseColor("#ff0000"));
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置画笔粗细
        paint.setStrokeWidth(2);
        //设置是否为空心
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        File filesDir = context.getFilesDir();
        Log.i(TAG, "filesDir=" + filesDir);
        File[] files = filesDir.listFiles();
        Log.i(TAG, "filesLength:" + files.length);
        for (File file : files) {
            Log.i(TAG, "file: " + file.getPath());
        }
        URL resource = MapMetaData.class.getResource("/");
        if (resource != null) {
            Log.i(TAG, resource.getPath());
        }
        File dataDirectory = Environment.getDataDirectory();
        Log.i(TAG, "dataDirectory:" + dataDirectory.getPath());
//        Environment.getExternalStorageDirectory() === /mnt/sdcard
//        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);

        AssetManager assets = context.getAssets();
        try {
            String[] list = assets.list("/");
            for (String s : list) {
                Log.i(TAG, s);
            }
            InputStream open = assets.open("test-tile1.png");
            if (open != null) {
                bitmap = BitmapFactory.decodeStream(open);
                Log.i(TAG, bitmap.getWidth() + ",");
                open.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        bitmap = BitmapFactory.decodeFile("/drawable/bg.png");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        }
        canvas.drawCircle(cx, cy, 50, paint);
        int width = getWidth();
        int height = getHeight();
        String viewWH = "view.width/height=" + width + "/"+height;
        String canvasWH = "canvas.width/height=" + canvas.getWidth() + "/" + canvas.getHeight();
        canvas.drawText(viewWH, 20.0f, 20.0f, paint);
        canvas.drawText(canvasWH, 20.0f, 50.0f, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        cx = event.getX();
        cy = event.getY();
        //刷新view
        invalidate();
        return true;
    }
}
