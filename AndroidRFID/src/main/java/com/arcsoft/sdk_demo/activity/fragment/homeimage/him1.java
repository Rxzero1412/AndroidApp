package com.arcsoft.sdk_demo.activity.fragment.homeimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arcsoft.sdk_demo.R;

/**
 * Created by 83541 on 2016/11/19.
 */

public class him1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView iv=new ImageView(getActivity());
        iv.setImageResource(R.drawable.him1);
        return iv;
    }
}
