package br.ufs.avisamed.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GestorDepartamentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static GestorDepartamento getGestorDepartamentoSample1() {
        return new GestorDepartamento().id(1L).titulo("titulo1").descricao("descricao1");
    }

    public static GestorDepartamento getGestorDepartamentoSample2() {
        return new GestorDepartamento().id(2L).titulo("titulo2").descricao("descricao2");
    }

    public static GestorDepartamento getGestorDepartamentoRandomSampleGenerator() {
        return new GestorDepartamento()
            .id(longCount.incrementAndGet())
            .titulo(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString());
    }
}
