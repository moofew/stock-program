package model.stock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the details of a Stock. It contains methods to get the data of a stock.
 */
public class StockData implements Stock {
  private final String ticker;
  private final Map<String, Double> closingPrice;

  /**
   * Constructs a StockData object with the basic details of a stock such as the ticker and
   * closing price.
   *
   * @param ticker is the ticker symbol of the stock.
   */
  public StockData(String ticker) {
    this.ticker = ticker;
    this.closingPrice = getClosingData(ticker);
  }

  @Override
  public String getTicker() {
    return this.ticker;
  }

  @Override
  public Map<String, Double> getClosingPrice() {
    return Map.copyOf(closingPrice);
  }

  private Map<String, Double> getClosingData(String ticker) {
    Map<String, Double> diffClosingPrice = new HashMap<>();
    File f = new File("res/csvFiles/" + ticker + ".csv");
    if (!f.exists()) {
      RetrieveData.writeToCSV(ticker);
    }
    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
      String currentLine;
      br.readLine();
      while ((currentLine = br.readLine()) != null) {
        if (!currentLine.isEmpty()) {
          String[] readLine = currentLine.split(",");
          if (readLine.length >= 5) {
            String date = readLine[0];
            double closingPrice = Double.parseDouble(readLine[4]);
            diffClosingPrice.put(date, closingPrice);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Map.copyOf(diffClosingPrice);
  }
}
