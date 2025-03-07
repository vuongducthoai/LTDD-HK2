package com.example.databinding_shared_preferences_sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.databinding_shared_preferences_sqllite.database.DatabaseHandler;
import com.example.databinding_shared_preferences_sqllite.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private SQLiteDatabase db;
    private DatabaseHandler dbHelper;

    public StudentDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertStudent(String name, String address, String phone) {
        open();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", name);
        values.put("phone_number", phone);

        long result = db.insert("students", null, values);
        if(result != -1){
            Log.d("DB_INSERT", "Thêm sinh viên thành công: "+name);
        } else {
            Log.d("DB_INSERT", "Lỗi khi thêm sinh viên!");
        }
        close();
    }

    public void updateStudent(Student student){
        ContentValues values = new ContentValues();
        values.put("name",student.getName());
        values.put("address", student.getAddress());
        values.put("phone_number", student.getPhone_number());
        db.update("students", values, "id = ?", new String[]{String.valueOf(student.getId())});
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM students", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                String phone = cursor.getString(3);
                studentList.add(new Student(id, name, address, phone));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    public void deleteStudent(int studentId){
        open();
        db.delete(dbHelper.TABLE_NAME, dbHelper.KEY_ID + " = ?", new String[]{
            String.valueOf(studentId)});
        db.close();
    }

    public void doCreateStudentTabe(){
        open();
        String sql = "CREATE TABLE tblsinhvien (" +
                "masv TEXT PRIMARY KEY ," +
                "tensv TEXT," +
                "malop TEXT NOT NULL CONSTRAINT malop "+
                " REFERENCES tbllop(malop) ON DELETE CASCADE)";
        db.execSQL(sql);
    }


}
