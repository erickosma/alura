package br.com.leilao.dominio;

import br.com.leilao.dominio.Avaliador;
import br.com.leilao.dominio.Lance;
import br.com.leilao.dominio.Leilao;
import br.com.leilao.dominio.Usuario;
import br.com.leilao.dominio.builder.CriadorDeLeilao;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AvaliadorTest {

    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;
    private Usuario outro;


    @BeforeClass
    public static void testandoBeforeClass() {
        System.out.println("before class");
    }

    @AfterClass
    public static void testandoAfterClass() {
        System.out.println("after class");
    }


    @Before
    public void criaAvaliador() {
        leiloeiro = new Avaliador();
        joao = new Usuario("João");
        jose = new Usuario("José");
        maria = new Usuario("Maria");
        outro  = new Usuario("Outro");
        System.out.println("inicializando teste!");
    }

    @After
    public void finaliza() {
        System.out.println("fim");
    }


    @Test
    public void testAvalia() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .lance(joao, 300.0)
                .lance(outro, 400.0)
                .constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.getMaior(), equalTo(400.00));
        assertThat(leiloeiro.getMenor(), equalTo(100.00));
    }

    @Test
    public void testMedia() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(maria, 200.0)
                .lance(joao, 300.0)
                .lance(outro, 400.0)
                .constroi();

        double media = leiloeiro.media(leilao);

        double mediaExperada = 300;

        assertEquals(mediaExperada, media, 0.0001);
    }
    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(maria, 200.0)
                .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(200, leiloeiro.getMaior(), 0.0001);
        assertEquals(200, leiloeiro.getMenor(), 0.0001);
    }

    @Test
    public void testMaiorEMenorValor() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(maria, 250.0)
                .lance(joao, 450.0)
                .lance(jose, 120.0)
                .lance(outro, 700.0)
                .lance(maria, 630.0)
                .lance(maria, 230.0)
                .constroi();

        leiloeiro.avalia(leilao);

        double maiorEsperado = 700;
        double menorEsperado = 120;

        assertEquals(maiorEsperado, leiloeiro.getMaior(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenor(), 0.0001);
    }

    @Test
    public void testAvaliaLeilaoDeveRetornarOsTresMaiores() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(maria, 400.0)
                .lance(joao, 300.0)
                .lance(jose, 200.0)
                .lance(outro, 100.0)
                .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(leilao.getLances().get(0), leiloeiro.getTresMaiores().get(0));
        assertEquals(leilao.getLances().get(1), leiloeiro.getTresMaiores().get(1));
        assertEquals(leilao.getLances().get(2), leiloeiro.getTresMaiores().get(2));
    }

    @Test(expected=RuntimeException.class)
    public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .constroi();

        leiloeiro.avalia(leilao);
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 100)
                .lance(maria, 200)
                .lance(joao, 300)
                .lance(maria, 400)
                .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(3, maiores.size());

        assertThat(maiores, hasItems(
                new Lance(maria, 400),
                new Lance(joao, 300),
                new Lance(maria, 200)
        ));
    }
} 
