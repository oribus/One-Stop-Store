/*
 * Copyright (c) 2023, Jérôme ROBERT
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package xyz.thingummy.oss.commons.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import xyz.thingummy.oss.commons.Localizable;

import java.util.Optional;

/**
 * Enumeration representing the different types of notifications with localization properties.
 * <p>
 * Each type in this enumeration corresponds to a specific kind of notification, ranging from simple confirmations to critical alerts.
 * The primary purpose of these types is to categorize notifications based on their nature and importance.
 * </p>
 * <p>
 * Additionally, each type has associated localization properties:
 * <ul>
 *     <li><b>key:</b> A unique identifier used to retrieve the localized message for this type from resource bundles.</li>
 *     <li><b>defaultMessage:</b> An optional default message that can be used as a fallback when the key doesn't yield a localized message.</li>
 * </ul>
 * These properties ensure that each type can be presented in a localized manner, catering to different languages or regional preferences.
 * </p>
 */

@AllArgsConstructor
@Getter
public enum TypeNotification implements Localizable {
    CONFIRMATION("confirmation.key", "Default confirmation message"),
    INFORMATION("information.key", "Default informational message"),
    AVERTISSEMENT("avertissement.key", "Default warning message"),
    ERREUR("erreur.key", "Default error message"),
    CRITIQUE("critique.key", "Default critical message");

    @NonNull
    private final String key;
    private final String defaultMessage;

    TypeNotification(String key) {
        this(key, null);
    }

    @Override
    public Optional<String> getDefaultMessage() {
        return Optional.ofNullable(defaultMessage);
    }

}
