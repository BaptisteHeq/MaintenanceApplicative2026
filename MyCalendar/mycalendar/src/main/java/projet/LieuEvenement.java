package projet;

import java.util.Objects;

public final class LieuEvenement {
    private final String valeur;

    public LieuEvenement(String valeur) {
        this.valeur = Objects.requireNonNull(valeur, "Le lieu ne peut pas etre null");
    }

    public String valeur() {
        return valeur;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LieuEvenement other)) {
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
