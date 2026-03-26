package projet;

import java.util.Objects;

public final class PeriodeEvenements {
    private final DateHeureEvenement debut;
    private final DateHeureEvenement fin;

    public PeriodeEvenements(DateHeureEvenement debut, DateHeureEvenement fin) {
        this.debut = Objects.requireNonNull(debut, "Le debut de periode est requis");
        this.fin = Objects.requireNonNull(fin, "La fin de periode est requise");
        if (debut.isAfter(fin)) {
            throw new IllegalArgumentException("Le debut de periode doit etre avant ou egal a la fin");
        }
    }

    public DateHeureEvenement debut() {
        return debut;
    }

    public DateHeureEvenement fin() {
        return fin;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PeriodeEvenements other)) {
            return false;
        }
        return debut.equals(other.debut) && fin.equals(other.fin);
    }

    @Override
    public int hashCode() {
        return 31 * debut.hashCode() + fin.hashCode();
    }

    @Override
    public String toString() {
        return "PeriodeEvenements[debut=" + debut + ", fin=" + fin + "]";
    }
}
