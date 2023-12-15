package xyz.thingummy.oss.commons.validation;

import lombok.NonNull;
import xyz.thingummy.oss.commons.notification.Message;
import xyz.thingummy.oss.model.specification.Specification;
import xyz.thingummy.oss.model.specification.SpecificationCombinee;
import xyz.thingummy.oss.model.specification.Specifications;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static xyz.thingummy.oss.commons.validation.ValidationOperations.soit;


public abstract class Validation<T> implements Specification<T> {
    @NonNull
    protected Specification<T> specification;
    protected Message message;
    protected Message messageAdditionnel;

    public abstract Validation<T> sansMessage();

    public abstract Validation<T> avec(final Message message);

    public abstract  Validation<T> avec(final Message message, final Message messageAdditionnel);

    protected Validation(@NonNull final Specification<T> specification) {
        this(specification,null,null);
    }
    protected Validation(@NonNull final Specification<T> specification, final Message message, final Message messageAdditionnel) {
        this.specification=(specification instanceof Validation<T> validation) ? validation.specification : specification;;
        this.message=message;
        this.messageAdditionnel=messageAdditionnel;
    }


    public Optional<Message> getMessageAdditionnel() {return Optional.ofNullable(messageAdditionnel);}

    public Optional<Message> getMessage() {return Optional.ofNullable(message);}
    @Override
    public boolean test(T t) {
        return specification.test(t);
    }



   @Override
   public Validation<T> combiner(Specification<? super T> spec, @NonNull BinaryOperator<Boolean> operateur) {
        return  new ValidationCombinee<>(this, soit(spec), operateur, SpecificationCombinee.ShortCut.NONE,null,null);
   }

    @Override
    public Specifications.Specifications0 differer(T t) {
        return specification.differer(t);
    }

    @Override
    public boolean estSatisfaitePar(T t) {
        return specification.estSatisfaitePar(t);
    }

    @Override
    public Validation<T> etPas(@NonNull Specification<? super T> autre) {
        return etNon(autre);
    }

    @Override
    public Validation<T> etNon(@NonNull Specification<? super T> autre) {
        return this.et(autre.non());
    }

    @Override
    public Validation<T> et(@NonNull Specification<? super T> autre) {
        return new ValidationCombinee.Et<>(this, soit(autre));
    }

    @Override
    public Validation<T> non() {
        return new ValidationCombinee.Non<>(this);
    }

    @Override
    public Validation<T> combiner(Specification<? super T> spec2, @NonNull BinaryOperator<Boolean> operateur, @NonNull SpecificationCombinee.ShortCut shortcut) {
        return new ValidationCombinee<>(this, soit(spec2), operateur, shortcut, null, null);
    }

    @Override
    public Validation<T> ni(@NonNull Specification<? super T> autre) {
        return etNon(autre);
    }

    @Override
    public Validation<T> ou(@NonNull Specification<? super T> autre) {
        return new ValidationCombinee.Ou<>(this, soit(autre));
    }

    @Override
    public Validation<T> ouX(@NonNull Specification<? super T> autre) {
        return new ValidationCombinee.OuX<>(this, soit(autre));
    }

    @Override
    public <U> Validation<U> transformer(@NonNull Function<U, T> fonctionTransformation) {
        return soit(specification.transformer(fonctionTransformation), message, messageAdditionnel);
    }



}

