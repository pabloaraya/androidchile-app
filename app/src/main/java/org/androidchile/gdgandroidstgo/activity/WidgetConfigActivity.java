package org.androidchile.gdgandroidstgo.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.androidchile.gdgandroidstgo.App;
import org.androidchile.gdgandroidstgo.R;
import org.androidchile.gdgandroidstgo.widget.WidgetProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WidgetConfigActivity extends AppCompatActivity {

  private TextView textViewTitle;
  private Button buttonUpdate;

  private int widgetId = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_widget_config);

    // Obtengo el intent desde donde fué abierta la actividad
    Intent intent = getIntent();

    // Obtengo los parámetros que se adjuntaron al intent
    Bundle bundle = intent.getExtras();

    // Obtengo el widget Id en el cual estoy trabajando
    widgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

    // Seteo RESULT_CANCELED por si el usuario presiona "back" en el teléfono
    setResult(RESULT_CANCELED);

    // Referencio los elementos de la vista
    textViewTitle = (TextView)findViewById(R.id.textView);
    buttonUpdate = (Button)findViewById(R.id.buttonUpdate);

    // Asigno el evento onClick al boton "update"
    buttonUpdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        GraphRequest request = new GraphRequest(
          AccessToken.getCurrentAccessToken(),
          "/187694778228655",
          null,
          HttpMethod.GET,
          new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {

              try {
                JSONObject feed = response.getJSONObject().getJSONObject("feed");
                JSONArray data = (JSONArray) feed.get("data");
                JSONObject post = (JSONObject) data.get(0);
                String message = post.getString("message");

                // Guardamos el mensaje en la session
                App.getSession().edit()
                  .putString("message_" + widgetId, message)
                  .apply();

                // Actualizamos el widget
                AppWidgetManager appWidgetManager =
                  AppWidgetManager.getInstance(WidgetConfigActivity.this);
                WidgetProvider.updateWidget(WidgetConfigActivity.this, appWidgetManager, widgetId);

                // Devolvemos el result de la activity como RESULT_OK
                Intent result = new Intent();
                result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
                setResult(RESULT_OK, result);

                // Cerramos la actividad de configuración
                finish();
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
          });

        Bundle parameters = new Bundle();
        parameters.putString(App.PARAM_FIELDS, "feed.limit(1){message,from}");
        request.setParameters(parameters);
        request.executeAsync();
      }
    });
  }
}
