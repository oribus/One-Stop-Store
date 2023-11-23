package xyz.thingummy.oss.model.specification;

import org.junit.jupiter.api.Test;
import xyz.thingummy.oss.commons.notification.CollecteurNotifications;
import xyz.thingummy.oss.commons.notification.Message;
import xyz.thingummy.oss.commons.notification.TypeMessage;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static xyz.thingummy.oss.commons.notification.CollecteurNotifications.ERREUR;
import static xyz.thingummy.oss.commons.notification.CollecteurNotifications.contientMessage;
import static xyz.thingummy.oss.model.specification.SpecificationTest.Constantes.*;
import static xyz.thingummy.oss.model.specification.SpecificationOperations.*;

class SpecificationTest {
    public static final Predicate<String> COMMENCE_PAR_A = s -> s.startsWith("a");
    public static final Predicate<String> CONTIENT_B = s -> s.contains("b");
    public static final Specification<String> CONTIENT_PLUS_DE_CINQ_CARACTERES = s -> s.length() > 5;
    public static final Specification<String> CONTIENT_PLUS_DE_QUATRE_CARACTERES = s -> s.length() > 4;
    public static final Specification<String> CONTIENT_PLUS_DE_TROIS_CARACTERES = s -> s.length() > 3;
    public static final Predicate<String> FINI_PAR_Z = s -> s.endsWith("z");
    public static final Message PLUS_DE_QUATRE_CARACTERES_KO = Message.of("+4car.ko", "La chaîne contient 4 caractéres ou moins.", TypeMessage.ERREUR);

    public static final Message CONTIENT_A_KO = Message.of("contienta.ko", "La chaîne ne contient pas 'a'", TypeMessage.ERREUR);
    public static final Message MESSAGE_DE_TEST = Message.of("critere1.ko", "Critère non satisfait", TypeMessage.ERREUR);
    public static final Message MESSAGE_DE_TEST_2 = Message.of("critere2.ko", "Critère non satisfait", TypeMessage.ERREUR);

    public static final Message MESSAGE_DE_TEST_3 = Message.of("critere3.ko", "Critère non satisfait", TypeMessage.ERREUR);

    public static final Message MESSAGE_DE_TEST_4 = Message.of("critere4.ko", "Critère non satisfait", TypeMessage.ERREUR);
    public static final Specification<String> NE_CONTIENT_PAS_A = s -> !s.contains("a");
    public static final Predicate<String> PREDICATE_CONTIENT_PLUS_DE_TROIS_CARACTERES = CONTIENT_PLUS_DE_TROIS_CARACTERES;
    public static final Specification<Integer> SUPPERIEUR_A_QUATRE = i -> i > 4;
    private static final Specification<String> CONTIENT_A = s -> s.contains("a");
    public static final Specification<String> CONTIENT_A_ET_MOINS_DE_CINQ_CARACTERES_2 = CONTIENT_A.etNon(CONTIENT_PLUS_DE_CINQ_CARACTERES);
    public static final Specification<String> CONTIENT_A_ET_MOINS_DE_CINQ_CARACTERES = CONTIENT_A.etPas(CONTIENT_PLUS_DE_CINQ_CARACTERES);
    public static final Specification<String> CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES = CONTIENT_A.combiner(CONTIENT_PLUS_DE_CINQ_CARACTERES, Boolean::logicalAnd);

    public static final Specification<String> CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES_2 = CONTIENT_A.combiner(CONTIENT_PLUS_DE_CINQ_CARACTERES, Boolean::logicalAnd, ShortCut.NONE);

    private static boolean contientUnMessage( Specification<String> specification) {
        return specification.getMessage().isPresent();
    }
    private static boolean contientUnMessageAdditionnel( Specification<String> specification) {
        return specification.getMessageAdditionnel().isPresent();
    }
    private static boolean contientCeMessage(Specification<String> specification, Message message) {
        return specification.getMessage().filter(Predicate.isEqual(message)).isPresent();
    }
    private static boolean contientCeMessageAdditionnel(Specification<String> specification, Message message) {
        return specification.getMessageAdditionnel().filter(Predicate.isEqual(message)).isPresent();
    }
    /**
     * Teste la méthode avec.
     * Vérifie que le message est correctement associé à la spécification et est récupérable.
     */
    @Test
    void test_Avec() {
        assertFalse(contientUnMessage(CONTIENT_PLUS_DE_TROIS_CARACTERES));
        final Specification<String> avecMessage = CONTIENT_PLUS_DE_TROIS_CARACTERES.avec(MESSAGE_DE_TEST);
        assertTrue(contientUnMessage(avecMessage));
        assertTrue(contientCeMessage(avecMessage,MESSAGE_DE_TEST));
        assertTrue(avecMessage.estSatisfaitePar(TEST));
        assertFalse(avecMessage.estSatisfaitePar(OUI));
    }

    @Test
    void test_Avec_MessageAdditionnel() {
        assertFalse(contientUnMessage(CONTIENT_PLUS_DE_TROIS_CARACTERES));
        assertFalse(contientUnMessageAdditionnel(CONTIENT_PLUS_DE_TROIS_CARACTERES));
        final Specification<String> avecMessage = CONTIENT_PLUS_DE_TROIS_CARACTERES.avec(MESSAGE_DE_TEST);
        assertTrue(contientUnMessage(avecMessage));
        assertFalse(contientUnMessageAdditionnel(avecMessage));
        final Specification<String> avecMessageAdditionnel = CONTIENT_PLUS_DE_TROIS_CARACTERES.avec(MESSAGE_DE_TEST, MESSAGE_DE_TEST_2);
        assertTrue(contientUnMessage(avecMessageAdditionnel));
        assertTrue(contientUnMessageAdditionnel(avecMessageAdditionnel));
    }
    @Test
    void test_sansMessage() {
        final Specification<String> avecMessage = CONTIENT_PLUS_DE_TROIS_CARACTERES.avec(MESSAGE_DE_TEST);
        assertFalse(contientUnMessage(CONTIENT_PLUS_DE_TROIS_CARACTERES.sansMessage()));
        assertFalse(contientUnMessage(avecMessage.sansMessage()));
    }

    @Test
    void test_Avec2() {
        final Specification<String> avecMessage = CONTIENT_PLUS_DE_TROIS_CARACTERES.avec(MESSAGE_DE_TEST);
        assertTrue(contientUnMessage(avecMessage));
        assertTrue(contientCeMessage(avecMessage,MESSAGE_DE_TEST));
        final Specification<String> avecMessage2 = avecMessage.avec(MESSAGE_DE_TEST_2);
        assertTrue(contientUnMessage(avecMessage2));
        assertTrue(contientCeMessage(avecMessage2,MESSAGE_DE_TEST_2));
        assertFalse(contientUnMessage(avecMessage2.sansMessage()));
    }
    @Test
    void test_Avec_MessageAdditionnel2() {
        final Specification<String> avecMessageAdditionnel = CONTIENT_PLUS_DE_TROIS_CARACTERES.avec(MESSAGE_DE_TEST, MESSAGE_DE_TEST_2);
        assertTrue(contientUnMessage(avecMessageAdditionnel));
        assertTrue(contientUnMessageAdditionnel(avecMessageAdditionnel));
        final Specification<String> avecMessageAdditionnel2 = avecMessageAdditionnel.avec(MESSAGE_DE_TEST_3, MESSAGE_DE_TEST_4);
        assertTrue(contientCeMessage(avecMessageAdditionnel2,MESSAGE_DE_TEST_3));
        assertTrue(contientCeMessageAdditionnel(avecMessageAdditionnel2,MESSAGE_DE_TEST_4));
        assertFalse(contientUnMessage(avecMessageAdditionnel2.sansMessage()));
    }

    @Test
    void test_Combiner() {
        final CollecteurNotifications c = new CollecteurNotifications();
        final Specification<String> avecMessage = CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES_2.avec(MESSAGE_DE_TEST);
        assertTrue(CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES.estSatisfaitePar(ABRACADABRA));
        assertFalse(CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES.estSatisfaitePar(ACT));
        assertFalse(CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES.estSatisfaitePar(HELLO));
        assertTrue(CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES_2.estSatisfaitePar(ABRACADABRA));
        assertFalse(CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES_2.estSatisfaitePar(ACT));
        assertFalse(avecMessage.estSatisfaitePar(HELLO,c));
        assertTrue(contientUnMessage(avecMessage));
        assertTrue(contientCeMessage(avecMessage,MESSAGE_DE_TEST));
    }

    @Test
    void test_Differer() {
        assertTrue(CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES.differer(ABRACADABRA).estSatisfaite());
        assertTrue(CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES.differer(ABRACADABRA).estSatisfaitePar(null));
        assertFalse(CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES.differer(ACT).estSatisfaite());
        assertFalse(CONTIENT_A_ET_PLUS_DE_CINQ_CARACTERES.differer(HELLO).estSatisfaite());
    }

    @Test
    void test_EtPas() {
        assertTrue(CONTIENT_A_ET_MOINS_DE_CINQ_CARACTERES.estSatisfaitePar(ACT));
        assertFalse(CONTIENT_A_ET_MOINS_DE_CINQ_CARACTERES.estSatisfaitePar(ABRACADABRA));
        assertFalse(CONTIENT_A_ET_MOINS_DE_CINQ_CARACTERES.estSatisfaitePar(HELLO));
    }

    /**
     * Teste la méthode etNon.
     * Vérifie que la spécification combinée est satisfaite lorsque la première spécification est vraie et la seconde fausse.
     */
    @Test
    void test_EtNon() {
        assertTrue(CONTIENT_A_ET_MOINS_DE_CINQ_CARACTERES_2.estSatisfaitePar(ACT));
        assertFalse(CONTIENT_A_ET_MOINS_DE_CINQ_CARACTERES_2.estSatisfaitePar(ABRACADABRA));
        assertFalse(CONTIENT_A_ET_MOINS_DE_CINQ_CARACTERES_2.estSatisfaitePar(HELLO));
    }

    /**
     * Teste la méthode estSatisfaitePar.
     * Vérifie si la spécification est correctement évaluée pour différentes chaînes de caractères.
     */
    @Test
    void test_EstSatisfaitePar() {
        assertTrue(CONTIENT_PLUS_DE_TROIS_CARACTERES.estSatisfaitePar(TEST));
        assertFalse(CONTIENT_PLUS_DE_TROIS_CARACTERES.estSatisfaitePar(OUI));
    }

    @Test
    void test_EstSatisfaitePar_avecCollecteur() {
        final CollecteurNotifications c = new CollecteurNotifications();
        final Specification<String> contientPlusDeTroisCaracteres = avec(CONTIENT_PLUS_DE_TROIS_CARACTERES, MESSAGE_DE_TEST);
        final Specification<String> neContientPasA = avec(NE_CONTIENT_PAS_A, MESSAGE_DE_TEST_2);
        final Specification<String> contientPlusDeTroisCaracteresEtNeContientPasA = contientPlusDeTroisCaracteres.et(neContientPasA);

        assertTrue(contientPlusDeTroisCaracteresEtNeContientPasA.estSatisfaitePar(TEST, c));
        assertFalse(c.hasNotifications());
        assertFalse(contientPlusDeTroisCaracteresEtNeContientPasA.estSatisfaitePar(OUI, c));
        assertTrue(c.hasNotifications());
        assertEquals(1, c.denombrerNotifications(ERREUR));
        assertEquals(1, c.denombrerNotifications(contientMessage(MESSAGE_DE_TEST)));
    }

    @Test
    void test_EstSatisfaitePar_avecCollecteur2() {
        final CollecteurNotifications c = new CollecteurNotifications();
        final Specification<String> contientPlusDeTroisCaracteres = avec(CONTIENT_PLUS_DE_TROIS_CARACTERES, MESSAGE_DE_TEST);
        final Specification<String> contientA = avec(CONTIENT_A, MESSAGE_DE_TEST_2);
        final Specification<String> contientPlusDeTroisCaracteresOuContientA = contientPlusDeTroisCaracteres.ou(contientA);

        assertTrue(contientPlusDeTroisCaracteresOuContientA.estSatisfaitePar(TEST, c));
        assertFalse(c.hasNotifications());
        assertFalse(contientPlusDeTroisCaracteresOuContientA.estSatisfaitePar(OUI, c));
        assertTrue(c.hasNotifications());
        assertEquals(2, c.denombrerNotifications(ERREUR));
        assertEquals(1, c.denombrerNotifications(contientMessage(MESSAGE_DE_TEST)));
        assertEquals(1, c.denombrerNotifications(contientMessage(MESSAGE_DE_TEST_2)));
    }

    /**
     * Teste la méthode : "et"
     * Vérifie que la spécification combinée est satisfaite uniquement lorsque les deux prédicats sont vrais.
     */
    @Test
    void test_Et() {
        final Specification<String> motValide = CONTIENT_A.et(CONTIENT_PLUS_DE_TROIS_CARACTERES);

        assertTrue(motValide.estSatisfaitePar(ABRACADABRA));
        assertFalse(motValide.estSatisfaitePar(ACT));
        assertFalse(motValide.estSatisfaitePar(HELLO));
    }

    /**
     * Teste la méthode : "ou".
     * Vérifie que la spécification combinée est satisfaite si au moins l'un des prédicats est vrai.
     */
    @Test
    void test_Ou() {
        final Specification<String> motValide = CONTIENT_A.ou(CONTIENT_PLUS_DE_CINQ_CARACTERES);

        assertTrue(motValide.estSatisfaitePar(ABRACADABRA));
        assertTrue(motValide.estSatisfaitePar(ACT));
        assertFalse(motValide.estSatisfaitePar(HELLO));
    }

    /**
     * Teste la méthode et avec plusieurs prédicats.
     * Vérifie que la spécification combinée est satisfaite uniquement lorsque tous les prédicats fournis sont vrais.
     * Ce test couvre le cas où plusieurs prédicats sont combinés en utilisant l'opération logique ET.
     */
    @Test
    void test_Et_MultiplePredicates() {
        final Specification<String> motValide = et(
                CONTIENT_B,
                PREDICATE_CONTIENT_PLUS_DE_TROIS_CARACTERES,
                COMMENCE_PAR_A
        );

        assertTrue(motValide.estSatisfaitePar(ABRACADABRA));
        assertFalse(motValide.estSatisfaitePar(ABC));
        assertFalse(motValide.estSatisfaitePar(HELLO));
        assertFalse(motValide.estSatisfaitePar(BRAVO));
    }

    /**
     * Teste la méthode ou avec plusieurs prédicats.
     * Vérifie que la spécification combinée est satisfaite si au moins l'un des prédicats fournis est vrai.
     * Ce test illustre la combinaison de plusieurs prédicats en utilisant l'opération logique OU.
     */
    @Test
    void test_Ou_MultiplePredicates() {
        final Specification<String> motValide = ou(
                CONTIENT_A,
                CONTIENT_PLUS_DE_CINQ_CARACTERES,
                FINI_PAR_Z
        );

        assertTrue(motValide.estSatisfaitePar(ABRACADABRA));
        assertTrue(motValide.estSatisfaitePar(ACT));
        assertTrue(motValide.estSatisfaitePar(JAZZ));
        assertFalse(motValide.estSatisfaitePar(HELLO));
    }

    /**
     * Teste la méthode ouX (OU exclusif).
     * Vérifie que la spécification combinée est satisfaite lorsque exactement l'un des prédicats est vrai.
     */
    @Test
    void test_OuX() {
        final CollecteurNotifications collecteur = new CollecteurNotifications();
        final Specification<String> contientPlusDeQuatreCaracteres = avec(CONTIENT_PLUS_DE_QUATRE_CARACTERES, PLUS_DE_QUATRE_CARACTERES_KO);
        final Specification<String> contientA = avec(CONTIENT_A, CONTIENT_A_KO);
        final Specification<String> CONTIENT_A_OUX_PLUS_DE_QUATRE_CARACTERES = contientA.ouX(contientPlusDeQuatreCaracteres).avec(MESSAGE_DE_TEST_3, MESSAGE_DE_TEST_4);

        assertTrue(CONTIENT_A_OUX_PLUS_DE_QUATRE_CARACTERES.estSatisfaitePar(ACT));
        assertTrue(CONTIENT_A_OUX_PLUS_DE_QUATRE_CARACTERES.estSatisfaitePar(HELLO));
        assertFalse(CONTIENT_A_OUX_PLUS_DE_QUATRE_CARACTERES.estSatisfaitePar(ABRACADABRA));
        assertFalse(CONTIENT_A_OUX_PLUS_DE_QUATRE_CARACTERES.estSatisfaitePar(OUI));
        assertTrue(CONTIENT_A_OUX_PLUS_DE_QUATRE_CARACTERES.estSatisfaitePar(ACT, collecteur));
        assertFalse(collecteur.hasNotifications());
        assertTrue(CONTIENT_A_OUX_PLUS_DE_QUATRE_CARACTERES.estSatisfaitePar(HELLO, collecteur));
        assertFalse(collecteur.hasNotifications());
        assertFalse(CONTIENT_A_OUX_PLUS_DE_QUATRE_CARACTERES.estSatisfaitePar(ABRACADABRA, collecteur));
        assertTrue(collecteur.hasNotifications());
        assertEquals(1, collecteur.denombrerNotifications(ERREUR));
        assertEquals(1, collecteur.denombrerNotifications(contientMessage(MESSAGE_DE_TEST_4)));
        collecteur.vider();
        assertFalse(CONTIENT_A_OUX_PLUS_DE_QUATRE_CARACTERES.estSatisfaitePar(OUI, collecteur));
        assertTrue(collecteur.hasNotifications());
        assertEquals(3, collecteur.denombrerNotifications(ERREUR));
        assertEquals(1, collecteur.denombrerNotifications(contientMessage(PLUS_DE_QUATRE_CARACTERES_KO)));
        assertEquals(1, collecteur.denombrerNotifications(contientMessage(CONTIENT_A_KO)));
        assertEquals(1, collecteur.denombrerNotifications(contientMessage(MESSAGE_DE_TEST_3)));

    }

    /**
     * Teste les méthodes statiques soit, pas et non.
     * Vérifie que ces méthodes créent des spécifications qui se comportent comme prévu.
     */
    @Test
    void test_MethodesStatiques() {
        final Specification<String> specSoit = soit(CONTIENT_A);
        final Specification<String> specPas = pas(CONTIENT_A);
        final Specification<String> specNon = non(CONTIENT_A);

        assertTrue(specSoit.estSatisfaitePar(APPLE));
        assertFalse(specPas.estSatisfaitePar(APPLE));
        assertFalse(specNon.estSatisfaitePar(APPLE));

        assertFalse(specSoit.estSatisfaitePar(HELLO));
        assertTrue(specPas.estSatisfaitePar(HELLO));
        assertTrue(specNon.estSatisfaitePar(HELLO));
    }

    @Test
    void test_Non() {
        final CollecteurNotifications c = new CollecteurNotifications();
        final Specification<String> specNon = avec(CONTIENT_A.non(), MESSAGE_DE_TEST);
        final Specification<String> specNon2 = non(CONTIENT_A);
        assertTrue(specNon.estSatisfaitePar(HELLO, c));
        assertFalse(c.hasNotifications());
        assertTrue(specNon2.estSatisfaitePar(HELLO));
        assertFalse(specNon.estSatisfaitePar(APPLE, c));
        assertTrue(c.hasNotifications());
        assertFalse(specNon2.estSatisfaitePar(APPLE));
    }

    @Test
    void test_Pas_Ni() {
        final Specification<String> NE_CONTIENT_PAS_A_ET_MOINS_DE_CINQ_CARACTERES = pas(CONTIENT_A).ni(CONTIENT_PLUS_DE_CINQ_CARACTERES);
        assertTrue(NE_CONTIENT_PAS_A_ET_MOINS_DE_CINQ_CARACTERES.estSatisfaitePar(HELLO));
        assertFalse(NE_CONTIENT_PAS_A_ET_MOINS_DE_CINQ_CARACTERES.estSatisfaitePar(APPLE));
        assertFalse(NE_CONTIENT_PAS_A_ET_MOINS_DE_CINQ_CARACTERES.estSatisfaitePar(JUMBLE));
    }

    @Test
    void test_Soit() {
        final Specification<String> specSoit = soit(CONTIENT_A);
        assertTrue(specSoit.estSatisfaitePar(APPLE));
        assertFalse(specSoit.estSatisfaitePar(HELLO));
    }

    @Test
    void test_transformer() {
        final Specification<String> contientPlusDeQuatreCaracteres = SUPPERIEUR_A_QUATRE.transformer(String::length);
        assertTrue(contientPlusDeQuatreCaracteres.estSatisfaitePar(Constantes.APPLE));
        assertFalse(contientPlusDeQuatreCaracteres.estSatisfaitePar(ACT));
    }

    static final class Constantes {
        public static final String ABC = "abc";
        public static final String ABRACADABRA = "abracadabra";
        public static final String ACT = "act";
        public static final String APPLE = "apple";
        public static final String BRAVO = "bravo";
        public static final String HELLO = "hello";
        public static final String JAZZ = "jazz";
        public static final String JUMBLE = "jumble";

        public static final String OUI = "oui";

        public static final String TEST = "Test";

        private Constantes() {
        }
    }


}