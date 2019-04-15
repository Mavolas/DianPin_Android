package com.mavolas.dianpin.Seller_Info;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.mavolas.dianpin.R;
import com.mavolas.dianpin.Seller_Info.adapter.SellerInfoListAdapter;
import com.mavolas.dianpin.bean.Sellers_Item;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * 列表内容Fragment：设施信息 / 车库信息
 */
public class SellerInfoListContentFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private SellerInfoListActivity activity;
    private List<Sellers_Item> mList = new ArrayList<>();
    private SellerInfoListAdapter adapter;
    private Button btnSubmit;
    private String mCURDModel = "";
    private int mPositionSelected;

    private Date firstQueryTime = new Date();
    private long pageIndex = 0;
    private long pageSize = 10;
    private ListView lv;
    RefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_list, container, false);

        activity = (SellerInfoListActivity) getActivity();

        lv = view.findViewById(R.id.lv_seller_list);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);


        adapter = new SellerInfoListAdapter(view.getContext(), R.layout.fragment_seller_item, mList);
        lv.setAdapter(adapter);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                LoadData();

                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });


        //region 侧滑菜单设定

        //endregion

        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sellers_Item sellers_item = mList.get(position);

                final String[] phones = sellers_item.phone.split(";");
                if (phones.length == 1)
                    callPhone(phones[0]);
                else if (phones.length == 2) {
                    final CharSequence[] items = {phones[0], phones[1]};
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            // 拍照
                            if (item == 0) {
                                callPhone(phones[0]);
                            }
                            // "相册(多选)"
                            else if (item == 1) {
                                callPhone(phones[1]);
                            }
                        }
                    }).create();

                    builder.show();

                } else {
                    Toast.makeText(getActivity(), "电话号码格式不正确，请记录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //endregion

        LoadData();

        initPermission();

        return view;
    }

    private void initPermission() {
        EasyPermissions.requestPermissions(
                new PermissionRequest.Builder(this, 1, "android.permission.CALL_PHONE")
                        .setRationale(R.string.camera_and_location_rationale)
                        .setPositiveButtonText(R.string.rationale_ask_ok)
                        .setNegativeButtonText(R.string.rationale_ask_cancel)
                        .setTheme(R.style.Theme_AppCompat_Dialog)
                        .build());
    }


    public void setValues(SellerInfoListActivity activity) {
        this.activity = activity;
    }

    //region 加载数据
    private void LoadData() {
        //LoadDataOnLine();

        for (int i = 0; i < 10; i++) {

            Sellers_Item item1 = new Sellers_Item("124237022", "成都美食" + i, "川菜", "成都映象(天府二街店)",
                    "四星商户", "028-85381999", "193条评论", "武侯区天府三街(入口在吉庆一路上)");

            mList.add(item1);

            Sellers_Item item = new Sellers_Item("124237022", "成都美食" + i, "川菜", "成都映象(天府二街店)",
                    "四星商户", "028-85381999;028-85381111", "193条评论", "武侯区天府三街(入口在吉庆一路上)");

            mList.add(item);
            adapter.notifyDataSetChanged();

        }


    }
    //endregion

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
