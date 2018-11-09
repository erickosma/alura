package br.com.leilao.dominio;

import br.com.leilao.dominio.Avaliador;
import br.com.leilao.dominio.Lance;
import br.com.leilao.dominio.Leilao;
import br.com.leilao.dominio.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertEquals;

public class AvaliadorTest {

    @Test
    public void testAvalia() {
        Usuario jao = new Usuario("Jao");
        Usuario maria = new Usuario("Maria");
        Usuario fulano = new Usuario("Fulano");
        Usuario outro = new Usuario("Outro");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(maria, 250.0));
        leilao.propoe(new Lance(jao, 300.0));
        leilao.propoe(new Lance(fulano, 400.0));
        leilao.propoe(new Lance(outro, 350.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        double maiorEsperado = 400;
        double menorEsperado = 250;

        assertEquals(maiorEsperado, leiloeiro.getMaior(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenor(), 0.0001);
    }

    @Test
    public void testMedia() {
        Usuario jao = new Usuario("Jao");
        Usuario maria = new Usuario("Maria");
        Usuario fulano = new Usuario("Fulano");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(maria, 200.0));
        leilao.propoe(new Lance(jao, 300.0));
        leilao.propoe(new Lance(fulano, 400.0));

        Avaliador leiloeiro = new Avaliador();
        double media = leiloeiro.media(leilao);

        double mediaExperada = 300;

        assertEquals(mediaExperada, media, 0.0001);
    }
    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        Usuario joao = new Usuario("Joao");
        Leilao leilao = new Leilao("PS1");

        leilao.propoe(new Lance(joao,200.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        // veja que não precisamos mais da palavra Assert!
        assertEquals(200, leiloeiro.getMaior(), 0.0001);
        assertEquals(200, leiloeiro.getMenor(), 0.0001);
    }

    @Test
    public void testMaiorEMenorValor() {
        Usuario jao = new Usuario("Jao");
        Usuario maria = new Usuario("Maria");
        Usuario fulano = new Usuario("Fulano");
        Usuario outro = new Usuario("Outro");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(maria, 250.0));
        leilao.propoe(new Lance(jao, 450.0));
        leilao.propoe(new Lance(fulano, 120.0));
        leilao.propoe(new Lance(outro, 700.0));
        leilao.propoe(new Lance(maria, 630.0));
        leilao.propoe(new Lance(maria, 230.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        double maiorEsperado = 700;
        double menorEsperado = 120;

        assertEquals(maiorEsperado, leiloeiro.getMaior(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenor(), 0.0001);
    }

    @Test
    public void testAvaliaLeilaoDeveRetornarOsTresMaiores() {
        Usuario jao = new Usuario("Jao");
        Usuario maria = new Usuario("Maria");
        Usuario fulano = new Usuario("Fulano");
        Usuario outro = new Usuario("Outro");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(maria, 400.0));
        leilao.propoe(new Lance(jao, 300.0));
        leilao.propoe(new Lance(fulano, 200.0));
        leilao.propoe(new Lance(outro, 100.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        assertEquals(leilao.getLances().get(0), leiloeiro.getTresMaiores().get(0));
        assertEquals(leilao.getLances().get(1), leiloeiro.getTresMaiores().get(1));
        assertEquals(leilao.getLances().get(2), leiloeiro.getTresMaiores().get(2));
    }



} 
