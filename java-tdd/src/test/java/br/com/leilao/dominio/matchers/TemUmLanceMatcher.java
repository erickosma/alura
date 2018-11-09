package br.com.leilao.dominio.matchers;

import br.com.leilao.dominio.Lance;
import br.com.leilao.dominio.Leilao;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class TemUmLanceMatcher  extends TypeSafeMatcher<Leilao> {

    private Lance lance;

    public TemUmLanceMatcher(Lance lance) {
        this.lance = lance;
    }

    @Override
    protected boolean matchesSafely(Leilao leilao) {
        if(leilao.getLances().isEmpty()){
            return false;
        }

        for (Lance l : leilao.getLances()) {
            if(l.getValor() == lance.getValor() && l.getUsuario() == lance.getUsuario()){
                return  true;
            }
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Leilao nao tem itens ")
                .appendValue(0);
    }

    @Factory
    public static Matcher temUmLance(Lance lance) {
        return new TemUmLanceMatcher(lance);
    }

}
