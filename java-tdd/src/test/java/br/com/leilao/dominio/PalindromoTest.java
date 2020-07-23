package br.com.leilao.dominio;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class PalindromoTest {

    @Test
    public void testEhPalindromo() {
        Palindromo palindromo = new Palindromo();
        String palavre  = "ARARA";
        String outra  = "outra";

        Assert.assertTrue(palindromo.ehPalindromo(palavre));
        Assert.assertFalse(palindromo.ehPalindromo(outra));
    }
} 
