package br.com.caelum.financas.teste;

import br.com.caelum.financas.modelo.Cliente;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;

public class TestaAvgMovimentacao {

    public static void main(String[] args) {


        EntityManager em = new JPAUtil().getEntityManager();
        String jpql = "select avg(m.valor) from Movimentacao m where m.conta=:pConta "
                + "and m.tipoMovimentacao=:pTipo";

        TypedQuery<BigDecimal> query = em.createQuery(jpql, BigDecimal.class);

        Conta conta = new Conta();
        conta.setId(2);

        query.setParameter("pConta", conta);
        query.setParameter("pTipo", TipoMovimentacao.SAIDA);

        BigDecimal resultado = query.getSingleResult();

       // em.getTransaction().begin();



       // em.getTransaction().commit();


    }
}
