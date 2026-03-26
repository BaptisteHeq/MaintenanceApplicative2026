package projet;

import java.time.LocalDateTime;

final class RegleCalendrierPonctuelle implements RegleCalendrierEvenement {
    @Override
    public boolean estDansPeriode(Event event, LocalDateTime debut, LocalDateTime fin) {
        return !event.dateDebut.isBefore(debut) && !event.dateDebut.isAfter(fin);
    }

    @Override
    public boolean participeAuxConflits() {
        return true;
    }
}
