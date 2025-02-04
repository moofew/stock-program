package model.stock;

import java.util.Map;

/**
 * Interface for a Stock. This interface has the basic methods to represent a stock such as
 * getter methods for the ticker and closing prices. In addition, it contains the methods to
 * call the API and convert data to CSV format.
 */
public interface Stock {

  /**
   * This method gets the ticker of a Stock.
   *
   * @return the ticker symbol as a String.
   */
  String getTicker();

  /**
   * This method gets the closing prices of a Stock.
   *
   * @return a map containing the ticker and closing prices of a stock.
   */
  Map<String, Double> getClosingPrice();
}
