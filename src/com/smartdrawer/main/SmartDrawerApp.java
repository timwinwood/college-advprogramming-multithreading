package com.smartdrawer.main;

/**
 * SmartDrawerApp - a class to perform statistical analysis of Drawer data
 *
 * @author Tim Winwood - x20213638
 * @version 1.0
 */
public class SmartDrawerApp {

	/**
	 * entry point to the SmartDrawer app, handles user input from the main app menu
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {

		SmartDrawerOperations smartDrawerOperations = new SmartDrawerOperations();

		while (true) {

			int intSelection = smartDrawerOperations.displayAppMenu();

			switch (intSelection) {
			case 0:
				smartDrawerOperations.operationGenerateDrawerData();
				break;
			case 1:
				smartDrawerOperations.operationDrawerStats();
				break;
			case 2:
				smartDrawerOperations.operationCombinedDrawerStats();
				break;
			case 3:
				smartDrawerOperations.exitApp();
				break;
			default:
				smartDrawerOperations.invalidOperation();
			}
		}
	}

}
