package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TitreEvenementTest {

    @Test
    void construitUnTitreValide() {
        TitreEvenement titre = new TitreEvenement("  Réunion Sprint  ");
        assertEquals("Réunion Sprint", titre.valeur());
    }

    @Test
    void rejetteUnTitreVide() {
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement("   "));
    }

    @Test
    void rejetteUnTitreNull() {
        assertThrows(NullPointerException.class, () -> new TitreEvenement(null));
    }

    @Test
    void egaliteBaséeSurValeur() {
        TitreEvenement titre1 = new TitreEvenement("Réunion");
        TitreEvenement titre2 = new TitreEvenement("Réunion");
        assertEquals(titre1, titre2);
    }

    @Test
    void hashCodeCohérentAvecEquals() {
        TitreEvenement titre1 = new TitreEvenement("Réunion");
        TitreEvenement titre2 = new TitreEvenement("Réunion");
        assertEquals(titre1.hashCode(), titre2.hashCode());
    }

    @Test
    void toStringRetourneValeur() {
        TitreEvenement titre = new TitreEvenement("Réunion");
        assertEquals("Réunion", titre.toString());
    }

    @Test
    void inegaliteEtTypeDifferent() {
        TitreEvenement titre = new TitreEvenement("Réunion");
        assertNotEquals(titre, new TitreEvenement("Atelier"));
        assertNotEquals(titre, "Réunion");
    }

}
