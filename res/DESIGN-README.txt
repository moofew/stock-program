Design README
This program is designed with the Model-View-Controller architecture and uses an interactive
text-based design to interact with users.

Design choices:

The program uses an interface, Stock.java, to define the properties of an individual stock – it
currently contains the method headers for the stock ticker and the closing price.
In the implementation, StockData.java, the program uses a helper method to retrieve the data from
the CSV file; namely, the date and the closing price associated with the date. The helper method
also writes in the date and closing price from the CSV file and then. The data stored in a
Map<String, Double> in order to store the data since we only need the closing price.

Stock Calculations:
For the calculation portion of the program, it uses a different interface, StockMethods.java, to
define all the methods (gain/loss, moving average, crossover dates).
In the implementation, StockMethodsImpl.java, the program handles invalid dates by using a helper
that incorporates the functionality of the LocalDate object and another helper that checks if the
date entered is before the earliest entry in the map. The program also handles valid dates that do
not have data entries such as weekends and holidays – it will advance backwards until a valid date
is retrieved. However, when calculating a cross-over day and the moving average, the program skips
the weekends and only accounts for weekdays into those calculations. For the moving-day average,
if the x-days is greater than the amount of entries we have before the certain date, it would
calculate the average base off the number of days it had read before reaching the end. This hasn't
changed much from the previous verison, we just renamed StockCalculator to StockMethodsImpl and we
added it as a field in the user class.

Stock Portfolios:
For the portfolio aspect of this program, it uses the interface, Portfolio.java, to contain the
methods for managing a stock portfolio (buy, sell, check value, view stocks). The program uses a
Map<StockInfo, Integer> to store individual stocks and their amount owned.
In the implementation, the program also accounted for invalid dates when checking the value of a
portfolio. The method also advances backwards like the stock calculator if the date is valid, but
does not have an entry. Ex: checking the value of the portfolio on a Saturday will calculate the
value using the closing price from Friday. The methods also account for invalid inputs such as
negative amounts of stocks or trying to sell more than what the user owns.

New Changes: In order to get a history of transaction we use a Map<LocalDate, <String, Double>>.
This essentially stores portfolios on a certain date. To retrieve a portfolio on a certain date, it
 uses a helper method to grab the most recent portfolio. Buying/selling on a past date will update
 future portfolios.

 Newer changes:
 We removed API method to retrieve the stock data from the model into a new class so it is not
 as dependent to the alphavantage API.


The program has a User.java interface and its implementation, UserData.java that contains a list
with the type PortfolioInfo. This allows the program to have multiple portfolios under an account.
In the implementation it allows users to create, delete, and manage the portfolios.

In addition to the previous assignment, we created a new interface and implementation class,
Chart.java(the interface), and PortfolioChart.java(the implementation). These are in new classes
separate from the original portfolio classes because there were an abundant amount of helper
methods, so it would be more readable and understandable in new classes.

Controller and View:
For our controller, we have three classes. IController(an interface),
StockController(implements IController), and StockProgram(the main method).

The IController interface:
Contains the control() method, which is used in the StockController class.

The StockController class
Implements the IController interface and processes inputs with a Readable object, which is declared
as Readable readable. The outputs are processed with an Appendable object, declared as Appendable
appendable. The class contains many methods to handle all scenarios resulting from user input. These
methods include but are not limited to: processing main menu inputs, processing stock information
menu inputs, processing portfolio menu inputs, and their respective features within each menu. The
main methods used in this class are control(), processMenu(), processStockInfo(), and
processPortfolioInput(). Most of the other classes are methods to call methods from the model. The
control() method takes the Readable object and calls it in a Scanner, so that when processMenu() is
called, it can be called over and over again until the user quits or the program is terminated.

View Design:
Using a JScrollPane and a list, any loaded or created portfolios would be added to the pane. Most of
 the GUI was created using the GridBagLayout and centered in the middle. We tried our best to make
 it look similar to what an actual all-in-one stock ticket trader would look like in a broker. We
 used buttons and it directs you to a new menu depending on the action you want to perform.


