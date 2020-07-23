package br.com.caelum.pm73;

import br.com.caelum.pm73.dao.CriadorDeSessao;
import br.com.caelum.pm73.dao.LeilaoDao;
import br.com.caelum.pm73.dao.UsuarioDao;
import br.com.caelum.pm73.dominio.Lance;
import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.Usuario;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class LeilaoDaoTests {
    private Session session;
    private LeilaoDao leilaoDao;
    private UsuarioDao usuarioDao;

    @Before
    public void antes() {
        session = new CriadorDeSessao().getSession();
        leilaoDao = new LeilaoDao(session);
        usuarioDao = new UsuarioDao(session);
        session.getTransaction().begin();
    }

    @After
    public void depois() {
        session.getTransaction().rollback();
        session.close();
    }

    @Test
    public void totalDeveContarLeiloesNaoEncerrados() {
        Usuario mauricio =
                new Usuario("Mauricio Aniche", "mauricio@teste.com.br");

        Leilao ativo = new LeilaoBuilder()
                .comNome("Geladeira")
                .comValor(1500.0)
                .comDono(mauricio)
                .constroi();

        Leilao encerrado = new LeilaoBuilder()
                .comNome("XBox")
                .comValor(700.0)
                .comDono(mauricio)
                .encerrado()
                .constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(ativo);
        leilaoDao.salvar(encerrado);

        long total = leilaoDao.total();

        assertEquals(1L, total);
    }


    @Test
    public void totalComLeiloesEncerradosDeveRetonarZero() {
        Usuario mauricio =
                new Usuario("Mauricio Aniche", "mauricio@teste.com.br");

        Leilao encerrado1 = new LeilaoBuilder()
                .comNome("Geladeira")
                .comValor(1500.0)
                .comDono(mauricio)
                .encerrado()
                .constroi();

        Leilao encerrado2 = new LeilaoBuilder()
                .comNome("XBox")
                .comValor(700.0)
                .comDono(mauricio)
                .encerrado()
                .constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(encerrado1);
        leilaoDao.salvar(encerrado2);

        long total = leilaoDao.total();

        assertEquals(0L, total);
    }

    @Test
    public void novosComLeiloesNovosDeveRetonarLeiloesNovos() {
        Usuario mauricio =
                new Usuario("Mauricio Aniche", "mauricio@teste.com.br");

        Leilao novo = new LeilaoBuilder()
                .comNome("Geladeira")
                .comValor(1500.0)
                .comDono(mauricio)
                .constroi();

        Leilao usado = new LeilaoBuilder()
                .comNome("XBox")
                .comValor(700.0)
                .comDono(mauricio)
                .usado()
                .constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(novo);
        leilaoDao.salvar(usado);

        List<Leilao> leilaoNovos = leilaoDao.novos();

        assertEquals(1L, leilaoNovos.size());
        assertEquals(novo.getDono(), leilaoNovos.get(0).getDono());
    }

    @Test
    public void antigosComLeiloesAntigosDeveRetonarApenasLeiloesAntigos() {
        Calendar mesAtras = Calendar.getInstance();
        mesAtras.add(Calendar.MONTH, -1);

        Usuario mauricio =
                new Usuario("Mauricio Aniche", "mauricio@teste.com.br");

        Leilao antigo =  new LeilaoBuilder()
                .comNome("Geladeira")
                .comValor(1500.0)
                .comDono(mauricio)
                .diasAtras(30)
                .constroi();

        Leilao novo = new LeilaoBuilder()
                .comNome("XBox")
                .comValor(700.0)
                .comDono(mauricio)
                .usado()
                .constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(antigo);
        leilaoDao.salvar(novo);

        List<Leilao> leilaoAntigo = leilaoDao.antigos();

        assertEquals(1L, leilaoAntigo.size());
        assertEquals(antigo.getDono(), leilaoAntigo.get(0).getDono());
    }

    @Test
    public void deveTrazerLeiloesNaoEncerradosNoPeriodo() {
        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();
        Calendar dataDoLeilao1 = Calendar.getInstance();
        dataDoLeilao1.add(Calendar.DAY_OF_MONTH, -2);
        Calendar dataDoLeilao2 = Calendar.getInstance();
        dataDoLeilao2.add(Calendar.DAY_OF_MONTH, -20);

        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        Leilao leilao1 =  new LeilaoBuilder()
                .comNome("XBox")
                .comValor(700.0)
                .comDono(mauricio)
                .diasAtras(2)
                .constroi();

        Leilao leilao2 =  new LeilaoBuilder()
                .comNome("Geladeira")
                .comValor(1700.0)
                .comDono(mauricio)
                .diasAtras(20)
                .constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        List<Leilao> leiloes =
                leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        assertEquals(1, leiloes.size());
        assertEquals("XBox", leiloes.get(0).getNome());
    }

    @Test
    public void naoDeveTrazerLeiloesEncerradosNoPeriodo() {
        Calendar comecoIntervalo = Calendar.getInstance();
        comecoIntervalo.add(Calendar.DAY_OF_MONTH, -20);
        Calendar now = Calendar.getInstance();
        Calendar dataLeilao = Calendar.getInstance();
        dataLeilao.add(Calendar.DAY_OF_MONTH, -2);

        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        Leilao leilao1 =  new LeilaoBuilder()
                .comNome("XBox")
                .comValor(700.0)
                .comDono(mauricio)
                .encerrado()
                .diasAtras(2)
                .constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);

        List<Leilao> leiloes =
                leilaoDao.porPeriodo(comecoIntervalo, now);

        assertEquals(0, leiloes.size());
    }

    @Test
    public void deveTrazerdisputadosEntreLeiloesNoPeriodo() {
        Usuario mauricio = new Usuario("Mauricio", "mauricio@aniche.com.br");
        Usuario marcelo = new Usuario("Marcelo", "marcelo@aniche.com.br");

        Leilao leilao1 = new LeilaoBuilder()
                .comDono(marcelo)
                .comValor(3000.0)
                .comLance(Calendar.getInstance(), mauricio, 3000.0)
                .comLance(Calendar.getInstance(), marcelo, 3100.0)
                .constroi();

        Leilao leilao2 = new LeilaoBuilder()
                .comDono(mauricio)
                .comValor(3200.0)
                .comLance(Calendar.getInstance(), mauricio, 3000.0)
                .comLance(Calendar.getInstance(), marcelo, 3100.0)
                .comLance(Calendar.getInstance(), mauricio, 3200.0)
                .comLance(Calendar.getInstance(), marcelo, 3300.0)
                .comLance(Calendar.getInstance(), mauricio, 3400.0)
                .comLance(Calendar.getInstance(), marcelo, 3500.0)
                .constroi();

        usuarioDao.salvar(marcelo);
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        List<Leilao> leiloes = leilaoDao.disputadosEntre(100, 3500);

        assertEquals(1, leiloes.size());
        assertEquals(3200.0, leiloes.get(0).getValorInicial(), 0.00001);

    }


    @Test
    public void listaLeiloesDoUsuarioDeveRetornarAiListaComLeiloesDoUsuario() {
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Usuario comprador2 = new Usuario("Guilherme", "g@g.com");
        Leilao leilao = new LeilaoBuilder()
                .comDono(dono)
                .comValor(50.0)
                .comLance(Calendar.getInstance(), comprador, 100.0)
                .comLance(Calendar.getInstance(), comprador2, 200.0)
                .constroi();
        Leilao leilao2 = new LeilaoBuilder()
                .comDono(dono)
                .comValor(250.0)
                .comLance(Calendar.getInstance(), comprador2, 100.0)
                .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        usuarioDao.salvar(comprador2);
        leilaoDao.salvar(leilao);
        leilaoDao.salvar(leilao2);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(comprador);
        assertEquals(1, leiloes.size());
        assertEquals(leilao, leiloes.get(0));
    }

    @Test
    public void listaDeLeiloesDeUmUsuarioNaoTemRepeticao() throws Exception {
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Leilao leilao = new LeilaoBuilder()
                .comDono(dono)
                .comLance(Calendar.getInstance(), comprador, 100.0)
                .comLance(Calendar.getInstance(), comprador, 200.0)
                .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        leilaoDao.salvar(leilao);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(comprador);
        assertEquals(1, leiloes.size());
        assertEquals(leilao, leiloes.get(0));
    }

    @Test
    public void  getValorInicialMedioDoUsuarioDeveRetornarValor(){
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Leilao leilao = new LeilaoBuilder()
                .comDono(dono)
                .comValor(50.0)
                .comLance(Calendar.getInstance(), comprador, 100.0)
                .comLance(Calendar.getInstance(), comprador, 200.0)
                .constroi();
        Leilao leilao2 = new LeilaoBuilder()
                .comDono(dono)
                .comValor(250.0)
                .comLance(Calendar.getInstance(), comprador, 100.0)
                .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        leilaoDao.salvar(leilao);
        leilaoDao.salvar(leilao2);

        assertEquals(150.0, leilaoDao.getValorInicialMedioDoUsuario(comprador), 0.001);
    }

    @Test
    public void deveDeletarUmLeilao() {

        Usuario usuario =
                new Usuario("usuario", "teste@teste.com.br");

        Leilao leilao = new LeilaoBuilder()
                .comNome("Geladeira")
                .comValor(1500.0)
                .comDono(usuario)
                .encerrado()
                .constroi();


        usuarioDao.salvar(usuario);
        leilaoDao.salvar(leilao);

        leilaoDao.deletar(leilao);
        assertNull(leilaoDao.porId(leilao.getId()));
    }
}
