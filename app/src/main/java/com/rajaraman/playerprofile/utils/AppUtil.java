package com.rajaraman.playerprofile.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.rajaraman.playerprofile.BuildConfig;
import com.rajaraman.playerprofile.R;

public class AppUtil {

    private static final String TAG = AppUtil.class.getCanonicalName();

    private static ProgressDialog pd = null;
    private static boolean inProgress = false;

    public final static void logDebugMessage(String tag, String message) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, message);
		}
	}

	public final static void logErrorMessage(String tag, String message) {
		// if (BuildConfig.DEBUG) {
		Log.e(tag, message);
		// }
	}

	public final static void showToastMessage(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}

    public final static void showProgressDialog(Context context) {

        if (inProgress == false) {
            pd = new ProgressDialog(context);

            pd.setTitle("Processing...");
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();

            inProgress = true;
        }
    }

    public final static void dismissProgressDialog() {
        if (inProgress) {
            pd.dismiss();
            inProgress = false;
        }
    }

    public final static void showDialog(final Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public final static void showErrorDialogAndQuitApp(final Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        AppUtil.logDebugMessage(TAG, "Going to kill the application...");
                        AppUtil.QuitApp(context);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }


    public final static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).
                                                                    getActiveNetworkInfo() != null;
    }

    public final static void QuitApp(Context context) {
        ((Activity)context).finish();
        System.exit(0);
    }
}