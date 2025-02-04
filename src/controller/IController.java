package controller;

import java.io.IOException;

/**
 * The IController interface is the interface for the controller that contains the method to run
 * the program.
 */
public interface IController {

  /**
   * The control method is the start of the program. This method makes the program run until the
   * user inputs "q" in the main menu to stop the program.
   *
   * @throws IOException is thrown when an input fails or is interrupted.
   */
  void control() throws IOException;
}
