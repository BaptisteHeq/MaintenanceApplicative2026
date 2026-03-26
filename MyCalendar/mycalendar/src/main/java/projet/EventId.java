package projet;

import java.util.Objects;
import java.util.UUID;

public final class EventId {
    private final String valeur;

    public EventId(String valeur) {
        String normalise = Objects.requireNonNull(valeur, "L'identifiant est requis").trim();
        if (normalise.isEmpty()) {
            throw new IllegalArgumentException("L'identifiant ne peut pas etre vide");
        }
        this.valeur = normalise;
    }

    public static EventId nouveau() {
        return new EventId(UUID.randomUUID().toString());
    }

    public String valeur() {
        return valeur;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EventId other)) {
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
