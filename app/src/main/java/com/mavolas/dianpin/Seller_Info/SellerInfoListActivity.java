package com.mavolas.dianpin.Seller_Info;

import android.content.Intent;
import android.os.Bundle;
import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mavolas.dianpin.R;
import com.mavolas.dianpin.Seller_Info.bean.FilterValues;


public class SellerInfoListActivity extends FragmentActivity {
    //region 变量定义
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerbar;
    private TextView imageViewHeaderFilter;
    private TextView textViewTitle;

    private SellerInfoListFilterFragment filterFragment = null;
    private SellerInfoListContentFragment contentFragment = null;

    public Boolean needRefresh = false;

    public FilterValues filterValues = new FilterValues();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.common_fragment_with_leftdrawer);

        //region 控件绑定
        textViewTitle = findViewById(R.id.tv_toolbar_title);
        imageViewHeaderFilter = findViewById(R.id.tv_toolbar_filter);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        //endregion

        textViewTitle.setText("商家信息");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        filterFragment = new SellerInfoListFilterFragment();
        filterFragment.setValues(mDrawerLayout, this);
        transaction.replace(R.id.left_drawer, filterFragment);
        contentFragment = new SellerInfoListContentFragment();
        transaction.replace(R.id.content_frame, contentFragment);
        contentFragment.setValues(this);
        transaction.commit();

        drawerbar = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.mipmap.ic_launcher,
                R.string.open, R.string.close) {

            //菜单打开
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                needRefresh = false;
            }

            // 菜单关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if (needRefresh == false)
                    return;
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                contentFragment = new SellerInfoListContentFragment();
                contentFragment.setValues(SellerInfoListActivity.this);
                transaction.replace(R.id.content_frame, contentFragment);
                transaction.commit();
            }

        };
        mDrawerLayout.setDrawerListener(drawerbar);

        imageViewHeaderFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*然后在碎片中调用重写的onActivityResult方法*/
        if (requestCode == 1)
            contentFragment.onActivityResult(requestCode, resultCode, data);
        else if (requestCode == 2)
            filterFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
