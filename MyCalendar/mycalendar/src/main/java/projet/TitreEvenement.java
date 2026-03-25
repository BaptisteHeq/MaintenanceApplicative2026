package projet;

import java.util.Objects;
import java.util.Optional;

public final class TitreEvenement {
    private final String valeur;

    public TitreEvenement(String valeur) {
        this.valeur = Optional.of(Objects.requireNonNull(valeur, "Le titre est requis"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Le titre ne peut pas être vide"));
    }

    public String valeur() {
        return valeur;
    }

    @Override
    public String toString() {
        return valeur;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof TitreEvenement autre) && valeur.equals(autre.valeur);
    }

    @Override
    public int hashCode() {
        return valeur.hashCode();
    }
}