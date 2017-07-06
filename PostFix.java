/**
 * This class can be used to find every combination in the card game 24 by finding every 4 card combination and every operator combination and 
 * evaluating  each using 5 possible post fix notations. If the answer is equal to 24 the solution is printed.
 * @author Patrick
 *
 */
public class PostFix 

{
	private static String[] Found = new String[1820]; // stores all combos of sums that equal 24; used to check for repeated combos
	private static int t = 0; // location of the next empty cell for array Found
	
	/**
	 * Assumes the string entered is a in correct format and numbers and operators are separated with a space
	 * There are 5 valid post fix notations so notation should only range 1 through 5
	 * 1 = num1 num2 op1 num3 num4 op2 op3 Equivalent:(num1 op1 num2) op3(num3 op2 num4)
	 * 2 = num1 num2 op1 num3 op2 num4 op3 Equivalent:((num1 op1 num2) op2 num3) op4 num4
	 * 3 = num1 num2 num3 num4 op1 op2 op3 Equivalent: num1 op3(num2 op2(num3 op1 num4))
	 * 4 = num1 num2 num3 op1 num4 op2 op3 Equivalent: num1 op3((num2 op1 num3) op2 num4)
	 * 5 = num1 num2 num3 op1 op2 num4 op3 Equivalent: (num1 op2 (num2 op1 num3) op3 num4
	 * The reason for the notation is only for the requirement of the output of the equation
	 * Each if statement does the exact same calculation with the stack the only difference is the output.
	 * @param postfix
	 * @param num1  // all int parameters are only use for the output.
	 * @param num2
	 * @param num3
	 * @param num4
	 * @return
	 */
	private static int Math(String postfix, int num1, int num2, int num3, int num4)
	{
		StackPostFixOp<Integer> S = new StackPostFixOp<Integer>(4);
		int answer = 0;
		String twentyfour = "";
		
		
		
		
		
			for(int x=0;x<postfix.length(); x++)
			{
				char current = postfix.charAt(x); // gets the char at each index of the string
				if(current == ' ')
				{
					// do nothing
				}
				else if( current != '*' && current != '/' && current != '+' && current != '-') // if the char is not an operator or a space
				{
					char next = postfix.charAt((x+1)); // next char in string 
					
					if (next == ' ') // if the next char is space that means the current char is a single digit number
					{
						int num = Character.getNumericValue(current);
						S.push(num); // adds number to the stack
					}
					else  // I assume the string is in proper notation so I know the next char, if the current char is
						  // a number, will have to be another number or a space
					{
						String str = "" + current + "" + next;	
						S.push(Integer.parseInt(str)); // adds the double digit number to the stack	
						x++; // increase x because I used the next char in the string
							 // basically skipping an iteration
					}
				}
				else //  the char is an operator
				{
					int right = S.pop(); // first pop is always on the right hand side of the operator
					int left = S.pop();	// second pop is always the left hand side
					
					if ( current == '+') // addition
					{
						answer = left + right;
						if( answer == 24)
						{
							twentyfour += ( left + " + " + right + " = " + answer + " "); // if this is the final operation that equals twenty four it will remove the comma.
						}
						else
						{
							twentyfour += ( left + " + " + right + " = " + answer + ", "); // if not the last operation will include a comma.
							
						}
						S.push(answer); // push back into stack to be evaluated again
					}
					else if(current == '-') // subtraction
					{
						answer = left - right; // doing the actual operation
						if( answer == 24)
						{
							twentyfour += ( left + " - " + right + " = " + answer + " "); // if this is the last operation that equals 24 no comma in the output
						}
						else
						{
							twentyfour += ( left + " - " + right + " = " + answer + ", "); // if not the last operation will keep the comma.
						}
						S.push(answer); // pushes answer back in the stack to be evaluated again
					}
					else if ( current == '/') // division
					{	
						if (right == 0) // can't divide by 0
						{
							return -1; // if you try to divided by 0 in any combination it will not produce 24
						}
						answer = left / right;
						if( answer == 24)
						{
							twentyfour += ( left + " / " + right + " = " + answer + " "); // if last operation will output without a comma at the end
						}
						else
						{
							twentyfour += ( left + " / " + right + " = " + answer + ", "); // if not the last operation will include the comma
						}
						S.push(answer); // push answer back into stack to be evaluated again
					}
					else // operator is * multiplication assuming the string is in proper notation
					{
						answer = left * right;
						if( answer == 24)
						{
							twentyfour += ( left + " * " + right + " = " + answer + " "); // if last operation will not keep the comma.
						}
						else
						{
							twentyfour += ( left + " * " + right + " = " + answer + ", "); // if not the last operation will keep the comma.
						}
						S.push(answer); // push answer to be evaluated again
						
					}
				}
				
			}
			if(answer == 24) // if the final answer is 24 will output the solution to the answer
			{
				//converts numbers from smallest to largest then adds them to a string and array
				String str = "";
				
				if(num1<=num2 && num1<=num3 && num1<=num4) // if num1 is the smallest
				{
					str += Integer.toString(num1); // add num1
					if(num2<=num3 && num2<=num4) // if num2 is the second smallest
					{
						str += Integer.toString(num2); // add num2
						if(num3<=num4) // if num3 is the third smallest
						{
							str += Integer.toString(num3); // add the num3 then num4
							str += Integer.toString(num4);
						}
						else // if num4 is the third smallest
						{
							str += Integer.toString(num4); //add num4 then num3
							str += Integer.toString(num3);
						}
					}
					else if(num3<=num2 && num3<=num4) // if num3 is the second smallest
					{
						str += Integer.toString(num3); // add num3 
						if(num2<=num4) // if num2 is the third third smallest
						{
							str += Integer.toString(num2); // add num2 then num4
							str += Integer.toString(num4);
						}
						else // num4 is the third smallest
						{
							str += Integer.toString(num4); // add num4 then num2
							str += Integer.toString(num2);
						}
					}
					else //if num4 is the second smallest
					{
						str += Integer.toString(num4); // add num4
						if(num3<=num2) // if num3 is the third smallest
						{
							str += Integer.toString(num3); //add num3 then num2
							str += Integer.toString(num2);
						}
						else // if num2 is the third smallest
						{
							str += Integer.toString(num2); // add num2 then num3
							str += Integer.toString(num3);
						}
					}
				}
				else if(num2<=num1 && num2<=num3 && num2<num4) // num2 is the smallest
				{
					str += Integer.toString(num2); // add num2
					if(num1<=num3 && num1<=num4) // if num1 is the second smallest
					{
						str += Integer.toString(num1); // add num1
						if(num3<=num4) // if num3 is the third smallest
						{
							str += Integer.toString(num3); // add num3 then num4
							str += Integer.toString(num4);
						}
						else // if num4 is the third smallest
						{
							str += Integer.toString(num4); // add num4 then num3
							str += Integer.toString(num3);
						}
					}
					else if(num3<=num1 && num3<=num4) // if num3 is the second smallest
					{
						str += Integer.toString(num3); //add num3
						if(num1<=num4) // if num1 is the third smallest
						{
							str += Integer.toString(num1); // add num1 the num4
							str += Integer.toString(num4);
						}
						else
						{
							str += Integer.toString(num4); // add num4 then num1
							str += Integer.toString(num1);
						}
					}
					else // if num4 is the second smallest
					{
						str += Integer.toString(num4); // add num4
						if(num3<=num1) // if num3 is the third smallest
						{
							str += Integer.toString(num3); // add num3 then num1
							str += Integer.toString(num1);
						}
						else // if num1 is the third smallest
						{
							str += Integer.toString(num1); // add num1 then num3
							str += Integer.toString(num3);
						}
					}
				}
				else if(num3<=num1 && num3<=num2 && num3<=num4) // if num3 is the smallest
				{
					str += Integer.toString(num3); // add num3
					if(num1<=num2 && num1<=num4) // if num1 is the second smallest
					{
						str += Integer.toString(num1); // add num1
						if(num2<=num4) // if num2 is the third smallest
						{
							str += Integer.toString(num2); // a  num2 then num4
							str += Integer.toString(num4);
						}
						else // if num4 is the third smallest
						{
							str += Integer.toString(num4); //add num4 then num3
							str += Integer.toString(num3);
						}
					}
					else if(num2<=num1 && num2<=num4) // if num2 is the second smallest
					{
						str += Integer.toString(num2); // add num2
						if(num1<=num4) // if num1 is the third smallest
						{
							str += Integer.toString(num1); // add num1 then num4
							str += Integer.toString(num4);
						}
						else // if num4 is the third smallest
						{
							str += Integer.toString(num4); // add num4 then num1
							str += Integer.toString(num1);
						}
					}
					else // if num4 is the second smallest
					{
						str += Integer.toString(num4); // add num4
						if(num1<=num2) // if num1 is the third smallest
						{
							str += Integer.toString(num1); // add num1 then num2
							str += Integer.toString(num2);
						}
						else // if num2 is the third smallest
						{
							str += Integer.toString(num2); // add num2 then num1
							str += Integer.toString(num1);
						}
					}
				}
				else // num4 is the smallest number
				{
					str += Integer.toString(num4); // add num4
					if(num1<=num3 && num1<=num2) // if num1 is the second smallest
					{
						str += Integer.toString(num1); // add num1
						if(num2<=num3) // if num2 is the third smallest
						{
							str += Integer.toString(num2); // add num2 then num3
							str += Integer.toString(num3);
						}
						else // if num3 is the third smallest
						{
							str += Integer.toString(num3); // add num3 then num2
							str += Integer.toString(num2);
						}
					}
					else if(num2<=num1 && num2<=num3) // if num2 the the second smallest
					{
						str += Integer.toString(num2); // add num2
						if(num1<=num3) // if num1 is the third smallest
						{
							str += Integer.toString(num1); // add num1 then num3
							str += Integer.toString(num3); 
						}
						else // if num3 is the third smallest
						{
							str += Integer.toString(num3); // add num3 then num1
							str += Integer.toString(num1);
						}
					}
					else  // if num3 is the second smallest
					{
						str += Integer.toString(num3); // add num3
						if(num1<=num2) // if num1 is the third smallest
						{
							str += Integer.toString(num1); // add num1 then num2
							str += Integer.toString(num2);
						}
						else // if num2 is the third smallest
						{
							str += Integer.toString(num2); // add num2 then num1
							str += Integer.toString(num1);
						}
					}
				}
				for(String v : Found)
				{
					if(v == null)
					{
						break;
					}
					
					if(v.equals(str))
					{
						return -1; // combination had already been found
					}
				}
				
				Found[t] = str; // adds new combo to Found
				t++; // increase t so it can be used again
				
				String one = "";
				String two = "";
				String three = "";
				String four = "";
				
				if(num1 == 11) // if the number is a J it will show the output as a J instead of 11
				{
					one = "J";
				}
				else if(num1 == 12) // if the number is a Q it will show as Q instead of 12
				{
					one = "Q";
				}
				else if(num1 == 13) // if the number is a K will show as a K instead of 13
				{
					one = "K";
				}
				else // if the number is not 11 12 or 13 meaning it is not a face card it will output the number
				{
					one = Integer.toString(num1);
				}
				
				if(num2 == 11) // if the number is a J it will show the output as a J instead of 11
				{
					two = "J";
				}
				else if(num2 == 12) // if the number is a Q it will show as Q instead of 12
				{
					two = "Q";
				}
				else if(num2 == 13) // if the number is a K will show as a K instead of 13
				{
					two = "K";
				}
				else // if the number is not 11 12 or 13 meaning it is not a face card it will output the number
				{
					two = Integer.toString(num2);
				}
				
				if(num3 == 11) // if the number is a J it will show the output as a J instead of 11
				{
					three = "J";
				}
				else if(num3 == 12) // if the number is a Q it will show as Q instead of 12
				{
					three = "Q";
				}
				else if(num3 == 13) // if the number is a K will show as a K instead of 13
				{
					three = "K";
				}
				else // if the number is not 11 12 or 13 meaning it is not a face card it will output the number
				{
					three = Integer.toString(num3);
				}
				
				if(num4 == 11) // if the number is a J it will show the output as a J instead of 11
				{
					four = "J";
				}
				else if(num4 == 12) // if the number is a Q it will show as Q instead of 12
				{
					four = "Q";
				}
				else if(num4 == 13) // if the number is a K will show as a K instead of 13
				{
					four = "K";
				}
				else // if the number is not 11 12 or 13 meaning it is not a face card it will output the number
				{
					four = Integer.toString(num4);
				}
				System.out.println(one + " " + two + " " + three + " " + four + " : " + twentyfour);

			}
			return answer; // once loop is complete will return the final answer pushed into the stack
		}
	/**
	 * This method finds every combination of 4 cards and every permutation of those 4 cards. 
	 * It will evaluated every permutation using 5 post fix notations and print out the solution if the combination equals 24
	 */
	public static void Combination() 
	{
		char[] ops = OpCombo(); // holds every operator combo
		int[] combo = new int[96]; // holds every permutation of the 4 numbers
		
		
		for(int i1=1; i1<=13; i1++) // first set of nested for loops finds every 4 card combination in a 52 card deck disregarding the suit of each card
			{
				for(int i2=1; i2<=13; i2++)
				{
					for(int i3=1; i3<=13; i3++)
					{
						
						for(int i4=1; i4<=13; i4++)
						{
							int card1 = i1; // four card combination
							int card2 = i2;
							int card3 = i3;
							int card4 = i4;
							//System.out.println(i1 + " " + i2 + " " + i3 + " " + i4 + " ");
							combo =  Permutate(card1,card2,card3,card4); // creates 24 different permutations of the 4 cards
							BreakLoop:
							for(int g=3;g<96;g+=4)
							{
								String n1 = Integer.toString(combo[g-3]); // Each string represents each number
								String n2 = Integer.toString(combo[g-2]);
								String n3 = Integer.toString(combo[g-1]);
								String n4 = Integer.toString(combo[g]);
								
								for(int p=2; p<192; p+=3)
								{
								String op1 = Character.toString(ops[p-2]); // each string represents the the three different operators
								String op2 = Character.toString(ops[p-1]);
								String op3 = Character.toString(ops[p]);
								p+=2;
								//all the post fix notations are below. I'm testing every combination with each post fix notation
								// stores the value into a variable and then checks to see if its 24
								// if its 24 the output has already been printed and it will break outside the loop to get a new set of 4 numbers
								int t1 = Math(n1 + " " + n2 + " " + op1 + " " + n3 + " " + n4 + " " + op2 + " " + op3,combo[g-3],combo[g-2],combo[g-1],combo[g]);
								if(t1 == 24)
									break BreakLoop;
								int t2 = Math(n1 + " " + n2 + " " + op1 + " " + n3 + " " + op2 + " " + n4 + " " + op3,combo[g-3],combo[g-2],combo[g-1],combo[g]);
								if(t2 == 24)
									break BreakLoop;
								int t3 = Math(n1 + " " + n2 + " " + n3 + " " + n4 + " " + op1 + " " + op2 + " " + op3,combo[g-3],combo[g-2],combo[g-1],combo[g]);
								if(t3 == 24)
									break BreakLoop;
								int t4 = Math(n1 + " " + n2 + " " + n3 + " " + op1 + " " + n4 + " " + op2 + " " + op3,combo[g-3],combo[g-2],combo[g-1],combo[g]);
								if(t4 == 24)
									break BreakLoop;
								int t5 = Math(n1 + " " + n2 + " " + n3 + " " + op1+ " " + op2  + " " + n4 + " " + op3,combo[g-3],combo[g-2],combo[g-1],combo[g]);
								if(t5 == 24)
									break BreakLoop;
								}
							}
						}
					}
				}
			}
	}
	/**
	 * Finds all permutations of 2 numbers, note that there are only 2 combinations
	 * Returns the combinations into an array of strings
	 * @param c1
	 * @param c2
	 * @return
	 */
	private static String[] Permutate2(int c1, int c2)
	{
		String[] strings = new String[2]; // array that is returned return
		String str1 = Integer.toString(c1) + " " + Integer.toString(c2) + " "; // combination 1
		String str2 = Integer.toString(c2) + " " + Integer.toString(c1) + " "; // combination 2
		strings[0] = str1;
		strings[1] = str2;
		return strings;
	}
	/**
	 *  Finds all permutation of 3 numbers. There are 6 permutations per 3 numbers
	 *  returns the in an array of strings
	 * @param c1
	 * @param c2
	 * @param c3
	 * @return
	 */
	private static String[] Permutate3(int c1, int c2, int c3)
	{
		String[] strings = new String[6];
		String[] strs1 = Permutate2(c1,c2); // find the permutations between 2 of the three numbers
		String[] strs2 = Permutate2(c1,c3);	// does this three times with every combination of the of 2 numbers of the three numbers
		String[] strs3 = Permutate2(c3,c2); // there are 3 combinations total
		int k = 0; // continuous location of the array that is returned
		// all three for loops do the exact same thing with different set of two numbers
		// loop goes through the the string array and adds the number no present in the two card combination to the string and stores it into the main
		// array that will be returned
		for(String x : strs1)
		{
			x += Integer.toString(c3) + " "; // adds missing number to the end of the original string
			strings[k] = x; // stores the new string into the returned array
			k++;
		}
		
		for(String x : strs2)
		{
			
			x += Integer.toString(c2) + " ";
			strings[k] = x;
			k++;
		}
		
		for(String x : strs3)
		{
			
			x += Integer.toString(c1) + " ";
			strings[k] = x;
			k++;
		}
		return strings;
		
	}
	/**
	 * This method permutates  4 number which is done by calling permuate3 4 times and adding the 4th number to the end of the string
	 * @param c1
	 * @param c2
	 * @param c3
	 * @param c4
	 * @return
	 */
	private static int[] Permutate(int c1, int c2 , int c3 , int c4)
	{
		int[] permutations = new int[96]; // 24 combinations for four numbers, but i store each number separately in order
		String[] strings = new String[24]; // stores each string of four numbers into an array of strings
		String[] strs1 = Permutate3(c1,c2,c3); // gets permutations of three numbers
		String[] strs2 = Permutate3(c1,c2,c4);	// i call it four times because there are four combinations of 3 numbers being together in any order
		String[] strs3 = Permutate3(c1,c3,c4);
		String[] strs4 = Permutate3(c2,c3,c4);
		int k = 0; // continuous location of the array of strings being stored.
		
		// each loop does the exact same thing with different numbers so ill comment one 
		// loop goes through every string of 3 number permutations there are 6 permutations per 3 numbers
		// adds the number not included in the 3 number permutation to the end of the string, then stores it back into another the main array
		for(String x: strs1)
		{
			x += Integer.toString(c4) + " "; // adds the number not included in the permutations to every string in the 3 permutation array
			strings[k] = x; // stores the new string into the main array
			k++; // increases k every time a value is stored into the array
		}
		
		for(String x: strs2)
		{
			
			x += Integer.toString(c3) + " ";
			strings[k] = x;
			k++;
		}
		
		for(String x: strs3)
		{
			x += Integer.toString(c2) + " ";
			strings[k] = x;
			k++;
		}
		
		for(String x: strs4)
		{
			x +=  Integer.toString(c1) + " ";
			strings[k] = x;
			k++;
		}
		permutations = StringtoInt(strings);
		return permutations;
		
		
	}
	
	/**
	 * this method specifically works for the Permutations method which is why it is private
	 * Converts a string of numbers separated by spaces into separate integers and stores them in an int array of 96
	 * @param strings
	 * @return
	 */
	private static int[] StringtoInt(String[] strings)
	{
		int[] permutations = new int[96];
		int k = 0; // continues location for array.
		char next = 'x';
		for(String x : strings)
		{
			for(int i=0;i<x.length();i++)
			{
				char current = x.charAt(i);
				
				if(i < (x.length() -1) ) // length -1 because the last character is always a space so we know its not a number
				{
					next = x.charAt(i+1); // set char to next char
				}
				if(current == ' ')
				{
					//do nothing go to next loop
				}
				else if(next == ' ') //  next character is a space
				{ 
					int y = Character.getNumericValue(current);
					permutations[k] = y; // add current character to array because it is a single digit number
					k++;
				}
				else // next character is a number
				{
					
					String str = "" + current + "" + next;
					int y = Integer.parseInt(str);
					permutations[k] = y; // double digit number to array
					i++; // increase i because I don't need to test the next character anymore
					k++;
				}
			}
		}
		return permutations;
	}
	
	/**
	 * creates an array of all operator combinations but stores each character 1 by 1
	 * so if you want the 3 operator combo you need to extract 3 cells at a time.
	 * @return
	 */
	private static char[] OpCombo()
	{
		char[] ops = new char[192]; // stores combinations, note 64 *3 is 192
		int k = 0; // represents the continuous location of the array that needs to be filled
		for(int i1 = 0; i1<4;i1++) // each loop goes from 1 to 4 because there are only 4 operators
		{
			for(int i2 = 0; i2<4; i2++)
			{
				for(int i3 = 0; i3<4; i3++)
				{
					// each for loop increment represents an operation
					// the character the increment represents is stored in an array 1 by 1 
					// each iteration of the loop stores 3 variables in 3 different cells which represent 1 combination.
					if( i1 == 0)
					{
						ops[k] = '+';
					}
					else if( i1 == 1)
					{
						ops[k] = '-';
					}
					else if(i1 == 2)
					{
						ops[k] = '/';
					}
					else
					{
						ops[k] = '*';
					}
					
					k++;
					
					if( i2 == 0)
					{
						ops[k] = '+';
					}
					else if( i2 == 1)
					{
						ops[k] = '-';
					}
					else if(i2 == 2)
					{
						ops[k] = '/';
					}
					else
					{
						ops[k] = '*';
					}
					
					k++;
					
					if( i3 == 0)
					{
						ops[k] = '+';
					}
					else if( i3 == 1)
					{
						ops[k] = '-';
					}
					else if(i3 == 2)
					{
						ops[k] = '/';
					}
					else
					{
						ops[k] = '*';
					}
					k++;
				}
			}
		}
		return ops;
	}	
}
