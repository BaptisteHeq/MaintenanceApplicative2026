package projet;

import java.time.LocalDateTime;

public class Event {
    public TypeEvenement type;
    public TitreEvenement title;
    public ProprietaireEvenement proprietaire;
    public LocalDateTime dateDebut;
    public DureeEvenement dureeMinutes;
    public LieuEvenement lieu; // utilise seulement pour REUNION
    public ParticipantsEvenement participants; // separes par virgules (pour REUNION uniquement)
    public FrequenceJours frequenceJours; // uniquement pour PERIODIQUE

    public Event(String type, String title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes,
            String lieu, String participants, int frequenceJours) {
        this(new TypeEvenement(type), new TitreEvenement(title), new ProprietaireEvenement(proprietaire), dateDebut,
                new DureeEvenement(dureeMinutes), new LieuEvenement(lieu),
                new ParticipantsEvenement(participants),
                new FrequenceJours(frequenceJours));
    }

    public Event(TypeEvenement type, TitreEvenement title, ProprietaireEvenement proprietaire, LocalDateTime dateDebut,
            DureeEvenement dureeMinutes,
            LieuEvenement lieu, ParticipantsEvenement participants, FrequenceJours frequenceJours) {
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
        if (TypeEvenement.RDV_PERSONNEL.equals(type.valeur())) {
            return "RDV : " + title + " à " + dateDebut.toString();
        }
        if (TypeEvenement.REUNION.equals(type.valeur())) {
            return "Réunion : " + title + " à " + lieu + " avec " + participants;
        }
        return "Événement périodique : " + title + " tous les " + frequenceJours.valeur() + " jours";
    }
}