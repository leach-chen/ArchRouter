package com.archrouter.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.archrouter.arch.annotation.RouterPage;
import com.archrouter.provider.constants.ConstantsUri;

@RouterPage(path = ConstantsUri.PATH_FRAGMENT,interceptors = TestFragmentInterceptor.class)
public class TestFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test, container, false);
        return v;
    }
}
