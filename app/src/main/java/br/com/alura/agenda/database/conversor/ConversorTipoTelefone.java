package br.com.alura.agenda.database.conversor;

import android.arch.persistence.room.TypeConverter;

import br.com.alura.agenda.model.Tipotelefone;

public class ConversorTipoTelefone {

    @TypeConverter
    public String toString(Tipotelefone tipo) {
        return tipo.name();
    }

    @TypeConverter
    public Tipotelefone toTipoTelefone(String valor) {
        if(valor != null) {
            return Tipotelefone.valueOf(valor);
        }
        return Tipotelefone.FIXO;
    }
}
