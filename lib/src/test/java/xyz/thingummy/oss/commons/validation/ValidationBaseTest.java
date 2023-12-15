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

package xyz.thingummy.oss.commons.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import xyz.thingummy.oss.commons.notification.CollecteurNotifications;
import xyz.thingummy.oss.commons.notification.Message;
import xyz.thingummy.oss.commons.notification.TypeMessage;
import xyz.thingummy.oss.model.specification.Specification;
import xyz.thingummy.oss.model.specification.SpecificationBaseTest;

import static org.junit.jupiter.api.Assertions.*;
import static xyz.thingummy.oss.commons.validation.ValidationOperations.soit;

public interface ValidationBaseTest extends SpecificationBaseTest {


    Message MESSAGE = Message.of("message.key1", TypeMessage.ERREUR);

    Message MESSAGE2 = Message.of("message.key2", TypeMessage.ERREUR);

    static boolean checkType(final Specification<?> specification) {
        return !(specification instanceof Validation<?>);
    }

    @Override
    Validation<String> commenceParA();

    @Override
    Validation<String> finiParA();

    @Override
    Validation<Integer> supperieurA4();


    default Validation<String> commenceParAAvecMessages() {
        return commenceParA().avec(MESSAGE, MESSAGE2);
    }

    @Test
    default void Test_Soit() {
        final Validation<String> ref = commenceParA();
        final Validation<String> validation = soit(ref);
        assertNotNull(ref);
        assertEquals(ref, validation);

    }

    @Test
    default void Test_GetSpecification() {
        assertTrue(checkType(commenceParA().getSpecification()));
        assertTrue(checkType(finiParA().getSpecification()));
        assertTrue(checkType(supperieurA4().getSpecification()));
    }


    @Test
    default void test_Avec1() {
        final Validation<String> avecMessage = commenceParA().avec(MESSAGE);
        assertFalse(avecMessage.getMessage().isEmpty());
        assertTrue(avecMessage.getMessageAdditionnel().isEmpty());
    }

    @Test
    default void test_Avec2() {
        final Validation<String> avecMessageAdditionnel = commenceParA().avec(null, MESSAGE);
        assertTrue(avecMessageAdditionnel.getMessage().isEmpty());
        assertFalse(avecMessageAdditionnel.getMessageAdditionnel().isEmpty());
    }

    @Test
    default void test_Avec3() {

        final Validation<String> avecMessageEtMessageAdditionnel = commenceParA().avec(MESSAGE, MESSAGE);
        assertFalse(avecMessageEtMessageAdditionnel.getMessage().isEmpty());
        assertFalse(avecMessageEtMessageAdditionnel.getMessageAdditionnel().isEmpty());
    }

    @Test
    default void test_Avec4() {

        final Validation<String> avecMessageEtMessageAdditionnel = commenceParAAvecMessages().avec(MESSAGE2, MESSAGE);
        assertFalse(avecMessageEtMessageAdditionnel.getMessage().isEmpty());
        assertFalse(avecMessageEtMessageAdditionnel.getMessage().filter(m -> m.equals(MESSAGE2)).isEmpty());
        assertFalse(avecMessageEtMessageAdditionnel.getMessageAdditionnel().isEmpty());
        assertFalse(avecMessageEtMessageAdditionnel.getMessageAdditionnel().filter(m -> m.equals(MESSAGE)).isEmpty());
    }

    @Test
    default void test_SansMessage1() {
        assertTrue(commenceParA().getMessage().isEmpty());
        assertTrue(commenceParA().getMessageAdditionnel().isEmpty());
        final Validation<String> sansMessage = commenceParA().sansMessage();
        assertTrue(sansMessage.getMessage().isEmpty());
        assertTrue(sansMessage.getMessageAdditionnel().isEmpty());
    }

    @Test
    default void test_SansMessage2() {
        assertFalse(commenceParAAvecMessages().getMessageAdditionnel().isEmpty());
        assertFalse(commenceParAAvecMessages().getMessage().isEmpty());
        final Validation<String> sansMessage = commenceParAAvecMessages().sansMessage();
        assertTrue(sansMessage.getMessage().isEmpty());
        assertTrue(sansMessage.getMessageAdditionnel().isEmpty());
    }

    @Test
    default void test_SansMessage3() {
        assertFalse(commenceParAAvecMessages().getMessageAdditionnel().isEmpty());
        assertFalse(commenceParAAvecMessages().getMessage().isEmpty());
        final Validation<String> avecMessageEtMessageAdditionnel = commenceParAAvecMessages().avec(MESSAGE2, MESSAGE);
        final Validation<String> sansMessage = avecMessageEtMessageAdditionnel.sansMessage();
        assertTrue(sansMessage.getMessage().isEmpty());
        assertTrue(sansMessage.getMessageAdditionnel().isEmpty());
    }

    @Test
    default void test_GetMessage() {
        assertTrue(commenceParA().getMessage().isEmpty());
    }

    @Test
    default void test_GetMessage2() {
        assertFalse(commenceParAAvecMessages().getMessage().isEmpty());
    }

    @Test
    default void test_GetMessageAdditionnel() {
        assertTrue(commenceParA().getMessageAdditionnel().isEmpty());
    }

    @Test
    default void test_GetMessageAdditionnel2() {
        assertFalse(commenceParAAvecMessages().getMessageAdditionnel().isEmpty());
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:true", "abracadabra:true", "jazz:false", "hello:false"}, delimiter = ':')
    default void test_EstSatisfaitePar_avec_collecteur(final String candidat, final boolean attendu) {
        final CollecteurNotifications collecteur = new CollecteurNotifications();
        assertEquals(commenceParA().tester(candidat), commenceParA().estSatisfaitePar(candidat, collecteur));
        assertEquals(attendu, commenceParA().estSatisfaitePar(candidat, collecteur));
        assertFalse(collecteur.existeNotifications());
    }

    @ParameterizedTest
    @CsvSource(value = {"abc:true", "abracadabra:true", "jazz:false", "hello:false"}, delimiter = ':')
    default void test_EstSatisfaitePar_avec_collecteur2(final String candidat, final boolean attendu) {
        final CollecteurNotifications collecteur = new CollecteurNotifications();
        final boolean estSatisfaite = commenceParAAvecMessages().estSatisfaitePar(candidat, collecteur);
        assertEquals(commenceParAAvecMessages().tester(candidat), estSatisfaite);
        assertEquals(attendu, estSatisfaite);
        assertEquals(!estSatisfaite, collecteur.existeNotifications());
    }

}