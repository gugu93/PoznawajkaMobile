package pl.kamil.poznawajkamobile.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import com.example.kml.poznawajkamobile.R;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;


public class GraphicUtils {

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static AnimationDrawable createRotateAnimation(Context context, Iconify.IconValue icon) {
        return createRotateAnimation(context, icon, R.color.my_red, R.dimen.text_forty);
    }

    public static AnimationDrawable createRotateAnimation(Context context, Iconify.IconValue icon, int colorResource, int sizeResource) {
        AnimationDrawable animation = new AnimationDrawable();
        Drawable drawable = getDrawable(context, icon, colorResource, sizeResource);
        for (int i = 0; i< 360; i += 45) {
            animation.addFrame(new BitmapDrawable(
                    context.getResources(), rotateBitmap(drawableToBitmap(drawable), i)), 200);
        }
        return animation;
    }

    public static StateListDrawable generateStateList(Context context, Iconify.IconValue icon, int standardColor, int pressedColor) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(20));
        states.addState(new int[] {android.R.attr.state_focused},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(20));
        states.addState(new int[] {android.R.attr.state_selected},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(20));
        states.addState(new int[] {android.R.attr.state_checked},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(20));
        states.addState(new int[] { },
                new IconDrawable(context, icon).colorRes(standardColor).sizeDp(20));
        return states;
    }

    public static StateListDrawable generateStateList(Context context, Iconify.IconValue icon,
                                                      int standardColor, int pressedColor, int sizeResource) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(sizeResource));
        states.addState(new int[] {android.R.attr.state_focused},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(sizeResource));
        states.addState(new int[] {android.R.attr.state_selected},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(sizeResource));
        states.addState(new int[] {android.R.attr.state_checked},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(sizeResource));
        states.addState(new int[] { },
                new IconDrawable(context, icon).colorRes(standardColor).sizeDp(sizeResource));
        return states;
    }

    public static StateListDrawable generateRateList(Context context, int sizeResource, boolean active) {
        StateListDrawable states = new StateListDrawable();
        Iconify.IconValue icon = Iconify.IconValue.fa_star;
        int pressedColor = android.R.color.black;
        int standardColor = R.color.border;
        if (active) {
            pressedColor = R.color.my_yellow;
            standardColor = R.color.my_red;
        }
        states.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_window_focused},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(sizeResource));
        states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_window_focused},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(sizeResource));
        states.addState(new int[] {android.R.attr.state_selected, android.R.attr.state_window_focused},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(sizeResource));
        states.addState(new int[] {android.R.attr.state_checked, android.R.attr.state_window_focused},
                new IconDrawable(context, icon).colorRes(pressedColor).sizeDp(sizeResource));
        states.addState(new int[] { },
                new IconDrawable(context, icon).colorRes(standardColor).sizeDp(sizeResource));
        return states;
    }



    public static Drawable getDrawable(Context context, Iconify.IconValue icon, int colorResource, int sizeDp) {
        return new IconDrawable(context, icon)
                .colorRes(colorResource)
                .sizeDp(sizeDp);
    }

    public static Drawable getMenuIcon(Context context, Iconify.IconValue icon) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed},
                getIconWithColor(context, icon, R.color.my_red));
        states.addState(new int[]{android.R.attr.state_focused},
                getIconWithColor(context, icon, R.color.my_red));
        states.addState(new int[]{},
                getIconWithColor(context, icon, android.R.color.black));
        return states;
    }

    public static StateListDrawable getStateList(Drawable normal, Drawable active) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed},
                active);
        states.addState(new int[]{android.R.attr.state_focused},
                active);
        states.addState(new int[]{android.R.attr.state_checked},
                active);
        states.addState(new int[]{},
                normal);
        return states;
    }

    public static IconDrawable getIconWithColor(Context context, Iconify.IconValue icon, int color) {
        return new IconDrawable(context, icon)
                .colorRes(color)
                .actionBarSize();
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
