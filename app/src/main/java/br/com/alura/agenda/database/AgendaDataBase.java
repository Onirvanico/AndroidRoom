package br.com.alura.agenda.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import br.com.alura.agenda.database.conversor.ConversorCalendar;
import br.com.alura.agenda.database.conversor.ConversorTipoTelefone;
import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

import static br.com.alura.agenda.database.AgendaMigrations.TODAS_MIGRATIONS;

@Database(entities = {Aluno.class, Telefone.class}, version = 6, exportSchema = false)
@TypeConverters({ConversorCalendar.class, ConversorTipoTelefone.class})
public abstract class AgendaDataBase extends RoomDatabase {

    private static final String NOME_DB = "agenda_db";


    public abstract AlunoDAO getRoomAlunoDAO();

    public static AgendaDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, AgendaDataBase.class, NOME_DB)
                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }

    public abstract TelefoneDAO getTelefoneDAO();
}
