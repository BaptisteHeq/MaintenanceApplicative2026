package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ProprietaireEvenementTest {

    @Test
    void construitUnProprietaireValide() {
        ProprietaireEvenement proprietaire = new ProprietaireEvenement("  Alice  ");
        assertEquals("Alice", proprietaire.valeur());
    }

    @Test
    void rejetteUnProprietaireVide() {
        assertThrows(IllegalArgumentException.class, () -> new ProprietaireEvenement("   "));
    }

    @Test
    void rejetteUnProprietaireNull() {
        assertThrows(NullPointerException.class, () -> new ProprietaireEvenement(null));
    }

    @Test
    void egaliteBaseeSurValeur() {
        ProprietaireEvenement p1 = new ProprietaireEvenement("Alice");
        ProprietaireEvenement p2 = new ProprietaireEvenement("Alice");
        assertEquals(p1, p2);
    }

    @Test
    void hashCodeCoherentAvecEquals() {
        ProprietaireEvenement p1 = new ProprietaireEvenement("Alice");
        ProprietaireEvenement p2 = new ProprietaireEvenement("Alice");
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void toStringRetourneLaValeur() {
        ProprietaireEvenement proprietaire = new ProprietaireEvenement("Alice");
        assertEquals("Alice", proprietaire.toString());
    }

    @Test
    void inegaliteEtTypeDifferent() {
        ProprietaireEvenement proprietaire = new ProprietaireEvenement("Alice");
        assertNotEquals(proprietaire, new ProprietaireEvenement("Bob"));
        assertNotEquals(proprietaire, "Alice");
    }
}
