package projet;

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

    private final String valeur;
    private final DescriptionEvenement descriptionEvenement;

    public TypeEvenement(String valeur) {
        this.valeur = Objects.requireNonNull(valeur, "Le type ne peut pas etre null");
        this.descriptionEvenement = Optional.ofNullable(DESCRIPTIONS.get(this.valeur))
                .orElseThrow(() -> new IllegalArgumentException("Type d'evenement non supporte: " + valeur));
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
