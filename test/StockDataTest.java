import org.junit.Test;

import java.io.File;
import java.io.IOException;

import model.stock.RetrieveData;
import model.stock.StockData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test file to check the methods of the {@link StockData}.
 */

public class StockDataTest {
  @Test
  public void testStockData() {
    StockData data = null;
    RetrieveData retrieveData = null;
    //invalid ticker
    try {
      data = new StockData("");
    } catch (Exception e) {
      assertEquals("Invalid ticker: ", e.getMessage());
    }

    //valid ticker and write to CSV
    RetrieveData.writeToCSV("META");
    File f = new File("res/csvFiles/META.csv");
    assertTrue(f.exists());
  }

  @Test
  public void testGetTicker() {
    StockData data = new StockData("AAPL");
    assertEquals("AAPL", data.getTicker());
    data = new StockData("GOOG");
    assertEquals("GOOG", data.getTicker());

  }

  @Test
  public void testStockInfo() throws IOException {
    //trying to create stock with invalid ticker
    assertThrows(IllegalArgumentException.class, () -> {
      StockData stock = new StockData("DONTEXIST");
    });

    StockData info = new StockData("TSLA");
    File f = new File("res/csvFiles/TSLA.csv");
    assertTrue(f.exists());

    assertThrows(IllegalArgumentException.class, () -> {
      StockData info2 = new StockData("DOESNOTEXIST");
    });
  }
}
