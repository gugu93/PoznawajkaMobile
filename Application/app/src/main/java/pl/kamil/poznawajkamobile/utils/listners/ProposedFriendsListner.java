package pl.kamil.poznawajkamobile.utils.listners;

import android.content.Context;

/**
 * Created by Kml on 2015-05-22.
 */
public interface ProposedFriendsListner {
    void onCompleteRequest(String result);

    Context getContext();
}