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

//Adapter này kế thừa từ BaseAdapter. Adapter này sẽ chuyển đổi mỗi đối tượng
//dữ liệu thành một view tương ứng trong ListView.
//(Adapter - Cầu nối dữ liệu với giao diện)
//Lam nhiệm vụ kết nối dữ lệu từ danh sách ArrayList<MonHoc> vào listView.
public class MonHocAdapter extends  BaseAdapter {
    //khai bao
    private Context context; // Doi tuong Context, dung de truy cap tai nguyen ung dung
    private int layout; // ID cua layout XML dung de hien thi tung item trong ListView.
    private List<MonHoc> monHocList; // Danh sach cac mon hoc (List<MonHoc>), chinh la du lieu can hien thi.

    public MonHocAdapter(Context context, int layout, List<MonHoc> monHocList){
        this.context = context;
        this.layout = layout;
        this.monHocList = monHocList;
    }


    //Tra ve so luong phan tu trong danh sach monHocList (so luong mon hoc can hien thi).
    @Override
    public int getCount() {
       return monHocList.size();
    }

    @Override
    public Object getItem(int position) {
        return monHocList.get(position);
    }


    //Tra ve ID tuong ung voi vi tri cua moi item
    @Override
    public long getItemId(int position) {
        return position;
    }



    //ViewHolder giup luu tru cac thanh phan giao dien (TextView, ImageView) cua moi item
    // trong ListView, giup tang hieu suat bang cach tránh viec goi findViewById() nhieu lan
    static class ViewHolder {
        TextView textName, textDesc;
        ImageView imagePic;
    }

    //Phuong thuc duoc goi khi ListView can ve mot item
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
        //gan gia tri cho item
        MonHoc monHoc = monHocList.get(i);
        viewHolder.textName.setText(monHoc.getName());
        viewHolder.textDesc.setText(monHoc.getDesc());
        viewHolder.imagePic.setImageResource(monHoc.getPic());
        return view;
    }
}
