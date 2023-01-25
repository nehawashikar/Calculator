package exercise1;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * This java class creates a Calculator.
 * 
 * @author Neha Washikar
 * @version 1.0
 * @since 2022-11-28
 *
 */
public class Calculator extends JFrame {
	private JTextField outputField;
	private ArrayList<JButton> digits;
	private ArrayList<Double> numbers;
	private JButton plusSign;
	private JButton minusSign;
	private JButton multSign;
	private JButton divSign;
	private JButton eqSign;
	private String currentOperation;
	private Double firstOperand;
	
	/**
	 * Constructor for Calculator class
	 */
	public Calculator() {
		
		this.currentOperation = "";
		this.firstOperand = 0.0;
		
		//create display panel
		JPanel displayPanel = new JPanel(new FlowLayout());	
		displayPanel.setBackground(Color.WHITE);
		
		//adding a menu
		JMenuBar menuBar = new JMenuBar();
		
		JMenu settingMenu = new JMenu("Mode");
		menuBar.add(settingMenu);
		
		JMenuItem lightMode = new JMenuItem("Light Mode");
		JMenuItem darkMode = new JMenuItem("Dark Mode");
		settingMenu.add(lightMode);
		settingMenu.add(darkMode);

		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		JMenuItem addMenuItem = new JMenuItem("Add");
		JMenuItem subtractMenuItem = new JMenuItem("Subtract");
		JMenuItem divideMenuItem = new JMenuItem("Divide");
		JMenuItem multiplyMenuItem = new JMenuItem("Multiply");
		JMenuItem clearMenuItem = new JMenuItem("Clear");
		menu.add(addMenuItem);
		menu.add(subtractMenuItem);
		menu.add(divideMenuItem);
		menu.add(multiplyMenuItem);
		menu.add(clearMenuItem);
		
		// display on the screen
		this.setJMenuBar(menuBar);
		
		
		//add output field
		outputField = new JTextField("0", 20);
		displayPanel.add(outputField);
		
		
		//create button panel 
		JPanel buttonPanel = new JPanel((new GridLayout(1, 2)));
		
		//create digit button panel
		JPanel digitButtonPanel = new JPanel(new GridLayout(4, 3));
		digitButtonPanel.setBackground(Color.WHITE);
		
		digits = new ArrayList<JButton>();
		digits.add(new JButton("0"));
		digits.add(new JButton("1"));
		digits.add(new JButton("2"));
		digits.add(new JButton("3"));
		digits.add(new JButton("4"));
		digits.add(new JButton("5"));
		digits.add(new JButton("6"));
		digits.add(new JButton("7"));
		digits.add(new JButton("8"));
		digits.add(new JButton("9"));
		digits.add(new JButton("."));
		digits.add(new JButton("AC"));
		
		digits.get(10).setForeground(Color.MAGENTA);
		digits.get(11).setForeground(Color.MAGENTA);

		
		for(JButton button : digits) {
			digitButtonPanel.add(button);

		}
				
		//create operator button panel
		JPanel operatorButtonPanel = new JPanel(new GridLayout(5, 1));
		operatorButtonPanel.setBackground(Color.WHITE);
		
		
		
		plusSign = new JButton(new ImageIcon(
	                new ImageIcon("plusSign.png").getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        minusSign = new JButton(new ImageIcon(
                new ImageIcon("minusSign.png").getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        multSign = new JButton(new ImageIcon(
                new ImageIcon("multSign.png").getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        divSign = new JButton(new ImageIcon(
                new ImageIcon("divSign.png").getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        eqSign = new JButton(new ImageIcon(
                new ImageIcon("equalSign.png").getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
	
		operatorButtonPanel.add(plusSign);
		operatorButtonPanel.add(minusSign);
		operatorButtonPanel.add(multSign);
		operatorButtonPanel.add(divSign);
		operatorButtonPanel.add(eqSign);
		
		// add digit and operator button panel to the button panel
		buttonPanel.add(digitButtonPanel);
		buttonPanel.add(operatorButtonPanel);
		
		//add button panel to display panel
		displayPanel.add(buttonPanel);
		//add display panel to our content panel
		setContentPane(displayPanel);
		
		//reset values for the AC button
		digits.get(11).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				resetValues();
			}
		} );
		
		//add . for the . button
		digits.get(10).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				String currentText = outputField.getText();
				if(currentText.indexOf(".") < 0 ) {
					outputField.setText(currentText + ".");
				}
			}
		} );
		
		OperatorListener opListener = new OperatorListener();
		plusSign.addActionListener(opListener);
		minusSign.addActionListener(opListener);
		multSign.addActionListener(opListener);
		divSign.addActionListener(opListener);
		
		for(int i = 0; i <= 9; i++) {
			digits.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					String currentText = outputField.getText();
					JButton source = (JButton) event.getSource();
					String newDigit = "";
					if(source == digits.get(0)) {
						newDigit = "0";
					}
					else if(source == digits.get(1)) {
						newDigit = "1";
					}
					else if(source == digits.get(2)) {
						newDigit = "2";
					}
					else if(source == digits.get(3)) {
						newDigit = "3";
					}
					else if(source == digits.get(4)) {
						newDigit = "4";
					}
					else if(source == digits.get(5)) {
						newDigit = "5";
					}
					else if(source == digits.get(6)) {
						newDigit = "6";
					}
					else if(source == digits.get(7)) {
						newDigit = "7";
					}
					else if(source == digits.get(8)) {
						newDigit = "8";
					}
					else if(source == digits.get(9)) {
						newDigit = "9";
					}
					
					currentText = currentText + newDigit;
					currentText = currentText.replaceFirst("^0+(?!$)", "");
					outputField.setText(currentText);
				}
			});
		}
			
		eqSign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Double result = 0.0;
				String currentText = outputField.getText();
				
				try {
					Double secondOperand = new Double(currentText);
					
					if(currentOperation == "+") {
						result = firstOperand + secondOperand;
					}
					else if(currentOperation == "-") {
						result = firstOperand - secondOperand;
					}
					else if(currentOperation == "*") {
						result = firstOperand * secondOperand;
					}
					else if(currentOperation == "/") {
						if(secondOperand != 0.0) {
							result = firstOperand / secondOperand;
						}
						else {
							resetValues();
							outputField.setBackground(Color.PINK);
						}
					}
					
					outputField.setText(result.toString());
					firstOperand = result;
				}
				catch(NumberFormatException e) {
					resetValues();
				}
			}
		});
			
		ActionListener menuAL = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JMenuItem source = (JMenuItem) event.getSource();
				if(source == addMenuItem) {
					currentOperation = "+";
				}
				else if (source == subtractMenuItem) {
					currentOperation = "-";
				}
				else if (source == multiplyMenuItem) {
					currentOperation = "*";
				}
				else if (source == divideMenuItem) {
					currentOperation = "/";
				}
				else if (source == clearMenuItem) {
					resetValues();
				}
				else if(source == lightMode) {
					digitButtonPanel.setBackground(Color.WHITE);
					displayPanel.setBackground(Color.WHITE);
					operatorButtonPanel.setBackground(Color.WHITE);
				}
				else if(source == darkMode) {
					digitButtonPanel.setBackground(Color.BLACK);
					displayPanel.setBackground(Color.BLACK);
					operatorButtonPanel.setBackground(Color.BLACK);
				}
				
				String currentText = outputField.getText();
				try {
					Double currentTextDouble = new Double(currentText);
					firstOperand = currentTextDouble;
					
					outputField.setText("0");
				}
				catch(NumberFormatException e) {
					resetValues();
				}
			}
		};
		
		addMenuItem.addActionListener(menuAL);
		subtractMenuItem.addActionListener(menuAL);
		multiplyMenuItem.addActionListener(menuAL);
		divideMenuItem.addActionListener(menuAL);
		clearMenuItem.addActionListener(menuAL);
		lightMode.addActionListener(menuAL);
		darkMode.addActionListener(menuAL);

		
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
		
		setTitle("Calculator");
		setSize(500, 250);
		setLocation(10, 200);
	}
	
	/**
	 * Reset the values
	 */
	private void resetValues() {
		currentOperation = "";
		firstOperand = 0.0;
		outputField.setText("0");
		outputField.setBackground(Color.WHITE);
	}
	
	
	/**
	 * Operator Listener for the operations
	 * @author nehawashikar
	 *
	 */
	private class OperatorListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JButton source = (JButton) event.getSource();
			if(source == plusSign) {
				currentOperation = "+";
			}
			else if (source == minusSign) {
				currentOperation = "-";
			}
			else if (source == multSign) {
				currentOperation = "*";
			}
			else if (source == divSign) {
				currentOperation = "/";
			}
			
			String currentText = outputField.getText();
			try {
				Double currentTextDouble = new Double(currentText);
				firstOperand = currentTextDouble;
				outputField.setText("0");
			}
			catch(NumberFormatException e) {
				resetValues();
			}
		}
	}
	
	
	/**
	 * Main Method for testing
	 */
	public static void main(String[] args) {
		JFrame calc = new Calculator();
		calc.show();
	}
}

