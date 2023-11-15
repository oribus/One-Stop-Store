package xyz.thingummy.oss.model;

import org.junit.jupiter.api.Test;
import xyz.thingummy.oss.commons.notification.Message;
import xyz.thingummy.oss.commons.notification.TypeMessage;
import xyz.thingummy.oss.model.specification.Specification;

import static org.junit.jupiter.api.Assertions.*;

class SpecificationTest {
    /**
     * Teste la méthode estSatisfaitePar.
     * Vérifie si la spécification est correctement évaluée pour différentes chaînes de caractères.
     */
    @Test
    void test_EstSatisfaitePar() {
        Specification<String> contientPlusDeTroisCaracteres = s -> s.length() > 3;
        assertTrue(contientPlusDeTroisCaracteres.estSatisfaitePar("Test"));
        assertFalse(contientPlusDeTroisCaracteres.estSatisfaitePar("oui"));
    }

    /**
     * Teste la méthode et.
     * Vérifie que la spécification combinée est satisfaite uniquement lorsque les deux prédicats sont vrais.
     */
    @Test
    void test_Et() {
        Specification<String> contientPetitA = s -> s.contains("a");
        Specification<String> contientPlusDeTroisCaracteres = s -> s.length() > 3;

        Specification<String> motValide = contientPetitA.et(contientPlusDeTroisCaracteres);

        assertTrue(motValide.estSatisfaitePar("abracadabra"));
        assertFalse(motValide.estSatisfaitePar("act"));
        assertFalse(motValide.estSatisfaitePar("hello"));
    }

    /**
     * Teste la méthode ou.
     * Vérifie que la spécification combinée est satisfaite si au moins l'un des prédicats est vrai.
     */
    @Test
    void test_Ou() {
        Specification<String> contientPetitA = s -> s.contains("a");
        Specification<String> contientPlusDeCinqCaracteres = s -> s.length() > 5;

        Specification<String> motValide = contientPetitA.ou(contientPlusDeCinqCaracteres);

        assertTrue(motValide.estSatisfaitePar("abracadabra"));
        assertTrue(motValide.estSatisfaitePar("act"));
        assertFalse(motValide.estSatisfaitePar("hello"));
    }

    /**
     * Teste la méthode et avec plusieurs prédicats.
     * Vérifie que la spécification combinée est satisfaite uniquement lorsque tous les prédicats fournis sont vrais.
     * Ce test couvre le cas où plusieurs prédicats sont combinés en utilisant l'opération logique ET.
     */
    @Test
    void test_Et_MultiplePredicates() {
        Specification<String> motValide = Specification.et(
                s -> s.contains("b"),
                s -> s.length() > 3,
                s -> s.startsWith("a")
        );

        assertTrue(motValide.estSatisfaitePar("abracadabra"));
        assertFalse(motValide.estSatisfaitePar("abc"));
        assertFalse(motValide.estSatisfaitePar("hello"));
        assertFalse(motValide.estSatisfaitePar("bravo"));
    }

    /**
     * Teste la méthode ou avec plusieurs prédicats.
     * Vérifie que la spécification combinée est satisfaite si au moins l'un des prédicats fournis est vrai.
     * Ce test illustre la combinaison de plusieurs prédicats en utilisant l'opération logique OU.
     */
    @Test
    void test_Ou_MultiplePredicates() {
        Specification<String> motValide = Specification.ou(
                s -> s.contains("a"),
                s -> s.length() > 5,
                s -> s.endsWith("z")
        );

        assertTrue(motValide.estSatisfaitePar("abracadabra"));
        assertTrue(motValide.estSatisfaitePar("act"));
        assertTrue(motValide.estSatisfaitePar("jazz"));
        assertFalse(motValide.estSatisfaitePar("hello"));
    }

    /**
     * Teste la méthode etNon.
     * Vérifie que la spécification combinée est satisfaite lorsque la première spécification est vraie et la seconde fausse.
     */
    @Test
    void test_EtNon() {
        Specification<String> spec1 = s -> s.contains("a");
        Specification<String> spec2 = s -> s.length() > 5;

        Specification<String> combinedSpec = spec1.etNon(spec2);

        assertTrue(combinedSpec.estSatisfaitePar("act"));
        assertFalse(combinedSpec.estSatisfaitePar("abracadabra"));
        assertFalse(combinedSpec.estSatisfaitePar("hello"));
    }

    /**
     * Teste la méthode ouX (OU exclusif).
     * Vérifie que la spécification combinée est satisfaite lorsque exactement l'un des prédicats est vrai.
     */
    @Test
    void test_OuX() {
        Specification<String> spec1 = s -> s.contains("a");
        Specification<String> spec2 = s -> s.length() > 4;

        Specification<String> combinedSpec = spec1.ouX(spec2);

        assertTrue(combinedSpec.estSatisfaitePar("act"));
        assertTrue(combinedSpec.estSatisfaitePar("hello"));
        assertFalse(combinedSpec.estSatisfaitePar("abracadabra"));
        assertFalse(combinedSpec.estSatisfaitePar("oui"));
    }

    /**
     * Teste les méthodes statiques soit, pas et non.
     * Vérifie que ces méthodes créent des spécifications qui se comportent comme prévu.
     */
    @Test
    void test_MethodesStatiques() {
        Specification<String> specSoit = Specification.soit(s -> s.contains("a"));
        Specification<String> specPas = Specification.pas(s -> s.contains("a"));
        Specification<String> specNon = Specification.non(s -> s.contains("a"));

        assertTrue(specSoit.estSatisfaitePar("apple"));
        assertFalse(specPas.estSatisfaitePar("apple"));
        assertFalse(specNon.estSatisfaitePar("apple"));

        assertFalse(specSoit.estSatisfaitePar("hello"));
        assertTrue(specPas.estSatisfaitePar("hello"));
        assertTrue(specNon.estSatisfaitePar("hello"));
    }

    /**
     * Teste la méthode avec.
     * Vérifie que le message est correctement associé à la spécification et est récupérable.
     */
    @Test
    void test_Avec() {
        Message messageTest = Message.of("critere.ko", "Critère non satisfait", TypeMessage.ERREUR);
        Specification<String> spec = s -> s.length() > 3;
        Specification<String> specAvecMessage = spec.avec(messageTest);

        assertEquals(messageTest, specAvecMessage.getMessage());
        assertTrue(specAvecMessage.estSatisfaitePar("Test"));
        assertFalse(specAvecMessage.estSatisfaitePar("oui"));
    }


}