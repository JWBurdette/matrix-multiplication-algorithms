package main;

/**
 * contains static methods to do matrix multiplication by brute force.
 */
public class Brute {
	
	/**
	 * multiplies 2 matrices and returns the resulting matrix.
	 */
	public static Matrix multiply(Matrix a, Matrix b) {
		
		int size = a.getSize();
		int[][] resultVertices = new int[size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				resultVertices[i][j] = sumRowColProducts(a.getRow(i), b.getCol(j));
			}			
		}
		
		return new Matrix(resultVertices);
	}

	/**
	 * calculates the item for a specific location in a product matrix. multiplies the values in a 
	 * row of one matrix by the values in a column of another and adds them.
	 */
	private static int sumRowColProducts(int[] rowOfFirst, int[] colOfSecond) {

		int size = rowOfFirst.length;
		int sum = 0;
		
		for (int i = 0; i < size; i++) {
			sum += rowOfFirst[i] * colOfSecond[i];
		}
		
		return sum;
	}
}