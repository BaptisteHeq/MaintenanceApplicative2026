package projet;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Map;

public final class TypeEvenement {
    public static final String RDV_PERSONNEL = "RDV_PERSONNEL";
    public static final String REUNION = "REUNION";
    public static final String PERIODIQUE = "PERIODIQUE";

    private static final Map<String, DescriptionEvenement> DESCRIPTIONS = Map.of(
            RDV_PERSONNEL, new DescriptionRdvPersonnel(),
            REUNION, new DescriptionReunion(),
            PERIODIQUE, new DescriptionPeriodique());

    private static final RegleCalendrierEvenement REGLE_PONCTUELLE = new RegleCalendrierPonctuelle();

    private static final Map<String, RegleCalendrierEvenement> REGLES_CALENDRIER = Map.of(
            RDV_PERSONNEL, REGLE_PONCTUELLE,
            REUNION, REGLE_PONCTUELLE,
            PERIODIQUE, new RegleCalendrierPeriodique());

    private final String valeur;
    private final DescriptionEvenement descriptionEvenement;
    private final RegleCalendrierEvenement regleCalendrierEvenement;

    public TypeEvenement(String valeur) {
        this.valeur = Objects.requireNonNull(valeur, "Le type ne peut pas etre null");
        this.descriptionEvenement = Optional.ofNullable(DESCRIPTIONS.get(this.valeur))
                .orElseThrow(() -> new IllegalArgumentException("Type d'evenement non supporte: " + valeur));
        this.regleCalendrierEvenement = Optional.ofNullable(REGLES_CALENDRIER.get(this.valeur))
                .orElseThrow(() -> new IllegalArgumentException("Regle de calendrier absente pour: " + valeur));
    }

    public String valeur() {
        return valeur;
    }

    public boolean estPeriodique() {
        return PERIODIQUE.equals(valeur);
    }

    public String decrire(Event event) {
        return descriptionEvenement.decrire(event);
    }

    public boolean estDansPeriode(Event event, LocalDateTime debut, LocalDateTime fin) {
        return regleCalendrierEvenement.estDansPeriode(event, debut, fin);
    }

    public boolean participeAuxConflits() {
        return regleCalendrierEvenement.participeAuxConflits();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TypeEvenement other)) {
            return false;
        }
        return valeur.equals(other.valeur);
    }

    @Override
    public int hashCode() {
        return valeur.hashCode();
    }

    @Override
    public String toString() {
        return valeur;
    }
}
