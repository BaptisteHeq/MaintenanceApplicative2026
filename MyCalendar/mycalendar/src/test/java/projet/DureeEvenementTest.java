package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DureeEvenementTest {

    @Test
    void construitUneDureeValide() {
        DureeEvenement duree = new DureeEvenement(45);
        assertEquals(45, duree.valeur());
    }

    @Test
    void rejetteUneDureeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new DureeEvenement(-1));
    }

    @Test
    void egaliteBaseeSurValeur() {
        DureeEvenement d1 = new DureeEvenement(30);
        DureeEvenement d2 = new DureeEvenement(30);
        assertEquals(d1, d2);
    }

    @Test
    void hashCodeCoherentAvecEquals() {
        DureeEvenement d1 = new DureeEvenement(30);
        DureeEvenement d2 = new DureeEvenement(30);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void toStringRetourneLaValeur() {
        DureeEvenement duree = new DureeEvenement(30);
        assertEquals("30", duree.toString());
    }

    @Test
    void inegaliteEtTypeDifferent() {
        DureeEvenement duree = new DureeEvenement(30);
        assertNotEquals(duree, new DureeEvenement(31));
        assertNotEquals(duree, "30");
    }
}
