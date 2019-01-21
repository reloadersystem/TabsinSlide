package resembrink.dev.reloader_helpdesk.Fragments;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import resembrink.dev.reloader_helpdesk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment  {

    ProgressBar progressBar;
    WebView webview;
    String url="https://github.com/reloadersystem/WebViewJsoup/blob/master/app/src/main/java/resembrink/dev/webview/MainActivity.java";

    View rootview;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rootview= inflater.inflate(R.layout.fragment_user, container, false);

        webview= (WebView) rootview.findViewById(R.id.user_wv_main);
        progressBar=rootview.findViewById(R.id.user_prg);

        new MyAsynTask().execute();

       return  rootview;
    }

    private class MyAsynTask extends AsyncTask<Void, Void, Document> {
        @Override
        protected Document doInBackground(Void... voids) {

            Document document = null;
            try {
                document= Jsoup.connect(url).get();
                document.getElementsByClass("position-relative js-header-wrapper ").remove();
                document.getElementsByClass("pagehead repohead instapaper_ignore readability-menu experiment-repo-nav  ").remove();
                document.getElementsByClass("breadcrumb").remove();

                document.getElementsByClass("position-relative d-flex flex-justify-between pt-6 pb-2 mt-6 f6 text-gray border-top border-gray-light ").remove();

                document.getElementsByClass(" btn btn-sm select-menu-button js-menu-target css-truncate").remove();
                document.getElementsByClass("position-relative").remove();
                document.getElementsByClass("commit-tease-contributors").remove();
                document.getElementsByClass("user-mention").remove();
                document.getElementsByClass("message").remove();
                document.getElementsByClass("avatar").remove();
                document.getElementsByClass("signup-prompt-bg rounded-1").remove();
                document.getElementsByClass("file-navigation").remove();
                document.getElementsByClass("commit-tease").remove();
                document.getElementsByClass("file-info").remove();
                document.getElementsByClass("file-actions").remove();
                document.getElementsByClass("d-flex flex-justify-center pb-6").remove();
                document.getElementsByClass("hovercard-aria-description").remove();
                document.getElementsByClass("file-header").remove();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            webview.loadDataWithBaseURL(url,document.toString(),"text/html","utf-8","");
            webview.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );

            webview.getSettings().setSupportZoom(true);
            webview.getSettings().setBuiltInZoomControls(true);
            webview.getSettings().setDisplayZoomControls(false);
            //webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            //webview.setScrollbarFadingEnabled(false);



            webview.setWebViewClient(new WebViewClient(){

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressBar.setVisibility(View.VISIBLE);
                    //setTitle("Loading...");
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                    setDesktopMode(webview, true);
                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, request);
                }


                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //quita los banners
                    if (!url.contains(url))
                    {
                        view.loadUrl(url);
                    }
                    return true;

                }



                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBar.setVisibility(View.GONE);
                    //setTitle(view.getTitle());
                    // setTitle("MainFragment.java");
                }
            });




        }

        public void setDesktopMode(WebView webview,boolean enabled) {
            String newUserAgent = webview.getSettings().getUserAgentString();
            if (enabled) {
                try {
                    String ua = webview.getSettings().getUserAgentString();
                    String androidOSString = webview.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1);
                    newUserAgent = webview.getSettings().getUserAgentString().replace(androidOSString, "(X11; Linux x86_64)");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                newUserAgent = null;
            }

            webview.getSettings().setUserAgentString(newUserAgent);
            webview.getSettings().setUseWideViewPort(enabled);
            webview.getSettings().setLoadWithOverviewMode(enabled);
            webview.reload();
        }

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }



}
