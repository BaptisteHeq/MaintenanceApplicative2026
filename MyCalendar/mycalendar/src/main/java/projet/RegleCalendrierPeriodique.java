package projet;

import java.time.LocalDateTime;

final class RegleCalendrierPeriodique implements RegleCalendrierEvenement {
    @Override
    public boolean estDansPeriode(Event event, LocalDateTime debut, LocalDateTime fin) {
        LocalDateTime occurrence = event.dateDebut;
        while (occurrence.isBefore(fin)) {
            if (!occurrence.isBefore(debut)) {
                return true;
            }
            occurrence = occurrence.plusDays(event.frequenceJours.valeur());
        }
        return false;
    }

    @Override
    public boolean participeAuxConflits() {
        return false;
    }
}
