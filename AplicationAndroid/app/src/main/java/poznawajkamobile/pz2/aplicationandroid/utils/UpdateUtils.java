package poznawajkamobile.pz2.aplicationandroid.utils;

import android.content.Context;
import android.os.Environment;

public class UpdateUtils {

    private static final String TAG = UpdateUtils.class.getSimpleName();

    public static String getFilesDir(Context context) {
        return context.getFilesDir().getAbsolutePath() + "/";
    }

    public static boolean hasExternalStorage() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWritable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWritable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWritable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWritable = false;
        }
        return mExternalStorageAvailable && mExternalStorageWritable;
    }

}
