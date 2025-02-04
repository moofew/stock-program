import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import controller.StockController;
import model.stock.StockData;
import model.user.User;
import model.user.UserData;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the outputs for the controller or StockController class.
 */
public class ControllerOutputTest {
  private Appendable appendable;
  private StockController controller;
  private User user;

  @Before
  public void setUp() {
    this.appendable = new StringBuilder();
    this.user = new UserData(new StockData("SPY"));
  }

  @Test
  public void testWelcomeAndFarewellMessage() throws Exception {
    String input = "q\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();

    String expectedOutput = "Welcome to our Stock Management program!\n\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n\n" +
            "Thank you for using our program!";

    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testInvalidInputMainMenu() throws Exception {
    String input = "invalid\nq\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();

    String expectedOutput = "Welcome to our Stock Management program!\n\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n\n" +
            "Invalid input, please try again. \n\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n\n" +
            "Thank you for using our program!";

    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testStockInformationMessage() throws IOException {
    String input = "1\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();

    String expectedOutput = "Welcome to our Stock Management program!\n\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n\n" +
            "Please select a feature: \n" +
            "(1) Gain/Loss Calculator\n" +
            "(2) Moving Average Calculator\n" +
            "(3) Crossover Calculator\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";

    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testInvalidInputStockInfoMenu() throws IOException {
    String input = "1\nbob\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n\n" +
            "Please select a feature: \n" +
            "(1) Gain/Loss Calculator\n" +
            "(2) Moving Average Calculator\n" +
            "(3) Crossover Calculator\n" +
            "(b) Back to Main Menu\n\n" +
            "Invalid input, please try again. \n\n" +
            "Please select a feature: \n" +
            "(1) Gain/Loss Calculator\n" +
            "(2) Moving Average Calculator\n" +
            "(3) Crossover Calculator\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testGainLossMethod() throws IOException {
    String input = "1\n1\naapl\n1950\n5\n5\n2024\n5\n5\naapl\n2024\n5\n5\n2023\n5\n5\n123stock\n" +
            "2024\n5\n5\n2024\n6\n4\naapl\n2024\n5\n5\n2024\n6\n4";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();

    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Gain/Loss Calculator\n" +
            "(2) Moving Average Calculator\n" +
            "(3) Crossover Calculator\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Invalid date. \n" +
            "\n" +
            "Start date cannot be after end date. \n" +
            "\n" +
            "Invalid date. \n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Gain/Loss Calculator\n" +
            "(2) Moving Average Calculator\n" +
            "(3) Crossover Calculator\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testCalculateMovingAverage() throws IOException {
    String input = "1\n2\naapl\n2024\n5\n5\napples\naapl\n2025\n5\n5\n10\nbob123\n2024\n5\n5" +
            "\n10\naapl\n2024\n5\n5\n10\nb";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Gain/Loss Calculator\n" +
            "(2) Moving Average Calculator\n" +
            "(3) Crossover Calculator\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Invalid input, please try again. \n" +
            "\n" +
            "Invalid input. \n" +
            "\n" +
            "Invalid input. \n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Gain/Loss Calculator\n" +
            "(2) Moving Average Calculator\n" +
            "(3) Crossover Calculator\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testCalculateCrossoverDays() throws IOException {
    String input = "1\n3\naapl\n2023\n15\n15\n2024\n5\n5\n30\naple123\n2024\n5\n5\n2024" +
            "\n5\n19\n30\naapl\n2024\n05\n05\n2024\n6\n2\n30\nb";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Gain/Loss Calculator\n" +
            "(2) Moving Average Calculator\n" +
            "(3) Crossover Calculator\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Invalid input. \n" +
            "\n" +
            "Invalid input. \n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Gain/Loss Calculator\n" +
            "(2) Moving Average Calculator\n" +
            "(3) Crossover Calculator\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testStockPortfolioMessage() throws IOException {
    String input = "2\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();

    String expectedOutput = "Welcome to our Stock Management program!\n\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testInvalidInputStockPortfolioMenu() throws IOException {
    String input = "2\nbob\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Invalid input, please try again. \n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testCreatePortfolioDeletePortfolio() throws IOException {
    String input = "2\n1\nbob123\n1\ntestPort\n2\n2\n1\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Not a valid index. \n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testEditPortfolio() throws IOException {
    String input = "2\n1\ntest\n3\n0\nb\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testInvalidInputEditPortfolio() throws IOException {
    String input = "2\n1\ntest\n3\n0\niloveood\nb\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Invalid input, please try again. \n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testShowAllPortfolios() throws IOException {
    String input = "2\n1\nbob\n1\ntest\n1\nood\n4\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testLoadPortfolio() throws IOException {
    String input = "2\n5\ntest\n5\ntestTwo\n3\n0\nb\n3\n1\nb\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testPortfolioOptionsMenu() throws IOException {
    String input = "2\n1\ntest\n3\n0\nb\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testInvalidInputPortfolioOptionsMenu() throws IOException {
    String input = "2\n1\ntest\n3\n0\nhaha\nb\n";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Invalid input, please try again. \n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testBuyStockSellStock() throws IOException {
    String input = "2\n1\ntest\n3\n0\n1\naapl\n5\n2020\n5\n5\n1\namzn\n10\n2020\n5\n5\n2" +
            "\n2021\n5\n5\naapl\n5\n2\n2021\n5\n5\namzn\n10\nb";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testBuyNegativeStocks() throws IOException {
    String input = "2\n1\ntest\n3\n0\n1\naapl\n-5\n2020\n5\n5\naapl\n5\n2020\n5\n5\nb";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Invalid stock date. \n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testSellUnownedStockOrTooMany() throws IOException {
    String input = "2\n1\ntest\n3\n0\n1\naapl\n5\n2020\n5\n5\n2\n2024\n5\n5\ngoog" +
            "\n5\n2020\n6\n5\naapl\n6\n2020\n5\n5\naapl\n5\nb";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Cannot sell more than owned amount.\n" +
            "\n" +
            "Cannot sell more than owned amount.\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testViewPortfolioValue() throws IOException {
    String input = "2\n1\ntest\n3\n0\n1\naapl\n5\n2020\n5\n5\n1\namzn\n10\n" +
            "2020\n5\n5\n3\n2021\n5\n5\nb";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testViewComposition() throws IOException {
    String input = "2\n1\ntest\n3\n0\n1\naapl\n5\n2020\n5\n5\n1\namzn\n10" +
            "\n2020\n5\n5\n4\n2021\n5\n5\nb";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testViewDistribution() throws IOException {
    String input = "2\n1\ntest\n3\n0\n1\naapl\n5\n2020\n5\n5\n1\namzn\n10" +
            "\n2020\n5\n5\n5\n2021\n5\n5\nb";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testRebalancePortfolio() throws IOException {
    String input = "2\n1\ntest\n3\n0\n1\naapl\n5\n2020\n5\n5\n1\namzn\n10\n2020\n5\n5" +
            "\n6\n2024\n5\n5\n0.5\n0.5\nb";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }

  @Test
  public void testSaveCurrentPortfolio() throws IOException {
    String input = "2\n1\ntest\n3\n0\n1\naapl\n5\n2020\n5\n5\n1\namzn\n10\n2020\n5\n5" +
            "\n7\n2020\n5\n5\n2023\n5\n5\nb";
    controller = new StockController(new StringReader(input), appendable, user);
    controller.control();
    String expectedOutput = "Welcome to our Stock Management program!\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Stock Information\n" +
            "(2) Stock Portfolio\n" +
            "(q) Quit Program\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Buy Stock\n" +
            "(2) Sell Stock\n" +
            "(3) View Portfolio Value\n" +
            "(4) View Stock Composition\n" +
            "(5) View Stock Distribution\n" +
            "(6) Rebalance Portfolio\n" +
            "(7) Performance Visualizer\n" +
            "(8) Save Current Portfolio\n" +
            "(b) Return to Portfolio Menu\n" +
            "\n" +
            "Please select a feature: \n" +
            "(1) Create Portfolio\n" +
            "(2) Delete Portfolio\n" +
            "(3) Edit Portfolio\n" +
            "(4) Show All Portfolios\n" +
            "(5) Load Portfolio\n" +
            "(b) Back to Main Menu\n" +
            "Thank you for using our program!";
    assertEquals(expectedOutput, appendable.toString().trim());
  }
}
