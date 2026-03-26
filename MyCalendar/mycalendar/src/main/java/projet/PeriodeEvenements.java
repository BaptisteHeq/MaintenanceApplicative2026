package projet;

import java.util.Objects;
import java.util.Optional;

public final class PeriodeEvenements {
    private final DateHeureEvenement debut;
    private final DateHeureEvenement fin;

    public PeriodeEvenements(DateHeureEvenement debut, DateHeureEvenement fin) {
        this.debut = Objects.requireNonNull(debut, "Le debut de periode est requis");
        this.fin = Objects.requireNonNull(fin, "La fin de periode est requise");
        Optional.of(this.debut)
                .filter(d -> !d.isAfter(this.fin))
                .orElseThrow(
                        () -> new IllegalArgumentException("Le debut de periode doit etre avant ou egal a la fin"));
    }

    public DateHeureEvenement debut() {
        return debut;
    }

    public DateHeureEvenement fin() {
        return fin;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PeriodeEvenements other && debut.equals(other.debut) && fin.equals(other.fin);
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
