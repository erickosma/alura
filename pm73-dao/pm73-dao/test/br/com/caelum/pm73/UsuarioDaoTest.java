package br.com.caelum.pm73;

import br.com.caelum.pm73.dao.CriadorDeSessao;
import br.com.caelum.pm73.dao.LeilaoDao;
import br.com.caelum.pm73.dao.UsuarioDao;
import br.com.caelum.pm73.dominio.Usuario;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class UsuarioDaoTest {

    private Session session;
    private UsuarioDao usuarioDao;

    @Before
    public void antes() {
        session = new CriadorDeSessao().getSession();
        usuarioDao = new UsuarioDao(session);
        session.getTransaction().begin();
    }

    @After
    public void depois() {
        session.getTransaction().rollback();
        session.close();
    }
    @Test
    public void deveEncontrarPeloNomeEEmail() {
        Session session = new CriadorDeSessao().getSession();
        UsuarioDao usuarioDao = new UsuarioDao(session);

        Usuario novoUsuario = new Usuario
                ("João da Silva", "joao@dasilva.com.br");
        usuarioDao.salvar(novoUsuario);

        Usuario usuarioDoBanco = usuarioDao
                .porNomeEEmail("João da Silva", "joao@dasilva.com.br");

        assertEquals("João da Silva", usuarioDoBanco.getNome());
        assertEquals("joao@dasilva.com.br", usuarioDoBanco.getEmail());

        session.close();
    }

    @Test
    public void deveRetornarUsuarioNulo(){

        Session session = new CriadorDeSessao().getSession();
        UsuarioDao usuarioDao = new UsuarioDao(session);

        Usuario usuarioDoBanco = usuarioDao
                .porNomeEEmail("aaaaa", "aaaaa");

        assertNull(usuarioDoBanco);
        session.close();
    }

    @Test
    public void deveDeletarUmUsuario() {
        Usuario usuario =
                new Usuario("usuario", "teste@teste.com.br");

        usuarioDao.salvar(usuario);
        usuarioDao.deletar(usuario);

        Usuario usuarioNoBanco =
                usuarioDao.porNomeEEmail("usuario", "teste@teste.com.br");

        assertNull(usuarioNoBanco);
    }

    @Test
    public void atualizaDeveAtualizarUmUsuario() {
        Usuario usuario =
                new Usuario("usuario", "teste@teste.com.br");

        usuarioDao.salvar(usuario);
        usuario.setNome("novo nome");
        usuario.setEmail("novoemail@teste.com");
        usuarioDao.atualizar(usuario);
        session.flush();

        Usuario usuarioAntigo =
                usuarioDao.porNomeEEmail("usuario", "teste@teste.com.br");

        Usuario usuarioNovo =
                usuarioDao.porNomeEEmail("novo nome", "novoemail@teste.com");
        assertNull(usuarioAntigo);
        assertNotNull(usuarioNovo);
    }
}
