package poznawajkamobile.pz2.aplicationandroid.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.kml.poznawajkamobile.R;

import poznawajkamobile.pz2.aplicationandroid.utils.GraphicUtils;


public class DotPagerIndicator extends View {
    int dotsAmount = 0;
    int activeDot = 0;
    int dotSpacing = 10;
    int horizontalSpace = 5;
    Bitmap activeDotBitmap;
    Bitmap normalDotBitmap;
    int x = 0;
    private Paint paint;

    public DotPagerIndicator(Context context) {
        super(context);
        init();
    }

    public DotPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawDot(canvas);
        super.onDraw(canvas);
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setBitmaps();
    }


    private void drawDot(Canvas canvas) {
        for (int i = 0; i < dotsAmount; i++) {
            if (i == activeDot) {
                canvas.drawBitmap(activeDotBitmap, x, 0, paint);
            } else {
                canvas.drawBitmap(normalDotBitmap, x, 0, paint);
            }
            x = x + activeDotBitmap.getWidth() + horizontalSpace + dotSpacing;
        }
    }

    private void setBitmaps() {
        activeDotBitmap = GraphicUtils.drawableToBitmap(getResources().getDrawable(R.drawable.dot_active));
        normalDotBitmap = GraphicUtils.drawableToBitmap(getResources().getDrawable(R.drawable.dot_normal));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (activeDotBitmap == null) {
            setBitmaps();
        }
        int width = dotsAmount * (activeDotBitmap.getWidth() + horizontalSpace + getDotSpacing());
        width = resolveSize(width, widthMeasureSpec);
        int height = activeDotBitmap.getHeight();
        height = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void refresh() {
        x = 0;
        invalidate();
    }

    public int getDotsAmount() {
        return dotsAmount;
    }

    public void setDotsAmount(int dotsAmount) {
        this.dotsAmount = dotsAmount;
        refresh();
    }

    public int getActiveDot() {
        return activeDot;
    }

    public void setActiveDot(int activeDot) {
        this.activeDot = activeDot;
        refresh();
    }

    public int getDotSpacing() {
        return dotSpacing;
    }

    public void setDotSpacing(int dotSpacing) {
        this.dotSpacing = dotSpacing;
        x = 0;
        invalidate();
    }

}