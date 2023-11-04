/*
 * Copyright (c) 2023, Jérôme ROBERT
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package xyz.thingummy.oss.commons.notification;


import lombok.*;
import xyz.thingummy.oss.model.ValueObject;
import xyz.thingummy.oss.commons.Localizable;
import xyz.thingummy.oss.model.ValueObject;

import java.util.Optional;

/**
 * Represents a notification intended to provide feedback or information about specific events or conditions.
 * <p>
 * A notification encapsulates a key (potentially for localization), an optional default message, and the type of the notification.
 * The key is primarily used to retrieve localized messages from resource bundles, while the optional default message serves as a fallback.
 * The type of the notification indicates its nature and importance.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Notification implements ValueObject<Notification>, Localizable {

   /**
     * The key used to retrieve the localized message. This key is essential and cannot be null.
     */
    @NonNull
    private final String key;

 /**
     * An optional default message provided as a fallback if the key isn't found in the resource bundle.
     */
    private final String defaultMessage;

    /**
     * The type of the notification, indicating its nature and importance.
     */
    @NonNull
    private final TypeNotification type;

    /**
     * Static factory method to create a new Notification instance with the specified key and type.
     *
     * @param key  The key used to retrieve the localized message.
     * @param type The type of the notification.
     * @return A new Notification instance.
     */
    public static Notification of(String key, TypeNotification type) {
        return new Notification(key, null, type);
    }

    /**
     * Static factory method to create a new Notification instance with the specified key, default value, and type.
     *
     * @param key          The key used to retrieve the localized message.
     * @param defaultValue An optional default message if the key isn't found.
     * @param type         The type of the notification.
     * @return A new Notification instance.
     */
    public static Notification of(String key, String defaultValue, TypeNotification type) {
        return new Notification(key, defaultValue, type);
    }

 public Optional<String> getDefaultMessage() {
  return Optional.of(defaultMessage);
 }
}