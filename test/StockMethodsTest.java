import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import model.stock.Stock;
import model.stock.StockData;
import model.stock.StockMethods;
import model.stock.StockMethodsImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Test file for all the stock calculations.
 */
public class StockMethodsTest {
  private StockMethods calc;

  @Before
  public void setUp() throws IOException {
    Stock test = new StockData("GOOG");
    calc = new StockMethodsImpl(test);
  }


  @Test
  public void totalGainLoss() {
    //takes the value of friday for weekends
    double ans = calc.totalGainLoss("2024-05-18", "2024-06-04");
    assertEquals(-2.16, ans, 0.1);

    //both start on same weekend
    double ans2 = calc.totalGainLoss("2024-06-01", "2024-06-02");
    assertEquals(0, ans2, 0.1);

    //same day
    double ans3 = calc.totalGainLoss("2024-06-03", "2024-06-03");
    assertEquals(0, ans3, 0.1);


  }

  @Test
  public void gainLossExceptions() {
    //end date is before early
    assertThrows(IllegalArgumentException.class, () -> {
      calc.totalGainLoss("2024-06-04", "2024-06-03");
    });
    //start is invalid date (day)
    assertThrows(DateTimeParseException.class, () -> {
      calc.totalGainLoss("2024-02-31", "2024-03-25");
    });

    //start is invalid date (month)
    assertThrows(IllegalArgumentException.class, () -> {
      calc.totalGainLoss("2024-13-31", "2024-03-25");
    });

    //start is invalid date (year)
    assertThrows(IllegalArgumentException.class, () -> {
      calc.totalGainLoss("2220-13-31", "2024-03-25");
    });


    //end is invalid date (day)
    assertThrows(DateTimeParseException.class, () -> {
      calc.totalGainLoss("2024-02-27", "2024-02-31");
    });
    //end is invalid date (month)
    assertThrows(IllegalArgumentException.class, () -> {
      calc.totalGainLoss("2024-02-27", "2024-13-31");
    });
    //end is invalid date (year)
    assertThrows(IllegalArgumentException.class, () -> {
      calc.totalGainLoss("2024-02-27", "2220-02-31");
    });
    //start does not exist, too early
    assertThrows(IllegalArgumentException.class, () -> {
      calc.totalGainLoss("1945-02-27", "2024-02-31");
    });
    //end does not exist, too early
    assertThrows(IllegalArgumentException.class, () -> {
      calc.totalGainLoss("2024-02-27", "1945-02-31");
    });

  }

  @Test
  public void movingAverageRegular() {
    //accounts for weekends
    double ans = calc.movingAverage("2024-06-04", 5);
    assertEquals(174.89, ans, 0.1);

    //one day
    assertEquals(1469.33, calc.movingAverage("2020-09-29", 1), 0.1);

    //start on a weekend (should go to friday or last available date)
    assertEquals(calc.movingAverage("2024-05-31", 1), calc.movingAverage("2024-06-02", 1), 0.1);

    //second to last day
    assertEquals(559.23, calc.movingAverage("2014-03-28", 30), .1);

    //last day
    assertEquals(558.46, calc.movingAverage("2014-03-27", 30), .1);

  }

  @Test
  public void movingAverageExpections() {
    //invalid date
    assertThrows(DateTimeParseException.class, () -> calc.movingAverage("2024-02-31", 5));

    //date from the future
    assertThrows(IllegalArgumentException.class, () -> calc.movingAverage("2025-02-4", 5));

    //date is not positive
    assertThrows(IllegalArgumentException.class, () -> calc.movingAverage("2024-06-04", -5));

    //date too far back without data
    assertThrows(IllegalArgumentException.class, () -> calc.movingAverage("1945-05-31", 5));
  }

  @Test
  public void crossoverDates() {
    List<String> exp = new ArrayList<>();
    exp.add("2024-05-29");
    exp.add("2024-05-28");
    exp.add("2024-05-24");
    exp.add("2024-05-23");
    exp.add("2024-05-22");
    List<String> ans = calc.crossoverDates("2024-05-22", "2024-05-29", 30);
    assertEquals(exp.toString(), ans.toString());

    //end date is a weekend
    List<String> exp2 = new ArrayList<>();
    exp2.add("2024-05-24");
    exp2.add("2024-05-23");
    exp2.add("2024-05-22");
    exp2.add("2024-05-21");
    exp2.add("2024-05-20");
    List<String> ans2 = calc.crossoverDates("2024-05-20", "2024-05-26", 30);
    assertEquals(exp2.toString(), ans2.toString());

    //start and end are the same and it is a weekend, should be blank
    List<String> exp3 = new ArrayList<>();
    List<String> ans3 = calc.crossoverDates("2024-05-26", "2024-05-26", 30);
    assertEquals(exp3.toString(), ans3.toString());

    //start and end are the same and it is a crossover, return itself
    List<String> exp4 = new ArrayList<>();
    exp4.add("2024-05-24");
    List<String> ans4 = calc.crossoverDates("2024-05-24", "2024-05-24", 30);
    assertEquals(exp4.toString(), ans4.toString());

    //regular test
    List<String> exp5 = new ArrayList<>();
    exp5.add("2024-05-31");
    exp5.add("2024-05-30");
    exp5.add("2024-05-29");
    exp5.add("2024-05-28");
    exp5.add("2024-05-24");
    exp5.add("2024-05-23");
    exp5.add("2024-05-22");
    exp5.add("2024-05-21");
    exp5.add("2024-05-20");
    exp5.add("2024-05-17");
    exp5.add("2024-05-16");
    exp5.add("2024-05-15");
    exp5.add("2024-05-14");
    exp5.add("2024-05-13");
    exp5.add("2024-05-10");
    exp5.add("2024-05-09");
    exp5.add("2024-05-08");
    exp5.add("2024-05-07");
    exp5.add("2024-05-06");

    List<String> ans5 = calc.crossoverDates("2024-05-05", "2024-06-02", 30);
    assertEquals(exp5.toString(), ans5.toString());


    //last date entry of the spreadsheet, should be blank since not crossover
    List<String> exp6 = new ArrayList<>();
    List<String> ans6 = calc.crossoverDates("2014-03-27", "2014-03-27", 30);
    assertEquals(exp6.toString(), ans6.toString());
  }
}