package com.smartdrawer.main;

import java.util.ArrayList;

/**
 * SmartDrawer - a class that represents a 'drawer' of integers as an ArrayList
 * this class implements Runnable, rather than extends Thread, so that we can extend ArrayList<Integer>
 * 
 * 
 * @extends ArrayList<Integer>
 * @implements Runnable
 * @author Tim Winwood - x20213638
 * @version 1.0
 */
public class SmartDrawer extends ArrayList<Integer> implements Runnable {

	// instance variables
	private int sum;
	private int average;
	private int max;
	private int min;

	/**
	 * SmartDrawer constructor, calls super without parameters
	 */
	public SmartDrawer() {
		super();
	}

	/**
	 * SmartDrawer constructor, calls super with 1 parameter
	 * allows us to initialise a SmartDrawer by copying the ArrayList<Integer> passes as a parameter
	 */
	public SmartDrawer(ArrayList<Integer> drawer) {
		super(drawer);
	}

	/**
	 * called when the thread is started
	 */
	@Override
	public void run() {
		
		// perform all the operations
		quickSort();
		calculateSum();
		calculateAverage();
		calculateMaximum();
		calculateMinimum();

	}
	
	/**
	 * Quick Sort the SmartDrawer ArrayList
	 * method without parameters, calls the recursive quickSort() method
	 * with start = 0, and end = this.size() - 1, to quick sort the full ArrayList
	 * 
	 */
	private void quickSort() {
		quickSort(0, this.size() - 1);
	}
	
	/**
	 * Quick Sort the SmartDrawer ArrayList
	 * recursive method
	 * 
	 * @param start index to start from
	 * @param end index to stop at
	 */
	private void quickSort(int start, int end) {
		
		// recursive case - start is less than end
		if (start < end) {
			
			// get the partition index
			int pIndex = partition(start, end);
			
			// sort elements less than the pivot
			quickSort(start, pIndex - 1);
			
			// sort elements greater than the pivot
			quickSort(pIndex + 1, end);
			
		}
		
	}

	/**
	 * partition the SmartDrawer ArrayList
	 * 
	 * @param start index to start from
	 * @param end index to stop at
	 */
	private int partition(int start, int end) {

		// get the pivot
		int pivot = this.get(end);

		// move the pivot into the correct position
		// loop from start to end
		int i = start;
		for (int j = start; j <= end - 1; j++) {
			
			// check if current is less than pivot
			if (this.get(j) < pivot) {
				
				// if so, swap the current element and the smaller element
				swap(i, j);
				i++;
			}
		}
		
		// finally, swap the pivot after the last smaller element
		swap(i, end);
		return (i);
	}

	/**
	 * swap two elements in the SmartDrawer ArrayList
	 *
	 * @param index of x element to swap
	 * @param index of y element to swap
	 */
	private void swap(int x, int y) {
		int temp = this.get(x);
		this.set(x, get(y));
		this.set(y, temp);
	}

	/**
	 * calculates the sum of all values in the drawer
	 */
	private void calculateSum() {
		for (int i = 0; i < this.size(); i++) {
			this.sum += this.get(i);
		}
	}

	/**
	 * calculates the average of all values in the drawer
	 */
	private void calculateAverage() {
		this.average = sum / this.size();
	}

	/**
	 * calculates the maximum value in the drawer
	 */
	private void calculateMaximum() {
		this.max = this.get(this.size() -1);
	}

	/**
	 * calculates the minimum value in the drawer
	 */
	private void calculateMinimum() {
		this.min = this.get(0);
	}

	/**
	 * prints the SmartDrawer ArrayList to the screen
	 *
	 */
	public void printArrayList() {
		for (int i = 0; i < this.size(); i++) {
			System.out.println(this.get(i).toString());
		}
	}

	/**
	 * @return the sum of all values in the drawer
	 */
	public int getSum() {
		return sum;
	}

	/**
	 * @return the average of all values in the drawer
	 */
	public int getAverage() {
		return average;
	}

	/**
	 * @return the maximum value in the drawer
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @return the minimum value in the drawer
	 */
	public int getMin() {
		return min;
	}

}
