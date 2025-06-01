package com.example.colgioexcelncia;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.colgioexcelncia.Classes.DB_Escola;
import com.example.colgioexcelncia.Classes.Professor;

public class VerNotasCadastradas extends AppCompatActivity {

    private TableLayout tabela;
    private DB_Escola db;
    private Professor professor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_notas_cadastradas);

        tabela = findViewById(R.id.tabela_notas);
        db = new DB_Escola(this);
        professor = TelaProfessor.getProfessor(); // Usa o método estático

        carregarNotasDoProfessor();
    }

    private void carregarNotasDoProfessor() {
        String query = "SELECT n.cpf_aluno, a.nome_aluno, n.av1, n.av2, n.media, n.trimestre, n.ano_letivo, n.data_lancamento " +
                "FROM nota n " +
                "JOIN aluno a ON n.cpf_aluno = a.cpf_aluno " +
                "JOIN disciplina d ON n.id_disciplina = d.id_disciplina " +
                "WHERE d.cpf_professor = ?";

        Cursor cursor = db.getReadableDatabase().rawQuery(query, new String[]{professor.getCpf()});

        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);

            for (int i = 0; i < 8; i++) {
                TextView cell = new TextView(this);
                cell.setPadding(8, 8, 8, 8);
                cell.setText(cursor.getString(i));
                row.addView(cell);
            }

            tabela.addView(row);
        }

        cursor.close();
    }
}