/*
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

import lombok.NonNull;

import java.util.function.BinaryOperator;
import java.util.function.Function;

import static xyz.thingummy.oss.model.specification.Specifications.Specifications0;

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

    default Specification<T> combiner(final Specification<? super T> spec2, @NonNull final BinaryOperator<Boolean> operateur) {
        return new SpecificationCombinee<>(this, spec2, operateur, SpecificationCombinee.ShortCut.NONE);
    }

    default Specifications0 differer(final T t) {
        return () -> estSatisfaitePar(t);
    }

    default boolean estSatisfaitePar(final T t) {
        return this.test(t);
    }

    default Specification<T> etPas(@NonNull final Specification<? super T> autre) {
        return etNon(autre);
    }

    /**
     * Combinateur personnalisé pour combiner cette spécification avec un autre prédicat en utilisant l'opération logique ET NON.
     * La spécification résultante est satisfaite si la première spécification est satisfaite et la seconde ne l'est pas.
     *
     * @param autre Le deuxième prédicat à combiner.
     * @return Une nouvelle spécification qui est la conjonction de cette spécification et de la négation du prédicat donné.
     */
    default Specification<T> etNon(@NonNull final Specification<? super T> autre) {
        return this.et(autre.non());
    }

    default Specification<T> et(@NonNull final Specification<? super T> autre) {
        return new SpecificationCombinee.Et<>(this, autre);
    }

    default Specification<T> non() {
        return new SpecificationCombinee.Non<>(this);
    }

    default Specification<T> combiner(final Specification<? super T> spec2, @NonNull final BinaryOperator<Boolean> operateur, @NonNull final SpecificationCombinee.ShortCut shortCut) {
        return new SpecificationCombinee<>(this, spec2, operateur, shortCut);
    }

    /**
     * Un synonyme pour 'etNon'. Combine cette spécification avec un autre prédicat en utilisant l'opération
     * logique ET NON. La spécification résultante est satisfaite si la première spécification est satisfaite et
     * la seconde ne l'est pas.
     *
     * @param autre Le deuxième prédicat à combiner.
     * @return Une nouvelle spécification qui est la conjonction de cette spécification et de la négation du prédicat donné.
     */
    default Specification<T> ni(@NonNull final Specification<? super T> autre) {
        return etNon(autre);
    }

    default Specification<T> ou(@NonNull final Specification<? super T> autre) {
        return new SpecificationCombinee.Ou<>(this, autre);
    }

    default Specification<T> ouX(@NonNull final Specification<? super T> autre) {
        return new SpecificationCombinee.OuX<>(this, autre);
    }

    default <U> Specification<U> transformer(@NonNull final Function<U, T> fonctionTransformation) {
        return (U u) -> this.estSatisfaitePar(fonctionTransformation.apply(u));
    }


}