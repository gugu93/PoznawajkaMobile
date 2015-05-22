package pl.kamil.poznawajkamobile.utils;

import android.content.Context;

/**
 * Created by Kml on 2015-05-22.
 */
public interface AuthorizeListner {
    void onAuthorize(String result);

    Context getContext();
}
