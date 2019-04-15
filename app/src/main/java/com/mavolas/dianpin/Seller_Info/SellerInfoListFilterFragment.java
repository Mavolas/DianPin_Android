package com.mavolas.dianpin.Seller_Info;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.mavolas.dianpin.R;

import java.util.List;

/**
 * 列表检索条件：设施信息 / 车库信息
 */
public class SellerInfoListFilterFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private SellerInfoListActivity activity;

    //region 变量定义

    /**
     * 设施ID
     */
    private EditText editTextFacilityName;

    /**
     * 设施ID
     */
    private EditText editTextFacilityAddress;

    /**
     * 确定按钮
     */
    private Button btnSubmit;
    //endregion

    public void setValues(DrawerLayout mDrawerLayout, SellerInfoListActivity activity) {
        this.mDrawerLayout = mDrawerLayout;
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_seller_list_filter, container, false);

        activity = (SellerInfoListActivity) getActivity();

        //设施ID
        editTextFacilityAddress = view.findViewById(R.id.editTextFacilityAddress);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        loadBaseData();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity.filterValues.CommunityID = spinnerCommunityID.getValue();
//                //设施ID
//                activity.filterValues.FacilityName = editTextFacilityName.getText().toString();
//                activity.filterValues.FacilityType = spinnerFacilityType.getValue();
//                activity.filterValues.FacilityAddress = editTextFacilityAddress.getText().toString();
//                activity.needRefresh = true;
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        return view;
    }

    private void loadBaseData() {


    }
}
