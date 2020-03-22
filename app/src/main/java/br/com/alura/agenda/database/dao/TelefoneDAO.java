package br.com.alura.agenda.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.alura.agenda.model.Telefone;

@Dao
public interface TelefoneDAO {

    @Query("SELECT * FROM Telefone " +
            "WHERE alunoId = :id LIMIT 1")
    Telefone buscaPrimeiroTelefone(int id);

    @Insert
    void salva(Telefone... telefones);

    @Query("SELECT * FROM Telefone " +
            "WHERE alunoId = :id")
    List<Telefone> buscaTodosTelefones(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void atualiza(Telefone... telefones);
}
