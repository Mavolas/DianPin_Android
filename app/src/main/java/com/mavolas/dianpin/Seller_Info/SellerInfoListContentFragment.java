package com.mavolas.dianpin.Seller_Info;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mavolas.dianpin.R;
import com.mavolas.dianpin.Seller_Info.adapter.SellerInfoListAdapter;
import com.mavolas.dianpin.api.SellersApi;
import com.mavolas.dianpin.bean.Sellers_Item;
import com.mavolas.dianpin.bean.Sellers_Status;
import com.mavolas.dianpin.common.ResponseCls;
import com.mavolas.dianpin.http.client.HttpClient;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 列表内容Fragment：设施信息 / 车库信息
 */
public class SellerInfoListContentFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private SellerInfoListActivity activity;
    private List<Sellers_Item> mList = new ArrayList<>();

    private List<Sellers_Status> mStatus = new ArrayList<>();
    private SellerInfoListAdapter adapter;
    private Button btnSubmit;
    private String mCURDModel = "";
    private int mPositionSelected;

    private Date firstQueryTime = new Date();
    private int pageIndex = 0;
    private int pageSize = 15;
    private ListView lv;
    RefreshLayout refreshLayout;
    private SellersApi mSellersApi;
    private AlertDialog mDialog;

    private String mTag;
    private int mPosition;


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
                mList.clear();
                pageIndex = 0;
                LoadData();

                refreshlayout.finishRefresh(500/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                LoadData();
                refreshlayout.finishLoadMore(100/*,false*/);//传入false表示加载失败
            }
        });

        mSellersApi = HttpClient.retrofit.create(SellersApi.class);

        LoadStatus();


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


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (mDialog != null) {
                    mPosition = position;
                    mDialog.show();
                } else {
                    Toast.makeText(getActivity(), "初始化数据错误", Toast.LENGTH_SHORT).show();
                }


                return false;
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

        // 创建网络请求接口的实例

        mSellersApi.getSellers_Info(pageIndex, pageSize).enqueue(new Callback<ResponseCls<List<Sellers_Item>>>() {
            @Override
            public void onResponse(Call<ResponseCls<List<Sellers_Item>>> call, Response<ResponseCls<List<Sellers_Item>>> response) {
                if (response.code() == 200 && response.body() != null) {

                    if (response.body().getData() == null || response.body().getData().size() == 0) {
                        Toast.makeText(activity, "没有数据", Toast.LENGTH_SHORT).show();

                    } else {
                        pageIndex++;
                        mList.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(activity, "加载错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCls<List<Sellers_Item>>> call, Throwable t) {
                Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });


    }
    //endregion

    private void LoadStatus() {
        //LoadDataOnLine();

        // 创建网络请求接口的实例

        mSellersApi.getSellers_Status().enqueue(new Callback<ResponseCls<List<Sellers_Status>>>() {
            @Override
            public void onResponse(Call<ResponseCls<List<Sellers_Status>>> call, Response<ResponseCls<List<Sellers_Status>>> response) {
                if (response.code() == 200 && response.body() != null) {

                    if (response.body().getData() == null || response.body().getData().size() == 0) {
                        Toast.makeText(activity, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {
                        mStatus.addAll(response.body().getData());

                        StatusAlert();
                    }
                } else {
                    Toast.makeText(activity, "加载错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCls<List<Sellers_Status>>> call, Throwable t) {
                Toast.makeText(activity, "加载常数失败", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void SaveStatus(String id,String status,String remark) {
        //LoadDataOnLine();

        // 创建网络请求接口的实例

        mSellersApi.SaveStatus(id,status,remark).enqueue(new Callback<ResponseCls<String>>() {
            @Override
            public void onResponse(Call<ResponseCls<String>> call, Response<ResponseCls<String>> response) {
                if (response.code() == 200 && response.body() != null) {

                    if (response.body().getData() != null && response.body().getData().equals("OK")) {
                        Toast.makeText(activity, "保存成功", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(activity, "保存出现错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, "保存失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCls<String>> call, Throwable t) {
                Toast.makeText(activity, "保存失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void StatusAlert() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = View.inflate(getContext(), R.layout.fragment_dialog_view, null);   // 账号、密码的布局文件，自定义
        final ChipGroup chipGroup = view.findViewById(R.id.cg_group_status);

        final EditText editText = view.findViewById(R.id.et_item_remark);

        ChipGroup.LayoutParams layoutParams = new ChipGroup.LayoutParams(ChipGroup.LayoutParams.WRAP_CONTENT, ChipGroup.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < mStatus.size(); i++) {

            Chip chip = new Chip(getActivity());
            chip.setTag(mStatus.get(i).code);
            chip.setText(mStatus.get(i).value);
            chip.setLayoutParams(layoutParams);
            chip.setCheckedIconResource(R.mipmap.ic_launcher);
            chip.setId(View.generateViewId());
            chip.setCheckable(true);

            chipGroup.addView(chip);

            if (i ==0) {
                chip.setChecked(true);
            }

        }


        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(ChipGroup chipGroup, @IdRes int checkedId) {

            }
        });


        builder.setTitle("输入状态信息");

        mDialog = builder.create();

        mDialog.setView(view);
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Sellers_Item sellers_item = mList.get(mPosition);

                Chip chip = chipGroup.findViewById(chipGroup.getCheckedChipId());
                mTag = (String) chip.getTag();

                SaveStatus(sellers_item.ID, mTag,editText.getText().toString());

                sellers_item.status = chip.getText().toString();
                sellers_item.remark = editText.getText().toString();


                mList.remove(mPosition);
                mList.add(mPosition,sellers_item);

                adapter.notifyDataSetChanged();

                dialog.dismiss();//关闭对话框
            }
        });
        mDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
            }
        });

        Window dialogWindow = mDialog.getWindow();//获取window对象
        dialogWindow.setGravity(Gravity.TOP);//设置对话框位置
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }


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
