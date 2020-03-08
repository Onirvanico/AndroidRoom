package br.com.alura.agenda.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

@Database(entities = {Aluno.class}, version = 1, exportSchema = false)
public abstract class  AgendaDataBase extends RoomDatabase {

    private static final String NOME_DB = "agenda_db";

    public abstract AlunoDAO getRoomAlunoDAO();

    public  static AgendaDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, AgendaDataBase.class, NOME_DB)
                .allowMainThreadQueries()
                .build();
    };
}
