package service;

import dao.Funcionario;

public class CalculadoraDeSalario {

    public double calcula(Funcionario funcionario) {
        return funcionario.calculaSalario();
    }


}
