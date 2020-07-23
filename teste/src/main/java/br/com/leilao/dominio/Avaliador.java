package br.com.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Avaliador {

    private double maior = Double.NEGATIVE_INFINITY;
    private double menor = Double.POSITIVE_INFINITY;
    private List<Lance> maiores;

    public void avalia(Leilao leilao) {
        for (Lance lance : leilao.getLances()) {
            if (lance.getValor() > maior) {
                maior = lance.getValor();
            }
            if (lance.getValor() < menor) {
                menor = lance.getValor();
            }
        }
        pegaOsMaioresNo(leilao);
    }

    private void pegaOsMaioresNo(Leilao leilao) {
        maiores = new ArrayList<Lance>(leilao.getLances());
        Collections.sort(maiores, new Comparator<Lance>() {
            public int compare(Lance o1, Lance o2) {
                if(o1.getValor() < o2.getValor()) return 1;
                if(o1.getValor() > o2.getValor()) return -1;
                return 0;
            }
        });
        maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
    }

    public List<Lance> getTresMaiores() {
        return this.maiores;
    }

    public double media(Leilao leilao) {
        double media;
        double soma = 0.0;
        for (Lance lance : leilao.getLances()) {
            soma = soma + lance.getValor();
        }
        media = soma / leilao.getLances().size();
        return media;
    }

    public double getMaior() {
        return maior;
    }

    public double getMenor() {
        return menor;
    }
}
