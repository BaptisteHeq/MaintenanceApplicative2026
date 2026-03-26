package projet;

import java.util.Optional;

public final class DureeEvenement {
    private final int minutes;

    public DureeEvenement(int minutes) {
        this.minutes = Optional.of(minutes)
                .filter(m -> m >= 0)
                .orElseThrow(() -> new IllegalArgumentException("La duree doit etre positive ou nulle"));
    }

    public int valeur() {
        return minutes;
    }

    @Override
    public String toString() {
        return String.valueOf(minutes);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DureeEvenement that && minutes == that.minutes;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(minutes);
    }
}
