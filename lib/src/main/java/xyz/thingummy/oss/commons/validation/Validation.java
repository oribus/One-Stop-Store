package xyz.thingummy.oss.commons.validation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import xyz.thingummy.oss.commons.notification.CollecteurNotifications;
import xyz.thingummy.oss.commons.notification.Message;
import xyz.thingummy.oss.model.specification.Specification;
import xyz.thingummy.oss.model.specification.SpecificationCombinee;
import xyz.thingummy.oss.model.specification.Specifications0;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static xyz.thingummy.oss.commons.validation.ValidationCombinee.*;
import static xyz.thingummy.oss.commons.validation.ValidationOperations.soit;


@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Validation<T> implements Specification<T> {
    @NonNull
    @Getter
    protected Specification<T> specification;
    protected Message message;
    protected Message messageAdditionnel;

    public abstract Validation<T> avec(final Message message);

    public abstract Validation<T> avec(final Message message, final Message messageAdditionnel);

    public abstract Validation<T> sansMessage();

    @Override
    public boolean test(final T t) {
        return specification.test(t);
    }

    @Override
    public boolean tester(final T t) {
        return specification.tester(t);
    }

    @Override
    public Validation<T> combiner(final Specification<? super T> spec2, @NonNull final BinaryOperator<Boolean> operateur) {
        return combiner(spec2, operateur, SpecificationCombinee.ShortCut.NONE);
    }

    @Override
    public Specifications0 differer(final T t) {
        return specification.differer(t);
    }

    @Override
    public boolean estSatisfaitePar(final T t) {
        return specification.estSatisfaitePar(t);
    }

    public abstract boolean estSatisfaitePar(T t, CollecteurNotifications collecteur);

    @Override
    public Validation<T> etPas(@NonNull final Specification<? super T> autre) {
        return etNon(autre);
    }

    @Override
    public Validation<T> etNon(@NonNull final Specification<? super T> autre) {
        return et(autre.non());
    }

    @Override
    public Validation<T> et(@NonNull final Specification<? super T> autre) {
        return new Et<>(this, soit(autre));
    }

    @Override
    public Validation<T> non() {
        return new Non<>(this);
    }

    @Override
    public Validation<T> combiner(final Specification<? super T> spec2, @NonNull final BinaryOperator<Boolean> operateur,
                                  final SpecificationCombinee.@NonNull ShortCut shortCut) {
        return new ValidationCombinee<>(soit(this), soit(spec2), operateur, shortCut, null, null);
    }

    @Override
    public Validation<T> ni(@NonNull final Specification<? super T> autre) {
        return etNon(autre);
    }

    @Override
    public Validation<T> ou(@NonNull final Specification<? super T> autre) {
        return new Ou<>(this, soit(autre));
    }

    @Override
    public Validation<T> ouX(@NonNull final Specification<? super T> autre) {
        return new OuX<>(this, soit(autre));
    }

    @Override
    public <U> Validation<U> transformer(@NonNull final Function<U, T> fonctionTransformation) {
        return new ValidationSimple<>(specification.transformer(fonctionTransformation));
    }

    public Optional<Message> getMessage() {
        return Optional.ofNullable(message);
    }

    public Optional<Message> getMessageAdditionnel() {
        return Optional.ofNullable(messageAdditionnel);
    }

}

