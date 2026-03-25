package projet;

public final class FrequenceJours {
    private final int valeur;

    public FrequenceJours(int valeur) {
        if (valeur < 0) {
            throw new IllegalArgumentException("La frequence doit etre positive ou nulle");
        }
        this.valeur = valeur;
    }

    public int valeur() {
        return valeur;
    }

    @Override
    public String toString() {
        return String.valueOf(valeur);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FrequenceJours that)) {
            return false;
        }
        return valeur == that.valeur;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(valeur);
    }
}
