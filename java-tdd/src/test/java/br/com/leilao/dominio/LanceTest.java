package br.com.leilao.dominio;

import org.junit.Test;

public class LanceTest {

    @Test(expected = IllegalArgumentException.class)
    public void  deveRecusarLancesComValorDeZero(){
        new Lance(new Usuario("Joao"), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void  deveRecusarLancesComValorMenorQueZero(){
        new Lance(new Usuario("Joao"), -10);
    }
}
