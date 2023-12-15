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

import lombok.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Représente un ensemble de notifications pouvant résulter d'un processus quelconque,
 * par exemple un processus de validation.
 */
public class CollecteurNotifications {
    private final Set<Notification<?, ?>> notifications = ConcurrentHashMap.newKeySet();


    /**
     * Ajoute une entrée de notification au collecteur.
     *
     * @param message   Le message associé à la notification à ajouter.
     * @param source    La source qui a généré la notification.
     * @param reference L'objet qui était en cours d'évaluation.
     * @param <T>       Le type de l'objet de référence.
     * @param <S>       Le type de la source de la notification.
     */
    public <T, S> void ajouter(final Message message, final S source, final T reference) {
        if (null != message) {
            notifications.add(new Notification<>(message, source, reference));
        }
    }

    /**
     * Filtre les notifications selon un critère donné.
     *
     * @param critere Le critère de filtrage des notifications.
     * @return Une liste de notifications correspondant au critère.
     */
    public Set<Notification<?, ?>> filtrer(final Predicate<Notification<?, ?>> critere) {
        return notifications.stream().filter(critere).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Vérifie si le collecteur a des entrées de notification.
     *
     * @return Vrai s'il y a des entrées de notification, faux sinon.
     */
    public boolean existeNotifications() {
        return !notifications.isEmpty();
    }

    /**
     * Efface toutes les entrées de notification du collecteur.
     */
    public void vider() {
        notifications.clear();
    }

    /**
     * Ajoute toutes les notifications d'un autre collecteur.
     *
     * @param c1        Le collecteur dont les notifications sont à ajouter.
     */
    public void ajouterTout(@NonNull final CollecteurNotifications c1) {
        this.notifications.addAll(c1.notifications);
    }


    /**
     * Récupère une liste non modifiable de toutes les entrées de notification collectées.
     *
     * @return Une liste non modifiable d'entrées de notification.
     */
    public Set<Notification<?, ?>> getNotifications() {
        return Collections.unmodifiableSet(notifications);
    }

    public Map<TypeMessage, List<Notification<?, ?>>> getNotificationsParTypeMessage() {
        return notifications.stream()
                .collect(Collectors.groupingBy(n -> n.getMessage().getType()));
    }

    public Map<TypeMessage, Long> denombrerNotifications() {
        return notifications.stream()
                .collect(Collectors.groupingBy(n -> n.getMessage().getType(), Collectors.counting()));
    }

    public long denombrerNotifications(final Predicate<Notification<?, ?>> critere) {
        return notifications.stream()
                .filter(critere).count();
    }

}
