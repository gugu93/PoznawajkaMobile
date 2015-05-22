package pl.kamil.poznawajkamobile.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class DialogUtil {

    public static Dialog getTextDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, listener);
        return builder.create();
    }


    public static Dialog getInternetDialog(final Context context, String title, String message, final String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    }
                });
        return builder.create();
    }


    public static Dialog getTextDialog(Context context, int title, int message, DialogInterface.OnClickListener listener) {
        return getTextDialog(context, context.getString(title), context.getString(message), listener);
    }
}
