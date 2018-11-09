package br.com.leilao.dominio;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatematicaMalucaTest {

    @Test
    public void contaMalucaComNumerosMaioresQue30DeveRetornarOnumeroMultiplicadoPor4() {
        MatematicaMaluca mat = new MatematicaMaluca();
        assertEquals(40*4, mat.contaMaluca(40));
        assertEquals(31*4, mat.contaMaluca(31));
    }

    @Test
    public void contaMalucaComNumerosMaioresQue10DeveRetornarOnumeroMultiplicadoPor3() {
        MatematicaMaluca mat = new MatematicaMaluca();
        assertEquals(15*3, mat.contaMaluca(15));
        assertEquals(21*3, mat.contaMaluca(21));
    }

    @Test
    public void contaMalucaComNumerosMenoresQue10DeveRetornarOnumeroMultiplicadoPor2() {
        MatematicaMaluca mat = new MatematicaMaluca();
        assertEquals(10*2, mat.contaMaluca(10));
        assertEquals(10, mat.contaMaluca(5));
    }
} 
