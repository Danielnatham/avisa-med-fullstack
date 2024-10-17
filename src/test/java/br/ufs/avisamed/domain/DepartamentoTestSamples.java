package br.ufs.avisamed.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DepartamentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Departamento getDepartamentoSample1() {
        return new Departamento().id(1L).idGestorDepartamento(1).nome("nome1").descricao("descricao1");
    }

    public static Departamento getDepartamentoSample2() {
        return new Departamento().id(2L).idGestorDepartamento(2).nome("nome2").descricao("descricao2");
    }

    public static Departamento getDepartamentoRandomSampleGenerator() {
        return new Departamento()
            .id(longCount.incrementAndGet())
            .idGestorDepartamento(intCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString());
    }
}
