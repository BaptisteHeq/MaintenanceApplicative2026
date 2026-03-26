package projet;

final class DescriptionPeriodique implements DescriptionEvenement {
    @Override
    public String decrire(Event event) {
        return "Événement périodique : " + event.title + " tous les " + event.frequenceJours.valeur() + " jours";
    }
}
