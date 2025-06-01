package com.example.colgioexcelncia;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.colgioexcelncia.Classes.Aluno;
import com.example.colgioexcelncia.Classes.DB_Escola;

public class TelaAluno extends AppCompatActivity {

    private TextView nome, iniciais, serie;
    private Button bt_logout;
    private DB_Escola db;
    private static Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_aluno);

        db = new DB_Escola(this);
        nome = findViewById(R.id.Nome_aluno);
        iniciais = findViewById(R.id.iniciais);
        bt_logout = findViewById(R.id.logout);
        serie = findViewById(R.id.Serie_aluno);

        aluno = buscarAlunoPorCPF(getIntent().getStringExtra("cpf"));

        nome.setText("Olá, " + aluno.getNome());
        iniciais.setText(aluno.getIniciais());
        serie.setText(aluno.getSerie() + " " +aluno.getNome_turma());


        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaAluno.this, MainActivity.class);
                startActivity(intent);
                aluno = null;
                finish();
            }
        });


    }

    private Aluno buscarAlunoPorCPF(String cpf) {
        String query = "SELECT * FROM " + DB_Escola.TABELA_ALUNO + "," + DB_Escola.TABELA_TURMA + " WHERE cpf_aluno = ? AND aluno.id_turma = turma.id_turma";
        Cursor cursor = db.getReadableDatabase().rawQuery(query, new String[]{cpf});

        if (cursor.moveToFirst()) {
            String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome_aluno"));
            String senha = cursor.getString(cursor.getColumnIndexOrThrow("senha_aluno"));
            String data_nascimento = cursor.getString(cursor.getColumnIndexOrThrow("data_nascimento"));
            String serie = cursor.getString(cursor.getColumnIndexOrThrow("serie"));
            String nome_turma = cursor.getString(cursor.getColumnIndexOrThrow("nome_turma"));
            cursor.close();
            return new Aluno(cpf, nome, senha, data_nascimento, serie, nome_turma);
        } else {
            cursor.close();
            return null;
        }
    }

    public void GoTelaNotas(View v)
    {
        Intent intent = new Intent(TelaAluno.this, TelaNotas.class);
        startActivity(intent);
    }

    public void TelaMinhasInformacoesAluno(View v)
    {
        Intent intent = new Intent(TelaAluno.this, MinhasInformacoes.class);
        startActivity(intent);
    }

    public static Aluno getAluno()
    {
        return aluno;
    }




}