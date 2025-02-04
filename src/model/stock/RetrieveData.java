package model.stock;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Handles the API and stock data. This class is responsible for things such as getting data
 * from the API and writing it to a CSV file.
 */
public class RetrieveData {
  /**
   * Constructor for stock data which creates a new StringBuilder to record the output
   * of the data of a stock to.
   */
  public RetrieveData() {
    //This constructor is empty because StockData is only for data retrieval.
  }

  /**
   * This method gets the data from the API for a certain stock given a ticker.
   *
   * @param ticker is the ticker symbol of the stock that you are getting the data for.
   */
  public static String getData(String ticker) {
    StringBuilder output = new StringBuilder();
    URL url = null;
    String apiKey = "RWI9HAQXNXJQQSJI";
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol=" + ticker
              + "&apikey=" + apiKey
              + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }

      if (output.toString().contains("Error Message")) {
        throw new IllegalArgumentException("Invalid ticker: " + ticker);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid ticker: " + ticker);
    }
    return output.toString();
  }

  /**
   * This method takes the data from getData() and writes it to a CSV file.
   *
   * @param ticker is the ticker symbol of the stock.
   */
  public static void writeToCSV(String ticker) {
    String data = getData(ticker);
    try (FileWriter fileWriter = new FileWriter("res/csvFiles/" + ticker + ".csv")) {
      fileWriter.write(data);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}