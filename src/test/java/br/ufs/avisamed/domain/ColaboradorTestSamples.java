package br.ufs.avisamed.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ColaboradorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Colaborador getColaboradorSample1() {
        return new Colaborador().id(1L).idDepartamento(1).idUsuario(1);
    }

    public static Colaborador getColaboradorSample2() {
        return new Colaborador().id(2L).idDepartamento(2).idUsuario(2);
    }

    public static Colaborador getColaboradorRandomSampleGenerator() {
        return new Colaborador()
            .id(longCount.incrementAndGet())
            .idDepartamento(intCount.incrementAndGet())
            .idUsuario(intCount.incrementAndGet());
    }
}
