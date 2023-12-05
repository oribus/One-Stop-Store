package xyz.thingummy.oss.commons.validation;

import lombok.NonNull;
import xyz.thingummy.oss.commons.notification.Message;
import xyz.thingummy.oss.model.specification.Specification;
import xyz.thingummy.oss.model.specification.SpecificationCombinee;
import xyz.thingummy.oss.model.specification.Specifications;

import java.util.function.BinaryOperator;
import java.util.function.Function;


public interface Validation<T> extends Specification<T> {
    /*  protected Validation(@NonNull Specification<T> specification, final Message message, final Message messageAdditionnel) {
          if (specification instanceof Validation<T> validation) {
              this.specification = validation.specification;
          } else {
              this.specification = specification;
          }
          this.message = message;
          this.messageAdditionnel = messageAdditionnel;
      } */

    static <T> Validation<T> soit(@NonNull Specification<T> specification) {
        return soit(specification, null, null);
    }

    static <T> Validation<T> soit(@NonNull Validation<T> validation) {
        return validation;
    }

    static <T> Validation<T> soit(@NonNull Specification<T> specification, final Message message) {
        return soit(specification, message, null);
    }

    static <T> Validation<T> soit(@NonNull Specification<T> specification, final Message message, final Message messageAdditionnel) {
        Specification<T> specificationSimple = (specification instanceof Validation<T> validation) ? validation.getSpecification() : specification;
        return new Validation<T>() {
            public Specification<T> getSpecification() {
                return specificationSimple;
            }

            public Message getMessage() {
                return message;
            }

            public Message getMessageAdditionnel() {
                return messageAdditionnel;
            }

        };
    }

    Message getMessageAdditionnel();

    Specification<T> getSpecification();

    Message getMessage();

    @Override
    default boolean test(T t) {
        return getSpecification().test(t);
    }

    default Validation<T> avec(final Message m) {
        return soit(getSpecification(), m, null);
    }

    default Validation<T> avec(final Message message, final Message messageAdditionnel) {
        return soit(getSpecification(), message, messageAdditionnel);
    }

   /* @Override
    public Validation<T> combiner(Specification<? super T> spec, @NonNull BinaryOperator<Boolean> operateur) {
        return soit(this, soit(spec), operateur, SpecificationCombinee.ShortCut.NONE);
    }*/

    @Override
    default Specifications.Specifications0 differer(T t) {
        return getSpecification().differer(t);
    }

    @Override
    default boolean estSatisfaitePar(T t) {
        return getSpecification().estSatisfaitePar(t);
    }

    @Override
    default Validation<T> etPas(@NonNull Specification<? super T> autre) {
        return etNon(autre);
    }

    @Override
    default Validation<T> etNon(@NonNull Specification<? super T> autre) {
        return this.et(autre.non());
    }

    @Override
    default Validation<T> et(@NonNull Specification<? super T> autre) {
        return new ValidationCombinee.Et<>(this, soit(autre));
    }

    @Override
    default Validation<T> non() {
        return new ValidationCombinee.Non<>(this);
    }

    @Override
    default Validation<T> combiner(Specification<? super T> spec2, @NonNull BinaryOperator<Boolean> operateur, @NonNull SpecificationCombinee.ShortCut shortCut) {
        return new ValidationCombinee<>(this, soit(spec2), operateur, shortcut, null, null);
    }

    @Override
    default Validation<T> ni(@NonNull Specification<? super T> autre) {
        return etNon(autre);
    }

    @Override
    default Validation<T> ou(@NonNull Specification<? super T> autre) {
        return new ValidationCombinee.Ou<>(this, soit(autre));
    }

    @Override
    default Validation<T> ouX(@NonNull Specification<? super T> autre) {
        return new ValidationCombinee.OuX<>(this, soit(autre));
    }

    @Override
    default <U> Validation<U> transformer(@NonNull Function<U, T> fonctionTransformation) {
        return soit(getSpecification().transformer(fonctionTransformation), getMessage(), getMessageAdditionnel());
    }

    default Validation<T> sansMessage() {
        return soit(getSpecification(), null, null);
    }

}

