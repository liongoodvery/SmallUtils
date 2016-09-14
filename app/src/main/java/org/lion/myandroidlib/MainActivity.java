package org.lion.myandroidlib;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "====_MainActivity";
    private static final String TEST_FRAGMENT = "TEST_FRAGMENT";
    @BindView(R.id.btn_attach)
    Button mBtnAttach;
    @BindView(R.id.btn_detach)
    Button mBtnDetach;
    @BindView(R.id.container)
    FrameLayout mContainer;
    private FragmentManager mManager;
    private TestFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.t(TAG).i("onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mManager = getFragmentManager();
        mFragment = new TestFragment();
    }

    @Override
    protected void onDestroy() {
        Logger.t(TAG).i("onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Logger.t(TAG).i("onStop()");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Logger.t(TAG).i("onPause()");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Logger.t(TAG).i("onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Logger.t(TAG).i("onStart()");
        super.onStart();
    }


    @Override
    protected void onRestart() {
        Logger.t(TAG).i("onRestart()");
        super.onRestart();
    }


    @Override
    protected void onResumeFragments() {
        Logger.t(TAG).i("onResumeFragments()");
        super.onResumeFragments();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }



    @OnClick({R.id.btn_attach, R.id.btn_detach})
    public void onClick(View view) {
        Fragment fragment = mManager.findFragmentByTag(TEST_FRAGMENT);
            switch (view.getId()) {
                case R.id.btn_attach:
                    if (fragment ==null){
                        mManager.beginTransaction().replace(R.id.container, mFragment, TEST_FRAGMENT).commit();
                    }
                    break;
                case R.id.btn_detach:
                    if (fragment!=null){
                        mManager.beginTransaction().remove(fragment).commit();
                    }
                    break;
        }

    }
}
