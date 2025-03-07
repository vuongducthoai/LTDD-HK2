package com.example.databinding_shared_preferences_sqllite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.databinding_shared_preferences_sqllite.adapter.NotesAdapter;
import com.example.databinding_shared_preferences_sqllite.database.DatabaseHandler2;
import com.example.databinding_shared_preferences_sqllite.model.NotesModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler2 databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NotesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


//        Goi ham databaseSQLite
        mapping();
        InitDataSQLite();
        createDatabaseSQLite();
        databaseSQLite();

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void createDatabaseSQLite(){
        //thêm dữ liệu vào bảng
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, ' Vi du SQLite 1')");
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Vi du SQLite 2')");
    }

    private void InitDataSQLite(){
        //Khởi tạo database
        databaseHandler = new DatabaseHandler2(this, "notes.sqlite", null, 1);
        databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS  Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }

    private void databaseSQLite(){
        arrayList.clear();
        //Lay du lieu
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        while (cursor.moveToNext()){
            //Them du lieu vao arrayList
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new NotesModel(id, name));
        }
        adapter.notifyDataSetChanged();
    }

    private void mapping(){
        listView = (ListView) findViewById(R.id.listView1);
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(adapter);
    }


    //goi menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //bat su kien cho menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAddNotes) { // Kiểm tra xem item nào được chọn
            Log.d("DEBUG", "Menu thêm được nhấn!");
            DialogThem(); // Gọi hàm hiển thị Dialog
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_notes);

        //anh xa
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonAdd = dialog.findViewById(R.id.buttonEdit);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

        //bat su kien cho nut them va huy
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString().trim();
                if(name.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHandler.QueryData("INSERT INTO Notes VALUES(null, '"+ name+ "')" );
                    Toast.makeText(MainActivity.this, "Đã thêm Notes", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    databaseSQLite(); // goi ham load lai du lieu
                }
            }
        });
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //ham dialog cap nhat note
    public void DialogCapNhatNotes(String name, int id){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_notes);

        //anh xa
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonEdit = dialog.findViewById(R.id.buttonEdit);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);
        editText.setText(name);
        //bat su kien
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString().trim();
                databaseHandler.QueryData("UPDATE Notes SET NameNotes = '"+ name+"' WHERE Id = '" + id +"'");
                Toast.makeText(MainActivity.this,
                        "Đã cập nhật Notes thành công",
                        Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                databaseSQLite(); // goi ham load lai du lieu
            }
        });

        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //Ham dialog xoa
    public void DialogDelete(String name, final int id){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa Notes " + name + " này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHandler.QueryData("DELETE From Notes WHERE Id = '" + id + "'");
                Toast.makeText(MainActivity.this,
                        "Đã xóa Notes " + name + " thành công",
                        Toast.LENGTH_SHORT).show();
                databaseSQLite();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}