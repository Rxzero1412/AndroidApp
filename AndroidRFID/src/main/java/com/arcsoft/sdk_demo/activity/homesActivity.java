package com.arcsoft.sdk_demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.arcsoft.sdk_demo.R;
import com.arcsoft.sdk_demo.activity.fragment.Fzhangdan;
import com.arcsoft.sdk_demo.activity.fragment.Fhome;
import com.arcsoft.sdk_demo.activity.fragment.Fyonghu;

/**
 * Created by 83541 on 2017/7/8.
 */

public class homesActivity extends AppCompatActivity {
    // /#707070
    String subjectcolor=null;
    private Button sigbtn;
    int titleBtn=0;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private RadioGroup mRadioGroup;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);


        resetTabBtn();
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        initView();
        sigbtn=(Button) findViewById(R.id.SignBtn);
        sigbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (titleBtn)
                {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        showPopupMenu(sigbtn);
                        break;
                }
            }
        });

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mFragments.size();
            }
            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                sigbtn=(Button) findViewById(R.id.SignBtn);
                resetTabBtn();
                switch (position)
                {
                    case 0:
                        sigbtn.setText(" ");
                        titleBtn=0;
                        new Fhome();
                        break;
                    case 1:
                        sigbtn.setText(" ");
                        titleBtn=1;
                        new Fzhangdan();
                        break;
                    case 2:
                        sigbtn.setText("帮助");
                        titleBtn=2;
                        new Fyonghu();
                        break;
                }
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }
            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });
        Intent i=getIntent();
        subjectcolor=i.getStringExtra("subjectcolor");
        style();
        Button toolbarBack= (Button) findViewById(R.id.Title_Back);
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homesActivity.this.finish();
            }
        });
    }
    public void style(){
        RelativeLayout titleA = (RelativeLayout) findViewById(R.id.layout_titlebar);
        LinearLayout bottom = (LinearLayout) findViewById(R.id.layout_bottom);
        Button signbtn= (Button) findViewById(R.id.SignBtn);
        signbtn.setText(" ");
        if(subjectcolor.equals("M")){
            titleA.setBackgroundColor(android.graphics.Color.parseColor("#1296db"));
            bottom.setBackgroundColor(android.graphics.Color.parseColor("#1296db"));
            sigbtn.setBackgroundColor(android.graphics.Color.parseColor("#1296db"));
        }
        else if(subjectcolor.equals("F")){
            titleA.setBackgroundColor(android.graphics.Color.parseColor("#ed4255"));
            bottom.setBackgroundColor(android.graphics.Color.parseColor("#ed4255"));
            sigbtn.setBackgroundColor(android.graphics.Color.parseColor("#ed4255"));
        }
        else{
            titleA.setBackgroundColor(android.graphics.Color.parseColor("#707070"));
            bottom.setBackgroundColor(android.graphics.Color.parseColor("#707070"));
            sigbtn.setBackgroundColor(android.graphics.Color.parseColor("#707070"));
        }
    }

    protected void resetTabBtn()
    {
        mRadioGroup= (RadioGroup) findViewById(R.id.layout_bottom);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_home:
                        sigbtn.setText(" ");
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.btn_zhangdan:
                        sigbtn.setText(" ");
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.btn_yonghu:
                        sigbtn.setText("设置");
                        mViewPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }
    private void initView()
    {
        mFragments.add(new Fhome());
        mFragments.add(new Fzhangdan());
        mFragments.add(new Fyonghu());
    }
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.setmenu, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                //TODO 获取设置内容
            }
        });
        popupMenu.show();
    }
}