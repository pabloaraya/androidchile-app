package org.androidchile.gdgandroidstgo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

/**
 * Created by pablo on 7/15/16.
 */

public class App extends Application {

  // Variables estáticas
  final public static String PARAM_FIELDS = "fields";

  private static SharedPreferences sharedPreferences;

  @Override
  public void onCreate() {
    super.onCreate();
    FacebookSdk.sdkInitialize(getApplicationContext());
    sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
  }

  public static SharedPreferences getSession(){
    return sharedPreferences;
  }

  public static boolean isLoggedIn() {
    AccessToken accessToken = AccessToken.getCurrentAccessToken();
    return accessToken != null;
  }
}
