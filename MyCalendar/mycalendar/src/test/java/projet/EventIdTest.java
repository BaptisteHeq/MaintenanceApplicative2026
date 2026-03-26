package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class EventIdTest {

    @Test
    void construitUnIdentifiantValide() {
        EventId id = new EventId("evt-123");

        assertEquals("evt-123", id.valeur());
    }

    @Test
    void rejetteNull() {
        assertThrows(NullPointerException.class, () -> new EventId(null));
    }

    @Test
    void rejetteVide() {
        assertThrows(IllegalArgumentException.class, () -> new EventId("   "));
    }

    @Test
    void genereUnIdentifiantNonVide() {
        EventId id = EventId.nouveau();

        assertNotEquals("", id.valeur());
    }

    @Test
    void egaliteBaseeSurValeur() {
        EventId id1 = new EventId("evt-123");
        EventId id2 = new EventId("evt-123");

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void inegaliteEtTypeDifferent() {
        EventId id = new EventId("evt-123");

        assertNotEquals(id, new EventId("evt-456"));
        assertNotEquals(id, "evt-123");
    }
}
