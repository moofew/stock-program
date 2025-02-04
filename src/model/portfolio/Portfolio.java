package model.portfolio;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Represents a stock portfolio for a user. It contains the methods that are used to manage
 * a portfolio.
 */
public interface Portfolio {

  /**
   * Gets the name of the portfolio.
   *
   * @return the name of the portfolio as a String.
   */
  String getName();

  /**
   * Gets the size of the portfolio.
   *
   * @param date the date of the most recent portfolio to check
   * @return the size of the portfolio as an int.
   */
  int getSize(String date);

  /**
   * This method adds a specific stock along with a specified quantity to the portfolio.
   *
   * @param ticker is the specific stock symbol to be bought.
   * @param amt    is the quantity of the stock to be bought.
   * @param date   is the date when the stock is bought.
   */
  void buyStock(String ticker, double amt, String date);

  /**
   * This method removes a specific stock along with a specified quantity from the portfolio.
   *
   * @param ticker is the specific stock symbol to be sold.
   * @param amt    is the quantity of the stock to be sold.
   * @param date   is the date when the stock is sold
   */
  void sellStock(String ticker, double amt, String date);


  /**
   * This method checks the value of the portfolio on a given date.
   *
   * @param date is the date that you are checking the value on.
   * @return a double representing the sum of all the stocks in the portfolio on the date.
   */
  double checkPortfolioValue(String date);

  /**
   * This method prints out all the stocks and their quantities in the portfolio.
   *
   * @param date is the date you want to see the composition of the portfolio
   * @return a list with all the tickers and their quantities.
   */
  List<String> printPortfolio(String date);

  /**
   * This method stores a string with the ticker and the value of it from the portfolio.
   *
   * @param date is the date of when the user wants to check the stock distribution
   * @return a list of strings with each ticker and the total value of each stock
   */
  List<String> getPortfolioDistribution(String date);

  /**
   * This function saves the portfolio that you are currently in as a txt file so that it can be
   * loaded up in the program in the future when it is re-run.
   */
  void savePortfolioToFile();

  /**
   * This method rebalances the portfolio with the given stocks in a portfolio on a given date.
   * Each stock is assigned a weight so that the program can assign each stock a fractional share
   * to reach the assigned weight. For example, if you have $1000 total dollars, and you own AAPL
   * and GOOG, rebalancing each at 0.5 and 0.5 would make it so that you have $500 in AAPL and
   * $500 in GOOG.
   *
   * @param date          is the date of the portfolio that you are rebalancing at.
   * @param targetWeights are the assigned weights for each stock.
   */
  void rebalancePortfolio(String date, Map<String, Double> targetWeights);

  /**
   * This method takes in a map of a date and a map of tickers+quantities and sets the history/
   * transaction log when you load a portfolio in to that map.
   *
   * @param history is the map that the method takes in to set the history to.
   */
  void setHistory(Map<LocalDate, Map<String, Double>> history);
}