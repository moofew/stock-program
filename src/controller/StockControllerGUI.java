package controller;

import model.portfolio.Portfolio;
import model.portfolio.PortfolioData;
import model.user.User;
import view.StockViewGUI;

import java.io.IOException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * Controller for the stock program GUI. This class handles user interactions and calls methods
 * from other classes accordingly.
 */
public class StockControllerGUI extends StockViewGUI implements IControllerGUI {
  private final StockViewGUI view;

  /**
   * This constructor initializes a StockControllerGUI object which sets the view of the
   * controller to a StockViewGUI.
   *
   * @param view is the StockViewGUI.
   */
  public StockControllerGUI(StockViewGUI view, User user) {
    super(user);
    this.view = view;
  }

  @Override
  public void control() throws IOException {
    view.setVisible(true);
  }

  @Override
  public void processCreatePortfolio(String portfolioName, User user) {
    user.addPortfolio(new PortfolioData(portfolioName));
  }

  @Override
  public void processLoadPortfolio(String portfolioName, User user) throws IOException {
    user.loadUserData(portfolioName);
  }

  @Override
  public void processBuyStock(User user, int index, String ticker, double amt, String date) {
    Portfolio port = user.selectPortfolio(index);
    port.buyStock(ticker, amt, date);
  }

  @Override
  public void processSellStock(User user, int index, String ticker, double amt, String date) {
    Portfolio port = user.selectPortfolio(index);
    port.sellStock(ticker, amt, date);
  }

  @Override
  public double processGetValue(User user, int index, String date) {
    Portfolio port = user.selectPortfolio(index);
    return port.checkPortfolioValue(date);
  }

  @Override
  public List<String> processGetComposition(User user, int index, String date) {
    Portfolio port = user.selectPortfolio(index);
    return port.printPortfolio(date);
  }

  @Override
  public Portfolio processEditPortfolio(int index, User user) {
    return user.selectPortfolio(index);
  }

  @Override
  public void processSavePortfolio(int index, User user) {
    Portfolio port = user.selectPortfolio(index);
    port.savePortfolioToFile();
  }

  @Override
  public void getPortfolioList(JList<String> list, User user) {
    List<String> portList = user.printAllPortfolios();
    DefaultListModel<String> listModel = new DefaultListModel<>();

    for (int i = 0; i < portList.size(); i++) {
      listModel.addElement(portList.get(i));
    }
    list.setModel(listModel);
  }
}
