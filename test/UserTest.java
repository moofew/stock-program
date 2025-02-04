import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.portfolio.Portfolio;
import model.portfolio.PortfolioData;
import model.stock.StockData;
import model.user.User;
import model.user.UserData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Test file for all the methods of the {@link User}.
 */

public class UserTest {
  private User user;
  private Portfolio account;
  private Portfolio account2;
  private Portfolio account3;


  @Before
  public void setUp() {
    user = new UserData(new StockData("SPY"));
    account = new PortfolioData("investment");
    account2 = new PortfolioData("savings");
    account3 = new PortfolioData("brokerage");
  }

  @Test
  public void addPortfolioTest() {
    //add one
    user.addPortfolio(account);
    assertEquals(1, user.getUserPortSize());
    List<String> exp = new ArrayList<String>();
    exp.add("(0) investment");
    assertEquals(exp, user.printAllPortfolios());

    user.addPortfolio(account2);
    exp.add("(1) savings");
    assertEquals(2, user.getUserPortSize());
    assertEquals(exp, user.printAllPortfolios());

    user.addPortfolio(account3);
    exp.add("(2) brokerage");
    assertEquals(3, user.getUserPortSize());
    assertEquals(exp, user.printAllPortfolios());
  }

  @Test
  public void removeAndSelectPortfolioTest() {
    user.addPortfolio(account);
    user.addPortfolio(account2);
    user.addPortfolio(account3);
    List<String> exp = new ArrayList<>();
    exp.add("(0) investment");
    exp.add("(1) savings");
    exp.add("(2) brokerage");
    assertEquals(exp, user.printAllPortfolios());

    //removes 2nd portfolio
    user.removePortfolio(1);
    exp.remove(1);
    exp.set(1, "(1) brokerage");
    assertEquals(exp, user.printAllPortfolios());
    //attempts to remove a non-existent index
    assertThrows(IllegalArgumentException.class, () -> user.removePortfolio(10));

    //selects a valid portfolio
    assertEquals(account, user.selectPortfolio(0));

    //selects an invalid portfolio
    assertThrows(IllegalArgumentException.class, () -> user.selectPortfolio(10));
  }

  @Test
  public void getUserPortSizeTest() {
    user.addPortfolio(account);
    assertEquals(1, user.getUserPortSize());
    user.addPortfolio(account2);
    assertEquals(2, user.getUserPortSize());
    user.addPortfolio(account3);
    assertEquals(3, user.getUserPortSize());
  }

  @Test
  public void loadUserDataTest() {
    user = new UserData(new StockData("SPY"));
    //load portfolio that doesn't exist
    assertThrows(IllegalArgumentException.class, () -> {
      user.loadUserData("dne");
    });

    //load one portfolio
    ArrayList<String> list = new ArrayList<String>();
    list.add("AAPL:5.0");
    list.add("AMZN:10.0");
    try {
      user.loadUserData("test");
      assertEquals(1, user.getUserPortSize());
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertEquals(list, user.selectPortfolio(0).printPortfolio("2024-05-05"));

    //load second portfolio
    ArrayList<String> listTwo = new ArrayList<String>();
    listTwo.add("GOOG:100.0");
    listTwo.add("AAPL:10.0");
    try {
      user.loadUserData("testTwo");
      assertEquals(2, user.getUserPortSize());
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertEquals(listTwo, user.selectPortfolio(1).printPortfolio("2024-05-05"));
  }
}