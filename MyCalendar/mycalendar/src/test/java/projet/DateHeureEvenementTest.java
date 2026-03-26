package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class DateHeureEvenementTest {

    @Test
    void construitDateHeureValide() {
        DateHeureEvenement dateHeure = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 30));

        assertEquals(LocalDateTime.of(2026, 3, 20, 10, 30), dateHeure.valeur());
    }

    @Test
    void rejetteNull() {
        assertThrows(NullPointerException.class, () -> new DateHeureEvenement(null));
    }

    @Test
    void plusDaysEtPlusMinutes() {
        DateHeureEvenement dateHeure = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 30));

        assertEquals(LocalDateTime.of(2026, 3, 21, 10, 30), dateHeure.plusDays(1).valeur());
        assertEquals(LocalDateTime.of(2026, 3, 20, 11, 0), dateHeure.plusMinutes(30).valeur());
    }

    @Test
    void comparaisonsAvantApres() {
        DateHeureEvenement d1 = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0));
        DateHeureEvenement d2 = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 11, 0));

        assertTrue(d1.isBefore(d2));
        assertTrue(d2.isAfter(d1));
    }

    @Test
    void egaliteValeurEtToString() {
        DateHeureEvenement d1 = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0));
        DateHeureEvenement d2 = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0));

        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
        assertEquals("2026-03-20T10:00", d1.toString());
        assertNotEquals(d1, "2026-03-20T10:00");
    }
}
