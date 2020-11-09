/**
 * Implementation of the Dynamic programming Sequence Alignment algorithm for COM2031
 * 
 * @author Manal Helal (2019), augmented by Steve Schneider (2020)
 */

import java.util.Arrays;

public class SeqAlign {
	// function to find out  
	// the minimum penalty 
	static void Needleman_Wunsch(String X, String Y, int M[][], 
	                              int misMatchPenalty, int gapPenalty) 
	{ 
		for(int i = 0; i<=X.length(); i++){
			M[i][0] = i*gapPenalty;
		}

		for(int j = 0; j <= Y.length(); j++){
			M[0][j] = j*gapPenalty;
		}

		for(int i = 1; i<=X.length(); i++){
			for(int j = 1; j<=Y.length(); j++){
				M[i][j] =Math.min(misMatch(X, Y, i, j, misMatchPenalty)+M[i-1][j-1],
						Math.min(gapPenalty+M[i-1][j], gapPenalty+M[i][j-1]));
			}
		}
	}

	public static int misMatch(String X, String Y, int i, int j, int misMatchPenalty){
		return (X.charAt(i-1) == Y.charAt(j-1)) ? 0 : misMatchPenalty;
	}
	
	
	static  String traceBack (String X, String Y, int M[][], int misMatchPenalty, int gapPenalty) {
		StringBuilder AlignmentX = new StringBuilder();
		StringBuilder AlignmentY = new StringBuilder();

		int XPos = X.length()-1;
		int YPos = Y.length()-1;

		while(XPos != -1 || YPos != -1){
			int movement = lowestAmount(M, XPos, YPos, gapPenalty);

			switch (movement){
				case 1: {
					AlignmentY.append("-");
					AlignmentX.append(X.charAt(XPos));
					XPos -= 1;
				}
				break;
				case 0: {
					AlignmentX.append(X.charAt(XPos));
					AlignmentY.append(Y.charAt(YPos));
					XPos -= 1;
					YPos -= 1;
				}
				break;
				case 2: {
					AlignmentX.append("-");
					AlignmentY.append(Y.charAt(YPos));
					YPos -= 1;
				}
			}
		}

		AlignmentX.reverse();
		AlignmentY.reverse();
		return "The alignment is: \n" + AlignmentX.toString() + "\n" + AlignmentY.toString() + "\n" ;
	}

	public static void printStuff(int M[][]){
		for(int x = 0; x < M.length; x++){
			for(int y = 0; y < M[0].length; y++){
				System.out.print(" ,");
				System.out.print(M[x][y]);
			}
			System.out.println("");
		}
	}

	public static int lowestAmount(int M[][], int x, int y, int gap){
		int[] numbers = {M[x][y], M[x][y+1]+gap, M[x+1][y]};
		int lowestValue = Integer.MAX_VALUE;
		int index = -1;

		for(int i = 0; i<3; i++){
			if(numbers[i] < lowestValue){
				index = i;
				lowestValue = numbers[i];
			}
		}
		return index;
	}
	  
	/**
	 * Utility:  pretty-print The scoring Matrix
	 */
	
	public static String printM(int M[][], String X, String Y) {

		StringBuffer s = new StringBuffer();

		// Table Header:
		s.append("\t-\t");
		for (int j = 0; j < Y.length(); j++) {
			s.append(Y.charAt(j) + "\t");
		}
		s.append("\n\n-\t");
		for (int j = 0; j <= Y.length(); j++) {
			s.append(M[0][j] + "\t");
		}
		s.append("\n");
		// Table Content:
		for (int i = 1; i <= X.length(); i++) {
			s.append(X.charAt(i-1) + " \t");
			for (int j = 0; j <= Y.length(); j++) {
				s.append(M[i][j] + "\t");
			}
			s.append("\n");
		}

		return s.toString();
	}
	
	// Test code
	
	public static void testNW(String name, String word1, String word2, 
								int misMatchPenalty, int gapPenalty, int expected)
	{
		final int M[][] = new int[word1.length()  + 1][word2.length() + 1]; 
	    Needleman_Wunsch(word1, word2, M, misMatchPenalty, gapPenalty); 
		int value = M[word1.length()][word2.length()];
		if(value==expected) {
	    	System.out.print(name+":  Pass ");
	    }
	    if(value!=expected) {
	    	System.out.println("*********************");
	    	System.out.print(name+":  Fail:  ");
	       	System.out.print("your value for "+word1+" and "+word2+" is "+value
	       			+"  (bottom right value of your array M)\n");
	       	System.out.print("Correct value is "+expected+"\n\n");
	       	System.out.println("Your array M is:");
	       	System.out.println(printM(M, word1, word2));
	    	System.out.print("*********************");
	    }
	    System.out.print("\n");
		}
		

	
	
	
	// Driver code 
	public static void main(String[] args) 
	{ 
		String word1 = "DEED"; 
		String word2 = "DECIDE"; 
		  
		int misMatchPenalty = 3; 
		int gapPenalty = 2; 
		final int M[][] = new int[word1.length()  + 1][word2.length() + 1]; 
		Needleman_Wunsch(word1, word2, M, misMatchPenalty, gapPenalty); 
		System.out.println("Testing the construction of the array M:");
		System.out.println(printM(M,word1,word2));
		
		System.out.println(traceBack(word1, word2, M, misMatchPenalty, gapPenalty));
		
	    testNW("test1 ", "BEER", "EBBE", 3, 2, 7);
	    testNW("test2 ", "ABBA", "BAAB", 3, 5, 12);
	    testNW("test3 ", "ABBA", "BAAB", 3, 2, 7);
	    testNW("test4 ", "SURREY", "GUILDFORD", 3, 2, 19);
	    testNW("test5 ", "ABCD", "EFGHIJ", 2, 5, 18);
	    testNW("test6 ", "ABCD", "EFGHIJ", 5, 2, 20);
	    testNW("test7 ", "", "AAAA", 1, 2, 8);
	    testNW("test6 ", "ABCD", "ABCD", 2, 2, 0);
	    testNW("test7 ", "ABACAB", "ABCABC", 3, 2, 4);
	    testNW("test8 ", "SUSSU", "USSUS", 2, 5, 8);
	    testNW("test9 ", "A", "B", 3, 2, 3);
	    testNW("test10", "A", "B", 2, 2, 2);
	    testNW("test11", "A", "B", 5, 2, 4);
	    testNW("test12", "", "", 5, 2, 0);

		System.out.println(traceBack(word1, word2, M, misMatchPenalty, gapPenalty));
		
	
	} 

}



