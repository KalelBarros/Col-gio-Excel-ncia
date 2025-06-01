package com.example.colgioexcelncia;

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

public class TelaNotas extends AppCompatActivity
{

    private DB_Escola db;

    private Button bt_voltar;

    // Nome do aluno
    private TextView nomeAluno, trimestre;

    // Matérias
    private TextView[] materias;
    // Notas AV1
    private TextView[] av1;
    // Notas AV2
    private TextView[] av2;
    // Médias
    private TextView[] medias;

    private Aluno aluno;

    private String Trimestre, Ano_letivo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_notas);

        db = new DB_Escola(this);

        db = new DB_Escola(this);

        nomeAluno = findViewById(R.id.nomeA);
        trimestre = findViewById(R.id.trimesre);

        bt_voltar = findViewById(R.id.bt_voltar);

        aluno = TelaAluno.getAluno();

        String cpfAluno = aluno.getCpf();

        // Inicializa arrays com os TextViews do layout
        materias = new TextView[]{
                findViewById(R.id.materia1),
                findViewById(R.id.materia2),
                findViewById(R.id.materia3),
                findViewById(R.id.materia4)
        };

        av1 = new TextView[]{
                findViewById(R.id.Av1_n1),
                findViewById(R.id.Av1_n2),
                findViewById(R.id.Av1_n3),
                findViewById(R.id.Av1_n4)
        };

        av2 = new TextView[]{
                findViewById(R.id.Av2_n1),
                findViewById(R.id.Av2_n2),
                findViewById(R.id.Av2_n3),
                findViewById(R.id.Av2_n4)
        };

        medias = new TextView[]{
                findViewById(R.id.media1),
                findViewById(R.id.media2),
                findViewById(R.id.media3),
                findViewById(R.id.media4)
        };

        bt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        carregarNotas(cpfAluno);


        if (Trimestre != null || Ano_letivo != null)
        {
            trimestre.setText(Trimestre + "° Trimestre de " + Ano_letivo);
        }
        else
        {
            trimestre.setText("Não há notas cadastradas ainda");
        }
    }


    private void carregarNotas(String cpf)
    {
        // Define o nome do aluno
        Cursor alunoCursor = db.getReadableDatabase().rawQuery(
                "SELECT nome_aluno FROM " + DB_Escola.TABELA_ALUNO + " WHERE cpf_aluno = ?",
                new String[]{cpf}
        );

        if (alunoCursor.moveToFirst()) {
            nomeAluno.setText("Aluno: " + alunoCursor.getString(0));
        }
        alunoCursor.close();


        String query = "SELECT d.id_disciplina, d.nome_disciplina, n.av1, n.av2, n.media, n.trimestre, n.ano_letivo " +
                "FROM nota n " +
                "JOIN disciplina d ON n.id_disciplina = d.id_disciplina " +
                "WHERE n.cpf_aluno = ?";



        Cursor cursor = db.getReadableDatabase().rawQuery(query, new String[]{cpf});

        while (cursor.moveToNext()) {
            int idDisciplina = cursor.getInt(cursor.getColumnIndexOrThrow("id_disciplina"));
            String nomeDisciplina = cursor.getString(cursor.getColumnIndexOrThrow("nome_disciplina"));
            Trimestre = cursor.getString(cursor.getColumnIndexOrThrow("trimestre"));
            Ano_letivo = cursor.getString(cursor.getColumnIndexOrThrow("ano_letivo"));
            double notaAv1 = cursor.getDouble(cursor.getColumnIndexOrThrow("av1"));
            double notaAv2 = cursor.getDouble(cursor.getColumnIndexOrThrow("av2"));
            double mediaFinal = cursor.getDouble(cursor.getColumnIndexOrThrow("media"));

            int index = idDisciplina - 1;
            if (index >= 0 && index < 4)
            {
                materias[index].setText(nomeDisciplina);
                av1[index].setText(String.format("%.2f", notaAv1));
                av2[index].setText(String.format("%.2f", notaAv2));
                medias[index].setText(String.format("%.2f", mediaFinal));
            }
        }

        cursor.close();
    }


}