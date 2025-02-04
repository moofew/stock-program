package model.user;

import java.util.List;

import model.portfolio.Portfolio;

/**
 * Interface that represents a user, which contains a user's portfolios and keeps track of them.
 */
public interface User {

  /**
   * Removes a portfolio from the user.
   *
   * @param index of the portfolio to be removed
   */
  void removePortfolio(int index);

  /**
   * Add a new portfolio to the user.
   *
   * @param portfolio is portfolio to be added.
   */
  void addPortfolio(Portfolio portfolio);

  /**
   * Views all portfolios that the user contains.
   */
  List<String> printAllPortfolios();

  /**
   * Returns the size of the user's account, or how many portfolios they have.
   */
  int getUserPortSize();

  /**
   * Returns the portfolio selected based on the given index.
   *
   * @param index is the index of the portfolio to be returned.
   * @return the selected StockPortfolio.
   */
  Portfolio selectPortfolio(int index);


  /**
   * This method loads the user data from a txt file that is given from the user given a String
   * that represents everything before the .txt extension in the local res/savedPortfolios
   * directory.
   *
   * @param fileName is the name of the txt file in the res/savedPortfolios directory.
   */
  void loadUserData(String fileName);
}
