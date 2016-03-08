package org.ninthworld.binarytrees;

import java.util.Random;

public class BSTest {
	
	enum BSTestArrayType {INCREASING, DECREASING, RANDOMIZED};
	
	public static double insertTest(BSTree tree, int[] values){
		return insertTest(tree, values, values.length);
	}
	
	public static double insertTest(BSTree tree, int[] values, int length){
		long start, end;
		start = System.nanoTime();
		for(int i=0; i<length; i++){
			tree.insert(values[i]);
		}
		end = System.nanoTime();
		
		return (end - start) / 1000000.0;
	}
	
	public static int[] generateArray(BSTestArrayType type, int length){
		return generateArray(type, length, new Random());
	}
	
	public static int[] generateArray(BSTestArrayType type, int length, Random rand){
		int[] array = new int[length];
		for(int i=0; i<array.length; i++){
			if(type == BSTestArrayType.DECREASING){
				array[i] = array.length - (i+1);
			}else{
				array[i] = i;
			}
		}
		
		if(type == BSTestArrayType.RANDOMIZED){
			for(int i=0; i<array.length; i++){
				int num = rand.nextInt(array.length);
				array[i] = num;
				array[num] = i;
			}
		}
		
		return array;
	}
}
