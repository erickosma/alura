package dao;

import lombok.Getter;
import lombok.Setter;
import model.Cargo;

import java.util.Calendar;

@Getter
@Setter
public class Funcionario {

    private int id;
    private String nome;
    private Cargo cargo;
    private Calendar dataDeAdmissao;
    private double salarioBase;

    public double calculaSalario() {
        return this.getCargo()
                .getRegraSalario()
                .calculo(this);
    }
}
