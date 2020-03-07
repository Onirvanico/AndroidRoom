package br.com.alura.agenda;

import android.app.Application;
import android.arch.persistence.room.Room;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.database.AgendaDataBase;
import br.com.alura.agenda.database.dao.RoomAlunoDAO;
import br.com.alura.agenda.model.Aluno;

@SuppressWarnings("WeakerAccess")
public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosDeTeste();
    }

    private void criaAlunosDeTeste() {
        RoomAlunoDAO dao = Room.databaseBuilder(this, AgendaDataBase.class,
                "agenda_db").allowMainThreadQueries().build().getRoomAlunoDAO();
        dao.salva(new Aluno("Alex", "1122223333", "alex@alura.com.br"));
        dao.salva(new Aluno("Fran", "1122223333", "fran@gmail.com"));
    }
}
