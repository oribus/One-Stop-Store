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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Représente un ensemble de notifications pouvant résulter d'un processus quelconque,
 * par exemple un processus de validation.
 */
public class CollecteurNotifications {

    public static final Predicate<Notification<?, ?>> CRITIQUE = n -> n.getTypeMessage() == TypeMessage.CRITIQUE;
    public static final Predicate<Notification<?, ?>> ERREUR = n -> n.getTypeMessage() == TypeMessage.ERREUR;
    public static final Predicate<Notification<?, ?>> AVERTISSEMENT = n -> n.getTypeMessage() == TypeMessage.AVERTISSEMENT;
    public static final Predicate<Notification<?, ?>> INFORMATION = n -> n.getTypeMessage() == TypeMessage.INFORMATION;
    public static final Predicate<Notification<?, ?>> CONFIRMATION = n -> n.getTypeMessage() == TypeMessage.CONFIRMATION;
    private final List<Notification<?, ?>> notifications = Collections.synchronizedList(new ArrayList<>());


    public static Predicate<Notification<?, ?>> contientMessage(final Message message) {
        return n -> n.getMessage().equals(message);
    }

    public static Predicate<Notification<?, ?>> contientMessage(final String message) {
        return n -> n.getMessage().getKey().equals(message);
    }

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
    public <T, S> void ajouter(final boolean condition, final Message message, final S source, final T reference) {
        if (condition && null != message) {
            notifications.add(new Notification<>(message, source, reference));
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
    public <T, S> void ajouter(final BooleanSupplier condition, final Message message, final S source, final T reference) {
        ajouter(!condition.getAsBoolean(), message, source, reference);
    }

    public List<Notification<?, ?>> filtrer(final Predicate<Notification<?, ?>> critere) {
        return notifications.stream().filter(critere).collect(Collectors.toList());
    }

    /**
     * Vérifie si le collecteur a des entrées de notification.
     *
     * @return Vrai s'il y a des entrées de notification, faux sinon.
     */
    public boolean hasNotifications() {
        return !notifications.isEmpty();
    }

    /**
     * Efface toutes les entrées de notification du collecteur.
     */
    public void vider() {
        notifications.clear();
    }

    public void ajouterTout(final boolean condition, @NonNull final CollecteurNotifications c1) {
        ajouterTout(condition, c1.getNotifications());
    }

    private void ajouterTout(final boolean condition, @NonNull final List<Notification<?, ?>> entries) {
        if (condition) {
            this.notifications.addAll(entries);
        }
    }

    /**
     * Récupère une liste non modifiable de toutes les entrées de notification collectées.
     *
     * @return Une liste non modifiable d'entrées de notification.
     */
    public List<Notification<?, ?>> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }

    public Map<TypeMessage, List<Notification<?, ?>>> getNotificationsParTypeMessage() {
        return notifications.stream()
                .collect(Collectors.groupingBy(Notification::getTypeMessage));
    }

    public Map<TypeMessage, Long> denombrerNotifications() {
        return notifications.stream()
                .collect(Collectors.groupingBy(Notification::getTypeMessage, Collectors.counting()));
    }

    public long denombrerNotifications(final Predicate<Notification<?, ?>> critere) {
        return notifications.stream()
                .filter(critere).count();
    }

}
