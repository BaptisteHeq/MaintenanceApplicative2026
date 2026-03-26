package projet;

import java.util.Map;
import java.util.Optional;

public final class FrequenceJours {
    public static final int AUCUNE = 0;
    public static final int HEBDOMADAIRE = 7;
    public static final int MENSUELLE = 30;
    public static final int ANNUELLE = 365;

    private static final Map<Integer, String> LIBELLES = Map.of(
            AUCUNE, "aucune",
            HEBDOMADAIRE, "hebdomadaire",
            MENSUELLE, "mensuelle",
            ANNUELLE, "annuelle");

    private final int valeur;

    public FrequenceJours(int valeur) {
        this.valeur = Optional.of(valeur)
                .filter(LIBELLES::containsKey)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Frequence invalide. Valeurs autorisees: 0, 7 (hebdo), 30 (mensuelle), 365 (annuelle)"));
    }

    public int valeur() {
        return valeur;
    }

    public String libelle() {
        return LIBELLES.get(valeur);
    }

    @Override
    public String toString() {
        return String.valueOf(valeur);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FrequenceJours that && valeur == that.valeur;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(valeur);
    }
}
