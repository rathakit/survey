package com.nimbl3.survey.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nimbl3.survey.App;

/**
 * The Util is a helper class.
 * @author Rathakit
 *
 */
public final class Util {

	/**
	 * Check the Internet connection available
	 * @return the status of internet connection
	 */
	public static boolean isNetworkConnected() {
		boolean available = false;
		Context context = App.getContext();
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		available = info != null && info.isConnected();
		return available;
	}
}
