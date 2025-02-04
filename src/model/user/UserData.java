package model.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.portfolio.Portfolio;
import model.portfolio.PortfolioData;
import model.stock.StockData;
import model.stock.StockMethods;
import model.stock.StockMethodsImpl;

/**
 * This class represents a user and implements the User interface. This class represents the
 * account for a user and keeps track of all the portfolios for a certain user.
 */
public class UserData implements User {
  private final List<Portfolio> portfolioList;
  public StockMethods calc;

  /**
   * This constructor creates a UserPortfolio object with a list that is initially empty.
   */
  public UserData(StockData stock) {
    portfolioList = new ArrayList<>();
    this.calc = new StockMethodsImpl(stock);
  }

  @Override
  public void removePortfolio(int index) {
    if (index < 0 || index >= portfolioList.size()) {
      throw new IllegalArgumentException("Not a valid index. ");
    }
    portfolioList.remove(index);
  }

  @Override
  public void addPortfolio(Portfolio portfolio) {
    portfolioList.add(portfolio);
  }

  @Override
  public List<String> printAllPortfolios() {
    List<String> ans = new ArrayList<>();
    for (int i = 0; i < portfolioList.size(); i++) {
      ans.add("(" + i + ") " + portfolioList.get(i).getName());
    }
    return ans;
  }

  @Override
  public int getUserPortSize() {
    return portfolioList.size();
  }

  @Override
  public Portfolio selectPortfolio(int index) {
    if (index < 0 || index >= portfolioList.size()) {
      throw new IllegalArgumentException("Not a valid index. ");
    }
    return portfolioList.get(index);
  }

  @Override
  public void loadUserData(String fileName) {
    File file = new File("res/savedPortfolios/" + fileName + ".txt");
    if (!file.exists()) {
      throw new IllegalArgumentException("Incorrect file format. ");
    }
    PortfolioData portfolio = loadPortfolioFromFile(file);
    if (portfolio != null) {
      portfolioList.add(portfolio);
    }
  }

  private PortfolioData loadPortfolioFromFile(File file) {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line = reader.readLine();
      String portfolioName = line.split(": ")[1];
      PortfolioData portfolio = new PortfolioData(portfolioName);
      Map<LocalDate, Map<String, Double>> history = new HashMap<>();

      String dateLine;
      while ((dateLine = reader.readLine()) != null) {
        if (dateLine.startsWith("Date: ")) {
          String dateStr = dateLine.split(": ")[1];
          LocalDate date = LocalDate.parse(dateStr);
          Map<String, Double> portfolioOnDate = new HashMap<>();

          String stockLine;
          while ((stockLine = reader.readLine()) != null && !stockLine.isEmpty()) {
            String[] stockData = stockLine.split(",");
            String ticker = stockData[0];
            double shares = Double.parseDouble(stockData[1]);
            portfolioOnDate.put(ticker, shares);
          }

          history.put(date, portfolioOnDate);
        }
      }

      portfolio.setHistory(history);
      portfolio.getPortfolioDistribution(LocalDate.now().toString());
      return portfolio;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}