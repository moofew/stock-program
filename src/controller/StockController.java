package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.portfolio.Portfolio;
import model.portfolio.PortfolioChart;
import model.portfolio.PortfolioData;
import model.stock.StockData;
import model.user.User;
import model.user.UserData;
import view.StockView;

/**
 * Controller for the stock program. This class handles user interactions and calls methods
 * from other classes accordingly.
 */
public class StockController extends StockView implements IController {
  private Readable readable;
  private Appendable appendable;
  private User user;
  private boolean quit;

  /**
   * This constructor creates a StockController object that contains readable and appendable
   * objects.
   *
   * @param readable   is where the inputs of the program are read from.
   * @param appendable is where the output of the program is written to.
   * @param user       is the model of the program.
   */
  public StockController(Readable readable, Appendable appendable, User user) {
    super(appendable);
    this.readable = readable;
    this.user = user;
    this.quit = false;
  }

  @Override
  public void control() throws IOException {
    Scanner sc = new Scanner(readable);
    welcomeMessage();

    while (!quit && sc.hasNext()) {
      String userInstruction = sc.nextLine().trim();
      processMenu(userInstruction, sc, user);
    }
    farewellMessage();
  }

  private void processMenu(String userInstruction, Scanner sc, User user) throws IOException {
    boolean validInput = false;
    String input = userInstruction;

    while (!validInput) {
      writeMessage(System.lineSeparator());
      switch (input) {
        case "1":
          validInput = true;
          processStockInfo(sc, user);
          break;
        case "2":
          validInput = true;
          processPortfolioInput(sc, user);
          break;
        case "q":
          validInput = true;
          quit = true;
          break;
        default:
          writeMessage("Invalid input, please try again. " + System.lineSeparator()
                  + System.lineSeparator());
          printMenu();
          if (sc.hasNext()) {
            input = sc.nextLine().trim();
          }
          break;
      }
    }
  }

  private void processStockInfo(Scanner sc, User user) throws IOException {
    stockInfoMenu();
    boolean validInput = false;

    while (!validInput && sc.hasNext()) {
      String input = sc.nextLine().trim();
      writeMessage(System.lineSeparator());
      switch (input) {
        case "1":
          validInput = true;
          calculateGainLoss(sc, user);
          break;
        case "2":
          validInput = true;
          calculateMovingAverage(sc, user);
          break;
        case "3":
          validInput = true;
          calculateXDayCrossovers(sc, user);
          break;
        case "b":
          validInput = true;
          printMenu();
          break;
        default:
          writeMessage("Invalid input, please try again. " + System.lineSeparator()
                  + System.lineSeparator());
          stockInfoMenu();
      }
    }
  }

  private void processPortfolioInput(Scanner sc, User user) throws IOException {
    portfolioMenu();
    boolean validInput = false;

    while (!validInput && sc.hasNext()) {
      String input = sc.nextLine().trim();
      writeMessage(System.lineSeparator());
      switch (input) {
        case "1":
          validInput = true;
          processCreatePortfolio(sc, user);
          break;
        case "2":
          validInput = true;
          processDeletePortfolio(sc, user);
          break;
        case "3":
          validInput = true;
          processEditPortfolio(sc, user);
          break;
        case "4":
          validInput = true;
          processShowPortfolios(sc, user);
          break;
        case "5":
          validInput = true;
          processLoadPortfolio(sc, user);
          break;
        case "b":
          validInput = true;
          printMenu();
          break;
        default:
          writeMessage("Invalid input, please try again. " + System.lineSeparator()
                  + System.lineSeparator());
          portfolioMenu();
      }
    }
  }

  private void calculateGainLoss(Scanner sc, User user) throws IOException {
    boolean validInput = false;

    while (!validInput) {
      System.out.println("Please enter the ticker symbol: ");
      String ticker = sc.nextLine().trim().toUpperCase();
      String startDate = getStartDate(sc);
      String endDate = getEndDate(sc);

      try {
        user = new UserData(new StockData(ticker));
      } catch (IllegalArgumentException e) {
        writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator());
        continue;
      }
      try {
        double gainLoss = ((UserData) user).calc.totalGainLoss(startDate, endDate);
        System.out.println(System.lineSeparator() + "The total gain/loss of " + ticker + " from "
                + startDate + " to " + endDate + " is $" + gainLoss);
        validInput = true;
      } catch (IllegalArgumentException e) {
        writeMessage(System.lineSeparator() + e.getMessage());
      }
      writeMessage(System.lineSeparator());
    }
    processStockInfo(sc, user);
  }

  private void calculateMovingAverage(Scanner sc, User user) throws IOException {
    boolean validInput = false;
    int x;

    while (!validInput) {
      System.out.println("Please enter the ticker symbol: ");
      String ticker = sc.nextLine().trim().toUpperCase();

      String startDate = getStartDate(sc);

      System.out.println("Please enter the value for x: ");
      if (sc.hasNextInt()) {
        x = sc.nextInt();
        sc.nextLine();
      } else {
        writeMessage(System.lineSeparator() + "Invalid input, please try again. "
                + System.lineSeparator());
        sc.nextLine();
        continue;
      }
      try {
        user = new UserData(new StockData(ticker));
      } catch (IllegalArgumentException e) {
        writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator());
        continue;
      }
      try {
        double movingAverage = ((UserData) user).calc.movingAverage(startDate, x);
        System.out.println(System.lineSeparator() + "The " + x + " day moving average of " + ticker
                + " from " + startDate + " is $" + movingAverage);
        validInput = true;
      } catch (IllegalArgumentException e) {
        writeMessage(System.lineSeparator() + e.getMessage());
      }
      writeMessage(System.lineSeparator());
    }
    processStockInfo(sc, user);
  }

  private void calculateXDayCrossovers(Scanner sc, User user) throws IOException {
    boolean validInput = false;
    int x;

    while (!validInput) {
      System.out.println("Please enter the ticker symbol: ");
      String ticker = sc.nextLine().trim().toUpperCase();

      String startDate = getStartDate(sc);
      String endDate = getEndDate(sc);

      System.out.println("Please enter the value for x: ");
      if (sc.hasNextInt()) {
        x = sc.nextInt();
        sc.nextLine();
      } else {
        writeMessage(System.lineSeparator() + "Invalid input, please try again. "
                + System.lineSeparator());
        continue;
      }
      try {
        user = new UserData(new StockData(ticker));
      } catch (IllegalArgumentException e) {
        writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator());
        continue;
      }
      try {
        String dates = ((UserData) user).calc.crossoverDates(startDate, endDate, x).toString();
        System.out.println(System.lineSeparator() + "The crossover dates of " + ticker + " from "
                + startDate + " to " + endDate + " are: " + dates);
        validInput = true;
      } catch (IllegalArgumentException e) {
        writeMessage(System.lineSeparator() + e.getMessage());
      }
      writeMessage(System.lineSeparator());
    }
    processStockInfo(sc, user);
  }

  private void processCreatePortfolio(Scanner sc, User user) throws IOException {
    boolean validInput = false;

    while (!validInput) {
      System.out.println("Please give the portfolio a name: ");
      String name = sc.nextLine().trim();
      Portfolio newPortfolio = new PortfolioData(name);
      try {
        user.addPortfolio(newPortfolio);
        System.out.println(System.lineSeparator()
                + "You have successfully created a new portfolio " +
                "named " + "\"" + name + "\"" + "!" + System.lineSeparator());
        validInput = true;
      } catch (IllegalArgumentException e) {
        writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator());
      }
    }
    processPortfolioInput(sc, user);
  }

  private void processDeletePortfolio(Scanner sc, User user) throws IOException {
    boolean validInput = false;
    int index;

    while (!validInput) {
      if (user.getUserPortSize() == 0) {
        System.out.println("No portfolios found. ");
        validInput = true;
      } else {
        System.out.println("Which portfolio would you like to delete?" + System.lineSeparator());
        List<String> portfolios = user.printAllPortfolios();
        for (int i = 0; i < portfolios.size(); i++) {
          System.out.println(portfolios.get(i));
        }
        if (sc.hasNextInt()) {
          index = sc.nextInt();
          sc.nextLine();
        } else {
          writeMessage(System.lineSeparator() + "Invalid input, please try again. "
                  + System.lineSeparator());
          sc.nextLine();
          continue;
        }
        try {
          user.removePortfolio(index);
          System.out.println(System.lineSeparator()
                  + "You have successfully deleted a portfolio! " + System.lineSeparator());
          validInput = true;
        } catch (IllegalArgumentException e) {
          writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator());
        }
      }
    }
    processPortfolioInput(sc, user);
  }

  private void processEditPortfolio(Scanner sc, User user) throws IOException {
    boolean validInput = false;
    int index = 0;

    while (!validInput) {
      if (user.getUserPortSize() == 0) {
        System.out.println("No portfolios found. Please return to the menu and create one first. "
                + System.lineSeparator());
        processPortfolioInput(sc, user);
        validInput = true;
      } else {
        System.out.println("Which portfolio would you like to edit?");
        List<String> portfolios = user.printAllPortfolios();
        for (int i = 0; i < portfolios.size(); i++) {
          System.out.println(portfolios.get(i));
        }
        if (sc.hasNextInt()) {
          index = sc.nextInt();
        } else {
          writeMessage(System.lineSeparator() + "Invalid input, please try again. "
                  + System.lineSeparator());
          sc.nextLine();
          continue;
        }
      }
      try {
        Portfolio portfolio = user.selectPortfolio(index);
        sc.nextLine();
        System.out.println(System.lineSeparator() + "Successfully selected portfolio! ");
        validInput = true;
      } catch (IllegalArgumentException e) {
        writeMessage(System.lineSeparator() + e.getMessage());
        sc.nextLine();
      }
      writeMessage(System.lineSeparator());
    }
    processPortfolioMethods(sc, user, index);
  }

  private void processShowPortfolios(Scanner sc, User user) throws IOException {
    boolean validInput = false;

    while (!validInput) {
      try {
        System.out.println("Portfolios: ");
        List<String> portfolios = user.printAllPortfolios();
        for (int i = 0; i < portfolios.size(); i++) {
          System.out.println(portfolios.get(i));
        }
        writeMessage(System.lineSeparator());
        validInput = true;
      } catch (IllegalArgumentException e) {
        writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator());
      }
    }
    processPortfolioInput(sc, user);
  }

  private void processLoadPortfolio(Scanner sc, User user) throws IOException {
    boolean validInput = false;
    while (!validInput) {
      try {
        System.out.println("Which file would you like to load?");
        String fileName = sc.nextLine();
        user.loadUserData(fileName);
        System.out.println("You have successfully loaded a portfolio!" + System.lineSeparator());
        break;
      } catch (Exception e) {
        writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator()
                + System.lineSeparator());
        break;
      }
    }
    processPortfolioInput(sc, user);
  }

  private void processPortfolioMethods(Scanner sc, User user, int index) throws IOException {
    boolean validInput = false;
    String input;

    portfolioOptions();
    while (!validInput) {
      input = sc.nextLine().trim();
      writeMessage(System.lineSeparator());
      switch (input) {
        case "1":
          validInput = true;
          processBuyStock(sc, user, index);
          break;
        case "2":
          validInput = true;
          processSellStock(sc, user, index);
          break;
        case "3":
          validInput = true;
          processViewValue(sc, user, index);
          break;
        case "4":
          validInput = true;
          processViewComposition(sc, user, index);
          break;
        case "5":
          validInput = true;
          processViewDistribution(sc, user, index);
          break;
        case "6":
          validInput = true;
          processRebalancePortfolio(sc, user, index);
          break;
        case "7":
          validInput = true;
          processVisualizePortfolio(sc, user, index);
          break;
        case "8":
          validInput = true;
          processSavePortfolio(sc, user, index);
          break;
        case "b":
          validInput = true;
          processPortfolioInput(sc, user);
          break;
        default:
          writeMessage("Invalid input, please try again. " + System.lineSeparator()
                  + System.lineSeparator());
          portfolioOptions();
          break;
      }
    }
  }

  private void processBuyStock(Scanner sc, User user, int index) throws IOException {
    Portfolio portfolio = user.selectPortfolio(index);

    double quantity = 0;
    String date = "";
    StockData stock = null;
    String ticker = "";
    boolean validInput = false;

    while (!validInput) {
      System.out.println("Please enter the ticker symbol: ");
      ticker = sc.nextLine().trim().toUpperCase();
      System.out.println("Please enter the quantity you wish to purchase: ");
      try {
        quantity = Double.parseDouble(sc.nextLine().trim());
      } catch (NumberFormatException e) {
        writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator());
        continue;
      }
      if (quantity % 1 != 0) {
        writeMessage(System.lineSeparator() + "Sorry, we only support buying whole shares. "
                + System.lineSeparator());
        continue;
      }

      date = getDate(sc);

      try {
        portfolio.buyStock(ticker, quantity, date);
        validInput = true;
      } catch (IllegalArgumentException e) {
        writeMessage(System.lineSeparator() + e.getMessage());
      }
      writeMessage(System.lineSeparator());
    }
    System.out.println("You have successfully purchased " + quantity
            + " shares of " + ticker + " on " + date + "!" + System.lineSeparator());
    processPortfolioMethods(sc, user, index);
  }

  private void processSellStock(Scanner sc, User user, int index) throws IOException {
    Portfolio portfolio = user.selectPortfolio(index);
    double quantity = 0;
    String ticker = "";
    String date = "";
    StockData stock;
    boolean validInput = false;

    while (!validInput) {
      date = getDate(sc);
      if (portfolio.getSize(date) == 0) {
        System.out.println("You have no stocks to sell! " + System.lineSeparator());
        break;
      }
      System.out.println("Please enter the ticker symbol: ");
      ticker = sc.nextLine().trim().toUpperCase();
      System.out.println("Please enter the quantity you wish to sell: ");
      try {
        quantity = Double.parseDouble(sc.nextLine().trim());
      } catch (NumberFormatException e) {
        writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator());
        sc.nextLine();
        continue;
      }

      try {
        portfolio.sellStock(ticker, quantity, date);
        System.out.println(System.lineSeparator() + "You have successfully sold " + quantity
                + " shares of " + ticker + "! " + System.lineSeparator());


        validInput = true;
      } catch (IllegalArgumentException e) {
        writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator());
      }
    }
    processPortfolioMethods(sc, user, index);
  }

  private void processViewValue(Scanner sc, User user, int index) throws IOException {
    Portfolio portfolio = user.selectPortfolio(index);
    String date = "";
    double value;
    boolean validInput = false;

    while (!validInput) {
      date = getDate(sc);
      try {
        List<String> list = portfolio.printPortfolio(date);
      } catch (Exception e) {
        System.out.println(System.lineSeparator()
                + "Your portfolio on " + date + " is worth: $0.00."
                + System.lineSeparator());
        break;
      }
      try {
        value = portfolio.checkPortfolioValue(date);
        System.out.println(System.lineSeparator() + "Your portfolio on " + date + " is worth: $"
                + value + ". " + System.lineSeparator());
        validInput = true;
      } catch (IllegalArgumentException e) {
        writeMessage(e.getMessage() + System.lineSeparator() + System.lineSeparator());
      }
    }
    processPortfolioMethods(sc, user, index);
  }

  private void processViewComposition(Scanner sc, User user, int index) throws IOException {
    Portfolio portfolio = user.selectPortfolio(index);
    String date = "";
    List<String> list;
    boolean validInput = false;

    while (!validInput) {
      date = getDate(sc);
      try {
        list = portfolio.printPortfolio(date);
      } catch (Exception e) {
        System.out.println(System.lineSeparator() + "You have no stocks to view! "
                + System.lineSeparator());
        break;
      }

      System.out.println(System.lineSeparator()
              + "The composition of stocks in your portfolio are: " + System.lineSeparator() + list
              + System.lineSeparator());
      validInput = true;
    }
    processPortfolioMethods(sc, user, index);
  }

  private void processViewDistribution(Scanner sc, User user, int index) throws IOException {
    Portfolio portfolio = user.selectPortfolio(index);
    String date = "";
    boolean validInput = false;

    while (!validInput) {
      date = getDate(sc);
      List<String> list = portfolio.getPortfolioDistribution(date);
      if (list.isEmpty()) {
        System.out.println(System.lineSeparator() + "You have no stocks to view! "
                + System.lineSeparator());
        validInput = true;
      } else {
        System.out.println(System.lineSeparator()
                + "The distribution of stocks in your portfolio are: "
                + System.lineSeparator() + list
                + System.lineSeparator());
        validInput = true;
      }
      processPortfolioMethods(sc, user, index);
    }
  }

  private void processRebalancePortfolio(Scanner sc, User user, int index) throws IOException {
    Portfolio portfolio = user.selectPortfolio(index);
    String date = "";
    int size = 0;
    Map<String, Double> weightList = new HashMap<String, Double>();
    boolean validInput = false;

    while (!validInput) {
      date = getDate(sc);
      List<String> stocks = portfolio.printPortfolio(date);

      try {
        size = portfolio.getSize(date);
      } catch (Exception e) {
        writeMessage(System.lineSeparator() + "You do not have enough stocks to rebalance!"
                + System.lineSeparator() + System.lineSeparator());
        break;
      }

      if (size == 1) {
        writeMessage(System.lineSeparator() + "You do not have enough stocks to rebalance!"
                + System.lineSeparator() + System.lineSeparator());
        break;
      }

      for (int i = 0; i < stocks.size(); i++) {
        String ticker = stocks.get(i).split(":")[0];
        System.out.println("Please enter the weight for" + ticker + ": ");
        double weight = Double.parseDouble(sc.nextLine().trim());
        weightList.put(ticker, weight);
      }
      try {
        portfolio.rebalancePortfolio(date, weightList);
        System.out.println("Your portfolio has been successfully rebalanced!");
        validInput = true;
      } catch (Exception e) {
        writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator());
      }
    }
    processPortfolioMethods(sc, user, index);
  }

  private void processVisualizePortfolio(Scanner sc, User user, int index) throws IOException {
    Portfolio portfolio = user.selectPortfolio(index);
    String start = getStartDate(sc);
    String end = getEndDate(sc);
    String chartVisualizer = "";
    PortfolioChart chart = new PortfolioChart(portfolio, start, end);
    boolean validInput = false;

    while (!validInput) {
      StringBuilder builder = chart.printBarChart();
      try {
        chartVisualizer = builder.toString();
      } catch (Exception e) {
        writeMessage(System.lineSeparator() + e.getMessage() + System.lineSeparator());
      }
      System.out.println(System.lineSeparator() + chartVisualizer);
      validInput = true;
    }
    processPortfolioMethods(sc, user, index);
  }

  private void processSavePortfolio(Scanner sc, User user, int index) throws IOException {
    Portfolio portfolio = user.selectPortfolio(index);
    boolean validInput = false;

    while (!validInput) {
      portfolio.savePortfolioToFile();
      validInput = true;
      System.out.println(System.lineSeparator() + "Successfully saved portfolio!"
              + System.lineSeparator());
    }
    processPortfolioMethods(sc, user, index);

  }
}
