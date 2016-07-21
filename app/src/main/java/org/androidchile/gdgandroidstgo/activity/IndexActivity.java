package org.androidchile.gdgandroidstgo.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;

import org.androidchile.gdgandroidstgo.App;
import org.androidchile.gdgandroidstgo.R;
import org.androidchile.gdgandroidstgo.fragment.LoginFragment;

public class IndexActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_index);

    if(App.isLoggedIn()){
      startActivity(new Intent(this, MainActivity.class));
      finish();
    }else{
      Fragment fragment = LoginFragment.newInstance();
      getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragment, fragment)
        .commit();
    }
  }
}
