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
import lombok.NonNull;
import xyz.thingummy.oss.commons.Localizable;

import java.util.Optional;

/**
 * Énumération représentant les différents types de messages avec des propriétés de localisation.
 * <p>
 * Chaque type dans cette énumération correspond à un type spécifique de notification, allant de simples confirmations à des alertes critiques.
 * Le but principal de ces types est de catégoriser les messages en fonction de leur nature et de leur importance.
 * </p>
 * <p>
 * De plus, chaque type possède des propriétés de localisation associées :
 * <ul>
 *     <li><b>key:</b> Un identifiant unique utilisé pour récupérer le message localisé pour ce type à partir de ressources de localisation.</li>
 *     <li><b>defaultMessage:</b> Un message par défaut optionnel qui peut être utilisé comme solution de repli lorsque la clé ne permet pas d'obtenir un message localisé.</li>
 * </ul>
 * Ces propriétés garantissent que chaque type peut être présenté de manière localisée, répondant à différentes langues ou préférences régionales.
 * </p>
 */

@AllArgsConstructor
@Getter
public enum TypeMessage implements Localizable {
    CONFIRMATION("confirmation.key", "Message de confirmation par défaut"),
    INFORMATION("information.key", "Message d'information par défaut"),
    AVERTISSEMENT("avertissement.key", "Message d'avertissement par défaut"),
    ERREUR("erreur.key", "Message d'erreur par défaut"),
    CRITIQUE("critique.key", "Message critique par défaut");

    @NonNull
    private final String key;
    private final String defaultMessage;

    TypeMessage(String key) {
        this(key, null);
    }

    @Override
    public Optional<String> getDefaultMessage() {
        return Optional.ofNullable(defaultMessage);
    }

}

