package model.portfolio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This contains the method and its helper in order to visualize the performance of a portfolio.
 */
public class PortfolioChart implements Chart {
  private final Portfolio portfolio;
  private final String start;
  private final String end;
  private String state;

  /**
   * This is the constructor for the {@link PortfolioChart} class, it takes in the necessary
   * information needed in order to generate the performance chart. The state field is used for
   * the formatting of the visualization.
   *
   * @param portfolio the portfolio to be visualized
   * @param start     the start date of the visualization
   * @param end       the end date of the visualization
   */
  public PortfolioChart(Portfolio portfolio, String start, String end) {
    this.portfolio = portfolio;
    if (LocalDate.parse(end).isAfter(LocalDate.now())
            || LocalDate.parse(start).isAfter(LocalDate.parse(end))) {
      throw new IllegalArgumentException("Invalid date input");
    }
    this.start = start;
    this.end = end;
    state = "days";
  }

  @Override
  public StringBuilder printBarChart() {
    LocalDate startDate = LocalDate.parse(this.start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    LocalDate endDate = LocalDate.parse(this.end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    List<LocalDate> timestamps = getTimestamps(startDate, endDate);
    Map<LocalDate, Double> portfolioValues = getPortfolioValues(portfolio, timestamps);
    double maxValue = getMaxValue(portfolioValues);
    double minValue = getMinValue(portfolioValues);
    double scale = calculateScale(maxValue, minValue);

    StringBuilder sb = new StringBuilder();
    sb.append("Performance of portfolio ").append(portfolio.getName()).append(" from ")
            .append(startDate).append(" to ").append(endDate).append("\n\n");

    for (LocalDate timestamp : timestamps) {
      double value = portfolioValues.get(timestamp);
      if (state.equals("days")) {
        sb.append(String.format("%-12s: %s%n",
                timestamp.format(DateTimeFormatter.ofPattern("MMM dd yyyy")),
                generateAsterisks(value, scale)));
      } else if (state.equals("weeks")) {
        sb.append(String.format("%-12s: %s%n",
                timestamp.format(DateTimeFormatter.ofPattern("MMM dd yyyy")),
                generateAsterisks(value, scale)));
      } else if (state.equals("months")) {
        sb.append(String.format("%-9s: %s%n",
                timestamp.format(DateTimeFormatter.ofPattern("MMM yyyy")),
                generateAsterisks(value, scale)));
      } else {
        sb.append(String.format("%-5s: %s%n",
                timestamp.format(DateTimeFormatter.ofPattern("yyyy")),
                generateAsterisks(value, scale)));
      }
    }

    sb.append("\nScale: * = ").append(String.format("%.2f", scale)).append("\n");
    return sb;
  }

  /**
   * Base on how many days are between the start and end, will dictate the intervals it will use
   * and add it to a list.
   *
   * @param startDate the start date of the range
   * @param endDate   the end date of the range
   * @return a list with the dates for the chart
   */
  private List<LocalDate> getTimestamps(LocalDate startDate, LocalDate endDate) {
    List<LocalDate> timestamps = new ArrayList<>();
    int daysBetween = getDaysBetween(startDate, endDate);

    if (daysBetween <= 5) {
      state = "days";
      while (!startDate.isEqual(endDate)) {
        timestamps.add(startDate);
        startDate = startDate.plusDays(1);
      }
      timestamps.add(endDate);
    } else if (daysBetween <= 30) {
      state = "days";
      timestamps.add(startDate);
      while (!startDate.isEqual(endDate)) {
        startDate = startDate.plusDays(1);
        timestamps.add(startDate);
      }
      timestamps.add(endDate);
    } else if (daysBetween <= 210) {
      state = "weeks";
      timestamps.add(startDate);
      while (startDate.isBefore(endDate)) {
        startDate = startDate.plusWeeks(1);
        timestamps.add(startDate);
      }
      timestamps.add(endDate);
    } else if (daysBetween <= 900) {
      state = "months";
      timestamps.add(startDate);
      while (startDate.isBefore(endDate.minusMonths(1))) {
        startDate = startDate.plusMonths(1);
        timestamps.add(startDate);
      }
    } else {
      state = "years";
      timestamps.add(startDate);
      while (startDate.isBefore(endDate.minusYears(1))) {
        startDate = startDate.plusYears(1);
        timestamps.add(startDate);
      }
    }

    return timestamps;
  }

  private Map<LocalDate, Double> getPortfolioValues(Portfolio portfolio,
                                                    List<LocalDate> timestamps) {
    Map<LocalDate, Double> portfolioValues = new HashMap<>();
    String date;
    double valueOfFirst = portfolio.checkPortfolioValue(String.valueOf(timestamps.get(0)));
    portfolioValues.put(timestamps.get(0), valueOfFirst);
    for (LocalDate timestamp : timestamps) {
      date = getDateForCalculation(timestamp, timestamps.get(0),
              timestamps.get(timestamps.size() - 1));
      double value = portfolio.checkPortfolioValue(date);
      portfolioValues.put(timestamp, value);
    }
    return portfolioValues;
  }

  private String getDateForCalculation(LocalDate timestamp, LocalDate startDate,
                                       LocalDate endDate) {
    int daysBetween = getDaysBetween(startDate, endDate);

    if (daysBetween <= 5) {
      return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } else if (daysBetween <= 30) {
      return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } else if (daysBetween <= 210) {
      return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } else if (daysBetween <= 900) {
      return timestamp.with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.
              ofPattern("yyyy-MM-dd"));
    } else {
      LocalDate tempTime = LocalDate.parse(timestamp.with(TemporalAdjusters.lastDayOfYear()).
              format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      if (LocalDate.now().isBefore(tempTime)) {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      } else {
        return tempTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      }

    }
  }

  private double getMaxValue(Map<LocalDate, Double> portfolioValues) {
    double maxValue = 0;
    for (double value : portfolioValues.values()) {
      if (value > maxValue) {
        maxValue = value;
      }
    }
    return maxValue;
  }

  private double getMinValue(Map<LocalDate, Double> portfolioValues) {
    double minValue = 0;
    for (double value : portfolioValues.values()) {
      if (value < minValue) {
        minValue = value;
      }
    }
    return minValue;
  }

  private double calculateScale(double maxValue, double minValue) {
    double range = maxValue - minValue;
    if (range == 0) {
      return 1.0;
    }
    double scale = maxValue / 50.0;
    if (scale < 1000) {
      return scale;
    } else {
      double adjustedScale = 1.0;
      while (maxValue / adjustedScale > 50) {
        adjustedScale *= 10;
      }
      return adjustedScale;
    }
  }


  private String generateAsterisks(double value, double scale) {
    int numberOfAsterisks = (int) (value / scale);
    String res = "";
    for (int i = 0; i < numberOfAsterisks; i++) {
      res += "*";
    }
    return res;
  }

  private int getDaysBetween(LocalDate startDate, LocalDate endDate) {
    int daysBetween = 0;
    LocalDate currentDate = startDate;
    while (!currentDate.isAfter(endDate)) {
      daysBetween++;
      currentDate = currentDate.plusDays(1);
    }
    return daysBetween;
  }
}