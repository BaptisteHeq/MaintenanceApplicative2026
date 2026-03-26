package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class PeriodeEvenementsTest {

    @Test
    void construitPeriodeValide() {
        DateHeureEvenement debut = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 0, 0));
        DateHeureEvenement fin = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 23, 59));

        PeriodeEvenements periode = new PeriodeEvenements(debut, fin);

        assertEquals(debut, periode.debut());
        assertEquals(fin, periode.fin());
    }

    @Test
    void rejetteDebutNull() {
        DateHeureEvenement fin = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 23, 59));
        assertThrows(NullPointerException.class, () -> new PeriodeEvenements(null, fin));
    }

    @Test
    void rejetteFinNull() {
        DateHeureEvenement debut = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 0, 0));
        assertThrows(NullPointerException.class, () -> new PeriodeEvenements(debut, null));
    }

    @Test
    void rejetteDebutApresFin() {
        DateHeureEvenement debut = new DateHeureEvenement(LocalDateTime.of(2026, 3, 21, 0, 0));
        DateHeureEvenement fin = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 23, 59));

        assertThrows(IllegalArgumentException.class, () -> new PeriodeEvenements(debut, fin));
    }

    @Test
    void egaliteEtToString() {
        PeriodeEvenements p1 = new PeriodeEvenements(
                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 0, 0)),
                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 23, 59)));
        PeriodeEvenements p2 = new PeriodeEvenements(
                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 0, 0)),
                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 23, 59)));

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, "periode");
        assertEquals("PeriodeEvenements[debut=2026-03-20T00:00, fin=2026-03-20T23:59]", p1.toString());
    }
}
