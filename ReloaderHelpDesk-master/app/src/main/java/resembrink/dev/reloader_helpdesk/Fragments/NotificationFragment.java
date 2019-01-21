package resembrink.dev.reloader_helpdesk.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import resembrink.dev.reloader_helpdesk.AsynTaskFrag;
import resembrink.dev.reloader_helpdesk.EnviarMensaje;
import resembrink.dev.reloader_helpdesk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment implements EnviarMensaje {

    String url="https://github.com/reloadersystem/WebViewJsoup/blob/master/app/src/main/java/resembrink/dev/webview/MainActivity.java";
    String cadena;
    String datoretorno;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        AsynTaskFrag asynTaskFrag= new AsynTaskFrag();

         cadena= asynTaskFrag.recibir(datoretorno);

         Toast.makeText(getContext(),cadena,Toast.LENGTH_LONG).show();
        return view;
    }


    @Override
    public String enviarDatos(String mensaje) {

        mensaje=url;

        return mensaje;
    }
}
