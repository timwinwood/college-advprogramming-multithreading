package com.smartdrawer.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * SmartDrawerOperations - a class that performs the SmartDrawer app operations
 *
 * @author Tim Winwood - x20213638
 * @version 1.0
 */
public class SmartDrawerOperations {

	// constants
	private static final String APP_NAME = "SmartDrawer";
	private static final String[] APP_OPS = { "Generate Drawer Data", "Calculate & Display Drawer Statistics", "Calculate & Display Combined Drawer Statistics", "Exit App" };

	// instance variables
	private Scanner input;
	ArrayList[] drawers;
	SmartDrawer[] smartDrawers;
	Thread[] threads;

	/**
	 * SmartDrawerOperations constructor
	 */
	public SmartDrawerOperations() {

		// user input from scanner
		this.input = new Scanner(System.in);
		
		// init the arrays with size 0
		drawers = new ArrayList[0];
		smartDrawers = new SmartDrawer[0];
		threads = new Thread[0];
	}

	/**
	 * SmartDrawerOperation - Calculate & Display Combined Drawer Statistics
	 */
	public void operationCombinedDrawerStats() {
		
		// check if drawer data has been generated
		if (this.drawers.length == 0) {

			// no drawers found, warn user
			System.out.println("No Drawer Data found. Please run \"Generate Drawer Data\", or contact your app admin.");

		}else if ( this.smartDrawers.length == 0) {
			
			// no smartDrawers found, warn user
			System.out.println("No Drawer Statistics found. Please run \"Calculate & Display Drawer Statistics\", or contact your app admin.");
			
		}else {

			// -- Q2.B - Combine Computed Statistics --
			
			// local variables
			int sumCombined = 0;
			int averageCombined = 0;
			
			int maxCombined = 0;
			int maxCombinedTemp = 0;
			
			int minCombined = 0;
			int minCombinedTemp = 0;
			
			// loop through smartDrawers, calculate the combined statistics
			for(int i = 0; i < this.smartDrawers.length; i++) {
				
				// combine the sums
				sumCombined += smartDrawers[i].getSum();
				
				// if the ith max is more than the current maxCombined, set maxCombined to ith max
				maxCombinedTemp = smartDrawers[i].getMax();
				if(maxCombinedTemp > maxCombined) {
					maxCombined = maxCombinedTemp;
				}
				
				// if the ith min is less than the current minCombined, set minCombined to ith min
				minCombinedTemp = smartDrawers[i].getMin();
				if(minCombinedTemp < minCombined) {
					minCombined = minCombinedTemp;
				}

			}
			
			// calculate the combined average
			averageCombined = sumCombined/smartDrawers.length;
			
			// display the combined statistics
			System.out.println("-- Combined Drawer Statistics:");
			System.out.println("Combined Sum: " + sumCombined);
			System.out.println("Combined Average: " + averageCombined);
			System.out.println("Combined Maximum: " + maxCombined);
			System.out.println("Combined Minimum: " + minCombined);

		}

		// return user to app menu
		pressEnterReturnToMenu();
		
	}

	/**
	 * SmartDrawerOperation - Calculate & Display Drawer Statistics
	 */
	public void operationDrawerStats() {

		// check if drawer data has been generated
		if (this.drawers.length == 0) {

			// no drawers found, warn user
			System.out.println("No Drawer Data found. Please run \"Generate Drawer Data\", or contact your app admin.");

		} else {

			// -- Q2.A - Multithreaded Statistics --

			// ask the user if they would like to display the data of each drawer before and
			// after the quick sort
			String yesNoQuestion = "To calculate the max and min values, the " + APP_NAME + " app sorts drawer data using the Quick Sort algorithm.\n" + "Would you like to display the data of each drawer before and after the quick sort?";
			boolean shouldDisplay = displayYesNoSelection(yesNoQuestion);

			// initialise smartDrawers[] and threads[] with size of drawers[]
			this.smartDrawers = new SmartDrawer[this.drawers.length];
			this.threads = new Thread[this.drawers.length];

			// loop through the drawers and create a SmartDrawer, and Thread from each drawer
			for (int i = 0; i < this.drawers.length; i++) {

				// create SmartDrawer and add to smartDrawers[]
				this.smartDrawers[i] = new SmartDrawer(this.drawers[i]);

				// if shouldDisplay, print the data before quick sort
				if (shouldDisplay) {
					System.out.println("-- Drawer[" + i + "] Data before Quick Sort:");
					this.smartDrawers[i].printArrayList();
				}

				// create and start Thread, add to threads[]
				this.threads[i] = new Thread(smartDrawers[i]);
				this.threads[i].start();

			}

			// loop through threads, call Thread.join() on each so that the program waits until the threads terminate
			for (int i = 0; i < this.threads.length; i++) {

				try {

					// call Thread.join()
					this.threads[i].join();

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

			// if shouldDisplay, print the data after quick sort
			if (shouldDisplay) {
				for (int i = 0; i < this.smartDrawers.length; i++) {

					if (shouldDisplay) {
						System.out.println("-- Drawer[" + i + "] Data after Quick Sort:");
						this.smartDrawers[i].printArrayList();
					}

				}
			}

			// loop through smartDrawers, display the statistics for each
			for (int i = 0; i < this.smartDrawers.length; i++) {

				// get the ith SmatyDrawer
				SmartDrawer sd = this.smartDrawers[i];

				// display the statistics
				System.out.println("-- Drawer[" + i + "] Statistics:");
				System.out.println("Sum: " + sd.getSum());
				System.out.println("Average: " + sd.getAverage());
				System.out.println("Maximum: " + sd.getMax());
				System.out.println("Minimum: " + sd.getMin());

			}

		}
		
		// return user to app menu
		pressEnterReturnToMenu();

	}

	/**
	 * SmartDrawerOperation - Generate Drawer Data
	 */
	public void operationGenerateDrawerData() {

		// generate drawer data
		this.drawers = Drawer.generateData();
		System.out.println("Drawer Data Generated Succesfully.");

		// return the user to the app menu
		pressEnterReturnToMenu();
	}

	/**
	 * press enter key to return to app menu
	 */
	public void pressEnterReturnToMenu() {

		System.out.println("Press Enter key to return to " + APP_NAME + " App Menu...");

		try {

			// read the next key
			System.in.read();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/**
	 * @return the menu selection
	 */
	public int displayAppMenu() {

		// show the menu
		System.out.println("-- " + APP_NAME + " App Menu --");
		for (int i = 0; i < APP_OPS.length; i++) {
			System.out.println((i) + " - " + APP_OPS[i]);
		}
		System.out.println("Enter Your Selection:");

		// read the users selection, if out of bounds, call this method again
		// recursively
		int intSelection = input.nextInt();
		if (!(intSelection >= 0 && intSelection <= APP_OPS.length - 1)) {
			System.out.println("Invalid Selection. Selection must be between 0 and " + (APP_OPS.length - 1));
			return displayAppMenu();
		}

		// return the menu selection
		return intSelection;

	}

	/**
	 * exit the app
	 */
	public void exitApp() {

		System.exit(0);

	}

	/**
	 * shows an invalid operation message, returns the user to the app menu
	 */
	public void invalidOperation() {

		System.out.println("Invalid Selection. Please enter a selection between 0 and " + APP_OPS.length + ".");

		// return user to app menu
		pressEnterReturnToMenu();

	}

	/**
	 * @param the question
	 * @return the user selection (yes/no)
	 */
	public boolean displayYesNoSelection(String question) {

		// show the question
		System.out.println(question + "(y/n)");

		// user selection, if "no" or "n" return false, if "yes" or "y" return true,
		// else invalid user input, call this method again recursively
		String strSelection = input.next();
		if (strSelection.equalsIgnoreCase("n") || strSelection.equalsIgnoreCase("no")) {
			return false;
		} else if (strSelection.equalsIgnoreCase("y") || strSelection.equalsIgnoreCase("yes")) {
			return true;
		} else {
			System.out.println("Invalid Selection. Selection must be (y/n).");
			return displayYesNoSelection(question);
		}

	}

}
