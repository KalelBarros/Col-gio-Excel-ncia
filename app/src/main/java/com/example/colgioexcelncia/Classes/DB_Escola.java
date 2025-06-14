package com.example.colgioexcelncia.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB_Escola extends SQLiteOpenHelper
{
    public static int VERSION = 1;
    public static String NOME_DB = "DB_Escola";
    public static String TABELA_ALUNO = "aluno";
    public static String TABELA_PROFESSOR = "professor";
    public static String TABELA_NOTA = "nota";
    public static String TABELA_DISCIPLINA = "disciplina";
    public static String TABELA_TURMA = "turma";
    public static String TABELA_PROFESSOR_LECIONA_TURMA = "professor_leciona_turma";


    public DB_Escola(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_PROFESSOR +
                "(cpf_professor VARCHAR(11) PRIMARY KEY, " +
                "nome_professor VARCHAR(50) NOT NULL," +
                "senha_professor VARCHAR(8) NOT NULL);";

        String sql2 = "CREATE TABLE IF NOT EXISTS " + TABELA_TURMA +
                "(id_turma INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome_turma VARCHAR(50), " +
                "nivel INTEGER , " +
                "serie VARCHAR(5));";

        String sql3 = "CREATE TABLE IF NOT EXISTS " + TABELA_DISCIPLINA +
                "(id_disciplina INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                "nome_disciplina VARCHAR(50) NOT NULL,"+
                "carga_horaria INTEGER , " +
                "cpf_professor VARCHAR(11), " +
                "id_turma INTEGER ,  " +
                "FOREIGN KEY (cpf_professor) REFERENCES " + TABELA_PROFESSOR + "(cpf_professor) ON UPDATE CASCADE ON DELETE CASCADE, " +
                "FOREIGN KEY (id_turma) REFERENCES " + TABELA_TURMA + "(id_turma) ON UPDATE CASCADE ON DELETE CASCADE);";


        String sql4 = "CREATE TABLE IF NOT EXISTS " + TABELA_ALUNO +
                "(cpf_aluno VARCHAR(11) PRIMARY KEY, " +
                "nome_aluno VARCHAR(50) NOT NULL, " +
                "senha_aluno VARCHAR(8) NOT NULL," +
                "data_nascimento TEXT NOT NULL," +
                "id_turma INTEGER ," +
                "FOREIGN KEY (id_turma) REFERENCES " + TABELA_TURMA + "(id_turma) ON UPDATE CASCADE ON DELETE CASCADE);";

        String sql5 = "CREATE TABLE IF NOT EXISTS " + TABELA_NOTA +
                "(id_nota INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                "av1 DECIMAL(3, 2), " +
                "av2 DECIMAL(3,2), " +
                "media DECIMAL(3,2), " +
                "ano_letivo INTEGER," +
                "trimestre INTEGER, " +
                "data_lancamento TEXT, " +
                "cpf_aluno VARCHAR(11), " +
                "id_disciplina INTEGER," +
                "FOREIGN KEY (cpf_aluno) REFERENCES " + TABELA_ALUNO + "(cpf_aluno) ON UPDATE CASCADE ON DELETE CASCADE, " +
                "FOREIGN KEY (id_disciplina) REFERENCES " + TABELA_DISCIPLINA + "(id_disciplina) ON UPDATE CASCADE ON DELETE CASCADE);";


        String sql6 = "CREATE TABLE IF NOT EXISTS " + TABELA_PROFESSOR_LECIONA_TURMA +
                "(cpf_professor VARCHAR(11), " +
                "id_turma INTEGER, " +
                "horario VARCHAR(10), " +
                "dia_da_aula VARCHAR(20) ," +
                "FOREIGN KEY (cpf_professor) REFERENCES " + TABELA_PROFESSOR + "(cpf_professor) ON UPDATE CASCADE ON DELETE CASCADE, " +
                "FOREIGN KEY (id_turma) REFERENCES " + TABELA_TURMA + "(id_turma) ON UPDATE CASCADE ON DELETE CASCADE);";

        String Inserir_Professor = "INSERT INTO " + TABELA_PROFESSOR + " (cpf_professor, nome_professor, senha_professor) VALUES ('47058249032', 'Gean Lima', '12345678')," +
                "('57630801068', 'José Augusto', '12345678'), ('53936426015','Jamylle Santana', '12345678'),('67596143059','Matheus Borges', '12345678');";

        String Inserir_Turma = "INSERT INTO " + TABELA_TURMA + " (nome_turma, nivel, serie) VALUES ('A', 1, '1° Ano'),('A', 2, '2° Ano');";

        String Inserir_Aluno = "INSERT INTO " + TABELA_ALUNO + " (cpf_aluno, nome_aluno, senha_aluno, data_nascimento , id_turma) VALUES ('58339531034', 'Nathyel Carneiro', '12345678', '2010-07-15' ,1)," +
                "('64827840032', 'Kaíque Paixão', '12345678', '2009-07-11', 2), ('66621498020', 'Brenno Ribeiro', '12345678', '2009-07-12',2), ('40684108011', 'Joemerson Maia', '12345678', '2010-07-16',1);";

        String Inserir_Disciplina = "INSERT INTO " + TABELA_DISCIPLINA + " (nome_disciplina, carga_horaria, cpf_professor, id_turma) VALUES ('Matemática', 40, '47058249032', 1)," +
                "('História', 40, '57630801068', 1), ('Linguagens', 40, '53936426015', 2), ('Ed.Física', 40, '67596143059', 2);";



        String[] ExecSQList = {sql, sql2, sql3, sql4, sql4, sql5, sql6, Inserir_Professor,
                 Inserir_Turma, Inserir_Aluno, Inserir_Disciplina};

        try
        {
            for (String s : ExecSQList) {
                db.execSQL(s);
            }

            //db.execSQL(Inserir_Nota);

            Log.i("Info DB", "Sucesso ao criar tabelas");
        }
        catch (Exception e)
        {
            Log.i("Info DB", "Erro ao criar a tabelas" + e.getMessage());
        }




    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
