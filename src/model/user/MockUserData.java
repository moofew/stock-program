package model.user;

import java.util.List;
import java.util.Objects;

import model.portfolio.Portfolio;

/**
 * Mock for the controllers, checks for the inputs for all controller methods.
 */
public class MockUserData implements User {
  final StringBuilder log;

  /**
   * This constructor creates a MockUserData object, which initializes a StringBuilder which
   * keeps track of all the inputs to ensure that the methods are called correctly.
   *
   * @param log is where all the inputs are kept for tracking.
   */
  public MockUserData(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public void removePortfolio(int index) {
    log.append(String.format("removePortfolio: index = %d\n", index));
  }

  @Override
  public void addPortfolio(Portfolio portfolio) {
    log.append(String.format("addPortfolio: portfolio = %s\n", portfolio.getName()));
  }

  @Override
  public List<String> printAllPortfolios() {
    log.append("printAllPortfolios: printed\n");
    return List.of();
  }

  @Override
  public int getUserPortSize() {
    log.append("getUserPortSize: got\n");
    return 0;
  }

  @Override
  public Portfolio selectPortfolio(int index) {
    log.append(String.format("selectPortfolio: index = %d\n", index));
    return null;
  }

  @Override
  public void loadUserData(String fileName) {
    log.append(String.format("loadUserData: fileName = %s.txt\n", fileName));
  }
}
