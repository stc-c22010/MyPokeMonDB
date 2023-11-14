package jp.suntech.c22010.mypokemondb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper _helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _helper = new DatabaseHelper(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        _helper.close();
        super.onDestroy();
    }
}