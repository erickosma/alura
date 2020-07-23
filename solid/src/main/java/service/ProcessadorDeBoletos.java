package service;

import dao.Boleto;
import dao.Fatura;
import dao.Pagamento;
import model.MeioDePagamento;

import java.util.List;

public class ProcessadorDeBoletos {

    public void processa(List<Boleto> boletos, Fatura fatura) {
        for(Boleto boleto : boletos) {
            Pagamento pagamento = new Pagamento(boleto.getValor(), MeioDePagamento.BOLETO);
            fatura.addPagamentos(pagamento);
        }


    }
}
