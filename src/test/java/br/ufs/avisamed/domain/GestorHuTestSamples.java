package br.ufs.avisamed.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GestorHuTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static GestorHu getGestorHuSample1() {
        return new GestorHu().id(1L).idColaborador(1);
    }

    public static GestorHu getGestorHuSample2() {
        return new GestorHu().id(2L).idColaborador(2);
    }

    public static GestorHu getGestorHuRandomSampleGenerator() {
        return new GestorHu().id(longCount.incrementAndGet()).idColaborador(intCount.incrementAndGet());
    }
}
