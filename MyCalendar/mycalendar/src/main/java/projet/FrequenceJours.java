package projet;

import java.util.Optional;

public final class FrequenceJours {
    private final int valeur;

    public FrequenceJours(int valeur) {
        this.valeur = Optional.of(valeur)
                .filter(v -> v >= 0)
                .orElseThrow(() -> new IllegalArgumentException("La frequence doit etre positive ou nulle"));
    }

    public int valeur() {
        return valeur;
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
