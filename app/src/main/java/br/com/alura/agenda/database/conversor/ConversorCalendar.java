package br.com.alura.agenda.database.conversor;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

public class ConversorCalendar {

    @TypeConverter
    public Long toLong(Calendar valor) {
        if(valor != null) return valor.getTimeInMillis();

        return null;
    }

    @TypeConverter
    public Calendar toCalendar(Long valor) {
        Calendar momentoAtual = Calendar.getInstance();
        if( valor != null) {
            momentoAtual.setTimeInMillis(valor);
        }
        return momentoAtual;
    }
}
