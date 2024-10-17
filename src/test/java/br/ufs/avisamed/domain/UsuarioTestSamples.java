package br.ufs.avisamed.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UsuarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Usuario getUsuarioSample1() {
        return new Usuario().id(1L).nome("nome1").telefone("telefone1").email("email1").cpf("cpf1").senha("senha1");
    }

    public static Usuario getUsuarioSample2() {
        return new Usuario().id(2L).nome("nome2").telefone("telefone2").email("email2").cpf("cpf2").senha("senha2");
    }

    public static Usuario getUsuarioRandomSampleGenerator() {
        return new Usuario()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .telefone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .cpf(UUID.randomUUID().toString())
            .senha(UUID.randomUUID().toString());
    }
}
