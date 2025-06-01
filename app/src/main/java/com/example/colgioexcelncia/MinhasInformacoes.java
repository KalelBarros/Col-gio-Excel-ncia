package com.example.colgioexcelncia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colgioexcelncia.Classes.Aluno;
import com.example.colgioexcelncia.Classes.DB_Escola;
import com.example.colgioexcelncia.Classes.Professor;

public class MinhasInformacoes extends AppCompatActivity {

    private DB_Escola db;
    private EditText novaSenhaInput;
    private TextView nome, cpf, senha, tipoUsuario, infoExtra;
    private Button btMudarSenha, voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_informacoes);

        db = new DB_Escola(this);

        // Inicialização de views
        novaSenhaInput = findViewById(R.id.novaSenha);
        btMudarSenha = findViewById(R.id.bt_mudarSenha);
        nome = findViewById(R.id.nome);
        cpf = findViewById(R.id.cpf);
        senha = findViewById(R.id.senha);
        tipoUsuario = findViewById(R.id.tipoUsuario);
        infoExtra = findViewById(R.id.infoExtra);
        voltar = findViewById(R.id.voltar);

        if (TelaAluno.getAluno() != null) {
            Aluno aluno = TelaAluno.getAluno();
            nome.setText("Nome: " + aluno.getNome());
            cpf.setText("CPF: " + aluno.getCpf());
            senha.setText("Senha: " + aluno.getSenha());
            tipoUsuario.setText("Tipo: Aluno");
            infoExtra.setText("Nascimento: " + aluno.getData_nascimento());
        } else if (TelaProfessor.getProfessor() != null) {
            Professor professor = TelaProfessor.getProfessor();
            nome.setText("Nome: " + professor.getNome());
            cpf.setText("CPF: " + professor.getCpf());
            senha.setText("Senha: " + professor.getSenha());
            tipoUsuario.setText("Tipo: Professor");
            infoExtra.setText("ID da disciplina: " + professor.getId_disciplina());
        }

        btMudarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novaSenha = novaSenhaInput.getText().toString().trim();

                if (novaSenha.isEmpty()) {
                    Toast.makeText(MinhasInformacoes.this, "Digite a nova senha", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean sucesso = false;

                if (TelaAluno.getAluno() != null) {
                    String cpf = TelaAluno.getAluno().getCpf();
                    sucesso = atualizarSenhaAluno(cpf, novaSenha);
                    TelaAluno.getAluno().setSenha(novaSenha);
                } else if (TelaProfessor.getProfessor() != null) {
                    String cpf = TelaProfessor.getProfessor().getCpf();
                    sucesso = atualizarSenhaProfessor(cpf, novaSenha);
                    TelaProfessor.getProfessor().setSenha(novaSenha);
                }

                if (sucesso) {
                    senha.setText("Senha: " + novaSenha);
                    novaSenhaInput.setText("");
                    Toast.makeText(MinhasInformacoes.this, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MinhasInformacoes.this, "Erro ao alterar a senha", Toast.LENGTH_SHORT).show();
                }
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private boolean atualizarSenhaAluno(String cpf, String novaSenha) {
        try {
            db.getWritableDatabase().execSQL("UPDATE " + DB_Escola.TABELA_ALUNO + " SET senha_aluno = ? WHERE cpf_aluno = ?", new Object[]{novaSenha, cpf});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean atualizarSenhaProfessor(String cpf, String novaSenha) {
        try {
            db.getWritableDatabase().execSQL("UPDATE " + DB_Escola.TABELA_PROFESSOR + " SET senha_professor = ? WHERE cpf_professor = ?", new Object[]{novaSenha, cpf});
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

