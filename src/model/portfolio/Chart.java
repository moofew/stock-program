package model.portfolio;

/**
 * Interface for a Chart. Contains the method to print out a chart.
 */
public interface Chart {
  /**
   * Using the fields from the constructor, the method creates the chart of the portfolio and
   * stores it in a string builder.
   *
   * @return a StringBuilder with the chart
   */
  StringBuilder printBarChart();
}
