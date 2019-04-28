package com.mavolas.dianpin.Seller_Info;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mavolas.dianpin.R;
import com.ziruk.viewitems.spinner.CodeValueCls;
import com.ziruk.viewitems.spinner.MySpinner;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表检索条件：设施信息 / 车库信息
 */
public class SellerInfoListFilterFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private SellerInfoListActivity activity;


    private EditText Name;
    private MySpinner Status;
    private MySpinner Type;
    private EditText Area;


    private Button btnSubmit;



    public void setValues(DrawerLayout mDrawerLayout, SellerInfoListActivity activity) {
        this.mDrawerLayout = mDrawerLayout;
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_seller_list_filter, container, false);

        activity = (SellerInfoListActivity) getActivity();

        Name = view.findViewById(R.id.et_filter_name);
        Status = view.findViewById(R.id.sp_filter_status);
        Type = view.findViewById(R.id.sp_filter_type);
        Area = view.findViewById(R.id.et_filter_area);

        btnSubmit = view.findViewById(R.id.btnSubmit);

        loadBaseData();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.filterValues.Name = Name.getText().toString();
                activity.filterValues.Status = Status.getValue();
                activity.filterValues.Type = Type.getValue();
                activity.filterValues.Area = Area.getText().toString();
                activity.needRefresh = true;
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        return view;
    }

    private void loadBaseData() {


        final List<CodeValueCls> status = new ArrayList<>();

        status.add(new CodeValueCls("01","已打"));
        status.add(new CodeValueCls("01","已打"));
        status.add(new CodeValueCls("01","已打"));
        status.add(new CodeValueCls("01","已打"));
        status.add(new CodeValueCls("01","已打"));
        status.add(new CodeValueCls("01","已打"));


        Status.setDataBindListener(new MySpinner.OnDataBindingBeginListener() {
            @Override
            public List<CodeValueCls> getData() {
                return status;
            }
        });


        Type.setDataBindListener(new MySpinner.OnDataBindingBeginListener() {
            @Override
            public List<CodeValueCls> getData() {


                return null;
            }
        });



    }


}
