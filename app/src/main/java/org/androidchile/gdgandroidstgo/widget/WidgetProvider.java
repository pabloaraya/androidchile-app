package org.androidchile.gdgandroidstgo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.androidchile.gdgandroidstgo.App;
import org.androidchile.gdgandroidstgo.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pablo on 7/18/16.
 */

public class WidgetProvider extends AppWidgetProvider {

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    super.onUpdate(context, appWidgetManager, appWidgetIds);

    for(int i = 0; i < appWidgetIds.length; i++){

      int widgetId = appWidgetIds[i];

      updateWidget(context, appWidgetManager, widgetId);
    }
  }

  @Override
  public void onReceive(Context context, Intent intent) {

    // Verificamos que sea nuestro "intent"
    if(intent.getAction().equals("org.androidchile.gdgandroidstgo.UPDATE_WIDGET")){

      // Obtener el id del widgent donde estamos trabajando
      int widgedtId = intent.getIntExtra(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID
      );

      // Obtenemos el manager de nuestro contexto.
      AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);

      // Verificar que el id de mi widget es valido
      if(widgedtId != AppWidgetManager.INVALID_APPWIDGET_ID){
        updateWidget(context, widgetManager, widgedtId);
      }
    }
  }

  public static void updateWidget(final Context context, final AppWidgetManager appWidgetManager, final int widgetId){

    // Recuperamos el mensaje guardado en la Session (SharedPreferences)
    String message = App.getSession().getString("message_"+widgetId, "Nuevo mensaje!");

    // Obtener las referencias a los elementos de mi layout
    // Primer parámetro es el path del proyecto
    // Segundo parámtero es el layout del widget
    final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

    // Setear un texto en el textview del Widget
    remoteViews.setTextViewText(R.id.textViewTitle, message);

    // Notificamos al WidgetManager de nuestro cambio.
    appWidgetManager.updateAppWidget(widgetId, remoteViews);

    // Defino el intent que recibiré en el Manifest
    Intent intent = new Intent("org.androidchile.gdgandroidstgo.UPDATE_WIDGET");
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

    // Dejo el intent pendiente
    PendingIntent pendingIntent =
      PendingIntent.getBroadcast(context, widgetId,
        intent, PendingIntent.FLAG_UPDATE_CURRENT);

    // Asigno el intent pendiente omo evento onclicklistener del botón "actualizar"
    remoteViews.setOnClickPendingIntent(R.id.buttonUpdate, pendingIntent);
  }
}
