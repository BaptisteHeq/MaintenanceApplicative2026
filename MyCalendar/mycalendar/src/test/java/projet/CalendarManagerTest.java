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
                manager.ajouterEvent("RDV_PERSONNEL", "Dentiste", "Alice", date, 30, "", "", 0);

                List<Event> result = manager.eventsDansPeriode(
                                LocalDateTime.of(2026, 3, 20, 0, 0),
                                LocalDateTime.of(2026, 3, 20, 23, 59));

                assertEquals(1, result.size());
                assertEquals("Dentiste", result.get(0).title.valeur());
        }

        @Test
        void evenementHorsPeriodeNonRetourne() {
                CalendarManager manager = new CalendarManager();

                manager.ajouterEvent("RDV_PERSONNEL", "Dentiste", "Alice",
                                LocalDateTime.of(2026, 3, 20, 10, 0), 30, "", "", 0);

                List<Event> result = manager.eventsDansPeriode(
                                LocalDateTime.of(2026, 3, 21, 0, 0),
                                LocalDateTime.of(2026, 3, 21, 23, 59));

                assertTrue(result.isEmpty());
        }

        @Test
        void evenementApresPeriodeNonRetourne() {
                CalendarManager manager = new CalendarManager();

                manager.ajouterEvent("RDV_PERSONNEL", "Dentiste", "Alice",
                                LocalDateTime.of(2026, 3, 22, 10, 0), 30, "", "", 0);

                List<Event> result = manager.eventsDansPeriode(
                                LocalDateTime.of(2026, 3, 21, 0, 0),
                                LocalDateTime.of(2026, 3, 21, 23, 59));

                assertTrue(result.isEmpty());
        }

        @Test
        void evenementPeriodiqueRetourneSiOccurrenceDansPeriode() {
                CalendarManager manager = new CalendarManager();

                manager.ajouterEvent("PERIODIQUE", "Sport", "Alice",
                                LocalDateTime.of(2026, 3, 1, 8, 0), 0, "", "", 2);

                List<Event> result = manager.eventsDansPeriode(
                                LocalDateTime.of(2026, 3, 5, 0, 0),
                                LocalDateTime.of(2026, 3, 5, 23, 59));

                assertEquals(1, result.size());
                assertEquals("Sport", result.get(0).title.valeur());
        }

        @Test
        void evenementPeriodiqueNonRetourneSiAucuneOccurrenceDansPeriode() {
                CalendarManager manager = new CalendarManager();

                manager.ajouterEvent("PERIODIQUE", "Sport", "Alice",
                                LocalDateTime.of(2026, 3, 1, 8, 0), 0, "", "", 2);

                List<Event> result = manager.eventsDansPeriode(
                                LocalDateTime.of(2026, 3, 4, 0, 0),
                                LocalDateTime.of(2026, 3, 4, 23, 59));

                assertTrue(result.isEmpty());
        }

        @Test
        void conflitRetourneTruePourChevauchement() {
                CalendarManager manager = new CalendarManager();

                Event e1 = new Event("RDV_PERSONNEL", "A", "Alice", LocalDateTime.of(2026, 3, 20, 10, 0),
                                60, "", "", 0);
                Event e2 = new Event("REUNION", "B", "Bob", LocalDateTime.of(2026, 3, 20, 10, 30),
                                30, "Salle", "Bob", 0);

                assertTrue(manager.conflit(e1, e2));
        }

        @Test
        void conflitRetourneFalseSiPasChevauchement() {
                CalendarManager manager = new CalendarManager();

                Event e1 = new Event("RDV_PERSONNEL", "A", "Alice", LocalDateTime.of(2026, 3, 20, 10, 0),
                                30, "", "", 0);
                Event e2 = new Event("REUNION", "B", "Bob", LocalDateTime.of(2026, 3, 20, 11, 0),
                                30, "Salle", "Bob", 0);

                assertFalse(manager.conflit(e1, e2));
        }

        @Test
        void conflitRetourneFalseSiPeriodique() {
                CalendarManager manager = new CalendarManager();

                Event e1 = new Event("PERIODIQUE", "A", "Alice", LocalDateTime.of(2026, 3, 20, 10, 0),
                                30, "", "", 1);
                Event e2 = new Event("REUNION", "B", "Bob", LocalDateTime.of(2026, 3, 20, 10, 15),
                                30, "Salle", "Bob", 0);

                assertFalse(manager.conflit(e1, e2));
        }

        @Test
        void conflitRetourneFalseSiDeuxiemeEstPeriodique() {
                CalendarManager manager = new CalendarManager();

                Event e1 = new Event("REUNION", "A", "Alice", LocalDateTime.of(2026, 3, 20, 10, 0),
                                30, "Salle", "Alice", 0);
                Event e2 = new Event("PERIODIQUE", "B", "Bob", LocalDateTime.of(2026, 3, 20, 10, 15),
                                30, "", "", 1);

                assertFalse(manager.conflit(e1, e2));
        }

        @Test
        void conflitRetourneFalseSiPremierCommenceApresFinSecond() {
                CalendarManager manager = new CalendarManager();

                Event e1 = new Event("RDV_PERSONNEL", "A", "Alice", LocalDateTime.of(2026, 3, 20, 12, 0),
                                30, "", "", 0);
                Event e2 = new Event("REUNION", "B", "Bob", LocalDateTime.of(2026, 3, 20, 10, 0),
                                30, "Salle", "Bob", 0);

                assertFalse(manager.conflit(e1, e2));
        }

        @Test
        void afficherEvenementsEcritLesDescriptions() {
                CalendarManager manager = new CalendarManager();
                manager.ajouterEvent("RDV_PERSONNEL", "Dentiste", "Alice",
                                LocalDateTime.of(2026, 3, 20, 10, 0), 30, "", "", 0);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                PrintStream originalOut = System.out;
                System.setOut(new PrintStream(out));
                try {
                        manager.afficherEvenements();
                } finally {
                        System.setOut(originalOut);
                }

                String output = out.toString();
                assertTrue(output.contains("RDV : Dentiste à 2026-03-20T10:00"));
        }

        @Test
        void supprimerEventSupprimeLEvenementCible() {
                CalendarManager manager = new CalendarManager();
                manager.ajouterEvent("RDV_PERSONNEL", "Dentiste", "Alice",
                                LocalDateTime.of(2026, 3, 20, 10, 0), 30, "", "", 0);

                EventId id = manager.events.get(0).id;

                boolean supprime = manager.supprimerEvent(id);

                assertTrue(supprime);
                assertTrue(manager.events.isEmpty());
        }

        @Test
        void supprimerEventRetourneFalseSiIdAbsent() {
                CalendarManager manager = new CalendarManager();
                manager.ajouterEvent("RDV_PERSONNEL", "Dentiste", "Alice",
                                LocalDateTime.of(2026, 3, 20, 10, 0), 30, "", "", 0);

                boolean supprime = manager.supprimerEvent(new EventId("inconnu"));

                assertFalse(supprime);
                assertEquals(1, manager.events.size());
        }
}
