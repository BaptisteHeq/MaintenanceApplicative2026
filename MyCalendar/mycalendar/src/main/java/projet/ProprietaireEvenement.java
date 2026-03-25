package projet;

import java.util.Objects;

public final class ProprietaireEvenement {
    private final String valeur;

    public ProprietaireEvenement(String valeur) {
        String normalise = Objects.requireNonNull(valeur, "Le proprietaire est requis").trim();
        if (normalise.isEmpty()) {
            throw new IllegalArgumentException("Le proprietaire ne peut pas etre vide");
        }
        this.valeur = normalise;
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProprietaireEvenement that)) {
            return false;
        }
        return valeur.equals(that.valeur);
    }

    @Override
    public int hashCode() {
        return valeur.hashCode();
    }
}
