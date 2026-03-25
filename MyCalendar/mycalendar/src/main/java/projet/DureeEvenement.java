package projet;

public final class DureeEvenement {
    private final int minutes;

    public DureeEvenement(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("La duree doit etre positive ou nulle");
        }
        this.minutes = minutes;
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof DureeEvenement that)) {
            return false;
        }
        return minutes == that.minutes;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(minutes);
    }
}
