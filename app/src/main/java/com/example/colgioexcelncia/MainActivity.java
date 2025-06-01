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

import com.example.colgioexcelncia.Classes.Aluno;
import com.example.colgioexcelncia.Classes.DB_Escola;
import com.example.colgioexcelncia.Classes.Professor;
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

        //deleteDatabase("DB_Escola");

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

                    if (autenticarUsuario(CPF.getUnMasked(), Senha.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = null;


                        if (isAluno) {
                            intent = new Intent(MainActivity.this, TelaAluno.class);
                            startActivity(intent);
                        } else
                        {
                            intent = new Intent(MainActivity.this, TelaProfessor.class);
                            startActivity(intent);
                        }


                        intent.putExtra("cpf", nome_usuario);

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


    private boolean autenticarUsuario(String cpf, String senha) {
        // Tenta autenticar como professor
        String queryProf = "SELECT * FROM " + DB_Escola.TABELA_PROFESSOR + " WHERE cpf_professor = ? AND senha_professor = ?";
        Cursor cursorProf = db.getReadableDatabase().rawQuery(queryProf, new String[]{cpf, senha});

        if (cursorProf.moveToFirst()) {
            nome_usuario = cursorProf.getString(cursorProf.getColumnIndexOrThrow("cpf_professor"));
            isAluno = false;
            cursorProf.close();
            return true;
        }
        cursorProf.close();

        // Tenta autenticar como aluno
        String queryAluno = "SELECT * FROM " + DB_Escola.TABELA_ALUNO + " WHERE cpf_aluno = ? AND senha_aluno = ?";
        Cursor cursorAluno = db.getReadableDatabase().rawQuery(queryAluno, new String[]{cpf, senha});

        if (cursorAluno.moveToFirst()) {
            nome_usuario = cursorAluno.getString(cursorAluno.getColumnIndexOrThrow("cpf_aluno"));
            isAluno = true;
            cursorAluno.close();
            return true;
        }
        cursorAluno.close();

        return false;
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