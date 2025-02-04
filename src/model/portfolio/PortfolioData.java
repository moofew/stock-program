package model.portfolio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.stock.Stock;
import model.stock.StockData;
import model.stock.StockMethods;
import model.stock.StockMethodsImpl;

/**
 * This class implements the Portfolio interface and it's methods. This class also represents a
 * stock portfolio and the information that a stock portfolio contains. It contains methods to buy
 * stocks, sell stocks, check the value of a portfolio, and check the stocks a portfolio contains.
 */
public class PortfolioData implements Portfolio {
  private final String name;
  private StockMethods stockCalculator;
  private Map<LocalDate, Map<String, Double>> history;

  /**
   * This constructor creates a PortfolioData object with a name and an empty map.
   **/
  public PortfolioData(String name) {
    if (name == null || name.isEmpty() || name.equals("\n")) {
      throw new IllegalArgumentException("Portfolio name cannot be null or empty. ");
    }
    this.name = name;
    Map<String, Double> portfolio = new HashMap<>();
    history = new HashMap<>();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getSize(String date) {
    LocalDate buyDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Map<String, Double> currentPort = getPreviousOrCurrentPortfolio(buyDate);
    return currentPort.size();
  }

  @Override
  public void buyStock(String ticker, double amt, String date) {
    StockData currentStock = new StockData(ticker);
    setStockCalculator(currentStock);
    if (amt < 0 || !stockCalculator.isValidDate(date)) {
      throw new IllegalArgumentException("Invalid stock date. ");
    } else {
      Map<String, Double> changesMade = new HashMap<>();
      LocalDate buyDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      changesMade.put(ticker, amt);
      updateHistoryBuy(changesMade, buyDate);
      Map<String, Double> portfolioOnDate = getPreviousOrCurrentPortfolio(buyDate);
      portfolioOnDate.merge(ticker, amt, Double::sum);
      history.put(buyDate, portfolioOnDate);
    }
  }


  @Override
  public void sellStock(String ticker, double amt, String date) {
    if (amt < 0 || !stockCalculator.isValidDate(date)) {
      throw new IllegalArgumentException("Invalid stock date. ");
    } else {
      Map<String, Double> changesMade = new HashMap<>();
      LocalDate sellDate = LocalDate.parse(date);
      changesMade.put(ticker, amt);
      updateHistorySell(changesMade, sellDate);
      Map<String, Double> portfolioOnDate = getPreviousOrCurrentPortfolio(sellDate);
      Double existingShares = portfolioOnDate.get(ticker);
      if (existingShares == null || existingShares < amt) {
        throw new IllegalArgumentException("Cannot sell more than owned amount.");
      }
      portfolioOnDate.put(ticker, existingShares - amt);
      if (portfolioOnDate.get(ticker) == 0.0) {
        portfolioOnDate.remove(ticker);
      }
      history.put(sellDate, portfolioOnDate);
    }
  }

  @Override
  public double checkPortfolioValue(String date) {
    if (!stockCalculator.isValidDate(date)) {
      throw new IllegalArgumentException("Invalid date");
    }
    LocalDate getDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Map<String, Double> portfolioOnDate = getPreviousOrCurrentPortfolio(getDate);
    double value = 0;
    String newDate = date;
    for (Map.Entry<String, Double> entry : portfolioOnDate.entrySet()) {
      StockData currentStock = new StockData(entry.getKey());
      setStockCalculator(currentStock);
      if (!currentStock.getClosingPrice().containsKey(date)) {
        newDate = stockCalculator.advanceBack(date);
      }
      value += currentStock.getClosingPrice().get(newDate) * entry.getValue();
    }
    String format = String.format("%2f", value);
    return Double.parseDouble(format);
  }


  @Override
  public List<String> printPortfolio(String date) {
    if (!stockCalculator.isValidDate(date)) {
      throw new IllegalArgumentException("Invalid date");
    }
    LocalDate getDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Map<String, Double> portfolioOnDate = getPreviousOrCurrentPortfolio(getDate);
    List<String> view = new ArrayList<>();
    for (Map.Entry<String, Double> entry : portfolioOnDate.entrySet()) {
      view.add(entry.getKey() + ":" + entry.getValue().toString());
    }
    return view;

  }

  @Override
  public List<String> getPortfolioDistribution(String date) {
    List<String> res = new ArrayList<>();
    LocalDate getDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Map<String, Double> portfolioOnDate = getPreviousOrCurrentPortfolio(getDate);
    for (Map.Entry<String, Double> entry : portfolioOnDate.entrySet()) {
      String ticker = entry.getKey();
      double shares = entry.getValue();
      StockData currentStock = new StockData(ticker);
      setStockCalculator(currentStock);
      String adjustedDate = stockCalculator.advanceBack(date);
      double closingPrice = currentStock.getClosingPrice().get(adjustedDate);
      double value = closingPrice * shares;
      res.add(ticker + ":" + value);
    }
    return res;
  }

  @Override
  public void rebalancePortfolio(String date, Map<String, Double> targetWeights) {
    double value = 0;
    for (Map.Entry<String, Double> entry : targetWeights.entrySet()) {
      if (entry.getValue() >= 1.0) {
        throw new IllegalArgumentException("Cannot more than 100% percent");
      } else if (entry.getValue() <= 0) {
        throw new IllegalArgumentException("Cannot be less than 0 weight");
      }
      value += entry.getValue();
    }
    if (value != 1.0) {
      throw new IllegalArgumentException("Weight should add up to one");
    }
    LocalDate getDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Map<String, Double> currentPortfolio = getPreviousOrCurrentPortfolio(getDate);
    if (currentPortfolio.isEmpty()) {
      throw new IllegalArgumentException("No portfolio found");
    }
    double totalValue = checkPortfolioValue(date);

    Map<String, Double> transactionMap = new HashMap<>();

    for (Map.Entry<String, Double> entry : currentPortfolio.entrySet()) {
      String ticker = entry.getKey();
      double shares = entry.getValue();
      StockData stock = new StockData(ticker);
      setStockCalculator(stock);
      String adjustedDate = stockCalculator.advanceBack(date);
      double closingPrice = stock.getClosingPrice().get(adjustedDate);
      double currentValue = closingPrice * shares;
      double targetValue = totalValue * targetWeights.get(ticker);
      double difference = targetValue - currentValue;

      if (difference > 0) {
        double sharesToBuy = difference / closingPrice;
        transactionMap.put(ticker, sharesToBuy);
      } else if (difference < 0) {
        double sharesToSell = -difference / closingPrice;
        transactionMap.put(ticker, -sharesToSell);
      }
    }

    executeTransactions(transactionMap, getDate);
  }

  @Override
  public void savePortfolioToFile() {
    try {
      File dir = new File("res/savedPortfolios");
      if (!dir.exists()) {
        dir.mkdirs();
      }
      FileWriter writer = new FileWriter("res/savedPortfolios/" + name + ".txt");
      writer.write("Portfolio: " + name + "\n");

      for (Map.Entry<LocalDate, Map<String, Double>> entry : history.entrySet()) {
        LocalDate date = entry.getKey();
        Map<String, Double> portfolioComposition = entry.getValue();
        writer.write("Date: " + date.toString() + "\n");

        for (Map.Entry<String, Double> stockEntry : portfolioComposition.entrySet()) {
          String ticker = stockEntry.getKey();
          double shares = stockEntry.getValue();
          writer.write(ticker + "," + shares + "\n");
        }

        writer.write("\n");
      }

      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setHistory(Map<LocalDate, Map<String, Double>> history) {
    this.history = history;
  }

  private Map<String, Double> getPreviousOrCurrentPortfolio(LocalDate date) {
    Map<String, Double> port = history.get(date);
    if (port == null) {
      LocalDate previousDate = findPreviousDate(date);
      if (previousDate != null) {
        port = new HashMap<>(history.get(previousDate));
      } else {
        port = new HashMap<>();
      }
    }
    return port;
  }

  private LocalDate findPreviousDate(LocalDate date) {
    LocalDate previousDate = null;
    for (LocalDate historyDate : history.keySet()) {
      if (historyDate.isBefore(date) && (previousDate == null
              || historyDate.isAfter(previousDate))) {
        previousDate = historyDate;
      }
    }
    return previousDate;
  }

  private void executeTransactions(Map<String, Double> transactions, LocalDate date) {
    for (Map.Entry<String, Double> entry : transactions.entrySet()) {
      String ticker = entry.getKey();
      double shares = entry.getValue();
      if (shares > 0) {
        buyStock(ticker, shares, date.toString());
      } else if (shares < 0) {
        sellStock(ticker, -shares, date.toString());
      }
    }
  }

  private void updateHistoryBuy(Map<String, Double> port, LocalDate date) {
    for (Map.Entry<LocalDate, Map<String, Double>> entry : history.entrySet()) {
      LocalDate futureDate = entry.getKey();
      if (futureDate.isAfter(date)) {
        Map<String, Double> futurePortfolio = new HashMap<>(entry.getValue());
        for (Map.Entry<String, Double> portEntry : port.entrySet()) {
          String ticker = portEntry.getKey();
          double shares = portEntry.getValue();
          futurePortfolio.merge(ticker, shares, Double::sum);
        }
        history.put(futureDate, futurePortfolio);
      }
    }
  }

  private void updateHistorySell(Map<String, Double> port, LocalDate date) {
    for (Map.Entry<LocalDate, Map<String, Double>> entry : history.entrySet()) {
      LocalDate futureDate = entry.getKey();
      if (futureDate.isAfter(date)) {
        Map<String, Double> futurePortfolio = new HashMap<>(entry.getValue());
        for (Map.Entry<String, Double> portEntry : port.entrySet()) {
          String ticker = portEntry.getKey();
          double shares = portEntry.getValue();
          futurePortfolio.merge(ticker, -shares, Double::sum);
        }
        history.put(futureDate, futurePortfolio);
      }
    }
  }

  private void setStockCalculator(Stock stock) {
    this.stockCalculator = new StockMethodsImpl(stock);
  }
}