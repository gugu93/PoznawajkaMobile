package pl.kamil.poznawajkamobile.utils;

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
import android.text.Spannable;
import android.text.style.URLSpan;
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

    public static int getResource(String name, String type, Context context) {
        return context.getResources().getIdentifier(name, type, context.getApplicationInfo().packageName);
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

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
        //picasso.setIndicatorsEnabled(BuildConfig.DEBUG);
        return picasso;
    }

    public static int dipsToPixels(Context context, int dips) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dips * scale + 0.5f);
    }

    public static void customizeSearchView(SearchView searchView) {
        Context context = searchView.getContext();
        View searchPlate = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            TextView searchText = (TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            if (searchText != null) {
                searchText.setTextColor(Color.BLACK);
                searchText.setHintTextColor(Color.GRAY);
            }
        }
        ImageView icon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        if (icon != null) {
            Drawable search = GraphicUtils.getDrawable(context, Iconify.IconValue.fa_search,
                android.R.color.black, 20);
            icon.setImageDrawable(search);
        }
    }

    public static double distanceTo(float lat_a, float lng_a, float lat_b, float lng_b) {
               Location from = new Location("from");
        from.setLatitude(lat_a);
        from.setLongitude(lng_a);
        Location to = new Location("to");
        to.setLatitude(lat_b);
        to.setLongitude(lng_b);
        return from.distanceTo(to);
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

    public static int getWindowWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static Paint createPaint(int color, int strokeWidth, Style style) {
        Paint paint = AndroidGraphicFactory.INSTANCE.createPaint();
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(style);
        return paint;
    }

    public static Intent generateNavigationIntent(String address) {
        if (address.contains(",")) {
            address = address.replace(",", "");
        }
        if (address.contains(" ")) {
            address = address.replace(" ", "+");
        }
        //if()
        String navUri = "http://maps.google.com/maps?q=" + address;
        Intent navIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(navUri));
        navIntent.setClassName("com.google.android.apps.maps",
            "com.google.android.maps.MapsActivity");
        return navIntent;
    }

    public static Intent generateAndDrawNavigationIntent(String address, String start) {
        String navUri = "http://maps.google.com/maps?saddr=" + start + "&daddr=" + address;
        Intent navIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(navUri));
        navIntent.setClassName("com.google.android.apps.maps",
            "com.google.android.maps.MapsActivity");
        return navIntent;
    }

    public static int[] getScreenSize(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        w.getDefaultDisplay().getMetrics(displaymetrics);
        return new int[]{
            displaymetrics.widthPixels,
            displaymetrics.heightPixels
        };
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

    public static void updateListViewHeight(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            return;
        }
        //get listview height
        int totalHeight = 0;
        int adapterCount = myListAdapter.getCount();
        for (int size = 0; size < adapterCount; size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //Change Height of ListView
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (adapterCount - 1));
        myListView.setLayoutParams(params);
    }

    public static int getColorFromHex(String hex) {
        return Color.parseColor(hex);
    }

    public static int[] hex2Rgb(String colorStr) {
        return new int[] {
            Integer.parseInt(colorStr.substring(1, 3), 16),
            Integer.parseInt(colorStr.substring(3, 5), 16),
            Integer.parseInt(colorStr.substring(5, 7), 16) };
    }



}
