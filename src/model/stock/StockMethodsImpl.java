package model.stock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class implements the StockMethods class and contains the methods for any calculations
 * needed for a stock. It contains methods to calculate things such as total gain/loss,
 * moving average, and crossover dates.
 */
public class StockMethodsImpl implements StockMethods {
  private final Map<String, Double> stockCSV;

  /**
   * This constructor creates a StockMethodsImpl object and initializes the map with
   * the closing prices of a stock.
   *
   * @param stock is the stock that we are going to use these calculating methods on.
   */
  public StockMethodsImpl(Stock stock) {
    stockCSV = stock.getClosingPrice();
  }

  @Override
  public double totalGainLoss(String startDate, String endDate) {
    if (isValidDate(startDate) && isValidDate(endDate)) {
      LocalDate parseEarly = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      LocalDate parseLate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      if (!parseLate.isBefore(parseEarly)) {
        String formattedDate = advanceBack(startDate);
        String formattedDate2 = advanceBack(endDate);
        double endVal = stockCSV.get(formattedDate2);
        double startVal = stockCSV.get(formattedDate);
        double ans = endVal - startVal;
        String truncate = String.format("%.2f", ans);
        return Double.parseDouble(truncate);
      } else {
        throw new IllegalArgumentException("Start date cannot be after end date. ");
      }
    } else {
      throw new IllegalArgumentException("Invalid date. ");
    }
  }

  @Override
  public double movingAverage(String startDate, int days) {
    if (isValidDate(startDate) && days > 0 && days <= stockCSV.size()) {
      LocalDate start = LocalDate.parse(startDate);
      double sum = 0;
      int count = 0;
      LocalDate currentDate = start;
      for (int i = 0; i < days; i++) {
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (!stockCSV.containsKey(formattedDate)) {
          String back = advanceBack(formattedDate);
          currentDate = LocalDate.parse(back);
          formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        sum += stockCSV.get(formattedDate);
        count++;
        currentDate = currentDate.minusDays(1);
        if (currentDate.isBefore(getEarliestDate())) {
          break;
        }
      }
      double avg = sum / count;
      String truncate = String.format("%.2f", avg);
      return Double.parseDouble(truncate);
    } else {
      throw new IllegalArgumentException("Invalid input. ");
    }
  }

  @Override
  public List<String> crossoverDates(String startDate, String endDate, int days) {
    List<String> crossoverDates = new ArrayList<>();
    if (isValidDate(startDate) && isValidDate(endDate) && days > 0 && days <= stockCSV.size()) {
      LocalDate start = LocalDate.parse(startDate);
      LocalDate currentDate = LocalDate.parse(endDate);
      if (startDate.equals(endDate)) {
        if (stockCSV.containsKey(startDate) && isACrossover(startDate, days)) {
          crossoverDates.add(startDate);
        }
        return crossoverDates;
      } else {
        while (!currentDate.isEqual(start)) {
          String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          if (!stockCSV.containsKey(formattedDate)) {
            String back = advanceBack(formattedDate);
            currentDate = LocalDate.parse(back);
            formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          }
          currentDate = currentDate.minusDays(1);
          if (isACrossover(formattedDate, days)) {
            crossoverDates.add(formattedDate);
          }
        }
      }
      if (stockCSV.containsKey(startDate) && isACrossover(startDate, days)) {
        crossoverDates.add(startDate);
      }
      return crossoverDates;

    } else {
      throw new IllegalArgumentException("Invalid input. ");
    }
  }

  @Override
  public String advanceBack(String date) {
    if (isValidDate(date) && !stockCSV.containsKey(date)) {
      LocalDate parsedDate = LocalDate.parse(date);
      String formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      while (parsedDate.isAfter(getEarliestDate().minusDays(1))
              && !stockCSV.containsKey(formattedDate)) {
        parsedDate = parsedDate.minusDays(1);
        formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      }
      return formattedDate;
    }
    return date;
  }

  @Override
  public boolean isValidDate(String date) {
    try {
      LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      LocalDate currentDate = LocalDate.now();
      LocalDate earliestDate = getEarliestDate();
      if (parsedDate.isAfter(currentDate) || parsedDate.isBefore(earliestDate)) {
        return false;
      }
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

  private LocalDate getEarliestDate() {
    LocalDate earliestDate = LocalDate.MAX;
    for (String date : stockCSV.keySet()) {
      LocalDate currentDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      if (currentDate.isBefore(earliestDate)) {
        earliestDate = currentDate;
      }
    }
    return earliestDate;
  }

  private boolean isACrossover(String date, int day) {
    double price = stockCSV.get(date);
    double movingDayAverage = movingAverage(date, day);
    return price > movingDayAverage;
  }
}

