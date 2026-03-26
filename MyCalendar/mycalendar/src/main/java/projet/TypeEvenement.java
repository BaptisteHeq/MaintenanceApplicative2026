package projet;

import java.util.Objects;
import java.util.Set;

public final class TypeEvenement {
    public static final String RDV_PERSONNEL = "RDV_PERSONNEL";
    public static final String REUNION = "REUNION";
    public static final String PERIODIQUE = "PERIODIQUE";

    private static final Set<String> TYPES_VALIDES = Set.of(RDV_PERSONNEL, REUNION, PERIODIQUE);

    private final String valeur;

    public TypeEvenement(String valeur) {
        this.valeur = Objects.requireNonNull(valeur, "Le type ne peut pas etre null");
        if (!TYPES_VALIDES.contains(this.valeur)) {
            throw new IllegalArgumentException("Type d'evenement non supporte: " + valeur);
        }
    }

    public String valeur() {
        return valeur;
    }

    public boolean estPeriodique() {
        return PERIODIQUE.equals(valeur);
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
