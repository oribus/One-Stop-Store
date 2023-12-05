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

package xyz.thingummy.oss.commons.validation;

import lombok.Getter;
import lombok.NonNull;
import xyz.thingummy.oss.commons.notification.CollecteurNotifications;
import xyz.thingummy.oss.commons.notification.Message;
import xyz.thingummy.oss.model.specification.Specification;
import xyz.thingummy.oss.model.specification.SpecificationCombinee;

import java.util.function.BinaryOperator;

import static xyz.thingummy.oss.commons.validation.Validation.soit;
import static xyz.thingummy.oss.model.specification.Specifications.toujoursVrai;

@Getter
public class ValidationCombinee<T> implements Validation<T> {

    @NonNull
    protected final Specification<T> specification;
    @NonNull
    protected final Validation<T> valid1;
    @NonNull
    protected final Validation<? super T> valid2;
    @NonNull
    protected final BinaryOperator<Boolean> operator;
    @NonNull
    protected final SpecificationCombinee.ShortCut shortCut;

    protected final Message message;

    protected final Message messageAdditionnel;

    public ValidationCombinee(@NonNull Validation<T> valid1, @NonNull Validation<? super T> valid2,
                              @NonNull BinaryOperator<Boolean> operator,
                              @NonNull SpecificationCombinee.ShortCut shortCut,
                              Message message, Message messageAdditionnel) {
        this.valid1 = valid1;
        this.valid2 = valid2;
        this.operator = operator;
        this.shortCut = shortCut;
        this.message = message;
        this.messageAdditionnel = messageAdditionnel;
        this.specification = new SpecificationCombinee<>(valid1, valid2, operator, shortCut);
    }

    @Override
    public boolean estSatisfaitePar(final T t, final CollecteurNotifications c) {
        final CollecteurNotifications c1 = new CollecteurNotifications();
        final boolean left = valid1.estSatisfaitePar(t, c1);
        final boolean estSatisfaite = switch (shortCut) {
            case AND -> (left) ? operator.apply(true, valid2.estSatisfaitePar(t, c1)) : false;
            case OR -> (!left) ? operator.apply(false, valid2.estSatisfaitePar(t, c1)) : true;
            case UNARY -> operator.apply(left, null);
            case NONE -> {
                final boolean right = valid2.estSatisfaitePar(t, c);
                yield operator.apply(left, right);
            }
        };
        if (null != c && !estSatisfaite) c.ajouterTout(c1);
        return estSatisfaite;
    }

    public boolean test(final T t) {
        return estSatisfaitePar(t, null);
    }

    static class Et<T> extends ValidationCombinee<T> {

        public Et(@NonNull final Validation<T> valid1, @NonNull final Validation<? super T> valid2) {
            super(valid1, valid2, (b1, b2) -> b1 && b2, SpecificationCombinee.ShortCut.AND, null, null);
        }
    }

    static class Non<T> extends ValidationCombinee<T> {
        boolean excl;

        public Non(@NonNull final Validation<T> valid) {
            super(valid, soit(toujoursVrai), (b1, b2) -> !b1, SpecificationCombinee.ShortCut.UNARY);
        }

    }

    static class Ou<T> extends ValidationCombinee<T> {
        boolean excl;

        public Ou(@NonNull final Validation<T> valid1, @NonNull final Validation<? super T> valid2) {
            super(valid1, valid2, (b1, b2) -> b1 || b2, SpecificationCombinee.ShortCut.OR);
        }

    }

    static class OuX<T> extends ValidationCombinee<T> {

        public OuX(@NonNull final Validation<T> valid1, @NonNull final Validation<? super T> valid2) {
            super(valid1.ou(valid2), (valid1.et(valid2)).non(), (b1, b2) -> b1 && b2, SpecificationCombinee.ShortCut.AND);
        }

        @Override
        public Validation<T> avec(@NonNull final Message message, @NonNull final Message messageAdditionnel) {
            return valid1.avec(message).et(valid2.avec(messageAdditionnel));
        }
    }

}