package projet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarManager {
    public List<Event> events;

    public CalendarManager() {
        this.events = new ArrayList<>();
    }

    public void ajouterEvent(String type, String title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes,
            String lieu, String participants, int frequenceJours) {
        ajouterEvent(type, title, proprietaire, new DateHeureEvenement(dateDebut), dureeMinutes, lieu, participants,
                frequenceJours);
    }

    public void ajouterEvent(String type, String title, String proprietaire, DateHeureEvenement dateDebut,
            int dureeMinutes,
            String lieu, String participants, int frequenceJours) {
        Event e = new Event(EventId.nouveau(), new TypeEvenement(type), new TitreEvenement(title),
                new ProprietaireEvenement(proprietaire), dateDebut, new DureeEvenement(dureeMinutes),
                new LieuEvenement(lieu), new ParticipantsEvenement(participants), new FrequenceJours(frequenceJours));
        events.add(e);
    }

    public List<Event> eventsDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return eventsDansPeriode(new PeriodeEvenements(new DateHeureEvenement(debut), new DateHeureEvenement(fin)));
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
        DateHeureEvenement fin1 = e1.dateDebut.plusMinutes(e1.dureeMinutes.valeur());
        DateHeureEvenement fin2 = e2.dateDebut.plusMinutes(e2.dureeMinutes.valeur());

        return e1.type.participeAuxConflits()
                && e2.type.participeAuxConflits()
                && e1.dateDebut.isBefore(fin2)
                && fin1.isAfter(e2.dateDebut);
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