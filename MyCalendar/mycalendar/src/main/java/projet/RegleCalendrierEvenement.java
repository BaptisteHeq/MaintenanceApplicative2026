package projet;

interface RegleCalendrierEvenement {
    boolean estDansPeriode(Event event, DateHeureEvenement debut, DateHeureEvenement fin);

    boolean participeAuxConflits();
}
