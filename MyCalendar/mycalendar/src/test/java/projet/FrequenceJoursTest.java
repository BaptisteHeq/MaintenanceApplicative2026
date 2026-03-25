package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FrequenceJoursTest {

    @Test
    void construitUneFrequenceValide() {
        FrequenceJours frequence = new FrequenceJours(7);
        assertEquals(7, frequence.valeur());
    }

    @Test
    void rejetteUneFrequenceNegative() {
        assertThrows(IllegalArgumentException.class, () -> new FrequenceJours(-1));
    }

    @Test
    void egaliteBaseeSurValeur() {
        FrequenceJours f1 = new FrequenceJours(7);
        FrequenceJours f2 = new FrequenceJours(7);
        assertEquals(f1, f2);
    }

    @Test
    void hashCodeCoherentAvecEquals() {
        FrequenceJours f1 = new FrequenceJours(7);
        FrequenceJours f2 = new FrequenceJours(7);
        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    void toStringRetourneLaValeur() {
        FrequenceJours frequence = new FrequenceJours(7);
        assertEquals("7", frequence.toString());
    }
}
