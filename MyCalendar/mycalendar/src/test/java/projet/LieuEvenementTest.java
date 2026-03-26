package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LieuEvenementTest {

    @Test
    void construitLieuValide() {
        LieuEvenement lieu = new LieuEvenement("Salle 2");

        assertEquals("Salle 2", lieu.valeur());
    }

    @Test
    void rejetteNull() {
        assertThrows(NullPointerException.class, () -> new LieuEvenement(null));
    }

    @Test
    void egaliteBaseeSurValeur() {
        LieuEvenement l1 = new LieuEvenement("Salle A");
        LieuEvenement l2 = new LieuEvenement("Salle A");

        assertEquals(l1, l2);
        assertEquals(l1.hashCode(), l2.hashCode());
    }

    @Test
    void differenceDeValeurInegalite() {
        LieuEvenement l1 = new LieuEvenement("Salle A");
        LieuEvenement l2 = new LieuEvenement("Salle B");

        assertNotEquals(l1, l2);
    }

    @Test
    void toStringRetourneLaValeur() {
        LieuEvenement lieu = new LieuEvenement("Amphi");

        assertEquals("Amphi", lieu.toString());
    }

    @Test
    void equalsReflexifEtTypeDifferent() {
        LieuEvenement lieu = new LieuEvenement("Salle A");

        assertEquals(lieu, lieu);
        assertNotEquals(lieu, "Salle A");
    }
}
