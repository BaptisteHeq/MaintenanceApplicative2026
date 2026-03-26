package projet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public class CalendarManager {
    @FunctionalInterface
    private interface RegleConflit {
        boolean applique(Event e1, Event e2);
    }

    public List<Event> events;
    private final Map<String, RegleConflit> reglesConflit;

    public CalendarManager() {
        this.events = new ArrayList<>();
        this.reglesConflit = Map.of(
                cle(TypeEvenement.RDV_PERSONNEL, TypeEvenement.RDV_PERSONNEL), this::conflitPonctuels,
                cle(TypeEvenement.RDV_PERSONNEL, TypeEvenement.REUNION), this::conflitPonctuels,
                cle(TypeEvenement.REUNION, TypeEvenement.RDV_PERSONNEL), this::conflitPonctuels,
                cle(TypeEvenement.REUNION, TypeEvenement.REUNION), this::conflitPonctuels,
                cle(TypeEvenement.PERIODIQUE, TypeEvenement.RDV_PERSONNEL), this::conflitPeriodiqueEtPonctuel,
                cle(TypeEvenement.PERIODIQUE, TypeEvenement.REUNION), this::conflitPeriodiqueEtPonctuel,
                cle(TypeEvenement.RDV_PERSONNEL, TypeEvenement.PERIODIQUE), this::conflitPonctuelEtPeriodique,
                cle(TypeEvenement.REUNION, TypeEvenement.PERIODIQUE), this::conflitPonctuelEtPeriodique,
                cle(TypeEvenement.PERIODIQUE, TypeEvenement.PERIODIQUE), this::conflitDeuxPeriodiques);
    }

    public void ajouterEvent(TypeEvenement type, TitreEvenement title, ProprietaireEvenement proprietaire,
            DateHeureEvenement dateDebut, DureeEvenement dureeMinutes, LieuEvenement lieu,
            ParticipantsEvenement participants, FrequenceJours frequenceJours) {
        Event e = new Event(EventId.nouveau(), type, title, proprietaire, dateDebut, dureeMinutes, lieu, participants,
                frequenceJours);
        events.add(e);
    }

    public List<Event> eventsDansPeriode(DateHeureEvenement debut, DateHeureEvenement fin) {
        return eventsDansPeriode(new PeriodeEvenements(debut, fin));
    }

    public List<Event> eventsDansPeriode(PeriodeEvenements periode) {
        return events.stream().filter(e -> e.type.estDansPeriode(e, periode.debut(), periode.fin())).toList();
    }

    public boolean conflit(Event e1, Event e2) {
        return reglesConflit.get(cle(e1.type.valeur(), e2.type.valeur())).applique(e1, e2);
    }

    private boolean conflitPonctuels(Event e1, Event e2) {
        return chevauchement(e1.dateDebut.valeur(), e1.dureeMinutes.valeur(), e2.dateDebut.valeur(),
                e2.dureeMinutes.valeur());
    }

    private boolean conflitPonctuelEtPeriodique(Event ponctuel, Event periodique) {
        return conflitPeriodiqueEtPonctuel(periodique, ponctuel);
    }

    private boolean conflitPeriodiqueEtPonctuel(Event periodique, Event ponctuel) {
        int frequence = Math.max(1, periodique.frequenceJours.valeur());

        LocalDateTime ponctuelDebut = ponctuel.dateDebut.valeur();
        LocalDateTime ponctuelFin = ponctuelDebut.plusMinutes(ponctuel.dureeMinutes.valeur());
        LocalDateTime occurrence = premiereOccurrenceProche(periodique, ponctuelDebut, frequence);
        long limite = Math.max(1L,
                ChronoUnit.DAYS.between(occurrence.toLocalDate(), ponctuelFin.toLocalDate()) / frequence + 2);

        return Stream.iterate(occurrence, d -> d.plusDays(frequence))
                .limit(limite)
                .anyMatch(o -> chevauchement(o, periodique.dureeMinutes.valeur(), ponctuelDebut,
                        ponctuel.dureeMinutes.valeur()));
    }

    private boolean conflitDeuxPeriodiques(Event e1, Event e2) {
        int f1 = Math.max(1, e1.frequenceJours.valeur());
        int f2 = Math.max(1, e2.frequenceJours.valeur());

        LocalDateTime debutFenetre = Stream.of(e1.dateDebut.valeur(), e2.dateDebut.valeur())
                .max(LocalDateTime::compareTo)
                .orElse(e1.dateDebut.valeur());
        long lcmJours = lcm(f1, f2);
        LocalDateTime finFenetre = debutFenetre.plusDays(lcmJours + 1);

        LocalDateTime occ1 = premiereOccurrenceProche(e1, debutFenetre, f1);
        long limite = Math.max(1L, ChronoUnit.DAYS.between(occ1.toLocalDate(), finFenetre.toLocalDate()) / f1 + 2);

        return Stream.iterate(occ1, d -> d.plusDays(f1))
                .limit(limite)
                .anyMatch(occurrence1 -> {
                    LocalDateTime occ2 = premiereOccurrenceProche(e2, occurrence1, f2);
                    return Stream.of(occ2, occ2.minusDays(f2))
                            .filter(o -> !o.isBefore(e2.dateDebut.valeur()))
                            .anyMatch(o -> chevauchement(occurrence1, e1.dureeMinutes.valeur(), o,
                                    e2.dureeMinutes.valeur()));
                });
    }

    private LocalDateTime premiereOccurrenceProche(Event event, LocalDateTime reference, int frequence) {
        LocalDateTime debut = event.dateDebut.valeur();
        long jours = ChronoUnit.DAYS.between(debut.toLocalDate(), reference.toLocalDate());
        long cycles = Math.max(0, (jours / Math.max(1, frequence)) - 1);
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
        return Math.max(1, x);
    }

    private String cle(String type1, String type2) {
        return type1 + "|" + type2;
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