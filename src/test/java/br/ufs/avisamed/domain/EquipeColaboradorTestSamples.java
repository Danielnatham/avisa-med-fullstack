package br.ufs.avisamed.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EquipeColaboradorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EquipeColaborador getEquipeColaboradorSample1() {
        return new EquipeColaborador().id(1L).idColaborador(1).idEquipe(1);
    }

    public static EquipeColaborador getEquipeColaboradorSample2() {
        return new EquipeColaborador().id(2L).idColaborador(2).idEquipe(2);
    }

    public static EquipeColaborador getEquipeColaboradorRandomSampleGenerator() {
        return new EquipeColaborador()
            .id(longCount.incrementAndGet())
            .idColaborador(intCount.incrementAndGet())
            .idEquipe(intCount.incrementAndGet());
    }
}
