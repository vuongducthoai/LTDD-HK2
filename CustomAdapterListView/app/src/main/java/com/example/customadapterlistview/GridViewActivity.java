package com.example.customadapterlistview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {
    //Khai bao
    GridView gridView;
    ArrayList<String> arrayList;
    EditText editText1;
    Button btnNhap;
    Button btnCapNhat;
    Button btnXoa;
    int viTri = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_view);
        //ánh xạ
        gridView = (GridView) findViewById(R.id.gridview1);
        editText1 = (EditText)findViewById(R.id.editText1);
        btnNhap = (Button)findViewById(R.id.btnNhap);
        btnCapNhat = (Button)findViewById(R.id.btnCapNhat);
        btnXoa = (Button)findViewById(R.id.btnXoa);


        arrayList = new ArrayList<>();
        //Thêm dữ liệu vào List
        arrayList.add("Java");
        arrayList.add("C#");
        arrayList.add("PHP");
        arrayList.add("Kotlin");
        arrayList.add("Dart");

        //Tạo ArrayAdapter
        ArrayAdapter adapter = new ArrayAdapter(
                GridViewActivity.this,
                android.R.layout.simple_list_item_1,
                arrayList
        );

        gridView.setAdapter(adapter);

        //Bat su kien click nhanh tren tung dong cua GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                //code yêu cầu
                //i: trả về vị trí click chuột trên GridView -> i
               // ban đầu =0
                editText1.setText(arrayList.get(i));
                Toast.makeText(GridViewActivity.this, "i", Toast.LENGTH_SHORT).show();
                viTri = i;
            }
        });

        //Bat su kien du tren tung dong cua GridView
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //code yêu cầu
                //i: trả về vị trí click chuột trên GridView -> i
                // ban đầu =0
                Toast.makeText(GridViewActivity.this, "i", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText1.getText().toString();
                arrayList.add(name);

            }
        });


        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.set(viTri, editText1.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(viTri);
                adapter.notifyDataSetChanged();
            }
        });

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
