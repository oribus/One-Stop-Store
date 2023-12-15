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

//package xyz.thingummy.oss.commons.validation;
//
//import lombok.AllArgsConstructor;
//import lombok.NonNull;
//import xyz.thingummy.oss.commons.notification.CollecteurNotifications;
//import xyz.thingummy.oss.commons.notification.Message;
//import xyz.thingummy.oss.model.specification.Specification;
//
//import java.util.Optional;
//
//@AllArgsConstructor
//public class Validation_<T> implements Specification<T> {
//    private final Specification<T> specification;
//
//    @NonNull
//    private final Message message;
//    private final Message messageAdditionnel;
//
//    static <T> Validation_<T> avec(Specification<T> specification, final Message m) {
//        return new Validation_<>(specification, m, null);
//    }
//
//    static <T> Validation_<T> avec(Specification<T> specification, final Message message, final Message messageAdditionnel) {
//        return new Validation_<>(specification, message, messageAdditionnel);
//    }
//
//    public Validation_<T> avec(final Message m) {
//        return new Validation_<>(specification, m, null);
//    }
//
//    public Validation_<T> avec(final Message message, final Message messageAdditionnel) {
//        return new Validation_<>(specification, message, messageAdditionnel);
//    }
//
//
//    @Override
//    public boolean estSatisfaitePar(final T t, @NonNull final CollecteurNotifications c) {
//        final boolean estSatisfaite = specification.estSatisfaitePar(t, c);
//        getMessage().ifPresent(m -> c.ajouter(!estSatisfaite, m, this, t));
//        return estSatisfaite;
//    }
//
//    @Override
//    public Optional<Message> getMessage() {
//        return Optional.ofNullable(message);
//    }
//
//    @Override
//    public Optional<Message> getMessageAdditionnel() {
//        return Optional.ofNullable(messageAdditionnel);
//    }
//
//    @Override
//    public Specification<T> sansMessage() {
//        return specification;
//    }
//
//    @Override
//    public boolean test(final T t) {
//        return specification.test(t);
//    }
//
//    default boolean estSatisfaitePar(final T t, @NonNull final CollecteurNotifications c) {
//        final boolean estSatisfaite = estSatisfaitePar(t);
//        getMessage().ifPresent(m -> c.ajouter(!estSatisfaite, m, this, t));
//        return estSatisfaite;
//    }
//}