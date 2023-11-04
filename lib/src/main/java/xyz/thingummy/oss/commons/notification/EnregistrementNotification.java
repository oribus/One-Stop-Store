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
package xyz.thingummy.oss.commons.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Représente un enregistrement de notification.
 * <p>
 * Chaque enregistrement encapsule une Notification, la source qui l'a générée, et la référence associée.
 * </p>
 *
 * @param <T> Le type de la référence.
 * @param <S> Le type de la source.
 */
@Getter
@AllArgsConstructor
public class EnregistrementNotification<T, S> {
    private Notification notification;
    private S source;
    private T reference;
    private String tag;

    public EnregistrementNotification(Notification notification, S source, T reference) {
        this(notification, source, reference, null);
    }
}