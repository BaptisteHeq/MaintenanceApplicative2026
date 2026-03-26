package projet;

import java.time.LocalDateTime;

interface RegleCalendrierEvenement {
    boolean estDansPeriode(Event event, LocalDateTime debut, LocalDateTime fin);

    boolean participeAuxConflits();
}
