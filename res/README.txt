Completed and operational features:
1. Retrieving Data From API:
   - The program can successfully retrieve stock data from the Alpha Vantage API using the provided API key.
   - It can handle invalid ticker symbols by throwing an IllegalArgumentException.
   - Retrieved data is written to CSV files in the "res/csvFiles/" directory for future-proofing — it will not add to the directory if file already exists.

2. Stock Information:
   - The StockInfo class can read and parse the CSV files to extract closing prices for each date and stores them in an immutable map .

3. Stock Calculations/Methods:
Working methods:
     a. Total gain/loss between two dates.
     b. Moving average of stock prices over a x-number of days.
     c. Cross-over days given a range of dates and a x-day.
   - It can handle invalid dates using functions the LocalDate library; it also checks for dates outside the available data range.
   - The advanceBack method finds the most recent date with a closing price when given an valid date that is missing such as Saturday, Sunday, and holiday.

4. Portfolio Management:
   - Users can create and name multiple portfolios.
   - Portfolios allow buying and selling stocks; it also checks for exceptions such as buying less than one stock and selling a stock that the user does not own or user enters an greater than the owned stock quantity.
   - Users can check the total value of a portfolio on a given date and the date must be valid or it throws an error.
   - Portfolios can be printed, showing the ticker and quantity of each stock.

   Additional Features for Assignment 5:
   - Buying and selling stocks now require a date to buy/sell on.
   - You can now see the stock distribution of a portfolio(how much money you have invested in
   each stock)
   - You can now rebalance a portfolio's stocks with a custom weight for each stock.
   - You can now save your current portfolio so that it can be used when you run the program the
   next time. All you have to do is enter the name of the portfolio.

5. User Management:
   - The UserPortfolio class allows users to manage multiple portfolios:
     a. Add new portfolios.
     b. Remove portfolios by index.
     c. Print all portfolio names with their indices.
     d. Select a specific portfolio by index for further actions.

   Additional Features for Assignment 5:
      - Users can now load a portfolio which has to be saved in a previous run of the program. The
      file comes from the res/savedPortfolios folder.
6. View/GUI
    -  You can load portfolios res/savedPortfolios, make sure the txt file that you're loading has
    proper formatting.
    - You can also create new portfolios, and it would show up in the scroll pane.
    - After clicking into the portfolio, you can buy, sell, get value, and get composition of the
    portfolio. You have to click the button and then enter date in the new menu in order to perform
    the action.
    - There is a back button to return to the previous menu.
