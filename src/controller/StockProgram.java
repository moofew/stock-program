package controller;

import model.stock.StockData;
import model.user.User;
import model.user.UserData;
import view.StockViewGUI;

import java.io.InputStreamReader;
import java.io.IOException;

/**
 * This class initializes the needed components to run the stock program and runs an instance of
 * the stock program.
 */
public class StockProgram {
  /**
   * This is the main method that is used to run the stock program.
   *
   * @param args are the command line arguments.
   * @throws IOException is thrown when an input fails or is interrupted.
   */
  public static void main(String[] args) throws IOException {
    if (args.length > 0 && args[0].equals("-text")) {
      runTextInterface();
    } else {
      runGUI();
    }
  }

  private static void runTextInterface() throws IOException {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    User user = new UserData(new StockData("SPY"));
    StockController controller = new StockController(rd, ap, user);
    controller.control();
  }

  private static void runGUI() {
    User user = new UserData(new StockData("SPY"));
    StockViewGUI view = new StockViewGUI(user);
    StockControllerGUI controller = new StockControllerGUI(view, user);
    view.setController(controller);
    try {
      controller.control();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

