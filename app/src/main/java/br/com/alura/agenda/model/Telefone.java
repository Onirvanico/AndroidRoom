package br.com.alura.agenda.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
public class Telefone {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String numero;
    private Tipotelefone tipo;
    @ForeignKey(entity = Aluno.class,
            parentColumns = "id",
            childColumns = "alunoId",
            onUpdate = CASCADE,
            onDelete = CASCADE)
    private int alunoId;

    public Telefone(String numero, Tipotelefone tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Tipotelefone getTipo() {
        return tipo;
    }

    public void setTipo(Tipotelefone tipo) {
        this.tipo = tipo;
    }
}
