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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

interface PredicatBaseTest {

    Predicat<String> commenceParA();

    Predicat<String> finiParA();

    Predicat<Integer> supperieurA4();

    /**
     * Teste la méthode estSatisfaitePar.
     * Vérifie si la spécification est correctement évaluée pour différentes chaînes de caractères.
     */
    @ParameterizedTest
    @CsvSource(value = {"abc:true", "abracadabra:true", "jazz:false", "hello:false"}, delimiter = ':')
    default void test_Tester(final String candidat, final boolean resultat) {
        assertEquals(resultat, commenceParA().tester(candidat));
    }

    /**
     * Teste la méthode : "et"
     * Vérifie que la spécification combinée est satisfaite uniquement lorsque les deux prédicats sont vrais.
     */
    @ParameterizedTest
    @CsvSource(value = {"abc:false", "ella:false", "abracadabra:true", "jazz:false", "hello:false"}, delimiter = ':')
    default void test_Et(final String candidat, final boolean resultat) {
        assertEquals(resultat, commenceParA().et(finiParA()).tester(candidat));
    }


    /**
     * Teste la méthode : "ou".
     * Vérifie que la spécification combinée est satisfaite si au moins l'un des prédicats est vrai.
     */
    @ParameterizedTest
    @CsvSource(value = {"abc:true", "ella:true", "abracadabra:true", "jazz:false"}, delimiter = ':')
    default void test_Ou(final String candidat, final boolean resultat) {
        assertEquals(resultat, commenceParA().ou(finiParA()).tester(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:false", "ella:true", "abracadabra:false", "jazz:true"}, delimiter = ':')
    default void test_Non(final String candidat, final boolean resultat) {
        assertEquals(resultat, commenceParA().non().tester(candidat));
    }

}




