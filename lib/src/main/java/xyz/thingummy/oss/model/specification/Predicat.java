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

package xyz.thingummy.oss.model.specification;

import java.util.function.Predicate;

/**
 * Interface Predicat - un wrapper en langue française pour l'interface standard Java Predicate.
 * Cette interface vise à permettre aux développeurs francophones d'écrire des prédicats qui sont
 * plus naturels et expressifs dans leur langue maternelle.
 */
@FunctionalInterface
public interface Predicat<T> extends Predicate<T> {


    /**
     * Évalue ce prédicat sur l'argument donné.
     *
     * @param t l'argument sur lequel évaluer le prédicat courant
     * @return true si l'argument d'entrée vérifie le prédicat, sinon false
     */
    default boolean tester(T t) {
        return test(t);
    }

    /**
     * Compose le prédicat courant avec un autre prédicat pour en créer un nouveau qui représente
     * une conjonction des deux prédicats. Cela correspond à une opération logique ET.
     *
     * @param autre le prédicat à combiner avec le prédicat courant
     * @return un prédicat qui est la conjonction du prédicat courant et du prédicat argument
     */
    default Predicat<T> et(Predicate<? super T> autre) {
        return Predicate.super.and(autre)::test;
    }

    /**
     * Compose le prédicat courant avec un autre prédicat pour en créer un nouveau qui représente
     * une disjonction des deux prédicats. Cela correspond à une opération logique OU.
     *
     * @param autre le prédicat à combiner avec le prédicat courant
     * @return un prédicat qui est la disjonction du prédicat courant et du prédicat argument
     */
    default Predicat<T> ou(Predicate<? super T> autre) {
        return Predicate.super.or(autre)::test;
    }

    /**
     * Retourne un prédicat représentant la négation de celui sur lequel cette méthode est invoquée.
     *
     * @return un prédicat qui représente la négation du prédicat courant.
     */
    default Predicat<T> non() {
        return Predicate.super.negate()::test;
    }


}