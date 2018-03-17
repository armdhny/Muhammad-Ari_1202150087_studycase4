package com.example.ari.muhammadari_1202150087_studycase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuUtama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
    }
/*
* Method untuk pindah ke aktivitas ShowName
* Method untuk pindah ke aktivitas SearchImage*/
    public void tampilNama(View view) {
        Intent i = new Intent(getApplicationContext(),ShowName.class);
        startActivity(i);
    }

    public void tampilGambar(View view) {
        Intent i = new Intent(getApplicationContext(),SearchImage.class);
        startActivity(i);
    }
}
