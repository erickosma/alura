package br.com.caelum.leilao.servico;

import java.util.Calendar;

public class RelogioDoSistema implements Relogio{

    public Calendar hoje() {
        return Calendar.getInstance();
    }
}
