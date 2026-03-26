package projet;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MyCalendarTest {

    @Test
    void afficherListeAvecListeVide() throws Exception {
        Method method = Main.class.getDeclaredMethod("afficherListe", List.class);
        method.setAccessible(true);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));
        try {
            method.invoke(null, Collections.emptyList());
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(out.toString().contains("Aucun événement trouvé pour cette période."));
    }

    @Test
    void afficherListeAvecElements() throws Exception {
        Method method = Main.class.getDeclaredMethod("afficherListe", List.class);
        method.setAccessible(true);

        Event e = new Event(new TypeEvenement(TypeEvenement.RDV_PERSONNEL), new TitreEvenement("Dentiste"),
                new ProprietaireEvenement("Alice"),
                new DateHeureEvenement(LocalDateTime.of(2026, 3, 20, 10, 0)),
                new DureeEvenement(30), new LieuEvenement(""), new ParticipantsEvenement(""),
                new FrequenceJours(0));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));
        try {
            method.invoke(null, List.of(e));
        } finally {
            System.setOut(originalOut);
        }

        String output = out.toString();
        assertTrue(output.contains("Événements trouvés :"));
        assertTrue(output.contains("- ID="));
        assertTrue(output.contains("RDV : Dentiste à 2026-03-20T10:00"));
    }

    @Test
    void mainParcoursCreationCompteEtActions() {
        String input = String.join("\n",
                "2", // création compte
                "alice",
                "pwd",
                "pwd",
                "2", // ajouter rdv
                "Dentiste",
                "2026", "3", "20", "10", "0", "30",
                "3", // ajouter réunion
                "Sprint",
                "2026", "3", "20", "11", "0", "45",
                "Salle A",
                "oui",
                "bob",
                "non",
                "4", // ajouter périodique
                "Sport",
                "2026", "3", "21", "7", "0", "7",
                "5", // suppression par id
                "inconnu",
                "1", // menu visualisation
                "1", // afficher tous
                "6", // déconnexion
                "n" // continuer ? non
        ) + "\n";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        runMainWithInput(input, out);

        String output = out.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Événement ajouté."));
        assertTrue(output.contains("Aucun événement trouvé avec cet identifiant."));
        assertTrue(output.contains("ID="));
        assertTrue(output.contains("Déconnexion"));
    }

    @Test
    void mainParcoursConnexionRogerEtVisualisations() {
        String input = String.join("\n",
                "1", // se connecter
                "Roger",
                "Chat",
                "1", // visualiser
                "2", // mois
                "2026", "3",
                "1", // visualiser
                "3", // semaine
                "2026", "12",
                "1", // visualiser
                "4", // jour
                "2026", "3", "20",
                "6", // déconnexion
                "n" // continuer ? non
        ) + "\n";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        runMainWithInput(input, out);

        String output = out.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Bonjour, Roger"));
        assertTrue(output.contains("Aucun événement trouvé pour cette période."));
    }

    @Test
    void mainParcoursConnexionPierreEtEchecPuisSortie() {
        String input = String.join("\n",
                "1", // connexion
                "Pierre",
                "wrong", // mauvais mot de passe
                "2", // création compte échouée
                "bob",
                "x",
                "y") + "\n";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            runMainWithInput(input, out);
        } catch (Exception ignored) {
            // Le programme lit en boucle; on accepte la fin sur entrée épuisée.
        }

        String output = out.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Les mots de passes ne correspondent pas"));
    }

    private void runMainWithInput(String input, ByteArrayOutputStream out) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        System.setOut(new PrintStream(out));
        System.setIn(in);
        try {
            Main.main(new String[0]);
        } catch (java.util.NoSuchElementException ignored) {
            // Main lit en boucle; lorsqu'il n'y a plus d'entrée, on considère le scénario
            // terminé.
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }
}
