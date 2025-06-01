package com.example.colgioexcelncia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.colgioexcelncia.Classes.DB_Escola;
import com.example.colgioexcelncia.Classes.Professor;
import com.santalu.maskara.widget.MaskEditText;

public class TelaPublicarNota extends AppCompatActivity {

    private EditText nota1, nota2;
    private MaskEditText cpf;
    private Button Salvar;
    private DB_Escola db;
    private Professor professor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_publicar_nota);

        db = new DB_Escola(this);

        // Recupera a instância do professor já logado
        professor = TelaProfessor.getProfessor();

        cpf = findViewById(R.id.cpfAluno);
        nota1 = findViewById(R.id.nota1);
        nota2 = findViewById(R.id.nota2);
        Salvar = findViewById(R.id.salvar);

        Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpfAluno = cpf.getUnMasked().toString().trim();
                String av1Str = nota1.getText().toString().trim();
                String av2Str = nota2.getText().toString().trim();

                if (cpfAluno.isEmpty() || av1Str.isEmpty() || av2Str.isEmpty()) {
                    Toast.makeText(TelaPublicarNota.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double av1 = Double.parseDouble(av1Str);
                    double av2 = Double.parseDouble(av2Str);
                    double media = (av1 + av2) / 2;

                    int anoLetivo = 2025;
                    int trimestre = 1;
                    String dataLancamento = "2025-06-01";

                    int idDisciplina = Integer.parseInt(professor.getId_disciplina());

                    String sql = "INSERT INTO " + DB_Escola.TABELA_NOTA +
                            " (av1, av2, media, ano_letivo, trimestre, data_lancamento, cpf_aluno, id_disciplina) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                    db.getWritableDatabase().execSQL(sql, new Object[]{
                            av1, av2, media, anoLetivo, trimestre, dataLancamento, cpfAluno, idDisciplina
                    });

                    Toast.makeText(TelaPublicarNota.this, "Nota salva com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();

                } catch (NumberFormatException e) {
                    Toast.makeText(TelaPublicarNota.this, "Notas inválidas", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(TelaPublicarNota.this, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}