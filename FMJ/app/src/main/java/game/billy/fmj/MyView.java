package game.billy.fmj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
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
//        bitmap = BitmapFactory.decodeFile("/drawable/bg.png");
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        canvas.drawCircle(cx, cy, 50, paint);
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
