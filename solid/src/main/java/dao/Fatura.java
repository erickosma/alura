package dao;

import jdk.nashorn.internal.objects.annotations.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Fatura {

    private String cliente;
    private double valor;
    private List<Pagamento> pagamentos;
    private boolean pago;

    public Fatura(String cliente, double valor) {
        this.cliente = cliente;
        this.valor = valor;
        this.pagamentos = new ArrayList<>();
        this.pago = false;
    }

    public void addPagamentos(Pagamento pagamento){
        this.pagamentos.add(pagamento);
        if(valorPago() >= this.valor){
            this.pago = true;
        }
    }

    public Double valorPago(){
        return this.pagamentos.stream()
                .filter(o -> o.getValor() > 0)
                .mapToDouble(Pagamento::getValor)
                .sum();
    }
}
