package com.example.databinding_shared_preferences_sqllite.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "schoolManager";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "students";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE_NUMBER = "phone_number";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /*
        - Phương thức này được gọi bởi framework, nếu có yêu cầu truy cập database mà lại chưa khởi tạo
database.
        - ở đây ta phải viết code khởi tạo database, cụ thể là khởi tạo bảng (chú ý: khi khởi tạo bảng, ta phải đặt tên
khóa chính là _id
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_students_table = String.format( "CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, KEY_ID, KEY_NAME, KEY_ADDRESS, KEY_PHONE_NUMBER);
        db.execSQL(create_students_table);
    }

    /*
       - phương thức này được dùng khi ứng dụng của bạn có nhiều phiên bản database được thêm vào.
       - Nó sẽ cập nhật database hiện có hoặc khởi tạo lại thông qua onCreate()
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN new_column TEXT");
        }
    }

}
