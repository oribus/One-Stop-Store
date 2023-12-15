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

import lombok.NonNull;
import xyz.thingummy.oss.commons.notification.CollecteurNotifications;
import xyz.thingummy.oss.commons.notification.Message;
import xyz.thingummy.oss.model.specification.Specification;

class ValidationSimple<T> extends Validation<T> {

    protected ValidationSimple(@NonNull final Specification<T> specification, final Message message, final Message messageAdditionnel) {
        super(specification, message, messageAdditionnel);
    }

    protected ValidationSimple(@NonNull final Specification<T> specification) {
        this(specification, null, null);
    }

    @Override
    public Validation<T> avec(final Message message) {
        return new ValidationSimple<>(specification, message, null);
    }

    @Override
    public Validation<T> avec(final Message message, final Message messageAdditionnel) {
        return new ValidationSimple<>(specification, message, messageAdditionnel);
    }

    @Override
    public Validation<T> sansMessage() {
        return new ValidationSimple<>(specification, null, null);
    }

    public boolean estSatisfaitePar(final T t, final CollecteurNotifications collecteur) {
        final boolean estSatisfaite = specification.estSatisfaitePar(t);
        if (!estSatisfaite && null != message) {
            collecteur.ajouter(message, this, t);
        }
        return estSatisfaite;
    }
}