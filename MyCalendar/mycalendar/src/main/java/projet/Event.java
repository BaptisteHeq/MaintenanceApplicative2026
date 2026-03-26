package projet;

public class Event {
    public EventId id;
    public TypeEvenement type;
    public TitreEvenement title;
    public ProprietaireEvenement proprietaire;
    public DateHeureEvenement dateDebut;
    public DureeEvenement dureeMinutes;
    public LieuEvenement lieu; // utilise seulement pour REUNION
    public ParticipantsEvenement participants; // separes par virgules (pour REUNION uniquement)
    public FrequenceJours frequenceJours; // uniquement pour PERIODIQUE

    public Event(String type, String title, String proprietaire, DateHeureEvenement dateDebut, int dureeMinutes,
            String lieu, String participants, int frequenceJours) {
        this(EventId.nouveau(), new TypeEvenement(type), new TitreEvenement(title),
                new ProprietaireEvenement(proprietaire), dateDebut,
                new DureeEvenement(dureeMinutes), new LieuEvenement(lieu),
                new ParticipantsEvenement(participants),
                new FrequenceJours(frequenceJours));
    }

    public Event(TypeEvenement type, TitreEvenement title, ProprietaireEvenement proprietaire,
            DateHeureEvenement dateDebut,
            DureeEvenement dureeMinutes,
            LieuEvenement lieu, ParticipantsEvenement participants, FrequenceJours frequenceJours) {
        this(EventId.nouveau(), type, title, proprietaire, dateDebut, dureeMinutes, lieu,
                participants, frequenceJours);
    }

    public Event(EventId id, TypeEvenement type, TitreEvenement title, ProprietaireEvenement proprietaire,
            DateHeureEvenement dateDebut,
            DureeEvenement dureeMinutes,
            LieuEvenement lieu, ParticipantsEvenement participants, FrequenceJours frequenceJours) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.dureeMinutes = dureeMinutes;
        this.lieu = lieu;
        this.participants = participants;
        this.frequenceJours = frequenceJours;
    }

    public String description() {
        return type.decrire(this);
    }
}