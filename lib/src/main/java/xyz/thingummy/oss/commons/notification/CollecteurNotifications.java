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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Représente un ensemble de notifications pouvant résulter d'un processus de validation.
 */
public class CollecteurNotifications {

    private final List<EnregistrementNotification<?, ?>> entries = new ArrayList<>();

    /**
     * Ajoute une entrée de notification au collecteur.
     *
     * @param notification La notification à ajouter.
     * @param source       La source qui a généré la notification.
     * @param reference    L'objet qui était en cours d'évaluation.
     */
    public <T, S> void ajouter(Notification notification, S source, T reference) {
        entries.add(new EnregistrementNotification<>(notification, source, reference));
    }

    /**
     * Récupère une liste non modifiable de toutes les entrées de notification collectées.
     *
     * @return Une liste non modifiable d'entrées de notification.
     */
    List<EnregistrementNotification<?, ?>> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Vérifie si le collecteur a des entrées de notification.
     *
     * @return Vrai s'il y a des entrées de notification, faux sinon.
     */
    public boolean hasNotifications() {
        return !entries.isEmpty();
    }

    /**
     * Efface toutes les entrées de notification du collecteur.
     */
    public void vider() {
        entries.clear();
    }
}





