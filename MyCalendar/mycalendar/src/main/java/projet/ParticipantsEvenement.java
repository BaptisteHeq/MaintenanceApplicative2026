package projet;

import java.util.Objects;

public final class ParticipantsEvenement {
    private final String valeur;

    public ParticipantsEvenement(String valeur) {
        this.valeur = Objects.requireNonNull(valeur, "Les participants ne peuvent pas etre null");
    }

    public String valeur() {
        return valeur;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ParticipantsEvenement other && valeur.equals(other.valeur);
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
