package com.example.customadapterlistview.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.customadapterlistview.adapter.MonHocAdapter;
import com.example.customadapterlistview.model.MonHoc;
import com.example.customadapterlistview.R;

import java.util.ArrayList;

public class CustomAdapterListViewActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<MonHoc> arrayList;
    MonHocAdapter adapter;
    EditText editText;
    int viTri = -1;
    Button btnCapNhat;
    Button btnXoa;
    Button btnThem;
    MonHoc monHoc;

    //anh xa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_list_view);

        AnhXa();
        adapter = new MonHocAdapter(CustomAdapterListViewActivity.this, R.layout.row_monhoc, arrayList);
        //truyen du lieu tu adapter ra listView
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                //code yêu cầu
                //i: trả về vị trí click chuột ListView -> i ban đầu = 0
                Toast.makeText(CustomAdapterListViewActivity.this, "Bạn đang nhấn giữ" + i + "-" + arrayList.get(i).getDesc(), Toast.LENGTH_SHORT).show();
                editText.setText(arrayList.get(i).getName());
                monHoc = new MonHoc(arrayList.get(i));
                viTri = i;
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                MonHoc newMonHoc = new MonHoc(name, monHoc.getDesc(), monHoc.getPic());
                arrayList.add(new MonHoc(newMonHoc));
                adapter.notifyDataSetChanged(); // Goi lại getView() cho danh sach moi
            }
        });


        btnCapNhat.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MonHoc monHocMoi = new MonHoc(
                        editText.getText().toString(),
                        monHoc.getDesc(),
                        monHoc.getPic()
                );
                arrayList.set(viTri, monHocMoi);
                adapter.notifyDataSetChanged(); // Goi lại getView() de cap nhat item
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                arrayList.remove(viTri);
                adapter.notifyDataSetChanged(); //Goi lai getView() de cap nhat danh sach
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void AnhXa(){
        listView = (ListView)findViewById(R.id.listView1);
        editText  = (EditText)findViewById(R.id.editText1);
        btnThem = (Button)findViewById(R.id.btnNhap);
        btnCapNhat = (Button)findViewById(R.id.btnCapNhat);
        btnXoa = (Button)findViewById(R.id.btnXoa);

        //Them du lieu vao List
        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("SpringBoot", "Java", R.drawable.spring));
        arrayList.add(new MonHoc("AngularJS", "Javascript", R.drawable.angular));
        arrayList.add(new MonHoc("ReactJS", "Javascript", R.drawable.react));
        arrayList.add(new MonHoc("Android", "Java", R.drawable.android));
    }
}
