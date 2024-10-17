package br.ufs.avisamed.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class GestorDepartamentoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGestorDepartamentoAllPropertiesEquals(GestorDepartamento expected, GestorDepartamento actual) {
        assertGestorDepartamentoAutoGeneratedPropertiesEquals(expected, actual);
        assertGestorDepartamentoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGestorDepartamentoAllUpdatablePropertiesEquals(GestorDepartamento expected, GestorDepartamento actual) {
        assertGestorDepartamentoUpdatableFieldsEquals(expected, actual);
        assertGestorDepartamentoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGestorDepartamentoAutoGeneratedPropertiesEquals(GestorDepartamento expected, GestorDepartamento actual) {
        assertThat(expected)
            .as("Verify GestorDepartamento auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGestorDepartamentoUpdatableFieldsEquals(GestorDepartamento expected, GestorDepartamento actual) {
        assertThat(expected)
            .as("Verify GestorDepartamento relevant properties")
            .satisfies(e -> assertThat(e.getTitulo()).as("check titulo").isEqualTo(actual.getTitulo()))
            .satisfies(e -> assertThat(e.getDescricao()).as("check descricao").isEqualTo(actual.getDescricao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGestorDepartamentoUpdatableRelationshipsEquals(GestorDepartamento expected, GestorDepartamento actual) {
        assertThat(expected)
            .as("Verify GestorDepartamento relationships")
            .satisfies(e -> assertThat(e.getColaborador()).as("check colaborador").isEqualTo(actual.getColaborador()));
    }
}
