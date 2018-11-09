package br.com.leilao.dominio;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnoBissextoTest {


    @Test
    public void deveRetornarUmAnoBissexto(){
        AnoBissexto anoBis =  new AnoBissexto();

        assertEquals(false,anoBis.ehBissexto(2014));
        assertEquals(true,anoBis.ehBissexto(2016));
    }
}
