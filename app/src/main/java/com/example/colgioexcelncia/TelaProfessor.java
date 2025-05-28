package com.example.colgioexcelncia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaProfessor extends AppCompatActivity
{
    private TextView nome;
    private Button bt_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_professor);

        bt_logout = findViewById(R.id.logout);
        nome = findViewById(R.id.Nome_professor);
        nome.setText(getIntent().getStringExtra("nome"));

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaProfessor.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}