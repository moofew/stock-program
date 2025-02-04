package controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.swing.JList;

import model.portfolio.Portfolio;
import model.user.User;

/**
 * This class is the mock for the GUI controller. It checks the input for the controller methods
 * and ensures that they are properly called.
 */
public class MockStockControllerGUI implements IControllerGUI {
  final StringBuilder log;

  /**
   * This constructor creates a MockStockControllerGUI object, which uses a StringBuilder to keep
   * track of all the inputs to make sure that the controllers are handling the input correctly.
   *
   * @param log is where the inputs will be stored.
   */
  public MockStockControllerGUI(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public void processCreatePortfolio(String portfolioName, User user) {
    log.append(String.format("processCreatePortfolio: portfolioName = %s user = %s\n",
            portfolioName, user.getUserPortSize()));
  }

  @Override
  public void processLoadPortfolio(String portfolioName, User user) throws IOException {
    log.append(String.format("processLoadPortfolio: portfolioName = %s user = %s\n",
            portfolioName, user.getUserPortSize()));
  }

  @Override
  public void processBuyStock(User user, int index, String ticker, double amt, String date) {
    log.append(String.format("processBuyStock: user = %s index = %d, " +
            "ticker = %s amt = %s date = %s\n", user.getUserPortSize(), index, ticker, amt,
            date));
  }

  @Override
  public void processSellStock(User user, int index, String ticker, double amt, String date) {
    log.append(String.format("processSellStock: user = %s index = %d, " +
            "ticker = %s amt = %s date = %s", user.getUserPortSize(), index, ticker, amt, date));
  }

  @Override
  public double processGetValue(User user, int index, String date) {
    log.append(String.format("processGetValue: user = %s index = %d, date = %s",
            user.getUserPortSize(), index, date));
    return 0;
  }

  @Override
  public List<String> processGetComposition(User user, int index, String date) {
    log.append(String.format("processGetComposition: user = %s index = %d, " +
            "date = %s", user.getUserPortSize(), index, date));
    return List.of();
  }

  @Override
  public Portfolio processEditPortfolio(int index, User user) {
    log.append(String.format("processEditPortfolio: user = %s index = %d\n",
            user.getUserPortSize(), index));
    return null;
  }

  @Override
  public void processSavePortfolio(int index, User user) {
    log.append(String.format("processSavePortfolio: user = %s index = %d\n",
            user.getUserPortSize(), index));
  }

  @Override
  public void getPortfolioList(JList<String> list, User user) {
    log.append(String.format("getPortfolioList: user = %s user = %s\n",
            user.getUserPortSize(), list));
  }

  @Override
  public void control() throws IOException {
    log.append("control: visible");
  }
}
