package br.com.alura.agenda.ui.asycntask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class BuscaTodosTelefonesDoAluno extends AsyncTask<Void, Void, List<Telefone>> {

    private final TelefoneDAO telefoneDAO;
    private final Aluno aluno;
    private final TelefonesRetornadosListener listener;

    public BuscaTodosTelefonesDoAluno(TelefoneDAO telefoneDAO, Aluno aluno, TelefonesRetornadosListener listener) {
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.listener = listener;
    }

    @Override
    protected List<Telefone> doInBackground(Void... voids) {

        return telefoneDAO.buscaTodosTelefones(aluno.getId());
    }

    @Override
    protected void onPostExecute(List<Telefone> telefones) {
        super.onPostExecute(telefones);
        listener.quandoRetornado(telefones);
    }

    public interface TelefonesRetornadosListener {
        void quandoRetornado(List<Telefone> telefones);
    }
}
