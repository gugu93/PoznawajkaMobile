package pl.kamil.poznawajkamobile.fragment;

import android.content.Context;

public interface DownloadListener {
    void onCheckFinished(String result);

    Context getContext();

}
