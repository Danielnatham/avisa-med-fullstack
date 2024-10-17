package br.ufs.avisamed.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class OcorrenciaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Ocorrencia getOcorrenciaSample1() {
        return new Ocorrencia()
            .id(1L)
            .idSolicitante(1)
            .idDepartamento(1)
            .titulo("titulo1")
            .descricao("descricao1")
            .imagem("imagem1")
            .situacao("situacao1")
            .complexidade(1)
            .protocolo("protocolo1");
    }

    public static Ocorrencia getOcorrenciaSample2() {
        return new Ocorrencia()
            .id(2L)
            .idSolicitante(2)
            .idDepartamento(2)
            .titulo("titulo2")
            .descricao("descricao2")
            .imagem("imagem2")
            .situacao("situacao2")
            .complexidade(2)
            .protocolo("protocolo2");
    }

    public static Ocorrencia getOcorrenciaRandomSampleGenerator() {
        return new Ocorrencia()
            .id(longCount.incrementAndGet())
            .idSolicitante(intCount.incrementAndGet())
            .idDepartamento(intCount.incrementAndGet())
            .titulo(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .imagem(UUID.randomUUID().toString())
            .situacao(UUID.randomUUID().toString())
            .complexidade(intCount.incrementAndGet())
            .protocolo(UUID.randomUUID().toString());
    }
}
