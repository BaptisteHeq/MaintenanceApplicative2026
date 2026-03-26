package projet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

class CalendarManagerTest {

        @Test
        void ajouterEventEtRecupererDansPeriode() {
                CalendarManager manager = new CalendarManager();

                LocalDateTime date = LocalDateTime.of(2026, 3, 20, 10, 0);
                ajouterEvent(manager, "RDV_PERSONNEL", "Dentiste", "Alice", new DateHeureEvenement(date), 30, "",
                                "", 0);

                List<Event> result = manager.eventsDansPeriode(
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 0, 0)),
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 23, 59)));

                assertEquals(1, result.size());
                assertEquals("Dentiste", result.get(0).title.valeur());
        }

        @Test
        void recupererDansPeriodeAvecVoPeriode() {
                CalendarManager manager = new CalendarManager();

                ajouterEvent(manager, "RDV_PERSONNEL", "Dentiste", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0)), 30, "", "", 0);

                PeriodeEvenements periode = new PeriodeEvenements(
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 0, 0)),
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 23, 59)));

                List<Event> result = manager.eventsDansPeriode(periode);

                assertEquals(1, result.size());
                assertEquals("Dentiste", result.get(0).title.valeur());
        }

        @Test
        void evenementHorsPeriodeNonRetourne() {
                CalendarManager manager = new CalendarManager();

                ajouterEvent(manager, "RDV_PERSONNEL", "Dentiste", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0)), 30, "", "", 0);

                List<Event> result = manager.eventsDansPeriode(
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 21, 0, 0)),
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 21, 23, 59)));

                assertTrue(result.isEmpty());
        }

        @Test
        void evenementApresPeriodeNonRetourne() {
                CalendarManager manager = new CalendarManager();

                ajouterEvent(manager, "RDV_PERSONNEL", "Dentiste", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 22, 10, 0)), 30, "", "", 0);

                List<Event> result = manager.eventsDansPeriode(
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 21, 0, 0)),
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 21, 23, 59)));

                assertTrue(result.isEmpty());
        }

        @Test
        void evenementPeriodiqueRetourneSiOccurrenceDansPeriode() {
                CalendarManager manager = new CalendarManager();

                ajouterEvent(manager, "PERIODIQUE", "Sport", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 1, 8, 0)), 0, "", "",
                                FrequenceJours.HEBDOMADAIRE);

                List<Event> result = manager.eventsDansPeriode(
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 8, 0, 0)),
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 8, 23, 59)));

                assertEquals(1, result.size());
                assertEquals("Sport", result.get(0).title.valeur());
        }

        @Test
        void evenementPeriodiqueNonRetourneSiAucuneOccurrenceDansPeriode() {
                CalendarManager manager = new CalendarManager();

                ajouterEvent(manager, "PERIODIQUE", "Sport", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 1, 8, 0)), 0, "", "",
                                FrequenceJours.HEBDOMADAIRE);

                List<Event> result = manager.eventsDansPeriode(
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 4, 0, 0)),
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 4, 23, 59)));

                assertTrue(result.isEmpty());
        }

        @Test
        void conflitRetourneTruePourChevauchement() {
                CalendarManager manager = new CalendarManager();

                Event e1 = event("RDV_PERSONNEL", "A", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0)),
                                60, "", "", 0);
                Event e2 = event("REUNION", "B", "Bob",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 30)),
                                30, "Salle", "Bob", 0);

                assertTrue(manager.conflit(e1, e2));
        }

        @Test
        void conflitRetourneFalseSiPasChevauchement() {
                CalendarManager manager = new CalendarManager();

                Event e1 = event("RDV_PERSONNEL", "A", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0)),
                                30, "", "", 0);
                Event e2 = event("REUNION", "B", "Bob",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 11, 0)),
                                30, "Salle", "Bob", 0);

                assertFalse(manager.conflit(e1, e2));
        }

        @Test
        void conflitRetourneTrueSiPeriodiqueChevaucheUnPonctuel() {
                CalendarManager manager = new CalendarManager();

                Event e1 = event("PERIODIQUE", "A", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0)),
                                30, "", "", FrequenceJours.HEBDOMADAIRE);
                Event e2 = event("REUNION", "B", "Bob",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 15)),
                                30, "Salle", "Bob", 0);

                assertTrue(manager.conflit(e1, e2));
        }

        @Test
        void conflitRetourneTrueSiPonctuelChevaucheUnPeriodique() {
                CalendarManager manager = new CalendarManager();

                Event e1 = event("REUNION", "A", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0)),
                                30, "Salle", "Alice", 0);
                Event e2 = event("PERIODIQUE", "B", "Bob",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 15)),
                                30, "", "", FrequenceJours.HEBDOMADAIRE);

                assertTrue(manager.conflit(e1, e2));
        }

        @Test
        void conflitRetourneFalseSiPeriodiqueNeChevauchePasLePonctuel() {
                CalendarManager manager = new CalendarManager();

                Event e1 = event("PERIODIQUE", "A", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 6, 0)),
                                30, "", "", FrequenceJours.HEBDOMADAIRE);
                Event e2 = event("REUNION", "B", "Bob",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 15)),
                                30, "Salle", "Bob", 0);

                assertFalse(manager.conflit(e1, e2));
        }

        @Test
        void conflitRetourneFalseSiPremierCommenceApresFinSecond() {
                CalendarManager manager = new CalendarManager();

                Event e1 = event("RDV_PERSONNEL", "A", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 12, 0)),
                                30, "", "", 0);
                Event e2 = event("REUNION", "B", "Bob",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0)),
                                30, "Salle", "Bob", 0);

                assertFalse(manager.conflit(e1, e2));
        }

        @Test
        void afficherEvenementsEcritLesDescriptions() {
                CalendarManager manager = new CalendarManager();
                ajouterEvent(manager, "RDV_PERSONNEL", "Dentiste", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0)), 30, "", "", 0);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                PrintStream originalOut = System.out;
                System.setOut(new PrintStream(out));
                try {
                        manager.afficherEvenements();
                } finally {
                        System.setOut(originalOut);
                }

                String output = out.toString();
                assertTrue(output.contains("ID="));
                assertTrue(output.contains("RDV : Dentiste à 2026-03-20T10:00"));
        }

        @Test
        void supprimerEventSupprimeLEvenementCible() {
                CalendarManager manager = new CalendarManager();
                ajouterEvent(manager, "RDV_PERSONNEL", "Dentiste", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0)), 30, "", "", 0);

                EventId id = manager.events.get(0).id;

                boolean supprime = manager.supprimerEvent(id);

                assertTrue(supprime);
                assertTrue(manager.events.isEmpty());
        }

        @Test
        void supprimerEventRetourneFalseSiIdAbsent() {
                CalendarManager manager = new CalendarManager();
                ajouterEvent(manager, "RDV_PERSONNEL", "Dentiste", "Alice",
                                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0)), 30, "", "", 0);

                boolean supprime = manager.supprimerEvent(new EventId("inconnu"));

                assertFalse(supprime);
                assertEquals(1, manager.events.size());
        }

        private void ajouterEvent(CalendarManager manager, String type, String titre, String proprietaire,
                        DateHeureEvenement debut, int dureeMinutes, String lieu, String participants,
                        int frequenceJours) {
                manager.ajouterEvent(new TypeEvenement(type), new TitreEvenement(titre),
                                new ProprietaireEvenement(proprietaire), debut, new DureeEvenement(dureeMinutes),
                                new LieuEvenement(lieu), new ParticipantsEvenement(participants),
                                new FrequenceJours(frequenceJours));
        }

        private Event event(String type, String titre, String proprietaire, DateHeureEvenement debut,
                        int dureeMinutes, String lieu, String participants, int frequenceJours) {
                return new Event(new TypeEvenement(type), new TitreEvenement(titre),
                                new ProprietaireEvenement(proprietaire), debut, new DureeEvenement(dureeMinutes),
                                new LieuEvenement(lieu), new ParticipantsEvenement(participants),
                                new FrequenceJours(frequenceJours));
        }
}
