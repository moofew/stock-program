import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import controller.IController;
import controller.StockController;
import model.stock.StockData;
import model.user.MockUserData;
import model.user.User;
import model.user.UserData;

import static org.junit.Assert.assertEquals;

/**
 * This class is the testing class for MockUserData, which in other words is testing the inputs
 * of the controller.
 */
public class ControllerInputTest {
  User model;
  Readable rd;
  Appendable ap;
  IController controller;

  @Before
  public void setUp() {
    model = new UserData(new StockData("SPY"));
    rd = new StringReader("");
    ap = new StringBuilder();
    controller = new StockController(rd, ap, model);
  }

  @Test
  public void testAddPort() throws IOException {
    Readable in = new StringReader("2\n1\ntestPort\n1\ntestPort2\n");
    Appendable dontCareOutput = new StringBuilder();
    StringBuilder log = new StringBuilder();
    MockUserData mockUser = new MockUserData(log);
    StockController test = new StockController(in, dontCareOutput, mockUser);
    test.control();
    String expected =
            "addPortfolio: portfolio = testPort\n" + "addPortfolio: portfolio = testPort2\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testPrintAllPorts() throws IOException {
    Readable in = new StringReader("2\n1\ntestPort\n1\ntestPort2\n4\n");
    Appendable dontCareOutput = new StringBuilder();
    StringBuilder log = new StringBuilder();
    MockUserData mockUser = new MockUserData(log);
    StockController test = new StockController(in, dontCareOutput, mockUser);
    test.control();
    String expected = "addPortfolio: portfolio = testPort\n" + "addPortfolio: portfolio " +
            "= testPort2\n" + "printAllPortfolios: printed\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testLoadData() throws IOException {
    Readable in = new StringReader("2\n1\ntestPort\n1\ntestPort2\n5\ntest\n");
    Appendable dontCareOutput = new StringBuilder();
    StringBuilder log = new StringBuilder();
    MockUserData mockUser = new MockUserData(log);
    StockController test = new StockController(in, dontCareOutput, mockUser);
    test.control();
    String expected = "addPortfolio: portfolio = testPort\n" + "addPortfolio: portfolio = " +
            "testPort2\n" + "loadUserData: fileName = test.txt\n";
    assertEquals(expected, log.toString());
  }
}
