package main;

/**
 * contains static methods to do matrix multiplication by a combination 
 * of brute force and strassens algorithm using a cutoff point.
 */
public class Combined {
	
	/*
	 * cutoff point that determines the matrix width to switch to brute force at
	 */
	private static int cutoffPoint = 2;
	
	/**
	 * multiplies 2 matrices and returns the resulting matrix.
	 */
	public static Matrix multiply(Matrix a, Matrix b) {
		
		if (a.getSize() == cutoffPoint) {
			return Brute.multiply(a, b);
		} else if (a.getSize() == 2) {
			return Strassen.smallMultiply(a, b);
		}
		
		Matrix[][] submatricesA = a.divideIntoFour();
		Matrix[][] submatricesB = b.divideIntoFour();
		
		return bigMultiply(submatricesA, submatricesB);
	}
	
	/**
	 * sets the cutoff point.
	 */
	public static void setCutoff(int cutoff) {
		cutoffPoint = cutoff;
	}
	
	/**
	 * uses the rules of strassen's method to multiply 2 groups of matrices and 
	 * combine them and return the result.
	 */
	public static Matrix bigMultiply(Matrix[][] submatricesA, Matrix[][] submatricesB) {
		
		Matrix a = submatricesA[0][0];
		Matrix b = submatricesA[0][1];
		Matrix c = submatricesA[1][0];
		Matrix d = submatricesA[1][1];
		Matrix e = submatricesB[0][0];
		Matrix f = submatricesB[0][1];
		Matrix g = submatricesB[1][0];
		Matrix h = submatricesB[1][1];
		
		//make recursive calls
		Matrix p1 = Combined.multiply(a, subtract(f, h));
		Matrix p2 = Combined.multiply(add(a, b), h);
		Matrix p3 = Combined.multiply(add(c, d), e);
		Matrix p4 = Combined.multiply(d, subtract(g, e));
		Matrix p5 = Combined.multiply(add(a, d), add(e, h));
		Matrix p6 = Combined.multiply(subtract(b, d), add(g, h));
		Matrix p7 = Combined.multiply(subtract(a, c), add(e, f));

		Matrix m1 = add(p5, add(p6, subtract(p4, p2)));
		Matrix m2 = add(p1, p2);
		Matrix m3 = add(p3, p4);
		Matrix m4 = add(p1, subtract(subtract(p5, p3), p7));

		//combine and return
		return Matrix.combineFour(m1, m2, m3, m4);
	}

	/**
	 * shorthand for matrix addition using Matrix.addOrSubtractMatrices method for readability 
	 */
	private static Matrix add(Matrix a, Matrix b) {
		return Matrix.addOrSubtractMatrices(a, b, true);
	}
	
	/**
	 * shorthand for matrix subtraction Matrix.addOrSubtractMatrices method for readability 
	 */
	private static Matrix subtract(Matrix a, Matrix b) {
		return Matrix.addOrSubtractMatrices(a, b, false);
	}
}