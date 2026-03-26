package projet;

import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

final class RegleCalendrierPeriodique implements RegleCalendrierEvenement {
    @Override
    public boolean estDansPeriode(Event event, DateHeureEvenement debut, DateHeureEvenement fin) {
        int frequence = Math.max(1, event.frequenceJours.valeur());
        long limite = Math.max(1L,
                ChronoUnit.DAYS.between(event.dateDebut.valeur().toLocalDate(), fin.valeur().toLocalDate())
                        / frequence + 2);

        return Stream.iterate(event.dateDebut, d -> d.plusDays(frequence))
                .limit(limite)
                .anyMatch(o -> !o.isBefore(debut) && !o.isAfter(fin));
    }

    @Override
    public boolean participeAuxConflits() {
        return false;
    }
}
