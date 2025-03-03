package com.example.customadapterlistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customadapterlistview.R;
import com.example.customadapterlistview.model.MonHoc;

import java.util.List;

public class MonHocAdapter extends BaseAdapter {
    //khai bao
    private Context context;
    private int layout;
    private List<MonHoc> monHocList;


    public MonHocAdapter(Context context, int layout, List<MonHoc> monHocList){
        this.context = context;
        this.layout = layout;
        this.monHocList = monHocList;
    }


    @Override
    public int getCount() {
       return monHocList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    static class  ViewHolder {
        TextView textName, textDesc;
        ImageView imagePic;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        //Khoi tao viewHolder
        ViewHolder viewHolder;
        //lay context
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Goi view chua layout
            view = inflater.inflate(layout, null);

            //Anh xa View
            viewHolder = new ViewHolder();
            viewHolder.textName = (TextView)view.findViewById(R.id.textName);
            viewHolder.textDesc = (TextView)view.findViewById(R.id.textDesc);
            viewHolder.imagePic = (ImageView) view.findViewById(R.id.imagePic);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //gan gia tri
        MonHoc monHoc = monHocList.get(i);
        viewHolder.textName.setText(monHoc.getName());
        viewHolder.textDesc.setText(monHoc.getDesc());
        viewHolder.imagePic.setImageResource(monHoc.getPic());
        return view;
    }
}
