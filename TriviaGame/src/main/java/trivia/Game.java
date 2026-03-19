package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game implements IGame {
   private static final int MAX_PLAYERS = 6;
   private static final int BOARD_SIZE = 12;
   private static final int QUESTION_COUNT = 50;
   private static final int WINNING_PURSE = 6;

   private final ArrayList<String> players = new ArrayList<>();
   private final int[] places = new int[MAX_PLAYERS];
   private final int[] purses = new int[MAX_PLAYERS];
   private final boolean[] inPenaltyBox = new boolean[MAX_PLAYERS];

   private final LinkedList<String> popQuestions = new LinkedList<>();
   private final LinkedList<String> scienceQuestions = new LinkedList<>();
   private final LinkedList<String> sportsQuestions = new LinkedList<>();
   private final LinkedList<String> rockQuestions = new LinkedList<>();

   private int currentPlayer = 0;
   private boolean isGettingOutOfPenaltyBox;

   public Game() {
      for (int i = 0; i < QUESTION_COUNT; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast(("Science Question " + i));
         sportsQuestions.addLast(("Sports Question " + i));
         rockQuestions.addLast(createRockQuestion(i));
      }
   }

   public String createRockQuestion(int index) {
      return "Rock Question " + index;
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      places[howManyPlayers()] = 1;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;
      players.add(playerName);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > BOARD_SIZE)
               places[currentPlayer] = places[currentPlayer] - BOARD_SIZE;

            System.out.println(players.get(currentPlayer)
                  + "'s new location is "
                  + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         places[currentPlayer] = places[currentPlayer] + roll;
         if (places[currentPlayer] > BOARD_SIZE)
            places[currentPlayer] = places[currentPlayer] - BOARD_SIZE;

         System.out.println(players.get(currentPlayer)
               + "'s new location is "
               + places[currentPlayer]);
         System.out.println("The category is " + currentCategory());
         askQuestion();
      }

   }

   private void askQuestion() {
      if (currentCategory().equals("Pop"))
         System.out.println(popQuestions.remove(0));
      if (currentCategory().equals("Science"))
         System.out.println(scienceQuestions.remove(0));
      if (currentCategory().equals("Sports"))
         System.out.println(sportsQuestions.remove(0));
      if (currentCategory().equals("Rock"))
         System.out.println(rockQuestions.remove(0));
   }

   private String currentCategory() {
      if (places[currentPlayer] - 1 == 0)
         return "Pop";
      if (places[currentPlayer] - 1 == 4)
         return "Pop";
      if (places[currentPlayer] - 1 == 8)
         return "Pop";
      if (places[currentPlayer] - 1 == 1)
         return "Science";
      if (places[currentPlayer] - 1 == 5)
         return "Science";
      if (places[currentPlayer] - 1 == 9)
         return "Science";
      if (places[currentPlayer] - 1 == 2)
         return "Sports";
      if (places[currentPlayer] - 1 == 6)
         return "Sports";
      if (places[currentPlayer] - 1 == 10)
         return "Sports";
      return "Rock";
   }

   public boolean handleCorrectAnswer() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                  + " now has "
                  + purses[currentPlayer]
                  + " Gold Coins.");

            boolean winner = didPlayerWin();
            nextPlayer();

            return winner;
         } else {
            nextPlayer();
            return true;
         }

      } else {

         System.out.println("Answer was corrent!!!!");
         purses[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
               + " now has "
               + purses[currentPlayer]
               + " Gold Coins.");

         boolean winner = didPlayerWin();
         nextPlayer();

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      nextPlayer();
      return true;
   }

   private void nextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size())
         currentPlayer = 0;
   }

   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == WINNING_PURSE);
   }
}
