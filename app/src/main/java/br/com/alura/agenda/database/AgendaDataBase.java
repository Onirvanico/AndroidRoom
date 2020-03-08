package br.com.alura.agenda.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

@Database(entities = {Aluno.class}, version = 3, exportSchema = false)
public abstract class AgendaDataBase extends RoomDatabase {

    private static final String NOME_DB = "agenda_db";

    public abstract AlunoDAO getRoomAlunoDAO();

    public static AgendaDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, AgendaDataBase.class, NOME_DB)
                .allowMainThreadQueries()
                .addMigrations(new Migration(1, 2) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        database.execSQL("ALTER TABLE aluno ADD COLUMN sobrenome TEXT");
                    }
                }, new Migration(2, 3) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        //Criar tabela nova com os campos desejados
                        database.execSQL(
                                "CREATE TABLE IF NOT EXISTS `Aluno_novo` (" +
                                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                        "`nome` TEXT," +
                                        "`telefone` TEXT, " +
                                        "`email` TEXT)");
                        //Copiar os dados da tabela antiga para a nova
                        database.execSQL("INSERT INTO Aluno_novo(id, nome, telefone, email)" +
                                "SELECT id, nome, telefone, email FROM aluno");
                        //Remover a tabela antiga
                        database.execSQL("DROP TABLE Aluno");
                        //Renomear nome da tabela nova com o da antiga
                        database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
                    }
                })
                .build();
    }

    ;
}
