import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.portfolio.Portfolio;
import model.portfolio.PortfolioData;
import model.stock.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test file for the methods of {@link Portfolio}.
 */
public class StockPortfolioTest {
  private Portfolio portfolio;
  private Stock apple;
  private Stock google;

  @Before
  public void setUp() throws IOException {
    portfolio = new PortfolioData("brokerage");
  }

  @Test
  public void testGetNameGetSize() {
    assertEquals("brokerage", portfolio.getName());
    assertEquals(0, portfolio.getSize("2020-05-05"));
  }

  @Test
  public void testBuyStock() throws IOException {
    assertEquals(0, portfolio.getSize("2020-05-05"));
    //adds a valid stock
    portfolio.buyStock("AAPL", 5, "2020-05-05");
    List<String> exp = new ArrayList<String>();
    exp.add("AAPL:5.0");
    assertEquals(exp, portfolio.printPortfolio("2020-05-05"));
    //buys 5 again in the future
    portfolio.buyStock("AAPL", 5, "2020-06-05");
    assertEquals(1, portfolio.getSize("2020-07-05"));
    List<String> exp2 = new ArrayList<>();
    exp2.add("AAPL:10.0");
    assertEquals(exp2, portfolio.printPortfolio("2020-06-05"));
    //add a second stock, but it is a negative value, throw an error
    assertThrows(IllegalArgumentException.class, () -> {
      portfolio.buyStock("GOOG", -5, "2020-07-05");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      portfolio.buyStock("GOOG", 5, "2030-07-05");
    });

    portfolio.buyStock("GOOG", 5, "2020-07-05");
    List<String> exp3 = new ArrayList<>();
    exp3.add("AAPL:10.0");
    exp3.add("GOOG:5.0");
    assertEquals(exp3, portfolio.printPortfolio("2020-07-05"));
  }

  @Test
  public void testSellStocks() throws IOException {
    //buy 5 aapl and 10 goog
    portfolio.buyStock("AAPL", 5, "2020-05-05");
    portfolio.buyStock("GOOG", 10, "2020-05-05");
    List<String> exp = new ArrayList<>();
    exp.add("GOOG:10.0");
    exp.add("AAPL:5.0");
    assertEquals(exp, portfolio.printPortfolio("2020-05-05"));
    assertEquals(2, portfolio.getSize("2020-05-05"));
    //sell stock in the future
    portfolio.sellStock("GOOG", 5, "2020-06-05");
    assertEquals(2, portfolio.getSize("2020-07-05"));
    exp.set(0, "GOOG:5.0");
    assertEquals(exp, portfolio.printPortfolio("2020-06-05"));
    //selling more than what the portfolio has
    assertThrows(IllegalArgumentException.class, () -> {
      portfolio.sellStock("GOOG", 10, "2020-07-05");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      portfolio.sellStock("GOOG", 10, "2030-07-05");
    });

    //removing all the stocks one, should remove the stock from the portfolio
    portfolio.sellStock("AAPL", 5, "2020-07-01");
    exp.remove(1);
    assertEquals(exp, portfolio.printPortfolio("2020-07-05"));
  }

  @Test
  public void testViewPortfolioValue() throws IOException {
    //no stocks in portfolio
    try {
      portfolio.checkPortfolioValue("2024-06-02");
    } catch (Exception e) {
      assertNull(e.getMessage());
    }
    portfolio.buyStock("AAPL", 10, "2024-06-02");
    //check value of 10 stocks
    assertEquals(1922.5, portfolio.checkPortfolioValue("2024-06-02"), .1);
    //removes stocks in the future and then checks again
    portfolio.sellStock("AAPL", 5, "2024-06-04");
    assertEquals(971.75, portfolio.checkPortfolioValue("2024-06-04"), .1);
    //adds a new stock in the future and then checks
    portfolio.buyStock("GOOG", 3, "2024-06-06");
    assertEquals(1507.45, portfolio.checkPortfolioValue("2024-06-06"), .1);
    //checking value on an invalid date
    assertThrows(IllegalArgumentException.class, () -> {
      portfolio.checkPortfolioValue("1800-06-02");
    });
  }

  @Test
  public void testPrintPortfolio() {
    //test printPortfolio on an invalid date
    try {
      portfolio.printPortfolio("5000-06-02");
    } catch (Exception e) {
      assertNull(e.getMessage());
    }

    //buy 2 stocks on different dates and checks if correct value is returned
    portfolio.buyStock("AAPL", 5, "2024-05-05");
    portfolio.buyStock("GOOG", 10, "2024-06-05");
    List<String> exp = new ArrayList<String>();
    exp.add("GOOG:10.0");
    exp.add("AAPL:5.0");
    assertEquals(exp, portfolio.printPortfolio("2024-06-10"));
  }

  @Test
  public void testGetPortfolioDistribution() {
    //buy 2 stocks on different dates and check the distribution in the future
    portfolio.buyStock("AAPL", 5, "2024-05-05");
    portfolio.buyStock("GOOG", 10, "2024-06-05");
    List<String> exp = new ArrayList<String>();
    exp.add("GOOG:1766.3");
    exp.add("AAPL:965.6");

    assertEquals(exp, portfolio.getPortfolioDistribution("2024-06-10"));
  }

  @Test
  public void testRebalancePortfolio() {
    Map<String, Double> weightList;
    List<String> expected;
    //cant have negative weight
    portfolio = new PortfolioData("brokerage");
    weightList = new HashMap<String, Double>();
    weightList.put("AAPL", -0.5);
    weightList.put("GOOG", 0.4);
    portfolio.buyStock("AAPL", 5, "2024-05-05");
    portfolio.buyStock("GOOG", 10, "2024-06-05");
    try {
      portfolio.rebalancePortfolio("2024-06-10", weightList);
    } catch (Exception e) {
      assertEquals("Cannot be less than 0 weight", e.getMessage());
    }

    //cant have more than 1 weight
    portfolio = new PortfolioData("brokerage");
    weightList = new HashMap<String, Double>();
    weightList.put("AAPL", 1.1);
    weightList.put("GOOG", 0.1);
    portfolio.buyStock("AAPL", 5, "2024-05-05");
    portfolio.buyStock("GOOG", 10, "2024-06-05");
    try {
      portfolio.rebalancePortfolio("2024-06-10", weightList);
    } catch (Exception e) {
      assertEquals("Cannot more than 100% percent", e.getMessage());
    }

    //does equal less than 1
    portfolio = new PortfolioData("brokerage");
    weightList = new HashMap<String, Double>();
    weightList.put("AAPL", 0.5);
    weightList.put("GOOG", 0.4);
    portfolio.buyStock("AAPL", 5, "2024-05-05");
    portfolio.buyStock("GOOG", 10, "2024-06-05");
    try {
      portfolio.rebalancePortfolio("2024-06-10", weightList);
    } catch (Exception e) {
      assertEquals("Weight should add up to one", e.getMessage());
    }
    //cannot equal over 1
    portfolio = new PortfolioData("brokerage");
    weightList = new HashMap<String, Double>();
    weightList.put("AAPL", 0.5);
    weightList.put("GOOG", 0.8);
    portfolio.buyStock("AAPL", 5, "2024-05-05");
    portfolio.buyStock("GOOG", 10, "2024-06-05");
    try {
      portfolio.rebalancePortfolio("2024-06-10", weightList);
    } catch (Exception e) {
      assertEquals("Weight should add up to one", e.getMessage());
    }

    //one that owrks
    portfolio = new PortfolioData("brokerage");
    weightList = new HashMap<String, Double>();
    weightList.put("AAPL", 0.5);
    weightList.put("GOOG", 0.5);
    portfolio.buyStock("AAPL", 5, "2024-05-05");
    portfolio.buyStock("GOOG", 10, "2024-06-05");
    portfolio.rebalancePortfolio("2024-06-10", weightList);
    expected = new ArrayList<String>();
    expected.add("GOOG:1365.95");
    expected.add("AAPL:1365.95");
    assertEquals(expected, portfolio.getPortfolioDistribution("2024-06-10"));
  }

  @Test
  public void testSavePortfolio() {
    portfolio.buyStock("AAPL", 5, "2024-05-05");
    portfolio.buyStock("GOOG", 10, "2024-06-05");
    portfolio.savePortfolioToFile();
    File file = new File("res/savedPortfolios/brokerage.txt");
    assertTrue(file.exists());
  }
}