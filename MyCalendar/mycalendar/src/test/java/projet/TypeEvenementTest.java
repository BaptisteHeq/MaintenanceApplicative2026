package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TypeEvenementTest {

    @Test
    void construitTypeValide() {
        TypeEvenement type = new TypeEvenement(TypeEvenement.REUNION);

        assertEquals(TypeEvenement.REUNION, type.valeur());
    }

    @Test
    void rejetteTypeNull() {
        assertThrows(NullPointerException.class, () -> new TypeEvenement(null));
    }

    @Test
    void rejetteTypeInvalide() {
        assertThrows(IllegalArgumentException.class, () -> new TypeEvenement("AUTRE"));
    }

    @Test
    void detecteTypePeriodique() {
        assertTrue(new TypeEvenement(TypeEvenement.PERIODIQUE).estPeriodique());
        assertFalse(new TypeEvenement(TypeEvenement.RDV_PERSONNEL).estPeriodique());
    }

    @Test
    void egaliteBaseeSurValeur() {
        TypeEvenement t1 = new TypeEvenement(TypeEvenement.RDV_PERSONNEL);
        TypeEvenement t2 = new TypeEvenement(TypeEvenement.RDV_PERSONNEL);

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void toStringRetourneValeur() {
        TypeEvenement type = new TypeEvenement(TypeEvenement.REUNION);

        assertEquals(TypeEvenement.REUNION, type.toString());
    }

    @Test
    void inegaliteEtTypeDifferent() {
        TypeEvenement type = new TypeEvenement(TypeEvenement.REUNION);

        assertNotEquals(type, new TypeEvenement(TypeEvenement.RDV_PERSONNEL));
        assertNotEquals(type, "REUNION");
    }
}
