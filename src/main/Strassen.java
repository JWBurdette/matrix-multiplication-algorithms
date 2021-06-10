package main;

/**
 * contains static methods to do matrix multiplication by strassen's method.
 */
public class Strassen {
	
	/**
	 * multiplies 2 matrices and returns the resulting matrix.
	 */
	public static Matrix multiply(Matrix a, Matrix b) {
		
		if (a.getSize() == 2) {
			return smallMultiply(a, b);
		}
		
		Matrix[][] submatricesA = a.divideIntoFour();
		Matrix[][] submatricesB = b.divideIntoFour();
				
		return bigMultiply(submatricesA, submatricesB);	
	}

	/**
	 * uses the rules of strassen's method to multiply 2 groups of matrices and 
	 * combine them and return the result.
	 */
	private static Matrix bigMultiply(Matrix[][] submatricesA, Matrix[][] submatricesB) {
			
		Matrix a = submatricesA[0][0];
		Matrix b = submatricesA[0][1];
		Matrix c = submatricesA[1][0];
		Matrix d = submatricesA[1][1];
		Matrix e = submatricesB[0][0];
		Matrix f = submatricesB[0][1];
		Matrix g = submatricesB[1][0];
		Matrix h = submatricesB[1][1];
		
		//make recursive calls
		Matrix p1 = Strassen.multiply(a, subtract(f, h));
		Matrix p2 = Strassen.multiply(add(a, b), h);
		Matrix p3 = Strassen.multiply(add(c, d), e);
		Matrix p4 = Strassen.multiply(d, subtract(g, e));
		Matrix p5 = Strassen.multiply(add(a, d), add(e, h));
		Matrix p6 = Strassen.multiply(subtract(b, d), add(g, h));
		Matrix p7 = Strassen.multiply(subtract(a, c), add(e, f));
		
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

	/**
	 * uses the rules of strassen's method to multiply 2 groups of numbers and combine them 
	 * and return the result. called by multiply when matrix is size 2.
	 */
	public static Matrix smallMultiply(Matrix first, Matrix second) {
		
		int[][] result = new int[2][2];
		
		int a = first.get(0, 0);
		int b = first.get(0, 1);
		int c = first.get(1, 0);
		int d = first.get(1, 1);
		int e = second.get(0, 0);
		int f = second.get(0, 1);
		int g = second.get(1, 0);
		int h = second.get(1, 1);

		int p1 = a * (f - h);
		int p2 = (a + b) * h;
		int p3 = (c + d) * e;
		int p4 = d * (g - e);
		int p5 = (a + d) * (e + h);
		int p6 = (b - d) * (g + h);
		int p7 = (a - c) * (e + f);
		
		result[0][0] = p5 + p4 - p2 + p6;
		result[0][1] = p1 + p2;
		result[1][0] = p3 + p4;
		result[1][1] = p1 + p5 - p3 - p7;
		
		return new Matrix(result);
	}
}