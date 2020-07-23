package br.com.leilao.dominio;

import br.com.leilao.dominio.builder.CriadorDeLeilao;
import org.junit.Test;

import static br.com.leilao.dominio.matchers.TemUmLanceMatcher.temUmLance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class LeilaoTest {

    @Test
    public void deveReceberApenasUmlance(){
        Leilao leilao = new Leilao("PS4");
        leilao.propoe(new Lance(new Usuario("Joao"), 2000));

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario(){
        Leilao leilao = new Leilao("PS4");
        Usuario joao  = new Usuario("Joao");
        leilao.propoe(new Lance(joao, 2000));
        leilao.propoe(new Lance(joao, 3000));
        leilao.propoe(new Lance(joao, 4000));
        leilao.propoe(new Lance(joao, 1000));
        leilao.propoe(new Lance(joao, 5000));

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void deveDobrarOUltimoLanceDado() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.dobraLance(steveJobs);

        assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
    }

    @Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");

        leilao.dobraLance(steveJobs);

        assertEquals(0, leilao.getLances().size());
    }

    @Test
    public void leilaoTemUmLance() {
        Usuario joao =  new Usuario("Steve Jobs");
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 2000)
                .constroi();

        Lance lance = new Lance(joao, 2000);
        assertThat(leilao, temUmLance(lance));
    }
}
