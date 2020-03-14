package br.com.alura.agenda.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import br.com.alura.agenda.database.conversor.ConversorCalendar;
import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

import static br.com.alura.agenda.database.AgendaMigrations.TODAS_MIGRATIONS;

@Database(entities = {Aluno.class}, version = 5, exportSchema = false)
@TypeConverters({ConversorCalendar.class})
public abstract class AgendaDataBase extends RoomDatabase {

    private static final String NOME_DB = "agenda_db";


    public abstract AlunoDAO getRoomAlunoDAO();

    public static AgendaDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, AgendaDataBase.class, NOME_DB)
                .allowMainThreadQueries()
                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }

    ;
}
