package br.com.alura.agenda.ui.asycntask;

import android.os.AsyncTask;

import br.com.alura.agenda.model.Telefone;

abstract class BaseAlunoComTelefoneTask extends AsyncTask<Void, Void, Void> {

    private final FinalizaListener listener;

     BaseAlunoComTelefoneTask(FinalizaListener listener) {
        this.listener = listener;
    }

     void vinculaAlunoComTelefone(int alunoId, Telefone... telefones) {
        for(Telefone tel: telefones) {
            tel.setAlunoId(alunoId);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizado();
    }

    public interface FinalizaListener {
        void quandoFinalizado();
    }
}
