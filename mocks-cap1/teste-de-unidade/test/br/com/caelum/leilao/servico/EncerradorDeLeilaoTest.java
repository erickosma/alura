package br.com.caelum.leilao.servico;

public class EncerradorDeLeilaoTest {


    @Test
    public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {

        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(antiga).constroi();

        // mas como passo os leiloes criados para o EncerradorDeLeilao,
        // já que ele os busca no DAO?

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao();
        encerrador.encerra();

        assertTrue(leilao1.isEncerrado());
        assertTrue(leilao2.isEncerrado());
    }
}
