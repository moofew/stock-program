import org.junit.Before;
import org.junit.Test;

import model.portfolio.Chart;
import model.portfolio.Portfolio;
import model.portfolio.PortfolioChart;
import model.portfolio.PortfolioData;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the Chart interface, contains methods to check if printChart works properly.
 */

public class PortfolioTest {
  private Portfolio portfolio;
  private StringBuilder storeTest;

  @Before
  public void setUp() {
    portfolio = new PortfolioData("savings");
    storeTest = new StringBuilder();
    portfolio.buyStock("AAPL", 1, "2016-05-05");

  }

  @Test
  public void printPortfolioYearsTest() {
    StringBuilder expected = new StringBuilder();
    expected.append("Performance of portfolio savings from 2020-02-01 to 2024-06-05\n");
    expected.append("\n");
    expected.append("2020 : **********************************\n");
    expected.append("2021 : **********************************************\n");
    expected.append("2022 : *********************************\n");
    expected.append("2023 : **************************************************\n");
    expected.append("2024 : ************************************************\n");
    expected.append("\n");
    expected.append("Scale: * = 3.85");
    expected.append("\n");

    Chart testChart = new PortfolioChart(portfolio, "2020-02-01", "2024-06-05");
    storeTest = testChart.printBarChart();
    assertEquals(expected.toString(), storeTest.toString());
  }

  @Test
  public void printPortfolioDaysTest() {
    StringBuilder expected = new StringBuilder();
    expected.append("Performance of portfolio savings from 2020-02-01 to 2020-02-04\n");
    expected.append("\n");
    expected.append("Feb 01 2020 : ************************************************\n");
    expected.append("Feb 02 2020 : ************************************************\n");
    expected.append("Feb 03 2020 : ************************************************\n");
    expected.append("Feb 04 2020 : **************************************************\n");
    expected.append("\n");
    expected.append("Scale: * = 6.38");
    expected.append("\n");

    Chart testChart = new PortfolioChart(portfolio, "2020-02-01", "2020-02-04");
    storeTest = testChart.printBarChart();
    //System.out.println(storeTest.toString());
    assertEquals(expected.toString(), storeTest.toString());
  }

  @Test
  public void printPortfolioMonthsTest() {
    StringBuilder expected = new StringBuilder();
    expected.append("Performance of portfolio savings from 2023-10-20 to 2024-06-05\n");
    expected.append("\n");
    expected.append("Oct 2023 : ********************************************\n");
    expected.append("Nov 2023 : *************************************************\n");
    expected.append("Dec 2023 : **************************************************\n");
    expected.append("Jan 2024 : ***********************************************\n");
    expected.append("Feb 2024 : **********************************************\n");
    expected.append("Mar 2024 : ********************************************\n");
    expected.append("Apr 2024 : ********************************************\n");
    expected.append("May 2024 : *************************************************\n");
    expected.append("\n");
    expected.append("Scale: * = 3.85");
    expected.append("\n");

    Chart testChart = new PortfolioChart(portfolio, "2023-10-20", "2024-06-05");
    storeTest = testChart.printBarChart();
    //System.out.println(storeTest.toString());
    assertEquals(expected.toString(), storeTest.toString());
  }


}