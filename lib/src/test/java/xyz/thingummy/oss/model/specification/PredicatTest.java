/*
 *
 * The MIT License (MIT)
 *
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

package xyz.thingummy.oss.model.specification;

import org.junit.jupiter.api.Test;
import xyz.thingummy.oss.model.specification.Predicat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PredicatTest {

    /**
     * Prédicat vérifiant si un entier est strictement positif.
     */
    private final Predicat<Integer> estStrictementPositif = t -> t > 0;

    /**
     * Prédicat vérifiant si un entier est pair.
     */
    private final Predicat<Integer> estPair = t -> t % 2 == 0;

    /**
     * Test la méthode `tester` qui évalue
     */
    @Test
    void test_tester() {

    }

    /**
     * Teste la méthode `et` qui combine deux prédicats en utilisant une opération logique ET.
     * Le prédicat résultant doit retourner vrai uniquement si les deux prédicats initiaux sont vrais.
     */
    @Test
    void test_et() {
        // when
        final Predicat<Integer> estPairEtStrictementPositif = estStrictementPositif.et(estPair);

        // Vérification du comportement du prédicat.
        assertTrue(estPairEtStrictementPositif.tester(2));
        assertFalse(estPairEtStrictementPositif.tester(-2));
        assertFalse(estPairEtStrictementPositif.tester(1));
    }

    /**
     * Teste la méthode `ou` qui combine deux prédicats en utilisant une opération logique OU.
     * Le prédicat résultant doit retourner vrai si au moins un des deux prédicats initiaux est vrai.
     */
    @Test
    void test_ou() {
        // when
        final Predicat<Integer> estPairOuStrictementPositif = estStrictementPositif.ou(estPair);

        // Vérification du comportement du prédicat.
        assertTrue(estPairOuStrictementPositif.tester(2));
        assertFalse(estPairOuStrictementPositif.tester(-1));
        assertTrue(estPairOuStrictementPositif.tester(1));
    }

    /**
     * Teste la méthode `non` qui inverse le résultat d'un prédicat.
     * Le prédicat résultant doit retourner vrai si le prédicat initial est faux, et vice-versa.
     */
    @Test
    void test_non() {
        // Construction du prédicat estImpair par négation du prédicat estPair.
        final Predicat<Integer> estImpair = estPair.non();
        // Vérification du comportement du prédicat.
        assertFalse(estImpair.tester(2));
        assertTrue(estImpair.tester(1));
    }

}




