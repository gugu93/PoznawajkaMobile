package poznawajkamobile.pz2.aplicationandroid.fragment;

import android.content.Context;

public interface DownloadListener {
    void onCheckFinished(String result);

    Context getContext();

}
