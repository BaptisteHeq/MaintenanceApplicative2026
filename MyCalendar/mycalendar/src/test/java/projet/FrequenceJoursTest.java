package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FrequenceJoursTest {

    @Test
    void construitUneFrequenceValide() {
        FrequenceJours frequence = new FrequenceJours(FrequenceJours.HEBDOMADAIRE);
        assertEquals(FrequenceJours.HEBDOMADAIRE, frequence.valeur());
    }

    @Test
    void rejetteUneFrequenceNegativeEtArbitraire() {
        assertThrows(IllegalArgumentException.class, () -> new FrequenceJours(-1));
        assertThrows(IllegalArgumentException.class, () -> new FrequenceJours(8));
    }

    @Test
    void egaliteBaseeSurValeur() {
        FrequenceJours f1 = new FrequenceJours(FrequenceJours.HEBDOMADAIRE);
        FrequenceJours f2 = new FrequenceJours(FrequenceJours.HEBDOMADAIRE);
        assertEquals(f1, f2);
    }

    @Test
    void hashCodeCoherentAvecEquals() {
        FrequenceJours f1 = new FrequenceJours(FrequenceJours.HEBDOMADAIRE);
        FrequenceJours f2 = new FrequenceJours(FrequenceJours.HEBDOMADAIRE);
        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    void toStringRetourneLaValeur() {
        FrequenceJours frequence = new FrequenceJours(FrequenceJours.HEBDOMADAIRE);
        assertEquals("7", frequence.toString());
    }

    @Test
    void inegaliteEtTypeDifferent() {
        FrequenceJours frequence = new FrequenceJours(FrequenceJours.HEBDOMADAIRE);
        assertNotEquals(frequence, new FrequenceJours(FrequenceJours.MENSUELLE));
        assertNotEquals(frequence, "7");
    }
}
