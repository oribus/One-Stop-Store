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


import lombok.*;
import xyz.thingummy.oss.commons.Localizable;
import xyz.thingummy.oss.model.ValueObject;

import java.util.Optional;

/**
 * Représente un message destiné à fournir des retours ou des informations sur des événements ou conditions spécifiques.
 * <p>
 * Un message encapsule une clé (potentiellement pour la localisation), une valeur par défaut optionnelle, et un type.
 * La clé est principalement utilisée pour récupérer des messages localisés à partir de ressources de localisation,
 * tandis que le message par défaut optionnel sert de solution de repli.
 * Le type de la notification indique sa nature et son importance.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Message implements ValueObject<Message>, Localizable {

    /**
     * La clé utilisée pour récupérer le message localisé. Cette clé est essentielle et ne peut pas être nulle.
     */
    @NonNull
    private final String key;

    /**
     * Un message par défaut optionnel fourni comme solution de repli si la clé n'est pas trouvée dans le paquet de ressources.
     */
    private final String defaultMessage;

    /**
     * Le type de la notification, indiquant sa nature et son importance.
     */
    @NonNull
    private final TypeMessage type;

    /**
     * Méthode de fabrique statique pour créer une nouvelle instance de Message avec la clé et le type spécifiés.
     *
     * @param key  La clé utilisée pour récupérer le message localisé.
     * @param type Le type de la notification.
     * @return Une nouvelle instance de Message.
     */
    public static Message of(String key, TypeMessage type) {
        return new Message(key, null, type);
    }

    /**
     * Méthode de fabrique statique pour créer une nouvelle instance de Message avec la clé spécifiée, une valeur par défaut, et un type.
     *
     * @param key          La clé utilisée pour récupérer le message localisé.
     * @param defaultValue Un message par défaut optionnel si la clé n'est pas trouvée.
     * @param type         Le type de la notification.
     * @return Une nouvelle instance de Message.
     */
    public static Message of(String key, String defaultValue, TypeMessage type) {
        return new Message(key, defaultValue, type);
    }

    @Override
    public Optional<String> getDefaultMessage() {
        return Optional.ofNullable(defaultMessage);
    }
}
