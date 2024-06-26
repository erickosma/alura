package br.com.alura.owasp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import br.com.alura.owasp.model.Usuario;
import br.com.alura.owasp.util.ConnectionFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	@PersistenceContext
	private EntityManager manager;

	public void salva(Usuario usuario) {
		manager.persist(usuario);
	}

	public Usuario procuraUsuario(Usuario usuario) {
		TypedQuery<Usuario> query= manager
				.createQuery("select u from Usuario u where u.email=:email and u.senha=:senha", Usuario.class);
		query.setParameter("email", usuario.getEmail());
		query.setParameter("senha", usuario.getSenha());

		Usuario usuarioRetornado = query.getResultList().stream().findFirst().orElse(null);
		return usuarioRetornado;
	}
}
