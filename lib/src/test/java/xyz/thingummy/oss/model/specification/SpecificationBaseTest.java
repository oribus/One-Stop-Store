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
import static xyz.thingummy.oss.model.specification.SpecificationOperations.*;

public interface SpecificationBaseTest extends PredicatBaseTest {

    default Specification<String> commenceParA() {
        return SimpleTestSpecifications.commenceParA();
    }

    default Specification<String> finiParA() {
        return SimpleTestSpecifications.finiParA();
    }

    default Specification<Integer> supperieurA4() {
        return SimpleTestSpecifications.supperieurA4();
    }

    /**
     * Teste la méthode estSatisfaitePar.
     * Vérifie si la spécification est correctement évaluée pour différentes chaînes de caractères.
     */
    @ParameterizedTest
    @CsvSource(value = {"abc:true", "abracadabra:true", "jazz:false", "hello:false"}, delimiter = ':')
    default void test_EstSatisfaitePar(final String candidat, final boolean resultat) {
        assertEquals(commenceParA().tester(candidat), commenceParA().estSatisfaitePar(candidat));
        assertEquals(resultat, commenceParA().estSatisfaitePar(candidat));
    }

    /**
     * Teste la méthode ouX (OU exclusif).
     * Vérifie que la spécification combinée est satisfaite lorsque exactement l'un des prédicats est vrai.
     */
    @ParameterizedTest
    @CsvSource(value = {"abc:true", "ella:true", "abracadabra:false", "jazz:false"}, delimiter = ':')
    default void test_OuX(final String candidat, final boolean resultat) {
        final Specification<String> commenceOuXFiniParA = commenceParA().ouX(finiParA());
        assertEquals(resultat, commenceOuXFiniParA.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:true", "ella:false", "abracadabra:false", "jazz:false"}, delimiter = ':')
    default void test_EtPas(final String candidat, final boolean resultat) {
        final Specification<String> commenceParAMaisNeFiniPasParA = commenceParA().etPas(finiParA());
        assertEquals(resultat, commenceParAMaisNeFiniPasParA.estSatisfaitePar(candidat));
    }

    /**
     * Teste la méthode etNon.
     * Vérifie que la spécification combinée est satisfaite lorsque la première spécification est vraie et la seconde fausse.
     */
    @ParameterizedTest
    @CsvSource(value = {"abc:true", "ella:false", "abracadabra:false", "jazz:false"}, delimiter = ':')
    default void test_EtNon(final String candidat, final boolean resultat) {
        final Specification<String> commenceParAMaisNeFiniPasParA = commenceParA().etNon(finiParA());
        assertEquals(resultat, commenceParAMaisNeFiniPasParA.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:false", "ella:false", "abracadabra:false", "jazz:true"}, delimiter = ':')
    default void test_Pas_Ni(final String candidat, final boolean resultat) {
        final Specification<String> neCommenceNiNeFiniParA = pas(commenceParA()).ni(finiParA());
        assertEquals(resultat, neCommenceNiNeFiniParA.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:false", "abracadabra:true", "jazz:false"}, delimiter = ':')
    default void test_transformer(final String candidat, final boolean resultat) {
        final Specification<String> contientPlusDeQuatreCaracteres = supperieurA4().transformer(String::length);
        assertEquals(resultat, contientPlusDeQuatreCaracteres.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:true", "ella:false", "abracadabra:true", "jazz:false"}, delimiter = ':')
    default void test_statique_Soit(final String candidat, final boolean resultat) {
        final Specification<String> specSoit = soit(commenceParA());
        assertEquals(resultat, specSoit.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:true", "ella:false", "abracadabra:true", "jazz:false"}, delimiter = ':')
    default void test_methode_statique_Soit_avec_predicat(final String candidat, final boolean resultat) {
        final Predicat<String> predicat = commenceParA();
        final Specification<String> specSoit = soit(predicat);
        assertEquals(resultat, specSoit.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:false", "abracadabra:true", "jazz:false"}, delimiter = ':')
    default void test_statique_Soit_avec_extracteur(final String candidat, final boolean resultat) {
        final Specification<String> contientPlusDeQuatreCaracteres = soit(supperieurA4(), String::length);
        assertEquals(resultat, contientPlusDeQuatreCaracteres.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:false", "abracadabra:true", "jazz:false"}, delimiter = ':')
    default void test_statique_Soit_avec_extracteur_et_predicat(final String candidat, final boolean resultat) {
        final Predicat<Integer> predicat = supperieurA4();
        final Specification<String> contientPlusDeQuatreCaracteres = soit(predicat, String::length);
        assertEquals(resultat, contientPlusDeQuatreCaracteres.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:false", "ella:true", "abracadabra:false", "jazz:true"}, delimiter = ':')
    default void test_methode_statique_Pas(final String candidat, final boolean resultat) {
        final Specification<String> neCommencePasParA = pas(commenceParA());
        assertEquals(resultat, neCommencePasParA.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:false", "ella:true", "abracadabra:false", "jazz:true"}, delimiter = ':')
    default void test_methode_statique_Non(final String candidat, final boolean resultat) {
        final Specification<String> neCommencePasParA = non(commenceParA());
        assertEquals(resultat, neCommencePasParA.estSatisfaitePar(candidat));
    }

    /**
     * Teste la méthode : "et"
     * Vérifie que la spécification combinée est satisfaite uniquement lorsque les deux prédicats sont vrais.
     */
    @ParameterizedTest
    @CsvSource(value = {"abc:false", "ella:false", "abracadabra:true", "jazz:false", "hello:false"}, delimiter = ':')
    default void test_methode_statique_Et(final String candidat, final boolean resultat) {
        final Specification<String> commenceEtFiniParA = et(commenceParA(), finiParA());
        assertEquals(resultat, commenceEtFiniParA.estSatisfaitePar(candidat));
    }


    /**
     * Teste la méthode : "ou".
     * Vérifie que la spécification combinée est satisfaite si au moins l'un des prédicats est vrai.
     */
    @ParameterizedTest
    @CsvSource(value = {"abc:true", "ella:true", "abracadabra:true", "jazz:false"}, delimiter = ':')
    default void test_methode_statique_Ou(final String candidat, final boolean resultat) {
        final Specification<String> commenceOuFiniParA = ou(commenceParA(), finiParA());
        assertEquals(resultat, commenceOuFiniParA.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:false", "ella:false", "abracadabra:true", "jazz:false"}, delimiter = ':')
    default void test_methode_statique_Combiner(final String candidat, final boolean resultat) {
        final Specification<String> commenceEtFiniParA = combiner(commenceParA(), finiParA(), Boolean::logicalAnd);
        assertEquals(resultat, commenceEtFiniParA.estSatisfaitePar(candidat));
    }


    @ParameterizedTest
    @CsvSource(value = {"abc:true:true", "ella:true:false", "abracadabra:true:true", "jazz:true:false",
            "abc:false:false", "ella:false:true", "abracadabra:false:true", "jazz:false:false"}, delimiter = ':')
    default void Si(final String candidat, final boolean condition, final boolean resultat) {
        final Specification<String> specificationConditionnelle = si(() -> condition, commenceParA(), finiParA());
        assertEquals(resultat, specificationConditionnelle.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:false", "ella:false", "abracadabra:true", "jazz:false"}, delimiter = ':')
    default void test_Combiner(final String candidat, final boolean resultat) {
        final Specification<String> commenceEtFiniParA = commenceParA().combiner(finiParA(), Boolean::logicalAnd);
        assertEquals(resultat, commenceEtFiniParA.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:false", "ella:false", "abracadabra:true", "jazz:false"}, delimiter = ':')
    default void test_Combiner_avec_ShortCut(final String candidat, final boolean resultat) {
        final Specification<String> commenceEtFiniParA = commenceParA().combiner(finiParA(), Boolean::logicalAnd, SpecificationCombinee.ShortCut.AND);
        assertEquals(resultat, commenceEtFiniParA.estSatisfaitePar(candidat));
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:true", "ella:false"}, delimiter = ':')
    default void test_Differer(final String candidat, final boolean resultat) {
        assertEquals(resultat, commenceParA().differer(candidat).estSatisfaite());
        assertEquals(resultat, commenceParA().differer(candidat).estSatisfaitePar(null));
        assertEquals(resultat, commenceParA().differer(candidat).getAsBoolean());
    }

}