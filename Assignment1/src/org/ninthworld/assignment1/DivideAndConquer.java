package org.ninthworld.assignment1;

import java.util.Random;

public class DivideAndConquer {
	
	public static void main(String[] args){
		Random r = new Random(12345);
		
		Matrix A = new Matrix(4);
		Matrix B = new Matrix(4);
		
		for(int i=0; i<A.getSize(); i++){
			for(int j=0; j<A.getSize(); j++){
				A.setVal(i, j, (int)(r.nextDouble()*10));
			}
		}
		
		for(int i=0; i<B.getSize(); i++){
			for(int j=0; j<B.getSize(); j++){
				B.setVal(i, j, (int)(r.nextDouble()*10));
			}
		}
		
		Matrix C_MMult = MMult(A, B, A.getSize());
		Matrix C_Strassen = Strassen(A, B);
		
		System.out.printf("MMult ------ \n");
		printMatrixMulti(A, B, C_MMult);
		
		System.out.printf("\nStrassen --- \n");
		printMatrixMulti(A, B, C_Strassen);
	}
	
	public static void printMatrixMulti(Matrix A, Matrix B, Matrix C){
		for(int j=0; j<A.getSize(); j++){
			System.out.printf("|");
			
			for(int i=0; i<A.getSize(); i++){
				System.out.printf("%3d ", A.getVal(i, j));
			}
			
			System.out.printf("| %s |", (j == (A.getSize()/2) ? "x" : " "));
			
			for(int i=0; i<B.getSize(); i++){
				System.out.printf("%3d ", B.getVal(i, j));
			}
			
			System.out.printf("| %s |", (j == (A.getSize()/2) ? "=" : " "));
			
			for(int i=0; i<C.getSize(); i++){
				System.out.printf("%3d ", C.getVal(i, j));
			}
			
			System.out.printf("|\n");
		}
	}
	
	public static Matrix Strassen(Matrix A, Matrix B){
		int n = A.getSize();
		Matrix C = new Matrix(n);
		
		if(n == 1){
			C.setVal(0, 0, A.getVal(0, 0) * B.getVal(0, 0));
			return C;
		}else{
			Matrix[] A_sub = Matrix.subdivide(A);
			Matrix[] B_sub = Matrix.subdivide(B);
			
			Matrix[] x = new Matrix[7];
			
			// 7 recursive calls of n/2: 7T(n/2)
			x[0] = Strassen( A_sub[0], Matrix.sub(B_sub[1], B_sub[3]) );
			x[1] = Strassen( Matrix.add(A_sub[0], A_sub[1]), B_sub[3] );
			x[2] = Strassen( Matrix.add(A_sub[2], A_sub[3]), B_sub[0] );
			x[3] = Strassen( A_sub[3], Matrix.sub(B_sub[2], B_sub[0]) );
			x[4] = Strassen( Matrix.add(A_sub[0], A_sub[3]), Matrix.add(B_sub[0], B_sub[3]) );
			x[5] = Strassen( Matrix.sub(A_sub[1], A_sub[3]), Matrix.add(B_sub[2], B_sub[3]) );
			x[6] = Strassen( Matrix.sub(A_sub[0], A_sub[2]), Matrix.add(B_sub[0], B_sub[1]) );
			
			// Add/Sub n/2 x n/2 8 times = 8 * (n^2)/4 = O(2n^2)
			C.setSubMatrix(0, 0, Matrix.add(Matrix.sub(Matrix.add(x[4], x[3]), x[1]), x[5]));
			C.setSubMatrix(0, 1, Matrix.add(x[0], x[1]));
			C.setSubMatrix(1, 0, Matrix.add(x[2], x[3]));
			C.setSubMatrix(1, 1, Matrix.sub(Matrix.sub(Matrix.add(x[0], x[4]), x[2]), x[6]));
			
			// T(n) = 7T(n/2) + O(2n^2) = O(2log2(7)) = O(2n^2.81)
			return C;
		}
	}
	
	public static Matrix MMult(Matrix A, Matrix B, int n){
		Matrix C = new Matrix(n);
		
		if(n == 1){
			C.setVal(0, 0, A.getVal(0, 0) * B.getVal(0, 0));
			return C;
		}else{
			Matrix[] A_sub = Matrix.subdivide(A);
			Matrix[] B_sub = Matrix.subdivide(B);
			
			Matrix[] x = new Matrix[8];
			int m = n/2;
			
			// 8 Recursive calls of n/2: 8T(n/2)
			x[0] = MMult( A_sub[0], B_sub[0], m );
			x[1] = MMult( A_sub[1], B_sub[2], m );
			x[2] = MMult( A_sub[0], B_sub[1], m );
			x[3] = MMult( A_sub[1], B_sub[3], m );
			x[4] = MMult( A_sub[2], B_sub[0], m );
			x[5] = MMult( A_sub[3], B_sub[2], m );
			x[6] = MMult( A_sub[2], B_sub[1], m );
			x[7] = MMult( A_sub[3], B_sub[3], m );
			
			// Add n/2 x n/2 4 times: 4 * (n^2)/4 = O(n^2)
			C.setSubMatrix( 0, 0, Matrix.add(x[0], x[1]) );
			C.setSubMatrix( 0, 1, Matrix.add(x[2], x[3]) );
			C.setSubMatrix( 1, 0, Matrix.add(x[4], x[5]) );
			C.setSubMatrix( 1, 1, Matrix.add(x[6], x[7]) );
			
			// T(n) = 8T(n/2) + O(n^2) = O(n^log2(8)) = O(n^3)
			return C;
		}
	}
}

class Matrix {
	private int[][] vals;
	
	public Matrix(int size){
		this.vals = new int[size][size];
	}
	
	public void setVal(int i, int j, int val){
		if(validIndex(i, j)){
			vals[i][j] = val;
		}
	}
	
	public void setSubMatrix(int i, int j, Matrix subMatrix){
		int halfSize = this.getSize()/2;
		int rowOffset = j*halfSize;
		int colOffset = i*halfSize;
		
		for(int k=0; k<halfSize; k++){
			for(int l=0; l<halfSize; l++){
				this.setVal(k + rowOffset, l + colOffset, subMatrix.getVal(k, l));
			}
		}
	}
	
	public int getVal(int i, int j) throws IllegalArgumentException {
		if(validIndex(i, j)){
			return vals[i][j];
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	public int getSize(){
		return vals.length;
	}
	
	private boolean validIndex(int i, int j){
		return (i >= 0 && i < vals.length && j >= 0 && j < vals[0].length);
	}
	
	public static Matrix add(Matrix A, Matrix B){
		Matrix C = new Matrix(A.getSize());
		
		for(int i=0; i<C.getSize(); i++){
			for(int j=0; j<C.getSize(); j++){
				C.setVal(i, j, A.getVal(i, j) + B.getVal(i, j));
			}
		}
		
		return C;
	}
	
	public static Matrix sub(Matrix A, Matrix B){
		Matrix C = new Matrix(A.getSize());
		
		for(int i=0; i<C.getSize(); i++){
			for(int j=0; j<C.getSize(); j++){
				C.setVal(i, j, A.getVal(i, j) - B.getVal(i, j));
			}
		}
		
		return C;
	}
	
	public static Matrix[] subdivide(Matrix A){
		Matrix[] sub = new Matrix[4];
		
		for(int i=0; i<sub.length; i++){
			int halfSize = A.getSize()/2;
			int rowOffset = (i%2) * halfSize;
			int colOffset = (i/2) * halfSize;
			
			sub[i] = new Matrix(halfSize);
			
			for(int j=0; j<sub[i].getSize(); j++){
				for(int k=0; k<sub[i].getSize(); k++){
					sub[i].setVal(j, k, A.getVal(j + rowOffset, k + colOffset));
				}
			}
		}
		
		return sub;
	}
}