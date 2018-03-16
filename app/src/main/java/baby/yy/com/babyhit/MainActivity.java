package baby.yy.com.babyhit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    FragmentManager mFm;
    BabyHitFragment mBabyHitFragment;
    BabyMoveFragment mBabyMoveFragment;
    Context ctx;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    {
                        FragmentTransaction ft = mFm.beginTransaction();
                        if (mBabyMoveFragment == null) {
                            mBabyMoveFragment = new BabyMoveFragment();
                            mBabyMoveFragment.ctx = ctx;
                        } else {
                            System.out.println(mBabyMoveFragment);
                        }
                        ft.replace(R.id.content, mBabyMoveFragment);
                        ft.commit();
                    }
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    {
                        FragmentTransaction ft = mFm.beginTransaction();
                        //
                        if (mBabyHitFragment == null) {
                            mBabyHitFragment = new BabyHitFragment();
                            mBabyHitFragment.ctx = ctx;
                            ft.replace(R.id.content, mBabyHitFragment);
                        } else {
                            System.out.println(mBabyHitFragment);
                            ft.replace(R.id.content, mBabyHitFragment);
                        }
                        ft.commit();
                    }
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ctx = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout fl = (FrameLayout) findViewById(R.id.content);
        mFm = getSupportFragmentManager();
        if (mBabyMoveFragment == null) {
            FragmentTransaction ft = mFm.beginTransaction();
            mBabyMoveFragment = new BabyMoveFragment();
            ft.replace(R.id.content, mBabyMoveFragment);
            ft.commit();
        }
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
