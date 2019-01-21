package resembrink.dev.reloader_helpdesk;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;

import resembrink.dev.reloader_helpdesk.AuthGoogle.FBeginAuth;
import resembrink.dev.reloader_helpdesk.PageView.PagerViewAdapter;

public class MainActivity extends AppCompatActivity {

    private TextView mProfileLabel;
    private TextView mUserLabel;
    private TextView mNotificationsLabel;

    private ViewPager mMainPager;

    private PagerViewAdapter mPagerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfileLabel= findViewById(R.id.profileLabel);
        mUserLabel=findViewById(R.id.usersLabel);
        mNotificationsLabel=findViewById(R.id.notificationsLabel);

        mMainPager=findViewById(R.id.mainPager);
        mMainPager.setOffscreenPageLimit(2);

        mPagerViewAdapter= new PagerViewAdapter(getSupportFragmentManager());
        mMainPager.setAdapter(mPagerViewAdapter);

        mProfileLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPager.setCurrentItem(0);
            }
        });

        mUserLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPager.setCurrentItem(1);
            }
        });

        mNotificationsLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPager.setCurrentItem(2);
            }
        });

        mMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                changeTabs(position);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }




    private void changeTabs(int position)
    {
        if(position==0)
        {
            mProfileLabel.setTextColor(getColor(R.color.textTabBright));
            mProfileLabel.setTextSize(22);

            mUserLabel.setTextColor(getColor(R.color.textTabBright));
            mUserLabel.setTextSize(16);

            mNotificationsLabel.setTextColor(getColor(R.color.textTabBright));
            mNotificationsLabel.setTextSize(16);
        }

        if(position==1)
        {
            mProfileLabel.setTextColor(getColor(R.color.textTabBright));
            mProfileLabel.setTextSize(16);

            mUserLabel.setTextColor(getColor(R.color.textTabBright));
            mUserLabel.setTextSize(22);

            mNotificationsLabel.setTextColor(getColor(R.color.textTabBright));
            mNotificationsLabel.setTextSize(16);


        }

        if(position==2)
        {
            mProfileLabel.setTextColor(getColor(R.color.textTabBright));
            mProfileLabel.setTextSize(16);

            mUserLabel.setTextColor(getColor(R.color.textTabBright));
            mUserLabel.setTextSize(16);

            mNotificationsLabel.setTextColor(getColor(R.color.textTabBright));
            mNotificationsLabel.setTextSize(22);
        }
    }
}
