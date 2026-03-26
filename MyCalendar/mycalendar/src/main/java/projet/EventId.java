package projet;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public final class EventId {
    private static final AtomicLong COMPTEUR = new AtomicLong(0);

    private final String valeur;

    public EventId(String valeur) {
        String normalise = Objects.requireNonNull(valeur, "L'identifiant est requis").trim();
        this.valeur = Optional.of(normalise)
                .filter(v -> !v.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("L'identifiant ne peut pas etre vide"));
    }

    public static EventId nouveau() {
        return new EventId(String.valueOf(COMPTEUR.incrementAndGet()));
    }

    public String valeur() {
        return valeur;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EventId other && valeur.equals(other.valeur);
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
