package resembrink.dev.reloader_helpdesk.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import resembrink.dev.reloader_helpdesk.FragmentDataConsult;
import resembrink.dev.reloader_helpdesk.R;

public class URLContentFragment extends Fragment {

    ProgressBar progressBar;
    WebView webview;
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview= inflater.inflate(R.layout.fragment_urlcontent, container, false);

        //webview= (WebView) rootview.findViewById(R.id.wv_main);
        //progressBar=rootview.findViewById(R.id.prg);

        Bundle bundle = getArguments();

        if (bundle != null) {
            FragmentDataConsult dataConsult = new FragmentDataConsult();
            dataConsult.setFragmentView(rootview);
            dataConsult.execute(bundle.getString("URL"));
        }

        return rootview;
    }
}
