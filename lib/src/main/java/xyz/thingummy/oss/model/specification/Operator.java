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
import lombok.NonNull;
import xyz.thingummy.oss.commons.notification.CollecteurNotifications;

import java.util.function.BinaryOperator;

@AllArgsConstructor
abstract class Operator<T> implements Specification<T> {
    private final Specification<T> spec1;
    private final Specification<? super T> spec2;
    private final BinaryOperator<Boolean> operator;
    private final ShortCut shortCut;

    @Override
    public boolean estSatisfaitePar(T t, @NonNull CollecteurNotifications c) {
        final boolean left = spec1.estSatisfaitePar(t, c);
        return switch (shortCut) {
            case AND -> (left) ? operator.apply(true, spec2.estSatisfaitePar(t, c)) : false;
            case OR -> (!left) ? operator.apply(false, spec2.estSatisfaitePar(t, c)) : true;
            case UNARY -> operator.apply(left, null);
            case NONE -> {
                final boolean right = spec2.estSatisfaitePar(t, c);
                yield operator.apply(left, right);
            }
        };
    }

    public boolean test(T t) {
        return operator.apply(spec1.estSatisfaitePar(t), spec2.estSatisfaitePar(t));
    }

    protected enum ShortCut {
        AND, OR, NONE, UNARY
    }


}