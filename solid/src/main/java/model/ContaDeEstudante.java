package model;

import service.ManiupulaPagamento;

public class ContaDeEstudante implements Conta{

    private int milhas;

    public void deposita(ManiupulaPagamento maniupulaPagamento) {
        maniupulaPagamento.deposita(valor);
        this.milhas += (int)valor;
    }

    public int getMilhas() {
        return milhas;
    }

    public void rende() {
        throw new RuntimeException("Não pode render");
    }
}
