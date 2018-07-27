package com.jica.butterbookdata.fragmentpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jica.butterbookdata.R;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Page4_Settings extends Fragment {
    private Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_4_settings,container,false);
        ButterKnife.bind(this,view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null)
            unbinder.unbind();
    }
}
