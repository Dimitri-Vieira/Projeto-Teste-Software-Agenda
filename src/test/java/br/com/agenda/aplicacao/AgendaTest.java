package br.com.agenda.aplicacao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AgendaTest {

    @Test
    public void testeDeSucesso() {
        System.out.println("Executando teste que passa...");
        Assertions.assertTrue(true);
    }

    @Test
    public void testeDeFalhaProposital() {
        System.out.println("Executando teste que falha para a atividade...");
        // Isso vai fazer o Pipeline parar e acusar erro
        Assertions.assertEquals(10, 5, "O valor deveria ser 10, mas foi 5");
    }
}