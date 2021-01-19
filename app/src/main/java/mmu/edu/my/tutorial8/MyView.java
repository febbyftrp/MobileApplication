package mmu.edu.my.tutorial8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    private Paint paint = new Paint();
    private float x1 = 0;
    private float y1 = 0;
    private float x2 = 0;
    private float y2 = 0;
    private boolean isCircle1 = false;
    private boolean isCircle2 = false;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);

        if(isCircle1) {
            paint.setColor(Color.WHITE);
            canvas.drawCircle(x1, y1, 100, paint);
        }

        if(isCircle2) {
            paint.setColor(Color.CYAN);
            canvas.drawCircle(x2, y2, 100, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isCircle1 = true;
                x1 = event.getX(0);
                y1 = event.getY(0);
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                isCircle2 = true;
                if(event.getPointerCount()>1) {
                    x2 = event.getX(1);
                    y2 = event.getY(1);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isCircle2 = false;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isCircle1 = false;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                x1 = event.getX(0);
                y1 = event.getY(0);
                if(event.getPointerCount()>1) {
                    x2 = event.getX(1);
                    y2 = event.getY(1);
                }
                invalidate();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + (event.getAction() & MotionEvent.ACTION_MASK));
        }
        return true;
    }
}
