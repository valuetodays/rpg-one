package game.billy.fmj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
    private static final String TAG = MyView.class.getSimpleName();

    private Paint paint;
    private float cx = 100f;
    private float cy = 100f;
    private Bitmap bitmap = null;
    private MainActivity mainActivity;

    public MyView(Context context) {
        super(context);
        mainActivity = (MainActivity) context;
    }
/*
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

//        Environment.getExternalStorageDirectory() === /mnt/sdcard
//        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
    }
*/

/*

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(mainActivity.andriodCanvas);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        cx = event.getX();
        cy = event.getY();
        //刷新view
        invalidate();
        return true;
    }
}
