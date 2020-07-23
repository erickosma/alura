package model;

public enum Cargo {
    DESENVOLVEDOR( new DezOuVintePorcento()),
    DBA(new QuinzeOuVinteCincoPorcento()),
    TESTER(new QuinzeOuVinteCincoPorcento());

    private RegraSalario regraSalario;

    Cargo(RegraSalario regraSalario){
        this.regraSalario = regraSalario;
    }

    public RegraSalario getRegraSalario() {
        return regraSalario;
    }
}
