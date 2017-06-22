package moonlapse.com.notificaciones;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    final static String MI_GRUPO_DE_NOTIFIC = "mi_grupo_de_notific";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button wearButton = (Button) findViewById(R.id.boton1);
        wearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intencionLlamar = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:555123456"));
                PendingIntent intencionPendienteLlamar = PendingIntent.getActivity(MainActivity.this, 0, intencionLlamar, 0);

                Intent intencionMapa = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=universidad+politecnica+valencia"));
                PendingIntent intencionPendienteMapa = PendingIntent.getActivity(MainActivity.this, 0, intencionMapa, 0);

                NotificationCompat.Action accion = new NotificationCompat.Action.Builder(R.mipmap.ic_action_call, "llamar Wear", intencionPendienteLlamar).build();

                String s = "Texto largo con descripción detallada de la notificación. ";
                //Creamos una lista de acciones
                List<NotificationCompat.Action> acciones = new ArrayList<NotificationCompat.Action>();
                acciones.add(accion);
                acciones.add(new NotificationCompat.Action(R.mipmap.ic_action_locate, "Ver mapa", intencionPendienteMapa));

                int notificacionId = 001;
                // Creamos un BigTextStyle para la segunda página
                NotificationCompat.BigTextStyle segundaPg = new NotificationCompat.BigTextStyle();
                segundaPg.setBigContentTitle("Página 2").bigText("Más texto.");

                NotificationCompat.BigTextStyle terceraPg = new NotificationCompat.BigTextStyle();
                terceraPg.setBigContentTitle("Página 3").bigText("Más texto aún.");

                // Creamos una notification para la segunda página
                Notification notificacionPg2 = new NotificationCompat.Builder(MainActivity.this).setStyle(segundaPg).build();
                Notification notificacionPg3 = new NotificationCompat.Builder(MainActivity.this).setStyle(terceraPg).build();

                List<Notification> listaPaginas = new ArrayList<Notification>();
                listaPaginas.add(notificacionPg2);
                listaPaginas.add(notificacionPg3);

                NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true)
                        .setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.escudo_upv))
                        .addActions(acciones)
                        .addPages(listaPaginas);

                Notification notificacion = new NotificationCompat.Builder(MainActivity.this)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.escudo_upv))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Título")
                        .setContentText(Html.fromHtml("<b>Notificación</b> <u>Android Wear</u>"))
                        .setContentIntent(intencionPendienteMapa)
                        .addAction(R.mipmap.ic_action_call, "llamar", intencionPendienteLlamar)
                        .extend(wearableExtender)
                        .setGroup(MI_GRUPO_DE_NOTIFIC)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(s + s + s + s))
                        .build();
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(notificacionId, notificacion);

                int notificacionId2 = 002;
                Notification notificacion2 = new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.mipmap.ic_action_mail_add)
                        .setContentTitle("Nueva Conferencia")
                        .setContentText("Los neutrinos")
                        .setGroup(MI_GRUPO_DE_NOTIFIC)
                        .build();
                notificationManager.notify(notificacionId2, notificacion2);

                int idNotificacion3 = 003;
                Notification notificacion3 = new NotificationCompat.Builder(MainActivity.this).setContentTitle("2 notificaciones UPV").setSmallIcon(R.mipmap.ic_action_attach)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.escudo_upv)).setStyle(new NotificationCompat.InboxStyle().addLine("Nueva Conferencia Los neutrinos").addLine("Nuevo curso Android Wear").setBigContentTitle("2 notificaciones UPV").setSummaryText("info@upv.es")).setNumber(2)
                        .setGroup(MI_GRUPO_DE_NOTIFIC).setGroupSummary(true).build();
                notificationManager.notify(idNotificacion3, notificacion3);
            }
        });
    }
}