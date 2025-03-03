package com.example.customadapterlistview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {
    private RecyclerView rvSongs;
    private SongAdapter mSongAdapter;
    private List<SongModel> mSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view);
        rvSongs = (RecyclerView) findViewById(R.id.rv_songs);

        //Create song data
        mSongs = new ArrayList<>();
        mSongs.add(new SongModel("60696", "NẾU EM CÒN TỒN TẠI", "Khi anh bắt đầu 1 tình yêu Là lu anh tự thay", "Trình Đình Quang"));
        mSongs.add(new SongModel("60701", "NGỐC", "Có rất nhiều những câu chuyện Em dấu rieng mình em biết", "Khắc Việt"));
        mSongs.add(new SongModel("60650", "HÃY TIN ANH LẦN NỮA", "Dẫu cho ta đã sai khi  bên nhau Cô yêu thương", "Thiên Dũng"));
        mSongs.add(new SongModel("60610", "CHUỖI NGÀY VẮNG EM", "Từ khi em bước ra đi cõi lòng anh ngập tràng bao", "Duy Cường"));
        mSongs.add(new SongModel("60656", "KHI NGƯƠÌ MÌNH YÊU KHÓC", "Nước mắt em đang rơi trên những ngo tay Nước mắt em", "Phan Mạnh Quỳnh"));
        mSongs.add(new SongModel("60685", "MỞ", "Anh mơ gă em anh mơ được ôm anh mơ được gần", "Trịnh Thăng Bình"));
        mSongs.add(new SongModel("60752", "TÌNH YÊU CHẮP VÁ", "Muốn đi xa nơi yêu thương mình từng có Để không nghe", "Mr. Siro"));
        mSongs.add(new SongModel("60608", "CHỜ NGÀY MƯA TAN", "1 ngày mu và em khuất xa nơi anh bóng dáng cứ", "Trung Đức"));
        mSongs.add(new SongModel("60603", "CÂU HỎI EM CHƯA TRẢ LỜI", "Cần nơi em 1 lời giải thích thật lòng Đừng lặng im", "Yuki Huy Nam"));
        mSongs.add(new SongModel("60720", "QUA ĐI LẴNG LẼ", "Đôi khi đến với nhàu yêu thường chẳng được bao lâu nhưng khi", "Pham Mạnh Quỳnh"));
        mSongs.add(new SongModel("60856", "QUÊN ANH LÀ ĐIỀU EM KHÔNG THỂ - REMIX", "Cần thêm bao lâu để em qun đi niềm đâu Cần thêm", "Thiện Ngôn"));
        mSongAdapter = new SongAdapter(this, mSongs);
        rvSongs.setAdapter(mSongAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSongs.setLayoutManager(linearLayoutManager);
    }
}
