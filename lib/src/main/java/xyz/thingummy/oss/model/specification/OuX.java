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

class OuX<T> extends SpecificationCombinee<T> {
    boolean excl;

    public OuX(@NonNull final Specification<T> spec1, @NonNull final Specification<? super T> spec2) {
        super(spec1.ou(spec2), (spec1.et(spec2)).non(), (b1, b2) -> b1 && b2, ShortCut.AND);
    }

    @Override
    public Specification<T> avec(@NonNull final Message message, @NonNull final Message messageAdditionnel) {
        return spec1.avec(message).et(spec2.avec(messageAdditionnel));
    }
}