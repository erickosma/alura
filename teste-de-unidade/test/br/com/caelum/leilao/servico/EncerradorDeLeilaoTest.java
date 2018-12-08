package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.LeilaoDao;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class EncerradorDeLeilaoTest {

    @Test
    public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {

        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(antiga).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiro = mock(EnviadorDeEmail.class);

        when(daoFalso.correntes()).thenReturn(leiloesAntigos);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso,carteiro);
        encerrador.encerra();

        assertTrue(leilao1.isEncerrado());
        assertTrue(leilao2.isEncerrado());
        assertEquals(2, encerrador.getQuantidadeDeEncerrados());
    }

    @Test
    public void deveEncerrarLeiloesQueComecaramOntem() {

        Date currentDate = new Date();
        Calendar antiga = Calendar.getInstance();
        antiga.setTime(currentDate);
        antiga.add(Calendar.HOUR,-25);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(antiga).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiro = mock(EnviadorDeEmail.class);
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso,carteiro);

        when(daoFalso.correntes()).thenReturn(leiloesAntigos);

        encerrador.encerra();

        assertFalse(leilao1.isEncerrado());
        assertFalse(leilao2.isEncerrado());
        assertEquals(2, encerrador.getQuantidadeDeEncerrados());
    }

    @Test
    public void deveRetornarListaVazia() {
        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiro = mock(EnviadorDeEmail.class);
        when(daoFalso.correntes()).thenReturn(new ArrayList<Leilao>());

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso,carteiro);
        encerrador.encerra();

        assertEquals(0, encerrador.getTotalEncerrados());
    }

    @Test
    public void testDeveRetornarStringTeste(){
        LeilaoDao daoFalso = mock(LeilaoDao.class);
        when(daoFalso.teste()).thenReturn("teste");

    }

    @Test
    public void deveAtualizarLeiloesEncerrados() {

        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV")
                .naData(antiga).constroi();

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiro = mock(EnviadorDeEmail.class);

        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso,carteiro);
        encerrador.encerra();

        // verificando que o metodo atualiza foi realmente invocado!
        verify(daoFalso,times(1)).atualiza(leilao1);
    }

    @Test
    public void naoDeveEncerrarLeiloesQueComecaramMenosDeUmaSemanaAtras() {

        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_MONTH, -1);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(ontem).constroi();

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiro = mock(EnviadorDeEmail.class);

        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso,carteiro);
        encerrador.encerra();

        assertEquals(0, encerrador.getTotalEncerrados());
        assertFalse(leilao1.isEncerrado());
        assertFalse(leilao2.isEncerrado());

        // verifys aqui
        verify(daoFalso, never()).atualiza(leilao1);
        verify(daoFalso, never()).atualiza(leilao2);
    }

    @Test
    public void deveEncerrarleilaoEEnviarPorEmailEmOrdem() {

        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV")
                .naData(antiga).constroi();

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiro = mock(EnviadorDeEmail.class);

        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso,carteiro);
        encerrador.encerra();

        // passamos os mocks que serao verificados
        InOrder inOrder = inOrder(daoFalso, carteiro);
        // a primeira invocação
        inOrder.verify(daoFalso, times(1)).atualiza(leilao1);
        // a segunda invocação
        inOrder.verify(carteiro, times(1)).envia(leilao1);
    }
}
