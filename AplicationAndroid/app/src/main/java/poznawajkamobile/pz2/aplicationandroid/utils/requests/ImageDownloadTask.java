package poznawajkamobile.pz2.aplicationandroid.utils.requests;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import poznawajkamobile.pz2.aplicationandroid.utils.listners.AvatarListner;

public class ImageDownloadTask extends AsyncTask<String, Void, Boolean> {
    private AvatarListner mContext;

    public ImageDownloadTask(AvatarListner context) {
        mContext = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String source = params[0];
        String destination = params[1];

        try {
            URL url = new URL(source);
            InputStream is = url.openStream();
            File f = new File(destination, "avatar.jpg");
            if (!f.exists()) {
                FileOutputStream os = new FileOutputStream(f);

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
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mContext.onComplete(aBoolean);
    }
}
