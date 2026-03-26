package projet;

import java.util.Objects;
import java.util.Optional;

public final class ProprietaireEvenement {
    private final String valeur;

    public ProprietaireEvenement(String valeur) {
        String normalise = Objects.requireNonNull(valeur, "Le proprietaire est requis").trim();
        this.valeur = Optional.of(normalise)
                .filter(v -> !v.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Le proprietaire ne peut pas etre vide"));
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
        return o instanceof ProprietaireEvenement that && valeur.equals(that.valeur);
    }

    @Override
    public int hashCode() {
        return valeur.hashCode();
    }
}
