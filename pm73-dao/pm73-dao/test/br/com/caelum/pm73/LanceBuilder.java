package br.com.caelum.pm73;

import br.com.caelum.pm73.dominio.Lance;
import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.Usuario;

import java.util.Calendar;

public final class LanceBuilder {
    private double valor;
    private Calendar data;
    private Usuario usuario;
    private Leilao leilao;

    public LanceBuilder() {
        this.valor = 150.0;
        this.data = Calendar.getInstance();
        this.usuario = new Usuario("Joao da Silva", "joao@silva.com.br");
        this.leilao = new LeilaoBuilder().constroi();
    }

    public static LanceBuilder aLance() {
        return new LanceBuilder();
    }

    public LanceBuilder valor(double valor) {
        this.valor = valor;
        return this;
    }

    public LanceBuilder data(Calendar data) {
        this.data = data;
        return this;
    }

    public LanceBuilder usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public LanceBuilder leilao(Leilao leilao) {
        this.leilao = leilao;
        return this;
    }

    public Lance constroi() {
        Lance lance = new Lance();
        lance.setValor(valor);
        lance.setData(data);
        lance.setUsuario(usuario);
        lance.setLeilao(leilao);
        return lance;
    }
}
