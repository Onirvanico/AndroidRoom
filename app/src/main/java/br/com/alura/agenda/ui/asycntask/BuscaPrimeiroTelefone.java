package br.com.alura.agenda.ui.asycntask;

import android.os.AsyncTask;
import android.widget.TextView;

import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Telefone;

public class BuscaPrimeiroTelefone extends AsyncTask<Void, Void, Telefone> {
    private final TelefoneDAO dao;
    private final int alunoId;
    private final BuscaPrimeiroTelefoneListener listener;

    public BuscaPrimeiroTelefone(TelefoneDAO dao, TextView campoTelefone, int alunoId, BuscaPrimeiroTelefoneListener listener) {
        this.dao = dao;
        this.alunoId = alunoId;
        this.listener = listener;
    }

    @Override
    protected Telefone doInBackground(Void... voids) {
         return dao.buscaPrimeiroTelefone(alunoId);

    }

    @Override
    protected void onPostExecute(Telefone telefone) {
        super.onPostExecute(telefone);
        listener.quandoEncontrado(telefone);

    }

    public interface BuscaPrimeiroTelefoneListener {
        void quandoEncontrado(Telefone primeiroTelefone);
    }
}
