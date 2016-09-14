package org.lion.myandroidlib;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

/**
 * Created by more on 2016-09-07 15:43:42.
 */
public class TestFragment extends Fragment {
    private static final String TAG = "====_TestFragment";

    @Override
    public void onAttach(Context context) {
        Logger.t(TAG).i("onAttach activity=" + getActivity());
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.t(TAG).i("onCreateView activity=" + getActivity());
        return inflater.inflate(R.layout.fragment_test, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.t(TAG).i("onViewCreated activity=" + getActivity());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Logger.t(TAG).i("onActivityCreated activity=" + getActivity());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Logger.t(TAG).i("onStart activity=" + getActivity());
        super.onStart();
    }

    @Override
    public void onResume() {
        Logger.t(TAG).i("onResume activity=" + getActivity());
        super.onResume();
    }

    @Override
    public void onPause() {
        Logger.t(TAG).i("onPause activity=" + getActivity());
        super.onPause();
    }

    @Override
    public void onStop() {
        Logger.t(TAG).i("onStop activity=" + getActivity());
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Logger.t(TAG).i("onDestroyView activity=" + getActivity());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Logger.t(TAG).i("onDestroy activity=" + getActivity());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Logger.t(TAG).i("onDetach activity=" + getActivity());
        super.onDetach();
    }
}
