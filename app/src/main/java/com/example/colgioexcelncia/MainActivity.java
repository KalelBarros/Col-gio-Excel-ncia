package com.example.colgioexcelncia;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.colgioexcelncia.Classes.DB_Escola;
import com.santalu.maskara.widget.MaskEditText;

public class MainActivity extends AppCompatActivity
{

    private DB_Escola db;
    private Button bt_entrar;
    private MaskEditText CPF;
    private EditText Senha;
    private String nome_usuario;
    private boolean isAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteDatabase("DB_Escola");

        bt_entrar = findViewById(R.id.btEntrar);
        CPF = findViewById(R.id.cpf);
        Senha = findViewById(R.id.senha);
        db = new DB_Escola(this);


        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isCamposValidos(CPF, Senha))
                {

                    if (ValidarCPF(CPF.getUnMasked()) && ValidarSenha(Senha.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = null;

                        if (isAluno)
                        {
                            intent = new Intent(MainActivity.this, TelaProfessor.class);
                        } else
                        {
                            intent = new Intent(MainActivity.this, TelaProfessor.class);
                        }

                        intent.putExtra("nome", nome_usuario);

                        startActivity(intent);
                        finish();
                    } else
                    {
                        Toast.makeText(MainActivity.this, "Cpf ou senha incorreto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private boolean ValidarCPF(String cpf)
    {
        String query_professor = "SELECT * FROM " + DB_Escola.TABELA_PROFESSOR + " WHERE cpf_professor = ?";
        String query_aluno = "SELECT * FROM " + DB_Escola.TABELA_ALUNO + " WHERE cpf_aluno = ?";

        Cursor cursor_professor = db.getReadableDatabase().rawQuery(query_professor, new String[]{cpf});
        Cursor cursor_aluno = db.getReadableDatabase().rawQuery(query_aluno, new String[]{cpf});

        if (cursor_professor.moveToFirst())
        {
            nome_usuario = cursor_professor.getString(cursor_professor.getColumnIndexOrThrow("nome_professor"));
            cursor_professor.close();
            cursor_aluno.close();
            isAluno = false;
            return true;
        } else if (cursor_aluno.moveToFirst())
        {
            nome_usuario = cursor_aluno.getString(cursor_aluno.getColumnIndexOrThrow("nome_aluno"));
            cursor_professor.close();
            cursor_aluno.close();
            isAluno = true;
            return true;
        } else
        {
            cursor_professor.close();
            cursor_aluno.close();
            return false;
        }
    }

    private boolean ValidarSenha(String senha)
    {
        if(isAluno)
        {
            String query_aluno = "SELECT * FROM " + DB_Escola.TABELA_ALUNO + " WHERE senha_aluno = ?";
            Cursor cursor_aluno = db.getReadableDatabase().rawQuery(query_aluno, new String[]{senha});

            if (cursor_aluno.moveToFirst())
            {
                cursor_aluno.close();
                return true;
            } else
            {
                cursor_aluno.close();
                return false;
            }
        }
        else
        {
            String query_professor = "SELECT * FROM " + DB_Escola.TABELA_PROFESSOR + " WHERE senha_professor = ?";
            Cursor cursor_professor = db.getReadableDatabase().rawQuery(query_professor, new String[]{senha});

            if (cursor_professor.moveToFirst())
            {
                cursor_professor.close();
                return true;
            }
            else
            {
                cursor_professor.close();
                return false;
            }
        }
    }

    private boolean isCamposValidos(MaskEditText Campo_CPF, EditText Campo_Senha)
    {
        String cpf = Campo_CPF.getText().toString();
        String senha = Campo_Senha.getText().toString();

        if(cpf.isEmpty() || senha.isEmpty())
        {
            Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(cpf.length() < 11)
        {
            Toast.makeText(MainActivity.this, "Há campos incompletos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}