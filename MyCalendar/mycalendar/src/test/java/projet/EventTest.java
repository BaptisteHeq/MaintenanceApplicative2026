package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class EventTest {

    @Test
    void descriptionRdvPersonnel() {
        DateHeureEvenement date = new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 30));
        Event event = event("RDV_PERSONNEL", "Dentiste", "Alice", date, 45, "", "", 0);

        assertEquals("RDV : Dentiste à 2026-03-20T10:30", event.description());
    }

    @Test
    void descriptionReunion() {
        Event event = event("REUNION", "Sprint", "Alice",
                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 9, 0)),
                60, "Salle 2", "Alice, Bob", 0);

        assertEquals("Réunion : Sprint à Salle 2 avec Alice, Bob", event.description());
    }

    @Test
    void descriptionPeriodique() {
        Event event = event("PERIODIQUE", "Sport", "Alice",
                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 7, 0)),
                0, "", "", FrequenceJours.HEBDOMADAIRE);

        assertEquals("Événement périodique : Sport (hebdomadaire)", event.description());
    }

    @Test
    void typeInconnuEstRefuse() {
        assertThrows(IllegalArgumentException.class, () -> event("AUTRE", "Test", "Alice",
                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 7, 0)), 0, "", "", 0));
    }

    @Test
    void constructeurVoInitialiseTousLesChamps() {
        TypeEvenement type = new TypeEvenement(TypeEvenement.REUNION);
        TitreEvenement titre = new TitreEvenement("Comite");
        ProprietaireEvenement proprietaire = new ProprietaireEvenement("Alice");
        DateHeureEvenement debut = new DateHeureEvenement(LocalDateTime.of(2026, 4, 2, 14, 0));
        DureeEvenement duree = new DureeEvenement(30);
        LieuEvenement lieu = new LieuEvenement("Salle A");
        ParticipantsEvenement participants = new ParticipantsEvenement("Alice, Bob");
        FrequenceJours frequence = new FrequenceJours(0);

        Event event = new Event(type, titre, proprietaire, debut, duree, lieu, participants, frequence);

        assertNotNull(event.id);
        assertEquals(type, event.type);
        assertEquals(titre, event.title);
        assertEquals(proprietaire, event.proprietaire);
        assertEquals(debut, event.dateDebut);
        assertEquals(duree, event.dureeMinutes);
        assertEquals(lieu, event.lieu);
        assertEquals(participants, event.participants);
        assertEquals(frequence, event.frequenceJours);
    }

    @Test
    void constructeurGenereDesIdentifiantsDifferents() {
        Event e1 = event("RDV_PERSONNEL", "Dentiste", "Alice",
                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 30)), 45,
                "", "", 0);
        Event e2 = event("RDV_PERSONNEL", "Dentiste", "Alice",
                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 30)), 45,
                "", "", 0);

        assertNotEquals(e1.id, e2.id);
    }

    private Event event(String type, String titre, String proprietaire, DateHeureEvenement debut,
            int dureeMinutes, String lieu, String participants, int frequenceJours) {
        return new Event(new TypeEvenement(type), new TitreEvenement(titre), new ProprietaireEvenement(proprietaire),
                debut, new DureeEvenement(dureeMinutes), new LieuEvenement(lieu),
                new ParticipantsEvenement(participants), new FrequenceJours(frequenceJours));
    }

}
