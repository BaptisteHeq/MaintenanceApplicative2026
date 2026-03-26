package projet;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CalendarManager {
    public List<Event> events;

    public CalendarManager() {
        this.events = new ArrayList<>();
    }

    public void ajouterEvent(String type, String title, String proprietaire, DateHeureEvenement dateDebut,
            int dureeMinutes,
            String lieu, String participants, int frequenceJours) {
        Event e = new Event(EventId.nouveau(), new TypeEvenement(type), new TitreEvenement(title),
                new ProprietaireEvenement(proprietaire), dateDebut, new DureeEvenement(dureeMinutes),
                new LieuEvenement(lieu), new ParticipantsEvenement(participants), new FrequenceJours(frequenceJours));
        events.add(e);
    }

    public List<Event> eventsDansPeriode(DateHeureEvenement debut, DateHeureEvenement fin) {
        return eventsDansPeriode(new PeriodeEvenements(debut, fin));
    }

    public List<Event> eventsDansPeriode(PeriodeEvenements periode) {
        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.type.estDansPeriode(e, periode.debut(), periode.fin())) {
                result.add(e);
            }
        }
        return result;
    }

    public boolean conflit(Event e1, Event e2) {
        if (!e1.type.estPeriodique() && !e2.type.estPeriodique()) {
            return chevauchement(e1.dateDebut.valeur(), e1.dureeMinutes.valeur(), e2.dateDebut.valeur(),
                    e2.dureeMinutes.valeur());
        }

        if (e1.type.estPeriodique() && e2.type.estPeriodique()) {
            return conflitDeuxPeriodiques(e1, e2);
        }

        Event periodique = e1.type.estPeriodique() ? e1 : e2;
        Event ponctuel = e1.type.estPeriodique() ? e2 : e1;
        return conflitPeriodiqueEtPonctuel(periodique, ponctuel);
    }

    private boolean conflitPeriodiqueEtPonctuel(Event periodique, Event ponctuel) {
        int frequence = periodique.frequenceJours.valeur();
        if (frequence <= 0) {
            return chevauchement(periodique.dateDebut.valeur(), periodique.dureeMinutes.valeur(),
                    ponctuel.dateDebut.valeur(), ponctuel.dureeMinutes.valeur());
        }

        LocalDateTime ponctuelDebut = ponctuel.dateDebut.valeur();
        LocalDateTime ponctuelFin = ponctuelDebut.plusMinutes(ponctuel.dureeMinutes.valeur());
        LocalDateTime occurrence = premiereOccurrenceProche(periodique, ponctuelDebut, frequence);

        while (occurrence.isBefore(ponctuelFin)) {
            if (chevauchement(occurrence, periodique.dureeMinutes.valeur(), ponctuelDebut,
                    ponctuel.dureeMinutes.valeur())) {
                return true;
            }
            occurrence = occurrence.plusDays(frequence);
        }
        return false;
    }

    private boolean conflitDeuxPeriodiques(Event e1, Event e2) {
        int f1 = e1.frequenceJours.valeur();
        int f2 = e2.frequenceJours.valeur();

        if (f1 <= 0 || f2 <= 0) {
            if (f1 <= 0 && f2 <= 0) {
                return chevauchement(e1.dateDebut.valeur(), e1.dureeMinutes.valeur(), e2.dateDebut.valeur(),
                        e2.dureeMinutes.valeur());
            }
            return f1 <= 0 ? conflitPeriodiqueEtPonctuel(e2, e1) : conflitPeriodiqueEtPonctuel(e1, e2);
        }

        LocalDateTime debutFenetre = e1.dateDebut.valeur().isAfter(e2.dateDebut.valeur())
                ? e1.dateDebut.valeur()
                : e2.dateDebut.valeur();
        long lcmJours = lcm(f1, f2);
        LocalDateTime finFenetre = debutFenetre.plusDays(lcmJours + 1);

        LocalDateTime occ1 = premiereOccurrenceProche(e1, debutFenetre, f1);
        while (occ1.isBefore(finFenetre)) {
            LocalDateTime occ2 = premiereOccurrenceProche(e2, occ1, f2);
            if (chevauchement(occ1, e1.dureeMinutes.valeur(), occ2, e2.dureeMinutes.valeur())) {
                return true;
            }

            LocalDateTime occ2Precedente = occ2.minusDays(f2);
            if (!occ2Precedente.isBefore(e2.dateDebut.valeur())
                    && chevauchement(occ1, e1.dureeMinutes.valeur(), occ2Precedente, e2.dureeMinutes.valeur())) {
                return true;
            }
            occ1 = occ1.plusDays(f1);
        }
        return false;
    }

    private LocalDateTime premiereOccurrenceProche(Event event, LocalDateTime reference, int frequence) {
        LocalDateTime debut = event.dateDebut.valeur();
        if (debut.isAfter(reference) || frequence <= 0) {
            return debut;
        }

        long jours = ChronoUnit.DAYS.between(debut.toLocalDate(), reference.toLocalDate());
        long cycles = Math.max(0, (jours / frequence) - 1);
        return debut.plusDays(cycles * frequence);
    }

    private boolean chevauchement(LocalDateTime debut1, int duree1, LocalDateTime debut2, int duree2) {
        LocalDateTime fin1 = debut1.plusMinutes(duree1);
        LocalDateTime fin2 = debut2.plusMinutes(duree2);
        return debut1.isBefore(fin2) && fin1.isAfter(debut2);
    }

    private long lcm(long a, long b) {
        return (a / pgcd(a, b)) * b;
    }

    private long pgcd(long a, long b) {
        long x = Math.abs(a);
        long y = Math.abs(b);
        while (y != 0) {
            long tmp = x % y;
            x = y;
            y = tmp;
        }
        return x == 0 ? 1 : x;
    }

    public void afficherEvenements() {
        for (Event e : events) {
            System.out.println("ID=" + e.id + " | " + e.description());
        }
    }

    public boolean supprimerEvent(EventId id) {
        return events.removeIf(event -> event.id.equals(id));
    }
}