package view;

/**
 * This is the interface for the view part of our program. It contains a method to write messages
 * to the terminal.
 */
public interface IView {
  /**
   * This method is used to write a message to either the terminal or GUI. It takes in a String
   * that represents the message.
   *
   * @param message is the String to be written to the terminal.
   */
  void writeMessage(String message);
}
