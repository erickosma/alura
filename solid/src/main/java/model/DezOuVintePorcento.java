package model;

import dao.Funcionario;

public class DezOuVintePorcento  implements RegraSalario{


    public double calculo(Funcionario funcionario) {
        if(funcionario.getSalarioBase() > 3000.0) {
            return funcionario.getSalarioBase() * 0.8;
        }
        else {
            return funcionario.getSalarioBase() * 0.9;
        }
    }
}
