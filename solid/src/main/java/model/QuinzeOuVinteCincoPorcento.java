package model;

import dao.Funcionario;

public class QuinzeOuVinteCincoPorcento  implements RegraSalario{

    public double calculo(Funcionario funcionario) {
        if(funcionario.getSalarioBase() > 2000.0) {
            return funcionario.getSalarioBase() * 0.75;
        }
        else {
            return funcionario.getSalarioBase() * 0.85;
        }
    }
}
