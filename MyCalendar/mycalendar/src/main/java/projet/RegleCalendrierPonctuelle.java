package projet;

final class RegleCalendrierPonctuelle implements RegleCalendrierEvenement {
    @Override
    public boolean estDansPeriode(Event event, DateHeureEvenement debut, DateHeureEvenement fin) {
        return !event.dateDebut.isBefore(debut) && !event.dateDebut.isAfter(fin);
    }

    @Override
    public boolean participeAuxConflits() {
        return true;
    }
}
