package view;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements the IView interface which helps the controller process inputs. This
 * class implements methods to get the dates for stocks.
 */
public class StockView implements IView {
  private final Appendable appendable;

  /**
   * This constructor creates a StockView object which is used as the view for the controller.
   *
   * @param appendable is where the output of the program is written to.
   */
  public StockView(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void writeMessage(String message) throws IllegalStateException {
    try {
      appendable.append(message);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  protected void welcomeMessage() {
    writeMessage("Welcome to our Stock Management program!" + System.lineSeparator()
            + System.lineSeparator());
    printMenu();
  }

  protected void printMenu() {
    writeMessage("Please select a feature: " + System.lineSeparator());
    writeMessage("(1) Stock Information" + System.lineSeparator());
    writeMessage("(2) Stock Portfolio" + System.lineSeparator());
    writeMessage("(q) Quit Program" + System.lineSeparator());
  }

  protected void farewellMessage() {
    writeMessage("Thank you for using our program!");
  }

  protected void stockInfoMenu() {
    writeMessage("Please select a feature: " + System.lineSeparator());
    writeMessage("(1) Gain/Loss Calculator" + System.lineSeparator());
    writeMessage("(2) Moving Average Calculator" + System.lineSeparator());
    writeMessage("(3) Crossover Calculator" + System.lineSeparator());
    writeMessage("(b) Back to Main Menu" + System.lineSeparator());
  }

  protected void portfolioMenu() {
    writeMessage("Please select a feature: " + System.lineSeparator());
    writeMessage("(1) Create Portfolio" + System.lineSeparator());
    writeMessage("(2) Delete Portfolio" + System.lineSeparator());
    writeMessage("(3) Edit Portfolio" + System.lineSeparator());
    writeMessage("(4) Show All Portfolios" + System.lineSeparator());
    writeMessage("(5) Load Portfolio" + System.lineSeparator());
    writeMessage("(b) Back to Main Menu" + System.lineSeparator());
  }

  protected void portfolioOptions() {
    writeMessage("Please select a feature: " + System.lineSeparator());
    writeMessage("(1) Buy Stock" + System.lineSeparator());
    writeMessage("(2) Sell Stock" + System.lineSeparator());
    writeMessage("(3) View Portfolio Value" + System.lineSeparator());
    writeMessage("(4) View Stock Composition" + System.lineSeparator());
    writeMessage("(5) View Stock Distribution" + System.lineSeparator());
    writeMessage("(6) Rebalance Portfolio" + System.lineSeparator());
    writeMessage("(7) Performance Visualizer" + System.lineSeparator());
    writeMessage("(8) Save Current Portfolio" + System.lineSeparator());
    writeMessage("(b) Return to Portfolio Menu" + System.lineSeparator());
  }

  protected String getStartDate(Scanner sc) {
    System.out.println("Please enter the start year: ");
    String startYear = sc.nextLine().trim();
    System.out.println("Please enter the start month: ");
    String startMonth = sc.nextLine().trim();
    System.out.println("Please enter the start day: ");
    String startDay = sc.nextLine().trim();
    int month = Integer.parseInt(startMonth);
    int day = Integer.parseInt(startDay);
    String formattedMonth = String.format("%02d", month);
    String formattedDay = String.format("%02d", day);

    return (startYear + "-" + formattedMonth + "-" + formattedDay);
  }

  protected String getEndDate(Scanner sc) {
    System.out.println("Please enter the end year: ");
    String endYear = sc.nextLine().trim();
    System.out.println("Please enter the end month: ");
    String endMonth = sc.nextLine().trim();
    System.out.println("Please enter the end day: ");
    String endDay = sc.nextLine().trim();
    int month = Integer.parseInt(endMonth);
    int day = Integer.parseInt(endDay);
    String formattedMonth = String.format("%02d", month);
    String formattedDay = String.format("%02d", day);

    return (endYear + "-" + formattedMonth + "-" + formattedDay);
  }

  protected String getDate(Scanner sc) {
    System.out.println("Please enter the year: ");
    String year = sc.nextLine().trim();
    System.out.println("Please enter the month: ");
    String month = sc.nextLine().trim();
    System.out.println("Please enter the day: ");
    String day = sc.nextLine().trim();
    int convertMonth = Integer.parseInt(month);
    int convertDay = Integer.parseInt(day);
    String formattedMonth = String.format("%02d", convertMonth);
    String formattedDay = String.format("%02d", convertDay);

    return (year + "-" + formattedMonth + "-" + formattedDay);
  }
}
