package br.ufs.avisamed.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CidadaoIdentificadoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CidadaoIdentificado getCidadaoIdentificadoSample1() {
        return new CidadaoIdentificado().id(1L).idUsuario(1);
    }

    public static CidadaoIdentificado getCidadaoIdentificadoSample2() {
        return new CidadaoIdentificado().id(2L).idUsuario(2);
    }

    public static CidadaoIdentificado getCidadaoIdentificadoRandomSampleGenerator() {
        return new CidadaoIdentificado().id(longCount.incrementAndGet()).idUsuario(intCount.incrementAndGet());
    }
}
