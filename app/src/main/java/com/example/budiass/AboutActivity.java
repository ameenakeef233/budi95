package com.example.budiass;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        MaterialToolbar toolbar = findViewById(R.id.toolbarAbout);
        setSupportActionBar(toolbar);

        // Fungsi Butang Kembali
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Tutup Activity About dan balik ke Main
            }
        });
    }
}