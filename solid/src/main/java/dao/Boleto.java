package dao;

import jdk.nashorn.internal.objects.annotations.Getter;

@Getter
public class Boleto {
    private double valor;

    public Boleto(double valor) {
        this.valor = valor;
    }
}
