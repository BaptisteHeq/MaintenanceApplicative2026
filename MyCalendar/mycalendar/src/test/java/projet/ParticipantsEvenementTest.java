package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ParticipantsEvenementTest {

    @Test
    void construitParticipantsValides() {
        ParticipantsEvenement participants = new ParticipantsEvenement("Alice, Bob");

        assertEquals("Alice, Bob", participants.valeur());
    }

    @Test
    void rejetteNull() {
        assertThrows(NullPointerException.class, () -> new ParticipantsEvenement(null));
    }

    @Test
    void egaliteBaseeSurValeur() {
        ParticipantsEvenement p1 = new ParticipantsEvenement("Alice, Bob");
        ParticipantsEvenement p2 = new ParticipantsEvenement("Alice, Bob");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void valeursDifferentesSontInegales() {
        ParticipantsEvenement p1 = new ParticipantsEvenement("Alice");
        ParticipantsEvenement p2 = new ParticipantsEvenement("Bob");

        assertNotEquals(p1, p2);
    }

    @Test
    void toStringRetourneValeur() {
        ParticipantsEvenement participants = new ParticipantsEvenement("Alice, Bob");

        assertEquals("Alice, Bob", participants.toString());
    }
}
