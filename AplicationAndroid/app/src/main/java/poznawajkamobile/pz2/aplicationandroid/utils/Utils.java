package poznawajkamobile.pz2.aplicationandroid.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.joanzapata.android.iconify.Iconify;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;

import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class Utils {


    public static boolean isConnectionAvailable(Context context) {
        try {
            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conMan.getActiveNetworkInfo();
            return networkInfo.isConnected();
        } catch (Exception ex) {
            return false;
        }
    }

    public static Picasso getPicasso(Context context) {
        Picasso picasso = new Picasso.Builder(context)
            .memoryCache(new LruCache(24000))
            .downloader(new UrlConnectionDownloader(context))
            .build();
        return picasso;
    }


    public static double distanceBetween(float lat_a, float lng_a, float lat_b, float lng_b) {
        float[] results = new float[3];
        Location.distanceBetween(lat_a, lng_a, lat_b, lng_b, results);
        return results[0];
    }

    public static String humanReadableDistance(long meters) {
        if (meters < 1000) {
            return meters + "m";
        } else if (meters < 10000) {
            return formatDec(meters / 1000f, 1) + "km";
        } else {
            return ((int) (meters / 1000f)) + "km";
        }
    }


    public static String formatDec(float val, int dec) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        int factor = (int) Math.pow(10, dec);

        int front = (int) (val);
        int back = (int) Math.abs(val * (factor)) % factor;
        return front + "" + symbols.getDecimalSeparator() + back;
    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static int getScreenHeight(Context context) {
        final Point size = new Point();
        int screenHeight = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            windowManager.getDefaultDisplay().getSize(size);
            screenHeight = size.y;
        } else {
            Display d = windowManager.getDefaultDisplay();
            screenHeight = d.getHeight();
        }
        return screenHeight;
    }

}
