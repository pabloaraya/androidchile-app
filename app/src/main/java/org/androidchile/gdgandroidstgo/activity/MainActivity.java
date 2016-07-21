package org.androidchile.gdgandroidstgo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.androidchile.gdgandroidstgo.R;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button btnWidgetConfig = (Button)findViewById(R.id.btnWidgetConfig);
    btnWidgetConfig.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, WidgetConfigActivity.class));
      }
    });
  }
}
