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
import java.util.function.BooleanSupplier;

/**
 * Représente un ensemble de notifications pouvant résulter d'un processus quelconque,
 * par exemple un processus de validation.
 */
public class CollecteurNotifications {

    private final List<Notification<?, ?>> entries = new ArrayList<>();

    /**
     * Ajoute une entrée de notification au collecteur.
     *
     * @param message   Le message associé à la notification à ajouter.
     * @param source    La source qui a généré la notification.
     * @param reference L'objet qui était en cours d'évaluation.
     * @param <T>       Le type de l'objet de référence.
     * @param <S>       Le type de la source de la notification.
     */
    public <T, S> void ajouter(Message message, S source, T reference) {
        ajouter(true, message, source, reference);
    }

    /**
     * Ajoute une entrée de notification au collecteur si la condition est vraie.
     *
     * @param condition La condition déterminant si la notification doit être ajoutée.
     * @param message   Le message associé à la notification à ajouter.
     * @param source    La source qui a généré la notification.
     * @param reference L'objet qui était en cours d'évaluation.
     * @param <T>       Le type de l'objet de référence.
     * @param <S>       Le type de la source de la notification.
     */
    public <T, S> void ajouter(boolean condition, Message message, S source, T reference) {
        if (condition && null != message) {
            entries.add(new Notification<>(message, source, reference));
        }
    }

    /**
     * Ajoute une entrée de notification au collecteur si la condition fournie est vraie.
     *
     * @param condition Un fournisseur de condition qui doit être évalué.
     * @param message   Le message associé à la notification à ajouter.
     * @param source    La source qui a généré la notification.
     * @param reference L'objet qui était en cours d'évaluation.
     * @param <T>       Le type de l'objet de référence.
     * @param <S>       Le type de la source de la notification.
     */
    public <T, S> void ajouter(BooleanSupplier condition, Message message, S source, T reference) {
        ajouter(!condition.getAsBoolean(), message, source, reference);
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

    public void ajouterTout(boolean res, CollecteurNotifications c1) {
        ajouter(res, c1.getEntries());
    }

    private void ajouter(boolean res, List<Notification<?, ?>> entries) {
        this.entries.addAll(entries);
    }

    /**
     * Récupère une liste non modifiable de toutes les entrées de notification collectées.
     *
     * @return Une liste non modifiable d'entrées de notification.
     */
    public List<Notification<?, ?>> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
