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
import xyz.thingummy.oss.commons.notification.Message;

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;

public class SpecificationOperations {

    public static <T> Specification<T> soit(@NonNull final Specification<T> specification) {
        return specification;
    }

    public static <T, U> Specification<T> soit( @NonNull final Predicate<U> predicat,@NonNull final Function<T, U> extracteur) {
        return soit(predicat).transformer(extracteur);
    }

    public static <T> Specification<T> soit(@NonNull final Predicate<T> predicat) {
        return (predicat instanceof Specification) ? (Specification<T>) predicat : predicat::test;
    }

    public static <T, U> Specification<T> soit(final Specification<U> specification,@NonNull final Function<T, U> extracteur) {
        return specification.transformer(extracteur);
    }

    public static <T> Specification<T> combiner(@NonNull final Specification<T> spec1, final Specification<T> spec2, @NonNull final BinaryOperator<Boolean> operateur) {
        return spec1.combiner(spec2, operateur);
    }

    @SafeVarargs
    public static <T> Specification<T> et(@NonNull final Predicate<T>... predicates) {
        return Arrays.stream(predicates)
                .reduce(Predicate::and)
                .map(SpecificationOperations::soit)
                .orElse(t -> true);
    }


    @SafeVarargs
    public static <T> Specification<T> ou(@NonNull final Predicate<T>... predicates) {
        return Arrays.stream(predicates)
                .reduce(Predicate::or)
                .map(SpecificationOperations::soit)
                .orElse(t -> false);
    }

    public static <T> Specification<T> pas(@NonNull final Predicate<T> predicat) {
        return non(predicat);
    }

    public static <T> Specification<T> non(@NonNull final Predicate<T> predicat) {

        return soit(predicat).non();
    }

    public static <T> Specification<T> si(@NonNull final BooleanSupplier condition, @NonNull final Specification<? super T> siVrai, @NonNull final Specification<? super T> siFaux) {
        return (t) -> condition.getAsBoolean() ? siVrai.estSatisfaitePar(t) : siFaux.estSatisfaitePar(t);
    }


}
