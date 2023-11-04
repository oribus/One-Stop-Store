/*
 * Copyright (c) 2023, Jérôme ROBERT
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 *   this software and associated documentation files (the “Software”), to deal in the
 *   Software without restriction, including without limitation the rights to use, copy,
 *   modify, merge, publish, distribute, sublicense, and/or sell copies of the
 *   Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 *    copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 *    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *    HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *    FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *    OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package xyz.thingummy.oss.model;

import org.junit.jupiter.api.Test;
import xyz.thingummy.oss.model.specification.Specification;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static xyz.thingummy.oss.model.specification.Specification.*;

/**
 * Tests de la classe Specification
 */
class SpecificationTest {

    /**
     * Cette spécification permet de tester si un nombre est pair.
     */
    private final Specification<Integer> estPair = i -> i % 2 == 0;

    /**
     * Cette spécification permet de tester si un nombre est impair.
     * Elle est construite par la négation de la précédente.
     */
    private final Specification<Integer> estImpair = non(estPair);

    /**
     * Cette spécification permet de tester si un nombre est supérieur à 10.
     */
    private final Specification<Integer> estSuperieurADix = i -> i > 10;

    /**
     * Teste le comportement de la methode estSatisfaitePar avec la spécification estPair.
     */
    @Test
    void test_EstSatisfaitePar() {
        assertFalse(estPair.estSatisfaitePar(1));
        assertTrue(estPair.estSatisfaitePar(2));
    }

    /**
     * Teste la methode et : conjonction de spécifications.
     */
    @Test
    void test_Et() {
        final Specification<Integer> estPairEtSupperieurADix = estPair.et(estSuperieurADix);
        assertFalse(estPairEtSupperieurADix.estSatisfaitePar(2));
        assertTrue(estPairEtSupperieurADix.estSatisfaitePar(12));
    }

    /**
     * Teste la méthode ou : disjunction de spécifications.
     */
    @Test
    void test_Ou() {
        final Specification<Integer> estPairOuSupperieurADix = estPair.ou(estSuperieurADix);
        assertFalse(estPairOuSupperieurADix.estSatisfaitePar(9));
        assertTrue(estPairOuSupperieurADix.estSatisfaitePar(2));
        assertTrue(estPairOuSupperieurADix.estSatisfaitePar(11));
    }

    /**
     * Teste "l'opérateur" et : conjonction de spécifications.
     */
    @Test
    void test_OperateurEt() {
        final Specification<Integer> estPairEtSupperieurADix = et(estPair, estSuperieurADix);
        assertFalse(estPairEtSupperieurADix.estSatisfaitePar(2));
        assertTrue(estPairEtSupperieurADix.estSatisfaitePar(12));
    }

    /**
     * Teste "l'opérateur" ou : disjunction de spécifications. "
     */
    @Test
    void test_OperateurOu() {
        final Specification<Integer> estPairOuSupperieurADix = ou(estPair, estSuperieurADix);
        assertFalse(estPairOuSupperieurADix.estSatisfaitePar(9));
        assertTrue(estPairOuSupperieurADix.estSatisfaitePar(2));
        assertTrue(estPairOuSupperieurADix.estSatisfaitePar(11));
    }

    /**
     * Teste "l'opérateur" non : négation d'une spécification.
     */
    @Test
    void test_OperateurNon() {
        assertTrue(estImpair.estSatisfaitePar(1));
        assertFalse(estImpair.estSatisfaitePar(2));
    }

    /**
     * Teste "l'opérateur" ouEx : disjonction exclusive de spécifications.
     */
    @Test
    void test_OuEx() {
        final Specification<Integer> estPairOuXSupperieurADix = estPair.ouEx(estSuperieurADix);
        assertFalse(estPairOuXSupperieurADix.estSatisfaitePar(9));
        assertTrue(estPairOuXSupperieurADix.estSatisfaitePar(2));
        assertTrue(estPairOuXSupperieurADix.estSatisfaitePar(11));
        assertFalse(estPairOuXSupperieurADix.estSatisfaitePar(12));
    }

    @Test
    void test_differer() {
        Specification<Void> differee = estPair.differer(2);
        assertTrue(differee.estSatisfaitePar(null));

    }

}