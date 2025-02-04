package view;

import java.io.IOException;

import controller.StockControllerGUI;

/**
 * This interface is the interface for our GUI view. It has a method that sets the controller.
 */
public interface IViewGUI {
  /**
   * This method sets the controller for the view so that they can work hand in hand with the model.
   *
   * @param controller is the controller that the view is using.
   */
  void setController(StockControllerGUI controller);

  void processBuyStock();

  void processSellStock();

  void processGetValue();

  void processGetComposition();

  void processSavePortfolio() throws IOException;


}
