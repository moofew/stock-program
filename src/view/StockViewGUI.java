package view;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;


import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import controller.StockControllerGUI;
import model.portfolio.Portfolio;
import model.user.User;

/**
 * This class implements the IView interface which helps the controller process inputs. This
 * class implements methods to get the dates for stocks.
 */
public class StockViewGUI extends JFrame implements IViewGUI {
  private int index;
  private final User user;
  private StockControllerGUI controller;
  private final CardLayout cardLayout;
  private final JPanel allPanels;
  private JList<String> list;
  private JLabel loadedPort;
  private JLabel currentPortfolioLabel;

  /**
   * This constructor creates a StockViewGUI object, which sets up the GUI for the stock program.
   */
  public StockViewGUI(User user) {
    this.user = user;
    setTitle("Stock Program");
    setSize(720, 480);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    cardLayout = new CardLayout();
    allPanels = new JPanel(cardLayout);
    welcomePanel();

    cardLayout.show(allPanels, "All Panels");
    add(allPanels);
  }

  @Override
  public void setController(StockControllerGUI controller) {
    this.controller = controller;
  }

  private void welcomePanel() {
    JPanel welcomePanel = new JPanel(new BorderLayout());
    JLabel welcomeLabel =
            new JLabel("Welcome to our stock program! Click anywhere to continue.",
                    SwingConstants.CENTER);
    welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
    welcomePanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        menuPanel();
        cardLayout.show(allPanels, "menu");
      }
    });
    allPanels.add(welcomePanel, "welcome");
  }

  private void menuPanel() {
    JPanel menuPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    JButton create = new JButton("Create Portfolio");
    JButton load = new JButton("Load Portfolio");
    JLabel portfoliosLabel = new JLabel("Portfolios:");
    list = new JList<>();
    list.setVisibleRowCount(10);
    list.setFixedCellHeight(20);
    list.setFixedCellWidth(150);

    JScrollPane scrollPane = new JScrollPane(list);
    scrollPane.setPreferredSize(new Dimension(170, 200));

    loadedPort = new JLabel("Loaded Portfolio");

    JButton quit = new JButton("Quit");

    Dimension buttonSize = new Dimension(200, 50);
    create.setPreferredSize(buttonSize);
    load.setPreferredSize(buttonSize);
    quit.setPreferredSize(buttonSize);

    Font font = new Font("Times", Font.PLAIN, 15);
    create.setFont(font);
    load.setFont(font);
    quit.setFont(font);
    portfoliosLabel.setFont(font);
    list.setFont(font);
    loadedPort.setFont(font);

    gbc.gridx = 0;
    gbc.gridy = 0;
    menuPanel.add(create, gbc);

    gbc.gridx = 1;
    menuPanel.add(load, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    menuPanel.add(portfoliosLabel, gbc);

    gbc.gridy = 2;
    menuPanel.add(scrollPane, gbc);

    gbc.gridy = 3;
    menuPanel.add(loadedPort, gbc);

    gbc.gridy = 4;
    menuPanel.add(quit, gbc);

    loadedPort.setVisible(false);

    list.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          String selectedItem = list.getSelectedValue();
          if (selectedItem != null) {
            String indexString = String.valueOf(selectedItem.charAt(1));
            index = Integer.parseInt(indexString);
            Portfolio portfolio = controller.processEditPortfolio(index, user);
            updatePortfolioName(portfolio.getName());
            portfolioPanel(portfolio);
            cardLayout.show(allPanels, "portfolio");
            list.clearSelection();
          }
        }
      }
    });

    create.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        processCreatePortfolio();
        cardLayout.show(allPanels, "create");
      }
    });

    load.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          processLoadPortfolio();
          getPortfolioList();


        } catch (Exception ex) {
          throw new RuntimeException(ex);
        }
      }
    });

    quit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    allPanels.add(menuPanel, "menu");
  }

  private void processCreatePortfolio() {
    JPanel createPortfolioPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JButton create = new JButton("Create Portfolio");
    JButton quit = new JButton("Back To Main Menu");
    JLabel enterName = new JLabel("Enter Portfolio name: ");
    JLabel createdPort = new JLabel("Successfully Created Portfolio");
    JTextField textField = new JTextField(16);

    Dimension buttonSize = new Dimension(200, 50);
    create.setPreferredSize(buttonSize);
    quit.setPreferredSize(buttonSize);
    textField.setPreferredSize(new Dimension(200, 30));

    Font largeFont = new Font("Times", Font.PLAIN, 15);
    create.setFont(largeFont);
    quit.setFont(largeFont);
    enterName.setFont(largeFont);
    createdPort.setFont(largeFont);
    textField.setFont(largeFont);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    createPortfolioPanel.add(enterName, gbc);

    gbc.gridy = 1;
    createPortfolioPanel.add(textField, gbc);

    gbc.gridy = 2;
    createPortfolioPanel.add(create, gbc);

    gbc.gridy = 3;
    createPortfolioPanel.add(createdPort, gbc);

    gbc.gridy = 4;
    createPortfolioPanel.add(quit, gbc);

    createdPort.setVisible(false);

    create.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String portfolioName = textField.getText();
        try {
          controller.processCreatePortfolio(portfolioName, user);
          createdPort.setText("Successfully created " + "\"" + portfolioName + "\"" + "!");
          createdPort.setVisible(true);
          getPortfolioList();

          Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
              createdPort.setVisible(false);
            }
          });
          timer.setRepeats(false);
          timer.start();
        } catch (IllegalArgumentException ex) {
          createdPort.setText("Error: " + ex.getMessage());
          createdPort.setVisible(true);
        }
      }
    });

    quit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(allPanels, "menu");
      }
    });

    allPanels.add(createPortfolioPanel, "create");
  }

  private void processLoadPortfolio() throws IOException {
    JFileChooser fileChooser = new JFileChooser();
    File file = new File("res/savedPortfolios");
    fileChooser.setCurrentDirectory(file);
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "txt", "txt");
    fileChooser.setFileFilter(filter);
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String fileName = selectedFile.getName();
      fileName = fileName.substring(0, fileName.length() - 4);
      try {
        controller.processLoadPortfolio(fileName, user);
        loadedPort.setText("Successfully Loaded " + "\"" + fileName + "\"" + "!");
        loadedPort.setVisible(true);
      } catch (IllegalArgumentException ex) {
        loadedPort.setText("Error: " + ex.getMessage());
      }
      loadedPort.setVisible(true);
      Timer timer = new Timer(3000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
          loadedPort.setVisible(false);
        }
      });
      timer.setRepeats(false);
      timer.start();
    }
  }

  private void getPortfolioList() {
    controller.getPortfolioList(list, user);
  }

  private void portfolioPanel(Portfolio portfolio) {
    updatePortfolioName(portfolio.getName());
    JPanel portfolioPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel label = new JLabel("Successfully saved portfolio!");
    JButton buy = new JButton("Buy Stock");
    JButton sell = new JButton("Sell Stock");
    JButton value = new JButton("Query Value");
    JButton composition = new JButton("Query Composition");
    JButton save = new JButton("Save Portfolio");
    JButton quit = new JButton("Back To Main Menu");

    Dimension buttonSize = new Dimension(200, 50);
    buy.setPreferredSize(buttonSize);
    sell.setPreferredSize(buttonSize);
    value.setPreferredSize(buttonSize);
    composition.setPreferredSize(buttonSize);
    save.setPreferredSize(buttonSize);
    quit.setPreferredSize(buttonSize);

    Font buttonFont = new Font("Times", Font.PLAIN, 15);
    buy.setFont(buttonFont);
    sell.setFont(buttonFont);
    value.setFont(buttonFont);
    composition.setFont(buttonFont);
    save.setFont(buttonFont);
    quit.setFont(buttonFont);

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    portfolioPanel.add(currentPortfolioLabel, gbc);

    gbc.gridwidth = 1;
    gbc.gridy = 1;
    portfolioPanel.add(buy, gbc);

    gbc.gridx = 1;
    portfolioPanel.add(sell, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    portfolioPanel.add(value, gbc);

    gbc.gridx = 1;
    portfolioPanel.add(composition, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    portfolioPanel.add(save, gbc);

    gbc.gridy = 4;
    portfolioPanel.add(quit, gbc);

    gbc.gridy = 5;
    portfolioPanel.add(label, gbc);
    label.setVisible(false);


    buy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        processBuyStock();
        cardLayout.show(allPanels, "buy");
      }
    });

    sell.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        processSellStock();
        cardLayout.show(allPanels, "sell");
      }
    });

    value.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        processGetValue();
        cardLayout.show(allPanels, "value");
      }
    });

    composition.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        processGetComposition();
        cardLayout.show(allPanels, "composition");
      }
    });

    save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          processSavePortfolio();
          label.setVisible(true);
          Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
              label.setVisible(false);
            }
          });
          timer.setRepeats(false);
          timer.start();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        cardLayout.show(allPanels, "save");
      }
    });

    quit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(allPanels, "menu");
      }
    });

    allPanels.add(portfolioPanel, "portfolio");
  }

  @Override
  public void processBuyStock() {
    JPanel buyStockPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    JButton buy = new JButton("Buy Stock");
    JButton quit = new JButton("Back To Menu");
    JLabel enterTicker = new JLabel("Enter Stock Ticker: ");
    JTextField textField = new JTextField(16);
    JLabel enterQuantity = new JLabel("Enter Quantity: ");
    JTextField quantityField = new JTextField(16);
    JLabel enterDate = new JLabel("Enter Date: ");

    SpinnerDateModel dateModel = new SpinnerDateModel();
    JSpinner dateSpinner = new JSpinner(dateModel);
    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
    dateSpinner.setEditor(dateEditor);

    JLabel boughtStock = new JLabel("Successfully Bought Stock");

    Dimension buttonSize = new Dimension(200, 50);
    buy.setPreferredSize(buttonSize);
    quit.setPreferredSize(buttonSize);
    buy.setOpaque(true);
    buy.setForeground(new Color(0, 153, 0));

    Font font = new Font("Times", Font.PLAIN, 15);
    Font font2 = new Font("Times", Font.ITALIC, 15);
    buy.setFont(font);
    quit.setFont(font);
    enterTicker.setFont(font);
    textField.setFont(font);
    enterQuantity.setFont(font);
    quantityField.setFont(font);
    enterDate.setFont(font);
    dateSpinner.setFont(font);
    dateEditor.getComponent(0).setFont(font2);


    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    buyStockPanel.add(currentPortfolioLabel, gbc);


    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    buyStockPanel.add(enterTicker, gbc);
    gbc.gridx = 1;
    buyStockPanel.add(textField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    buyStockPanel.add(enterQuantity, gbc);
    gbc.gridx = 1;
    buyStockPanel.add(quantityField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    buyStockPanel.add(enterDate, gbc);
    gbc.gridx = 1;
    buyStockPanel.add(dateSpinner, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;
    buyStockPanel.add(buy, gbc);

    gbc.gridy = 5;
    buyStockPanel.add(quit, gbc);

    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.gridwidth = 2;
    boughtStock.setVisible(false);
    buyStockPanel.add(boughtStock, gbc);

    buy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int quantity = 0;
        String ticker = textField.getText().toUpperCase();

        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateSpinner.getValue());
        try {
          try {
            quantity = Integer.parseInt(quantityField.getText());
          } catch (NumberFormatException ex) {
            boughtStock.setText("Error: Invalid quantity input. ");
          }
          if (quantity == 0) {
            boughtStock.setText("Error: Invalid quantity input. ");
          } else {
            controller.processBuyStock(user, index, ticker, quantity, date);
            boughtStock.setText("Successfully bought " + quantity + " shares of " + ticker + "!");
            getPortfolioList();
          }
        } catch (Exception ex) {
          boughtStock.setText("Error: " + ex.getMessage());
        }

        boughtStock.setVisible(true);
        Timer timer = new Timer(3000, new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
            boughtStock.setVisible(false);
          }
        });
        timer.setRepeats(false);
        timer.start();
      }
    });

    quit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(allPanels, "portfolio");
      }
    });

    allPanels.add(buyStockPanel, "buy");
  }

  @Override
  public void processSellStock() {
    JPanel sellStockPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    JButton sell = new JButton("Sell Stock");
    JButton quit = new JButton("Back To Menu");
    JLabel enterTicker = new JLabel("Enter Stock Ticker: ");
    JTextField textField = new JTextField(16);
    JLabel enterQuantity = new JLabel("Enter Quantity: ");
    JTextField quantityField = new JTextField(16);
    JLabel enterDate = new JLabel("Enter Date: ");

    SpinnerDateModel dateModel = new SpinnerDateModel();
    JSpinner dateSpinner = new JSpinner(dateModel);
    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
    dateSpinner.setEditor(dateEditor);

    JLabel soldStock = new JLabel("Successfully Sold Stock");

    Dimension buttonSize = new Dimension(200, 50);
    sell.setPreferredSize(buttonSize);
    quit.setPreferredSize(buttonSize);
    sell.setOpaque(true);
    sell.setForeground(new Color(255, 0, 0));

    Font font = new Font("Times", Font.PLAIN, 15);
    Font font2 = new Font("Times", Font.ITALIC, 15);
    sell.setFont(font);
    quit.setFont(font);
    enterTicker.setFont(font);
    textField.setFont(font);
    enterQuantity.setFont(font);
    quantityField.setFont(font);
    enterDate.setFont(font);
    dateSpinner.setFont(font);
    dateEditor.getComponent(0).setFont(font2);


    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    sellStockPanel.add(currentPortfolioLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    sellStockPanel.add(enterTicker, gbc);
    gbc.gridx = 1;
    sellStockPanel.add(textField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    sellStockPanel.add(enterQuantity, gbc);
    gbc.gridx = 1;
    sellStockPanel.add(quantityField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    sellStockPanel.add(enterDate, gbc);
    gbc.gridx = 1;
    sellStockPanel.add(dateSpinner, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;
    sellStockPanel.add(sell, gbc);

    gbc.gridy = 5;
    sellStockPanel.add(quit, gbc);

    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 2;
    soldStock.setVisible(false);
    sellStockPanel.add(soldStock, gbc);

    sell.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int quantity = 0;
        String ticker = textField.getText().toUpperCase();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateSpinner.getValue());
        try {
          try {
            quantity = Integer.parseInt(quantityField.getText());
          } catch (NumberFormatException ex) {
            soldStock.setText("Error: Invalid quantity input. ");
          }
          if (quantity == 0) {
            soldStock.setText("Error: Invalid quantity input. ");
          } else {
            controller.processSellStock(user, index, ticker, quantity, date);
            soldStock.setText("Successfully sold " + quantity + " shares of " + ticker + "!");
            getPortfolioList();
          }
        } catch (Exception ex) {
          soldStock.setText("Error: " + ex.getMessage());
        }

        soldStock.setVisible(true);
        Timer timer = new Timer(3000, new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
            soldStock.setVisible(false);
          }
        });
        timer.setRepeats(false);

        timer.start();
      }
    });

    quit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(allPanels, "portfolio");
      }
    });

    allPanels.add(sellStockPanel, "sell");
  }

  @Override
  public void processGetValue() {
    JPanel getValuePanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    JButton value = new JButton("Get Value");
    JButton quit = new JButton("Back To Menu");
    JLabel enterDate = new JLabel("Enter Date: ");

    SpinnerDateModel dateModel = new SpinnerDateModel();
    JSpinner dateSpinner = new JSpinner(dateModel);
    JSpinner.DateEditor dateEditor =
            new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
    dateSpinner.setEditor(dateEditor);

    Dimension buttonSize = new Dimension(200, 50);
    enterDate.setPreferredSize(buttonSize);
    dateSpinner.setPreferredSize(buttonSize);
    value.setPreferredSize(buttonSize);
    quit.setPreferredSize(buttonSize);

    Font font = new Font("Times", Font.PLAIN, 15);
    Font font2 = new Font("Times", Font.ITALIC, 15);
    Font font3 = new Font("Times", Font.BOLD, 15);
    enterDate.setFont(font);
    dateSpinner.setFont(font);
    dateEditor.getComponent(0).setFont(font2);
    value.setFont(font);
    quit.setFont(font);


    JLabel gotValue = new JLabel("value");

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    getValuePanel.add(currentPortfolioLabel, gbc);


    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    getValuePanel.add(enterDate, gbc);

    gbc.gridx = 1;
    getValuePanel.add(dateSpinner, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    getValuePanel.add(value, gbc);

    gbc.gridy = 3;
    getValuePanel.add(gotValue, gbc);
    gotValue.setVisible(false);

    gbc.gridy = 4;
    gbc.anchor = GridBagConstraints.CENTER;
    getValuePanel.add(quit, gbc);

    value.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateSpinner.getValue());
        try {
          double value = controller.processGetValue(user, index, date);
          gotValue.setText("Your portfolio is worth $" + value);
          gotValue.setFont(font3);
          gotValue.setVisible(true);
        } catch (IllegalArgumentException ex) {
          gotValue.setText("Error: " + ex.getMessage());
          gotValue.setFont(font3);
          gotValue.setVisible(true);
          Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
              gotValue.setVisible(false);
            }
          });
          timer.setRepeats(false);
          timer.start();
        }
      }
    });

    quit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(allPanels, "portfolio");
      }
    });

    allPanels.add(getValuePanel, "value");
  }

  @Override
  public void processGetComposition() {
    JPanel getCompositionPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JButton comp = new JButton("Get Composition");
    JButton quit = new JButton("Back To Menu");
    JLabel enterDate = new JLabel("Enter Date: ");

    SpinnerDateModel dateModel = new SpinnerDateModel();
    JSpinner dateSpinner = new JSpinner(dateModel);
    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
    dateSpinner.setEditor(dateEditor);

    Dimension buttonSize = new Dimension(200, 50);
    enterDate.setPreferredSize(buttonSize);
    dateSpinner.setPreferredSize(buttonSize);
    comp.setPreferredSize(buttonSize);
    quit.setPreferredSize(buttonSize);

    Font font = new Font("Times", Font.PLAIN, 15);
    Font font2 = new Font("Times", Font.ITALIC, 15);
    enterDate.setFont(font);
    dateSpinner.setFont(font);
    dateEditor.getComponent(0).setFont(font2);
    comp.setFont(font);
    quit.setFont(font);

    JLabel gotComp = new JLabel("comp");
    gotComp.setFont(font);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    getCompositionPanel.add(currentPortfolioLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    getCompositionPanel.add(enterDate, gbc);

    gbc.gridx = 1;
    getCompositionPanel.add(dateSpinner, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    getCompositionPanel.add(comp, gbc);

    gbc.gridy = 3;
    getCompositionPanel.add(gotComp, gbc);
    gotComp.setVisible(false);

    gbc.gridy = 4;
    getCompositionPanel.add(quit, gbc);

    comp.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateSpinner.getValue());
        try {
          List<String> composition = controller.processGetComposition(user, index, date);
          gotComp.setText("Your portfolio consists of: " + composition.toString());
          gotComp.setVisible(true);
          getPortfolioList();
        } catch (IllegalArgumentException ex) {
          gotComp.setText("Error: " + ex.getMessage());
          gotComp.setVisible(true);
          Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
              gotComp.setVisible(false);
            }
          });
          timer.setRepeats(false);
          timer.start();
        }
      }
    });

    quit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(allPanels, "portfolio");
      }
    });

    allPanels.add(getCompositionPanel, "composition");
  }

  @Override
  public void processSavePortfolio() throws IOException {
    controller.processSavePortfolio(index, user);
  }

  private void updatePortfolioName(String name) {
    if (currentPortfolioLabel == null) {
      currentPortfolioLabel = new JLabel();
      currentPortfolioLabel.setFont(new Font("Times", Font.BOLD, 16));
    }
    currentPortfolioLabel.setText("Current Portfolio: " + name);
  }
}
