package br.com.alura.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.database.AgendaDataBase;
import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;
import br.com.alura.agenda.model.Tipotelefone;
import br.com.alura.agenda.ui.asycntask.BuscaTodosTelefonesDoAluno;
import br.com.alura.agenda.ui.asycntask.EditaAlunoTask;
import br.com.alura.agenda.ui.asycntask.SalvaAlunoTask;

import static br.com.alura.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private EditText campoNome;
    private EditText campoTelefoneFixo;
    private EditText campoTelefoneCelular;
    private EditText campoEmail;
    private AlunoDAO alunoDAO;
    private TelefoneDAO telefoneDAO;
    private Aluno aluno;
    private List<Telefone> telefones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        AgendaDataBase database = AgendaDataBase.getInstance(this);
        alunoDAO = database.getRoomAlunoDAO();
        telefoneDAO = database.getTelefoneDAO();
        inicializacaoDosCampos();
        carregaAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_formulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_formulario_aluno_menu_salvar) {
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoEmail.setText(aluno.getEmail());
        preencheCamposTelefones();

    }

    private void preencheCamposTelefones() {

        new BuscaTodosTelefonesDoAluno(telefoneDAO, aluno, telefonesRetornados -> {

            this.telefones = telefonesRetornados;

            for (Telefone tel :
                    telefones) {
                if (tel.getTipo() == Tipotelefone.FIXO) {
                    campoTelefoneFixo.setText(tel.getNumero());
                } else if (tel.getTipo() == Tipotelefone.CELULAR) {
                    campoTelefoneCelular.setText(tel.getNumero());
                }
            }
        }).execute();

    }

    private void finalizaFormulario() {
        preencheAluno();
        Telefone telefoneFixo = criaTelefone(campoTelefoneFixo, Tipotelefone.FIXO);
        Telefone telefoneCelular = criaTelefone(campoTelefoneCelular, Tipotelefone.CELULAR);

        if (aluno.temIdValido()) {
            editaAluno(telefoneFixo, telefoneCelular);
        } else {

            salvaAluno(telefoneFixo, telefoneCelular);

        }

    }

    @NonNull
    private Telefone criaTelefone(EditText campoTelefoneFixo, Tipotelefone tipo) {
        String numeroFixo = campoTelefoneFixo.getText().toString();
        return new Telefone(numeroFixo, tipo);
    }

    private void salvaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        new SalvaAlunoTask(aluno, telefoneFixo, telefoneCelular, alunoDAO,
                telefoneDAO, this::finish).execute();
    }

    private void editaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {

        new EditaAlunoTask(alunoDAO, telefoneDAO, aluno,
                telefoneFixo, telefoneCelular, telefones, this::finish).execute();

    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefoneFixo = findViewById(R.id.activity_formulario_aluno_telefoneFixo);
        campoTelefoneCelular = findViewById(R.id.activity_formulario_aluno_telefoneCelular);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();

        aluno.setNome(nome);
        aluno.setEmail(email);
    }
}
