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
package xyz.thingummy.oss.commons.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Représente une notification, conçue pour exprimer le fait qu'une source souhaite communiquer
 * une information relative à un traitement qu'elle effectue. Une notification encapsule donc un message,
 * la source de ce message, et une référence. La signification de la référence peut varier selon le contexte. Toutefois,
 * elle est conceptuellement prévue pour indiquer l'objet ou l'entité sur lequel le traitement a eu lieu.
 * <p>
 * Un tag (étiquette) peut également être associé à la notification pour fournir des informations supplémentaires
 * ou pour catégoriser la notification.
 *
 * @param <T> Le type de la référence associée à la notification.
 * @param <S> Le type de la source ayant généré la notification.
 */
@Getter
@AllArgsConstructor
public class Notification<T, S> {
    private Message message;
    private S source;
    private T reference;
    private String tag;

    /**
     * Constructeur pour créer une notification sans tag.
     *
     * @param message   Le message de la notification.
     * @param source    La source de la notification.
     * @param reference La référence associée à la notification.
     */
    public Notification(Message message, S source, T reference) {
        this(message, source, reference, null);
    }

    public @lombok.NonNull TypeMessage getTypeMessage() {
        return this.message.getType();
    }
}
