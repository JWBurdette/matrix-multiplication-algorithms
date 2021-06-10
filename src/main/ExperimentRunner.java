package main;

import java.io.FileNotFoundException;

/**
 * runner for the experiment.
 */
public class ExperimentRunner {
	
	/**
	 * exponent of n that determines the size of the matrices
	 */
	private static int nExponent;	

	/**
	 * main method for experiment 
	 */
	public static void main(String[] args) {

		//all matrices come from the same file
		String inputFileName = "20 pairs 1024.csv"; 
		nExponent = 10;
		Matrix[] matricesFromFile;
					
		try {
			int matrixSize = (int) Math.pow(2, nExponent);
			matricesFromFile = Matrix.getNMatrixPairsFromFile(inputFileName, matrixSize, 20);

		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			return;
		}

		//multiply 20 matrices for each cutoff point up to the exponent that sets the size of the matrix.
		for (int i = 0; i < nExponent; i++) {
			
			int cutoff = (int) Math.pow(2, i);
			
			System.out.println("\ncutoff: " + cutoff);
			Combined.setCutoff(cutoff);
			
			doBruteMultiplications(matricesFromFile);
			doStrassenMultiplications(matricesFromFile);			
			doCombinedMultiplications(matricesFromFile);
		}
	}

	/**
	 * multiply each pair of matrices from file using the combined approach.
	 */
	private static void doCombinedMultiplications(Matrix[] matricesFromFile) {
		
		long startTime;
		long endTime;
		long combinedTime;
		Matrix cProduct;

		System.out.println("combination algorithm times: ");
		
		for (int i = 0; i < matricesFromFile.length; i = i + 2) {
			startTime = System.currentTimeMillis();
			cProduct = Combined.multiply(matricesFromFile[i], matricesFromFile[i+1]);			
			endTime = System.currentTimeMillis();
			combinedTime = endTime - startTime;
			
			System.out.print(combinedTime + " ");
		}
		System.out.println();
	}

	/**
	 * multiply each pair of matrices from file using strassen's method.
	 */
	private static void doStrassenMultiplications(Matrix[] matricesFromFile) {
		
		long startTime;
		long endTime;
		long strassenTime;
		Matrix sProduct;

		System.out.println("strassen times: ");
		
		for (int i = 0; i < matricesFromFile.length; i = i + 2) {
			startTime = System.currentTimeMillis();
			sProduct = Strassen.multiply(matricesFromFile[i], matricesFromFile[i+1]);			
			endTime = System.currentTimeMillis();
			strassenTime = endTime - startTime;
			
			System.out.print(strassenTime + " ");
		}
		System.out.println();
	}

	/**
	 * multiply each pair of matrices from file using the brute force approach.
	 */
	private static void doBruteMultiplications(Matrix[] matricesFromFile) {
		
		long startTime;
		long endTime;
		long bruteTime;
		Matrix bProduct;

		System.out.println("brute times: ");
		
		for (int i = 0; i < matricesFromFile.length; i = i + 2) {

			startTime = System.currentTimeMillis();
			bProduct = Brute.multiply(matricesFromFile[i], matricesFromFile[i+1]);	
			endTime = System.currentTimeMillis();
			bruteTime = endTime - startTime;
			
			System.out.print(bruteTime + " ");			
		}
		System.out.println();
	}
}
