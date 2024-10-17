package br.ufs.avisamed.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EquipeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Equipe getEquipeSample1() {
        return new Equipe().id(1L).idOcorrencia(1).idG(1);
    }

    public static Equipe getEquipeSample2() {
        return new Equipe().id(2L).idOcorrencia(2).idG(2);
    }

    public static Equipe getEquipeRandomSampleGenerator() {
        return new Equipe().id(longCount.incrementAndGet()).idOcorrencia(intCount.incrementAndGet()).idG(intCount.incrementAndGet());
    }
}
