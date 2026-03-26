package projet;

final class DescriptionReunion implements DescriptionEvenement {
    @Override
    public String decrire(Event event) {
        return "Réunion : " + event.title + " à " + event.lieu + " avec " + event.participants;
    }
}
