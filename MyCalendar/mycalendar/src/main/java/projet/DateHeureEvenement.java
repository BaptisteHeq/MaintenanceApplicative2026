package projet;

import java.time.LocalDateTime;
import java.util.Objects;

public final class DateHeureEvenement {
    private final LocalDateTime valeur;

    public DateHeureEvenement(LocalDateTime valeur) {
        this.valeur = Objects.requireNonNull(valeur, "La date et l'heure sont requises");
    }

    public LocalDateTime valeur() {
        return valeur;
    }

    public DateHeureEvenement plusDays(int jours) {
        return new DateHeureEvenement(valeur.plusDays(jours));
    }

    public DateHeureEvenement plusMinutes(int minutes) {
        return new DateHeureEvenement(valeur.plusMinutes(minutes));
    }

    public boolean isBefore(DateHeureEvenement autre) {
        return valeur.isBefore(autre.valeur);
    }

    public boolean isAfter(DateHeureEvenement autre) {
        return valeur.isAfter(autre.valeur);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DateHeureEvenement other)) {
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
        return valeur.toString();
    }
}
