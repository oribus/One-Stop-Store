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

package xyz.thingummy.oss.model.specification;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interface Specification - représente une spécification dans le contexte de la conception pilotée par le domaine (DDD).
 * C'est une extension fonctionnelle de l'interface Predicat qui permet de construire des règles métier complexes
 * de manière expressive et lisible. Elle fournit des méthodes pour composer des spécifications à l'aide d'opérations
 * logiques telles que ET, OU et NON.
 *
 * @param <T> le type de l'entité sur laquelle la spécification est évaluée
 */
@FunctionalInterface
public interface Specification<T> extends Predicat<T> {


    // Synonyme pour la méthode statique 'non'
    static <T> Specification<T> pas(Predicate<? super T> predicat) {
        return non(predicat);
    }

    /**
     * Crée une spécification qui est la négation d'un prédicat donnée.
     * La spécification résultante est satisfaite si le prédicat donné est contredit.
     *
     * @param predicat Le prédicat à contredire.
     * @return Une nouvelle spécification qui est la négation du prédicat donnée.
     */
    static <T> Specification<T> non(Predicate<? super T> predicat) {
        return predicat.negate()::test;
    }

    // Utiliser 'soit' comme un nom de méthode pour commencer une spécification
    static <T> Specification<T> soit(Predicate<T> predicat) {
        return predicat::test;
    }

    static <T, U> Specification<T> soit(Function<T, U> extracteur, Predicate<U> predicat) {
        return (t) -> predicat.test(extracteur.apply(t));
    }

    /**
     * Crée une spécification qui est la conjonction de plusieurs spécifications données.
     * La spécification résultante est satisfaite uniquement si toutes les spécifications données sont satisfaits.
     *
     * @param predicates Les prédicats à combiner.
     * @return Une nouvelle spécification qui est la conjonction de toutes les spécifications données.
     */
    @SafeVarargs
    static <T> Specification<T> et(Predicate<T>... predicates) {
        return (t) -> Arrays.stream(predicates).allMatch(p -> p.test(t));
    }

    /**
     * Crée une spécification qui est la disjonction de plusieurs spécifications données.
     * La spécification résultante est satisfaite si au moins une des spécifications données est satisfaite.
     *
     * @param predicates Les prédicats à combiner.
     * @return Une nouvelle spécification qui est la disjonction de toutes les spécifications données.
     */
    @SafeVarargs
    static <T> Specification<T> ou(Predicate<T>... predicates) {
        return (t) -> Arrays.stream(predicates).anyMatch(p -> p.test(t));
    }

    /**
     * Combine cette spécification avec un prédicat (au sens large, il peut s'agir d'un Predicate, d'un Predicat
     * ou d'une Specification) en utilisant l'opération logique OU EXCLUSIF (XOR).
     * La spécification résultante est satisfaite si exactement l'un ou l'autre de la spécification ou du prédicat donné
     * est satisfaits (mais pas les deux en même temps).
     *
     * @param autre Le deuxième prédicat à combiner.
     * @return Une nouvelle spécification qui est la disjonction exclusive de cette spécification et du prédicat donné.
     */
    default Specification<T> ouEx(Specification<T> autre) {
        return this.et(autre.non()).ou(this.non().et(autre));
    }

    /**
     * Combine cette spécification avec un prédicat (au sens large, il peut s'agir d'un Predicate, d'un Predicat
     * ou d'une Specification) en utilisant l'opération logique ET.
     * La spécification résultante est satisfaite uniquement si les deux prédicats sont satisfaits.
     *
     * @param autre Le deuxième prédicat à combiner.
     * @return Une nouvelle spécification qui est la conjonction de cette spécification et du prédicat donné.
     */
    default Specification<T> et(Predicate<? super T> autre) {
        return Predicat.super.et(autre)::test;
    }

    /**
     * Combine cette spécification avec un prédicat (au sens large, il peut s'agir d'un Predicate, d'un Predicat
     * ou d'une Specification) en utilisant l'opération logique OU.
     * La spécification résultante est satisfaite si l'une ou l'autre des spécifications est satisfaite.
     *
     * @param autre Le deuxième prédicat à combiner.
     * @return Une nouvelle spécification qui est la disjonction de cette spécification et du prédicat donné.
     */
    default Specification<T> ou(Predicate<? super T> autre) {
        return Predicat.super.ou(autre)::test;
    }

    /**
     * Évalue si cette spécification est satisfaite par l'entité donnée.
     *
     * @param t L'entité à évaluer.
     * @return true si l'entité satisfait la spécification, false sinon.
     */
    default SpecificationDifferee differer(T t) {
        return (Void) -> estSatisfaitePar(t);
    }

    /**
     * Évalue si cette spécification est satisfaite par l'entité donnée.
     *
     * @param t L'entité à évaluer.
     * @return true si l'entité satisfait la spécification, false sinon.
     */
    default boolean estSatisfaitePar(T t) {
        return tester(t);
    }

    // Synonyme pour 'etNon'
    default Specification<T> etPas(Specification<? super T> autre) {
        return etNon(autre);
    }

    // Combinateur personnalisé exemple
    default Specification<T> etNon(Specification<? super T> autre) {
        return (t) -> this.test(t) && !autre.test(t);
    }

    // Un autre synonyme pour 'etNon'
    default Specification<T> ni(Specification<? super T> autre) {
        return etNon(autre);
    }

    // Aide au débogage exemple
    default Specification<T> debuguer(String etiquette) {
        return (t) -> {
            boolean resultat = this.test(t);
            System.out.println("Spécification " + etiquette + " pour " + t + ": " + resultat);
            return resultat;
        };
    }


}