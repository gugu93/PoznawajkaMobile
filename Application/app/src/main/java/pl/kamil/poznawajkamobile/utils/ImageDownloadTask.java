package pl.kamil.poznawajkamobile.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownloadTask extends AsyncTask<String, Void, Void> {

    private Context mContext;

    public ImageDownloadTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        String source = params[0];
        String destination = params[1];

        try {
            URL url = new URL(source);
            InputStream is = url.openStream();
            File f = new File(destination);
            if (!f.exists()) {
                FileOutputStream os = new FileOutputStream(destination);

                byte[] b = new byte[2048];
                int length;

                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }

                is.close();
                os.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
