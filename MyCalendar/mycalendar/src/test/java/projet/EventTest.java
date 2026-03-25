package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class EventTest {

    @Test
    void descriptionRdvPersonnel() {
        LocalDateTime date = LocalDateTime.of(2026, 3, 20, 10, 30);
        Event event = new Event("RDV_PERSONNEL", "Dentiste", "Alice", date, 45, "", "", 0);

        assertEquals("RDV : Dentiste à 2026-03-20T10:30", event.description());
    }

    @Test
    void descriptionReunion() {
        Event event = new Event("REUNION", "Sprint", "Alice", LocalDateTime.of(2026, 3, 20, 9, 0),
                60, "Salle 2", "Alice, Bob", 0);

        assertEquals("Réunion : Sprint à Salle 2 avec Alice, Bob", event.description());
    }

    @Test
    void descriptionPeriodique() {
        Event event = new Event("PERIODIQUE", "Sport", "Alice", LocalDateTime.of(2026, 3, 20, 7, 0),
                0, "", "", 2);

        assertEquals("Événement périodique : Sport tous les 2 jours", event.description());
    }

    @Test
    void descriptionTypeInconnuRetourneVide() {
        Event event = new Event("AUTRE", "Test", "Alice", LocalDateTime.of(2026, 3, 20, 7, 0),
                0, "", "", 0);

        assertEquals("", event.description());
    }

}
