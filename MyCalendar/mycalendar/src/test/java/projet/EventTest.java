package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void typeInconnuEstRefuse() {
        assertThrows(IllegalArgumentException.class, () -> new Event("AUTRE", "Test", "Alice",
                LocalDateTime.of(2026, 3, 20, 7, 0), 0, "", "", 0));
    }

    @Test
    void constructeurVoInitialiseTousLesChamps() {
        TypeEvenement type = new TypeEvenement(TypeEvenement.REUNION);
        TitreEvenement titre = new TitreEvenement("Comite");
        ProprietaireEvenement proprietaire = new ProprietaireEvenement("Alice");
        LocalDateTime debut = LocalDateTime.of(2026, 4, 2, 14, 0);
        DureeEvenement duree = new DureeEvenement(30);
        LieuEvenement lieu = new LieuEvenement("Salle A");
        ParticipantsEvenement participants = new ParticipantsEvenement("Alice, Bob");
        FrequenceJours frequence = new FrequenceJours(0);

        Event event = new Event(type, titre, proprietaire, debut, duree, lieu, participants, frequence);

        assertEquals(type, event.type);
        assertEquals(titre, event.title);
        assertEquals(proprietaire, event.proprietaire);
        assertEquals(debut, event.dateDebut);
        assertEquals(duree, event.dureeMinutes);
        assertEquals(lieu, event.lieu);
        assertEquals(participants, event.participants);
        assertEquals(frequence, event.frequenceJours);
    }

}
