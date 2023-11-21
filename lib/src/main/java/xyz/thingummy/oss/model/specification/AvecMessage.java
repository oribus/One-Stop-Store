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

import lombok.AllArgsConstructor;
import lombok.NonNull;
import xyz.thingummy.oss.commons.notification.CollecteurNotifications;
import xyz.thingummy.oss.commons.notification.Message;

import java.util.Optional;

@AllArgsConstructor
class AvecMessage<T> implements Specification<T> {
    private final Specification<T> specification;

    @NonNull
    private final Message message;
    private final Message messageAdditionnel;

    @Override
    public boolean estSatisfaitePar(final T t, @NonNull final CollecteurNotifications c) {
        final boolean estSatisfaite = specification.estSatisfaitePar(t, c);
        getMessage().ifPresent(m -> c.ajouter(!estSatisfaite, m, this, t));
        return estSatisfaite;
    }

    @Override
    public Optional<Message> getMessage() {
        return Optional.of(message);
    }

    @Override
    public Optional<Message> getMessageAdditionnel() {
        return Optional.of(messageAdditionnel);
    }

    @Override
    public boolean test(final T t) {
        return specification.test(t);
    }
}