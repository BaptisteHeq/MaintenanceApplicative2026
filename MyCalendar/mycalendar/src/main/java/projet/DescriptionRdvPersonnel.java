package projet;

final class DescriptionRdvPersonnel implements DescriptionEvenement {
    @Override
    public String decrire(Event event) {
        return "RDV : " + event.title + " à " + event.dateDebut;
    }
}
