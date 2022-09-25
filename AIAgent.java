import java.util.*;

public class AIAgent {
  Random rand;

  public AIAgent() {
    rand = new Random();
  }

  /*
   * The method randomMove takes as input a stack of potential moves that the AI
   * agent
   * can make. The agent uses a rondom number generator to randomly select a move
   * from
   * the inputted Stack and returns this to the calling agent.
   */

  public Move randomMove(Stack possibilities) {

    int moveID = rand.nextInt(possibilities.size());
    System.out.println("Agent randomly selected move : " + moveID);
    for (int i = 1; i < (possibilities.size() - (moveID)); i++) {
      possibilities.pop();
    }
    Move selectedMove = (Move) possibilities.pop();
    return selectedMove;
  }

  // select the lvl of the ai opponet
  public Move SelectedStrategy(int strategy, Stack whiteMoves, Stack blackMoves, Stack White) {
    Move move = new Move();
    if (strategy == 0) {
      move = randomMove(whiteMoves);
      return move;
    } else if (strategy == 1) {
      move = nextBestMove(whiteMoves);
      return move;
    } else {
      return move;// two levels deep called here
    }
  }

  public Move nextBestMove(Stack possibilities) {

    int points = 0;
    Move selectedMove = null;
    Stack random = (Stack) possibilities.clone();

    while (possibilities.size() > 0) {

      Move move = (Move) possibilities.pop();

      if (!(possibilities.size() == 1)) {

        if (points < move.getScore()) {
          points = move.getScore();
          selectedMove = move;
        }
      }

      if ((possibilities.size() == 1) && points == 0) {
        selectedMove = (Move) possibilities.pop();
      }

    }

    if (points > 0) {

      return selectedMove;

    } else {
      // If no piece can be taken, make a random move
      return randomMove(random);

    }

  }

  public Move twoLevelsDeep(Stack possibilities, Stack white, Stack black) {

    /*
     * Move selectedMove = new Move();
     * return selectedMove;
     */

    Move selectedMove = null;
    Stack black2 = (Stack) black.clone();
    Stack random = (Stack) possibilities.clone();
    Stack blacktest = (Stack) black.clone();
    for (int i = 0; i <= random.size(); i++) {
      Move fake = (Move) random.pop();
      Move fake2 = fake;
      String fakeMoveName = fake2.start.getName();
      int fakeMoveScore = fake2.start.getScore();
      fake2.landing.setName(fakeMoveName);
      fake2.landing.setScore(fakeMoveScore);
      fake2.start.setName("");
      fake2.start.setScore(0);
      Stack fakeWhite = (Stack) white.clone();
      for (int o = 0; o <= fakeWhite.size(); o++) {
        if (fakeWhite.get(o).equals(fake.start)) {
          fakeWhite.set(o, fake2.landing);
          break;
        }
      }
      for (int p = 0; p <= black2.size(); p++) {
        Move test = (Move) blacktest.pop();
        if (test.landing == fake.landing) {
          Move test2 = (Move) black2.get(p);
          test2.setScore(fake2.getScore());
          black2.set(p, test2);
        }
      }
      Move test3 = nextBestMove(black2);
    }
    return selectedMove;
  }

}
