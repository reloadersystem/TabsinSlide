package resembrink.dev.reloader_helpdesk.PageView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.jsoup.Connection;

import java.util.ArrayList;

import resembrink.dev.reloader_helpdesk.Fragments.NotificationFragment;
import resembrink.dev.reloader_helpdesk.Fragments.ProfileFragment;
import resembrink.dev.reloader_helpdesk.Fragments.URLContentFragment;
import resembrink.dev.reloader_helpdesk.Fragments.UserFragment;

public class PagerViewAdapter extends FragmentPagerAdapter {
    private ArrayList<String> URLList = new ArrayList<>();

    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        String BaseURL = "https://github.com/reloadersystem/WebViewJsoup/blob/master/app/src/main";
        URLList.add(BaseURL + "/AndroidManifest.xml");
        URLList.add(BaseURL + "/java/resembrink/dev/webview/MainActivity.java");
        URLList.add(BaseURL + "/java/resembrink/dev/webview/MainActivity.java");

        URLContentFragment contentFragment = new URLContentFragment();
        //Aqui se crea un paquete de argumentos
        Bundle bundle = new Bundle();
        bundle.putString("URL", URLList.get(position));
        //Se asigna/envia el paquete al fragmento
        contentFragment.setArguments(bundle);

//        switch (position) {
//            case 0:
//                ProfileFragment profileFragment = new ProfileFragment();
//                return profileFragment;
//            case 1:
//                UserFragment userFragment = new UserFragment();
//                return userFragment;
//            case 2:
//                NotificationFragment notificationFragment = new NotificationFragment();
//                return notificationFragment;
//            default:
//                return null;
//        }
        return contentFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
