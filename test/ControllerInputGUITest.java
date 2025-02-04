import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.MockStockControllerGUI;
import model.stock.Stock;
import model.stock.StockData;
import model.user.User;
import model.user.UserData;

import javax.swing.JList;

import java.io.IOException;

/**
 * Test file to check the mock inputs of the GUI controller.
 */
public class ControllerInputGUITest {

  private StringBuilder log;
  private MockStockControllerGUI mockController;
  private User test;

  @Before
  public void setUp() {
    log = new StringBuilder();
    mockController = new MockStockControllerGUI(log);
    Stock stock = new StockData("AAPL");
    test = new UserData((StockData) stock);
  }

  @Test
  public void testProcessCreatePortfolio() {
    mockController.processCreatePortfolio("createTest", test);
    assertEquals("processCreatePortfolio: portfolioName = createTest user = 0\n",
            log.toString());
  }

  @Test
  public void testProcessLoadPortfolio() throws IOException {
    mockController.processLoadPortfolio("LoadedPortfolio", test);
    assertEquals("processLoadPortfolio: portfolioName = LoadedPortfolio user = 0\n",
            log.toString());
  }

  @Test
  public void testProcessBuyStock() {
    mockController.processBuyStock(test, 0, "AAPL", 10, "2024-05-05");
    assertEquals("processBuyStock: user = 0 index = 0, ticker = AAPL amt = 10.0 " +
            "date" + " = 2024-05-05\n", log.toString());
  }

  @Test
  public void testProcessSellStock() {
    mockController.processSellStock(test, 1, "GOOGL", 5, "2024-05-05");
    assertEquals("processSellStock: user = 0 index = 1, ticker = GOOGL amt = 5.0 " +
            "date = 2024-05-05", log.toString());
  }

  @Test
  public void testProcessGetValue() {
    mockController.processGetValue(test, 2, "2024-05-05");
    assertEquals("processGetValue: user = 0 index = 2, date = 2024-05-05",
            log.toString());
  }

  @Test
  public void testProcessGetComposition() {
    mockController.processGetComposition(test, 2, "2024-05-05");
    assertEquals("processGetComposition: user = 0 index = 2, date = 2024-05-05",
            log.toString());
  }

  @Test
  public void testProcessEditPortfolio() {
    mockController.processEditPortfolio(4, test);
    assertEquals("processEditPortfolio: user = 0 index = 4\n", log.toString());
  }

  @Test
  public void testProcessSavePortfolio() {
    mockController.processSavePortfolio(3, test);
    assertEquals("processSavePortfolio: user = 0 index = 3\n", log.toString());
  }

  @Test
  public void testGetPortfolioList() {
    JList<String> testList = new JList<>();
    mockController.getPortfolioList(testList, test);
    assertTrue(log.toString().startsWith("getPortfolioList: user = 0 user = "));
  }

  @Test
  public void testControl() throws IOException {
    mockController.control();
    assertEquals("control: visible", log.toString());
  }

}