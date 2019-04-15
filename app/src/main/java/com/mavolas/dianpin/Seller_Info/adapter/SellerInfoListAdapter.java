package com.mavolas.dianpin.Seller_Info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.mavolas.dianpin.R;
import com.mavolas.dianpin.bean.Sellers_Item;

import java.util.List;
import java.util.Map;

/**
 * 列表Adapter：设施信息 / 车库信息
 */
public class SellerInfoListAdapter extends ArrayAdapter<Sellers_Item> {

    Context context;
    int resource;
    List<Sellers_Item> data;
    LayoutInflater inflater;

    Map<String, String> Type;

    public SellerInfoListAdapter(Context context, int resource, List<Sellers_Item> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Sellers_Item getItem(int position) {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();

            //region 控件绑定
            holder.area = convertView.findViewById(R.id.tv_seller_area);
            holder.type = convertView.findViewById(R.id.tv_seller_type);
            holder.name = convertView.findViewById(R.id.tv_seller_name);
            holder.star = convertView.findViewById(R.id.tv_seller_star);
            holder.phone = convertView.findViewById(R.id.tv_seller_phone);
            holder.commentCount = convertView.findViewById(R.id.tv_seller_comment);
            holder.address = convertView.findViewById(R.id.tv_seller_address);
            holder.status = convertView.findViewById(R.id.tv_seller_status);
            holder.remark = convertView.findViewById(R.id.tv_seller_remark);
            //endregion

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Sellers_Item item = getItem(position);

        holder.area.setText(item.area);
        holder.type.setText(item.type);
        holder.name.setText(item.name);

        holder.star.setText(item.star);
        holder.phone.setText("电话：" + item.phone);
        holder.commentCount.setText(item.commentCount);
        holder.address.setText("地址：" + item.address);
        holder.status.setText(item.status);
        holder.remark.setText("备注：" + item.remark);


        return convertView;
    }

    class ViewHolder {

        TextView area;
        TextView type;
        TextView name;
        TextView star;
        TextView phone;
        TextView commentCount;
        TextView address;
        TextView status;
        TextView remark;
    }
}
