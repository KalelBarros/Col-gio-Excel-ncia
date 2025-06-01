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
import com.example.colgioexcelncia.Classes.Professor;

public class TelaProfessor extends AppCompatActivity
{
    private TextView nome, iniciais;
    private Button bt_logout;
    private DB_Escola db;
    private static Professor professor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_professor);

        bt_logout = findViewById(R.id.logout);
        nome = findViewById(R.id.Nome_professor);
        iniciais = findViewById(R.id.iniciais);
        db = new DB_Escola(this);


        professor = buscarProfessorPorCPF(getIntent().getStringExtra("cpf"));

        nome.setText(professor.getNome());
        iniciais.setText(getIniciais(professor.getNome()));

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaProfessor.this, MainActivity.class);
                professor = null;
                startActivity(intent);
                finish();
            }
        });
    }

    private Professor buscarProfessorPorCPF(String cpf) {
        String query = "SELECT * FROM " + DB_Escola.TABELA_PROFESSOR + " p " +
                "JOIN " + DB_Escola.TABELA_DISCIPLINA + " d ON p.cpf_professor = d.cpf_professor " +
                "WHERE p.cpf_professor = ?;";

        Cursor cursor = db.getReadableDatabase().rawQuery(query, new String[]{cpf});

        if (cursor.moveToFirst()) {
            String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome_professor"));
            String senha = cursor.getString(cursor.getColumnIndexOrThrow("senha_professor"));
            String id_disciplina = cursor.getString(cursor.getColumnIndexOrThrow("id_disciplina"));

            cursor.close();
            return new Professor(cpf, nome, senha, id_disciplina);
        } else {
            cursor.close();
            return null;
        }
    }

    public String getIniciais(String nome) {
        String[] partes = nome.split(" ");
        String iniciais = "";

        if (partes.length > 0) {
            iniciais += partes[0].substring(0, 1).toUpperCase();
            if (partes.length > 1) {
                iniciais += partes[partes.length - 1].substring(0, 1).toUpperCase();
            }
        }

        return iniciais;
    }

    public void TelaPublicarNota(View view)
    {
        Intent intent = new Intent(TelaProfessor.this, TelaPublicarNota.class);
        startActivity(intent);
    }

    public void TelaMinhasInformacoesProfessor(View view)
    {
        Intent intent = new Intent(TelaProfessor.this, MinhasInformacoes.class);
        startActivity(intent);
    }

    public void TelaVerNotasCadastradas(View v)
    {
        Intent intent = new Intent(TelaProfessor.this, VerNotasCadastradas.class);
        startActivity(intent);
    }

    public static Professor getProfessor()
    {
        return professor;
    }


}