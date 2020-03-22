package br.com.alura.agenda.ui.asycntask;

import java.util.List;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class EditaAlunoTask extends BaseAlunoComTelefoneTask {
    private final AlunoDAO alunoDAO;
    private final TelefoneDAO telefoneDAO;
    private final Aluno aluno;
    private final Telefone telefoneFixo;
    private final Telefone telefoneCelular;
    private final List<Telefone> telefones;


    public EditaAlunoTask(AlunoDAO alunoDAO, TelefoneDAO telefoneDAO,
                          Aluno aluno, Telefone telefoneFixo, Telefone telefoneCelular,
                          List<Telefone> telefones, FinalizaListener listener) {

        super(listener);
        this.alunoDAO = alunoDAO;
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.telefones = telefones;

    }

    @Override
    protected Void doInBackground(Void... voids) {
        alunoDAO.edita(aluno);
        vinculaAlunoComTelefone(aluno.getId(), telefoneFixo, telefoneCelular);
        atualizaIdTelefones(telefoneFixo, telefoneCelular);
        telefoneDAO.atualiza(telefoneFixo, telefoneCelular);
        return null;
    }


    private void atualizaIdTelefones(Telefone telefoneFixo, Telefone telefoneCelular) {
        for(Telefone tel: this.telefones) {
            switch (tel.getTipo()) {
                case FIXO:
                    telefoneFixo.setId(tel.getId());
                    break;

                case CELULAR:
                    telefoneCelular.setId(tel.getId());
                    break;
            }
        }
    }

}
