package model.stock;

import java.util.List;

/**
 * Interface that contains the methods for the calculations of a stock. It contains methods such as
 * calculating the total gain/loss of a stock over a specified time period, the moving average of a
 * stock given an x amount of days, and the crossover dates of a stock given a time period and an x
 * amount of days.
 */
public interface StockMethods {

  /**
   * This method calculates the total gain/loss of a stock given a start date and ending date.
   *
   * @param startDate is the start date to calculate from.
   * @param endDate   is the end date to calculate until.
   * @return a double representing the value of the total gain/loss.
   */
  double totalGainLoss(String startDate, String endDate);

  /**
   * This method calculates the moving average of a stock given a start date and an x number of
   * days to backtrack by.
   *
   * @param startDate is the start date to calculate from.
   * @param days      is the amount of days to backtrack.
   * @return a double representing the value of the moving average.
   */
  double movingAverage(String startDate, int days);

  /**
   * This method gets all the crossover dates given a start date, end date, and an x number of days
   * to check from.
   *
   * @param startDate is the start date to look from.
   * @param endDate   is the ending date to stop at.
   * @param days      is the amount of days that you want to check by.
   * @return an ArrayList of Strings that contain the dates of crossover dates.
   */
  List<String> crossoverDates(String startDate, String endDate, int days);

  /**
   * This method is used if the closing price of a stock on a certain day does not exist. The date
   * has to be a valid date. If the closing price does not exist, then the current date is
   * decremented backwards until a valid date is attained. For example, although saturday and
   * sunday are valid dates, the closing price for those days do not exist, so the date is
   * decremented until it reaches a business day.
   *
   * @param date is the date that you are moving backwards from.
   * @return a String that represents the most recent business day.
   */
  String advanceBack(String date);


  /**
   * This method checks if the string inputted is a valid date. A valid date is when the date is
   * not earlier than the earliest entry of the data and follow the Georgian calendar.
   *
   * @param date is the date as a string.
   * @return a boolean on whether the date is valid,
   */
  boolean isValidDate(String date);
}
