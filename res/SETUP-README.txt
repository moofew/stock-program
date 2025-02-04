Setup README

To run the program from the JAR file:
In order to run assignment-5.jar, the file must be in any directory that also contains a
folder named “res”.
The res folder must contain a “csvFiles” folder in order for the program to read the information
for a stock.

To run the program from the JAR file:
In order to run assignment-5.jar, the file must be in any directory that also contains a
folder named “res”.
The res folder must contain a “csvFiles” folder in order for the program to read the information
for a stock.

To run the program from a terminal:
Go to the directory that contains the jar file which should be where this folder is located:
res/assignment-5.jar. Then, you can either do "java -jar assignment-5.jar" for the GUI version or
"java -jar assignment-5.jar -text" for the text interface version.

How to use the GUI:
Click anywhere in the program to get out of the start page, then either create or load a portfolio.
Once you do that, it will show up in the list, and you just have to click on one of the items
in the list to enter the portfolio. For example, you press create portfolio and create a portfolio
named test. Then, you go back to the menu and click test in the list to access the portfolio. Then,
you can press any of the buttons and there will be instructions for each feature. 


To create a portfolio with 3 different stocks on different dates:
1) Start the program and press 2, which will bring you to the Stock Portfolio feature of the
program.
2) From there, you must create a portfolio by pressing 1(you can name it anything).
3) After you have created a portfolio, you press 3 to edit a portfolio, and select the indices of
the
portfolio you would like to edit(in this case it is probably 0).
4) Now, to buy a stock, press 1, which is the buy stock feature. The program will prompt the user to
enter a ticker symbol, a quantity, and a date. For example, if you were to buy 5 shares of AAPL,
the input from the user would be “AAPL” or “aapl” -> enter key -> 5 -> enter key -> date. The
program will  now tell you tht you successfully bought 5 shares of AAPL on that date.
5) To buy the other two stocks, just repeat step 4.

To query the value of that portfolio on two specific dates:
1) Since you already have a portfolio with 3 stocks, you can query the value of the portfolio by
pressing 3, which is view portfolio value.
2) After pressing 3, it will prompt you to enter the date.
3) Repeat step 2 with a different valid date.

Our supported offline stocks are: AAPL, AMZN, GOOG, JPM, META, MSFT, NKE, NVDA, SPY, and TSLA.
