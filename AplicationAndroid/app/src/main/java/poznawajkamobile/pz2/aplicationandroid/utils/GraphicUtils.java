package poznawajkamobile.pz2.aplicationandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;


public class GraphicUtils {

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




    public static Drawable getDrawable(Context context, Iconify.IconValue icon, int colorResource, int sizeDp) {
        return new IconDrawable(context, icon)
                .colorRes(colorResource)
                .sizeDp(sizeDp);
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
