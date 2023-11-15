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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import xyz.thingummy.oss.commons.notification.CollecteurNotifications;
import xyz.thingummy.oss.commons.notification.Message;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interface Specification - représente une spécification selon acception du terme dans le contexte du
 * Design (conception) Dirigé par le Domaine (DDD Domain Driven Design).
 * C'est une extension fonctionnelle de l'interface Predicat qui permet de construire des règles métier complexes
 * de manière expressive et lisible. Elle fournit des méthodes pour composer des spécifications à l'aide d'opérations
 * logiques telles que ET, OU et NON et est adaptée aux locuteurs de langue française.
 *
 * @param <T> le type de l'objet sur lequel la spécification est évaluée
 */
@FunctionalInterface
public interface Specification<T> extends Predicat<T> {
    Specification<Object> toujoursVrai = (t) -> true;

    Specification<Object> toujoursFaux = (t) -> false;

    @SafeVarargs
    static <T> Specification<T> et(Predicate<T>... predicates) {
        return Arrays.stream(predicates)
                .reduce(Predicate::and)
                .map(Specification::soit)
                .orElse(t -> true);
    }

    static <T> Specification<T> soit(Predicate<? super T> predicat) {
        return predicat::test;
    }

    @SafeVarargs
    static <T> Specification<T> ou(Predicate<T>... predicates) {
        return Arrays.stream(predicates)
                .reduce(Predicate::or)
                .map(Specification::soit)
                .orElse(t -> false);
    }

    static <T, U> Specification<T> soit(Function<? super T, U> extracteur, Predicate<U> predicat) {
        return (t) -> predicat.test(extracteur.apply(t));
    }

    static <T> Specification<T> pas(Predicate<? super T> predicat) {
        return non(predicat);
    }

    static <T> Specification<T> non(Predicate<? super T> predicat) {

        return (t) -> !predicat.test(t);
    }


    default Specification<T> avec(Message m) {
        return new AvecMessage<>(this, m);
    }

    default boolean estSatisfaitePar(T t, @NonNull CollecteurNotifications c) {
        final boolean estSatisfaite = estSatisfaitePar(t);
        c.ajouter(!estSatisfaite, getMessage(), this, t);
        return estSatisfaite;
    }

    default boolean estSatisfaitePar(T t) {
        return this.test(t);
    }

    default Message getMessage() {
        return null;
    }

    default Specification<T> et(Specification<? super T> autre) {
        return new Et<>(this, autre);
    }

    default Specification<T> ou(Specification<? super T> autre) {
        return new Ou<>(this, autre);
    }

    default Specification<T> etPas(Specification<? super T> autre) {
        return etNon(autre);
    }

    /**
     * Combinateur personnalisé pour combiner cette spécification avec un autre prédicat en utilisant l'opération logique ET NON.
     * La spécification résultante est satisfaite si la première spécification est satisfaite et la seconde ne l'est pas.
     *
     * @param autre Le deuxième prédicat à combiner.
     * @return Une nouvelle spécification qui est la conjonction de cette spécification et de la négation du prédicat donné.
     */
    default Specification<T> etNon(Specification<? super T> autre) {
        return new Et<>(this, autre.non());
    }

    default Specification<T> non() {
        return new Non<>(this, null);
    }

    /**
     * Un synonyme pour 'etNon'. Combine cette spécification avec un autre prédicat en utilisant l'opération
     * logique ET NON. La spécification résultante est satisfaite si la première spécification est satisfaite et
     * la seconde ne l'est pas.
     *
     * @param autre Le deuxième prédicat à combiner.
     * @return Une nouvelle spécification qui est la conjonction de cette spécification et de la négation du prédicat donné.
     */
    default Specification<T> ni(Specification<? super T> autre) {
        return etNon(autre);
    }

    default Specification<T> ouX(Specification<? super T> autre) {
        return new Ou<>(this, autre, true);
    }

    default SpecificationDifferee differer(T t) {
        return (u) -> estSatisfaitePar(t);
    }

    @AllArgsConstructor
    static class AvecMessage<T> implements Specification<T> {
        private final Specification<T> specification;
        @Getter
        private final Message message;

        @Override
        public boolean test(T t) {
            return specification.test(t);
        }
    }


    static class Et<T> extends Operator<T> {


        public Et(Specification<T> spec1, Specification<? super T> spec2) {
            super(spec1, spec2, (b1, b2) -> b1 && b2, ShortCut.AND);
        }


    }

    static class Ou<T> extends Operator<T> {

        public Ou(Specification<T> spec1, Specification<? super T> spec2) {
            this(spec1, spec2, false);
        }

        public Ou(Specification<T> spec1, Specification<? super T> spec2, boolean excl) {
            super(spec1, spec2, (!excl) ? (b1, b2) -> b1 || b2 : (b1, b2) -> b1 ^ b2, Operator.ShortCut.OR);
        }

        @Override
        public boolean estSatisfaitePar(T t, @NonNull CollecteurNotifications c) {

            CollecteurNotifications c1 = new CollecteurNotifications();
            boolean res = super.estSatisfaitePar(t, c1);
            c.ajouterTout(!res, c1);
            return res;
        }
    }

    static class Non<T> extends Operator<T> {

        protected Non(Specification<T> spec1, Specification<? super T> spec2) {
            super(spec1, toujoursVrai, (b1, b2) -> !b1, Operator.ShortCut.UNARY);
        }
    }


}