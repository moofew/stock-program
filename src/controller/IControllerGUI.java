package controller;

import java.io.IOException;
import java.util.List;


import javax.swing.JList;

import model.portfolio.Portfolio;
import model.user.User;

/**
 * This interface represents the controller for our Stock Program GUI. It contains methods that
 * call methods from the model to connect it to the view.
 */
public interface IControllerGUI extends IController {

  /**
   * This method is called when the create portfolio button is pressed. It calls the method from
   * the model to create a portfolio.
   *
   * @param portfolioName is the name of the portfolio to be created.
   * @param user          is the user that the portfolio is being added to.
   */
  void processCreatePortfolio(String portfolioName, User user);

  /**
   * This method is called when the load portfolio button is pressed. It calls the method from the
   * model to load a portfolio from a file.
   *
   * @param portfolioName is the file name that represents the portfolio name.
   * @param user          is the user that the portfolio is being added to.
   * @throws IOException if the file that is trying to be loaded is not valid.
   */
  void processLoadPortfolio(String portfolioName, User user) throws IOException;

  /**
   * This method is called when the buy stock button is pressed. It calls the method from the model
   * to buy/add a stock to the current portfolio.
   *
   * @param user   is the user that contains the current portfolio.
   * @param index  is the index of the portfolio.
   * @param ticker is the ticker of the stock that is being bought/added.
   * @param amt    is the quantity that you are adding/buying.
   * @param date   is the date that you are buying on.
   */
  void processBuyStock(User user, int index, String ticker, double amt, String date);

  /**
   * This method is called when the sell stock button is pressed. It calls the method from the
   * model to sell/remove a stock from the current portfolio.
   *
   * @param user   is the user that contains the current portfolio.
   * @param index  is the index of the portfolio.
   * @param ticker is the ticker of the stock that is being sold/removed.
   * @param amt    is the quantity that you are selling/removing.
   * @param date   is the date that you are selling on.
   */
  void processSellStock(User user, int index, String ticker, double amt, String date);

  /**
   * This method is called when the query value button is pressed. It calls the method from the
   * model to get/query the value of the current portfolio on a specified date.
   *
   * @param user  is the user that contains the current portfolio.
   * @param index is the index of the portfolio.
   * @param date  is the date that you are querying the value on.
   * @return a double that represents the value amount of the portfolio on the specified date.
   */
  double processGetValue(User user, int index, String date);

  /**
   * This method is called when the query composition button is pressed. It calls the method from
   * the model to get/query the composition of the current portfolio on a specified date.
   *
   * @param user  is the user that contains the current portfolio.
   * @param index is the index of the portfolio.
   * @param date  is the date that you are querying the composition on.
   * @return a List of Strings that represents the composition of the portfolio on a specified date.
   */
  List<String> processGetComposition(User user, int index, String date);

  /**
   * This method is called when a portfolio on the list is pressed. It calls the method from the
   * model to get the portfolio given an index.
   *
   * @param index is the index of the portfolio.
   * @param user  is the user that contains the current portfolio.
   * @return a Portfolio that represents the current portfolio.
   */
  Portfolio processEditPortfolio(int index, User user);

  /**
   * This method is called when the save portfolio button is pressed. It calls the method from
   * the model to save the portfolio as a txt file.
   *
   * @param index is the index of the portfolio.
   * @param user  is the user that contains the current portfolio.
   */
  void processSavePortfolio(int index, User user);

  /**
   * This method is called whenever the list on the menu panel needs to be updated. It calls
   * the method from the model to get the portfolio list of a user so that it updates the list.
   *
   * @param list is the list that is being updated on the GUI.
   * @param user is the user that contains all the portfolios.
   */
  void getPortfolioList(JList<String> list, User user);
}
