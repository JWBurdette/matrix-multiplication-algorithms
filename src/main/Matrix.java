package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class to create and do various operations on matrices.
 */
public class Matrix {
	
	int[][] vertices;   
	
	/**
	 * constructor to create a matrix.
	 */
	public Matrix(int[][] vertices) {
		this.vertices = vertices;		
	}
	
	/**
	 * get the size (width) of the matrix.
	 */
	public int getSize() {
		return this.vertices[0].length;
	}

	/**
	 * get the vertices in a specified row of the matrix.
	 */
	public int[] getRow(int rowNum) {
		return vertices[rowNum];
	}
	
	/**
	 * get the vertices in a specified column of the matrix.
	 */
	public int[] getCol(int colNum) {
		
		int size = this.vertices.length;
		int[] column = new int[size];
		
		for (int i = 0; i < size; i++) {
			column[i] = this.vertices[i][colNum];
		}
		return column;
	}
	
	/**
	 * prints out the rows and columns of the matrix.
	 */
	public void printMatrix() {
		
		for (int i = 0; i < vertices.length; i++) {
			System.out.print("|");
			
			System.out.print(" ");
			for (int j = 0; j < vertices.length; j++) {
				System.out.print(vertices[i][j] + " ");
			}
			
			System.out.print("|");
			System.out.println();			
		}
		System.out.println();					
	}
	
	/**
	 * extracts n of the matrix pairs from the specified file.
	 */
	public static Matrix[] getNMatrixPairsFromFile(String inputFileName, int matrixSize, int n) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(new File(inputFileName));
		int numPairs;
		
		if (n == 0) {
			numPairs = getNumPairs(scanner);
			
		} else {
			numPairs = n;
			getNumPairs(scanner);//still need to move forward
		}
		
		Matrix[] matrices = new Matrix[numPairs * 2];
		
		for (int i = 0; i < numPairs * 2; i++) {

			int[][] verticesGroup = readUntilEndOfGroup(scanner);
			int[][] sizedVerticesGroup = shrinkToCorrectSize(verticesGroup, matrixSize);
			matrices[i] = new Matrix(sizedVerticesGroup);
			
			System.out.println("Created Matrix " + (i+1) + " from file.");
		}
		
		return matrices;		
	}
	
	/**
	 * makes a smaller 2d array from the passed in one of the specified size (starting at top left corner).
	 */
	private static int[][] shrinkToCorrectSize(int[][] verticesGroup, int size) {
		
		int[][] sizedVerticesGroup = new int[size][size];
		
		for (int i = 0; i < size; i++) {
			
			for (int j = 0; j < size; j++) {
				sizedVerticesGroup[i][j] = verticesGroup[i][j];
			}			
		}
		
		return sizedVerticesGroup;
	}

	/**
	 * reads rows and columns of data from file scanner until that group has ended.
	 */
	private static int[][] readUntilEndOfGroup(Scanner scanner) {
		
		ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>();

		while (true) {
			
			String line = scanner.nextLine();
			
			if (line.charAt(0) == ',' || line.charAt(0) == '~') break;
			
			ArrayList<Integer> lineValues = new ArrayList<Integer>();
			Scanner lineScanner = new Scanner(line);
	        lineScanner.useDelimiter(",");

	        while (lineScanner.hasNext()) {
	            lineValues.add(Integer.parseInt(lineScanner.next()));
	        }
	             
	        values.add(lineValues);	        
		}

		return convertToArray(values);
	}

	/**
	 * convert 2d arraylist into 2d array.
	 */
	private static int[][] convertToArray(ArrayList<ArrayList<Integer>> values) {
		
        int[][] arrayValues = new int[values.size()][values.size()];
        
        for (int i = 0; i < values.size(); i++) {
        	arrayValues[i] = values.get(i).stream().mapToInt(j -> j).toArray();
        }
        
		return arrayValues;
	}

	/**
	 * read through file line by line to find out how many rows and columns are there.
	 */
	private static int getNumPairs(Scanner scanner) {
		
		scanner.nextLine();//account for first line being different.
		
		ArrayList<String> values = new ArrayList<String>();
		
		Scanner rowScanner = new Scanner(scanner.nextLine());
        rowScanner.useDelimiter(",");

        while (rowScanner.hasNext()) {
            values.add(rowScanner.next());
        }

        return Integer.parseInt(values.get(2));
	}
	
	/**
	 * get the matrix's vertex at the specified row and column.
	 */
	public int get(int x, int y) {
		return vertices[x][y];
	}

	/**
	 * add or subtract 2 matrices depending on boolean value.
	 */
	public static Matrix addOrSubtractMatrices(Matrix a, Matrix b, boolean add) {
		
		int size = a.getSize();
		int[][] resultVertices = new int[size][size];
		
		if (add) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					resultVertices[i][j] = a.vertices[i][j] + b.vertices[i][j];
				}
			}
		} else {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					resultVertices[i][j] = a.vertices[i][j] - b.vertices[i][j];
				}
			}
		}
		
		return new Matrix(resultVertices);
	}


	/**
	 * divides the matrix into 4 matrices of half its size and return as 2d array.
	 */
	public Matrix[][] divideIntoFour() {
		
		int size = this.getSize();
		Matrix[][] matrices = new Matrix[2][2];
		
		int[][] vgroup1 = new int[size/2][size/2];
		int[][] vgroup2 = new int[size/2][size/2];
		int[][] vgroup3 = new int[size/2][size/2];
		int[][] vgroup4 = new int[size/2][size/2];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				
				if (i < size/2 && j < size/2) {
					vgroup1[i][j] = this.vertices[i][j];
					
				} else if (i < size/2 && j >= size/2) {
					vgroup2[i][j-size/2] = this.vertices[i][j];
					
				} else if (i >= size/2 && j < size/2) {
					vgroup3[i-size/2][j] = this.vertices[i][j];
					
				} else if (i >= size/2 && j >= size/2) {
					vgroup4[i-size/2][j-size/2] = this.vertices[i][j];
					
				} else {
					System.err.println("error"); 
				}
			}
		}
		
		matrices[0][0] = new Matrix(vgroup1);
		matrices[0][1] = new Matrix(vgroup2);
		matrices[1][0] = new Matrix(vgroup3);
		matrices[1][1] = new Matrix(vgroup4);

		return matrices;
	}

	/**
	 * combine 4 matrices into one.
	 * |m1 m2|
	 * |m3 m4|
	 */
	public static Matrix combineFour(Matrix m1, Matrix m2, Matrix m3, Matrix m4) {
		
		int size = m1.getSize() * 2;

		int[][] vgroup = new int[size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				
				if (i < size/2 && j < size/2) {
					vgroup[i][j] = m1.vertices[i][j];
					
				} else if (i < size/2 && j >= size/2) {
					vgroup[i][j] = m2.vertices[i][j-size/2];
					
				} else if (i >= size/2 && j < size/2) {
					vgroup[i][j] = m3.vertices[i-size/2][j];
					
				} else if (i >= size/2 && j >= size/2) {
					vgroup[i][j] = m4.vertices[i-size/2][j-size/2];
					
				} else {
					System.err.println("error"); 
				}
			}
		}
		
		return new Matrix(vgroup);
	}
}