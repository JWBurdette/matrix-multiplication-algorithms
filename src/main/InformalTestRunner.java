package main;

import java.io.FileNotFoundException;

/**
 * runner for the informal experiment. uses the same file as the 
 * formal experiment but only uses the first matrix pair.
 */
public class InformalTestRunner {
	
	/**
	 * main method for informal experiment to determine differences between running 
	 * times of the two algorithms for different matrix sizes.
	 */
	public static void main(String[] args) {	
		
		long startTime;
		long endTime;	
		String inputFileName = "20 pairs 1024.csv";

		Matrix[] matricesFromFile;
		Matrix bProduct = null;
		Matrix sProduct = null;
		
		int n = 10;
		
		for (int i = (n/2); i <= n; i++) {
			
			int matrixSize = (int) Math.pow(2, i);
			
			try {
				matricesFromFile = Matrix.getNMatrixPairsFromFile(inputFileName, matrixSize, 1);
			} catch (FileNotFoundException e) {
				System.out.println("File not found.");
				return;
			}
					
			startTime = System.currentTimeMillis();
			bProduct = Brute.multiply(matricesFromFile[0], matricesFromFile[1]);			
			endTime = System.currentTimeMillis();
			long bruteTime = endTime - startTime;
			
			startTime = System.currentTimeMillis();
			sProduct = Strassen.multiply(matricesFromFile[0], matricesFromFile[1]);			
			endTime = System.currentTimeMillis();
			long strassenTime = endTime - startTime;
			
			System.out.println("Time for matrix size " + matrixSize + ": brute force " + 
					bruteTime + " ms and strassen " + strassenTime + " ms. Difference is " +
					(strassenTime - bruteTime) + " ms.");
			 
//			bProduct.printMatrix();
//			sProduct.printMatrix();			
		}
	}
}