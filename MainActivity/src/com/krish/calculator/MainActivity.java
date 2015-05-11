/*
 * ---------------------------------------------------------------------------------------------------
 * Simple Calculator
 * 
 * Developed by Krishanthan Krishnamoorthy
 * 
 * 
 * 
 * The simple calculator supports :
 * -> Addition, subtraction, multiplication and division
 * -> Follows BODMAS rule.(For example if you type : 2-3+5/10*5 the calculator will answer it's 4.)
 * -> Adding, subtracting and clearing from the memory
 * -> Delete and Clear
 * 
 * This program runs in the O(n) time complexity.
 * 
 * --------------------------------------------------------------------------------------------------
 * 
 * I am not sure that I fixed all the bugs and it will run 100% without bugs.
 * As far as, I considered some of the use cases and fixed the bugs
 * 
 * -> If user enters just operators
 * -> If user enters number and operator, and press answer button without enter next value or previous value
 * -> Click the answer button without entering anything
 * -> If user got the answer, user cannot delete it, rather he should clear it
 * -> The calculator will allow users to do the calculation from the answer or
 *    user will able to add that answer to the memory.
 * -> And some other bugs
 * 
 * 
 * 
 * ---------HOW IT WORKS(Simple Algorithm) -------------
 * 
 * If user enters numbers or operators, those will store in the Array List. Meanwhile, when it store it will look the operators and determine the priority
 * For example, if you have already inserted "*" and try to insert "/" it will swap the two operators in the Linked List. So in this program top priority operator
 * will stay in the last of list. Once the user hits the answer button, it will go through the operators linked list and pop the last value then visit to the Array List.
 * In the next step, it will look previous value and next value and perform the arithmetic operation meanwhile it will replace the already visited value or operator
 * with "@". It will help the program to understand that it already visited or evaluated. Finally it will put that in the answer queue, then add them all.
 * 
 * In this program,
 * 
 * 6-5*6 =
 *      First it will look the operator(get from the operator Linked List) which is "*" then it will look the previous value "5" but it has negative sign so it will
 *      look that too and make that (-5). And next value which is 6, so now it will perform the operation => -5 * 6 = -30 then put that into answer queue. 
 *      Now it will replace these values with @. Finally there is a number left and put that in the answer queue. So the answer queue will have -30,6. So add together
 *      then you will get -24. At last it will clear all stacks and junks.
 * 
 * 
 * */




package com.krish.calculator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends Activity{

	EditText txt_display;
	EditText txt_mem;
	String AlgOp;
	ArrayList<String> _inputKeys = new ArrayList<String>();
	
	LinkedList<Integer> AlgOP_Queue=new LinkedList<Integer>();
	LinkedList<Double> Answer_Queue = new LinkedList<Double>();
	final Context context = this;
	
	Iterator iterator;
	
	double number1;
	double number2;
	double _answer;
	double finalAnswer = 0;
	double decimalNumber;
	double memory = 0;
	
	int operator1;
	int operator2;
	int symbol_value = 0;
	int key = 0;

	int sizeOfNum;
	double divideNum;
	boolean isDecimal;
	boolean isNegative;
	boolean isEqual;
	boolean isMem;
	boolean isSet;
	boolean isAfterAnswer;
	boolean error;
	
	String displayText;
	String _input;
	StringBuffer numbers = new StringBuffer();
	String negative;
	String getNumbers;
	String beforeDec;
	String getSymbol = null;
	String previous_value = "0";
	String next_value;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txt_display = (EditText)findViewById(R.id.txt_display);
		txt_mem = (EditText)findViewById(R.id.txt_mem);
		//Disable the android keyboard
		txt_display.setInputType(0);
		txt_mem.setInputType(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@SuppressLint("NewApi")
	public void _getInputKeys(View v) 
	{
	   _input = v.getTag().toString();
       String keys = _input.toString();
       displayText = txt_display.getText().toString();
       
	   //Display all the inputs to the screen
	   if(displayText.equals("0"))
	   {
		   if(keys.toString().equals(("neg")))
		   {
			   //Negative number
			   isNegative = true;
			   txt_display.setText("-");
		   }
		   else if(!keys.toString().equals("mplus") && !keys.toString().equals("mminus") && !keys.toString().equals("mc") && !keys.toString().equals("del"))
		   {
			   txt_display.setText(keys.toString());
		   }
	   }
	   else
	   {
		   if(keys.toString().equals(("neg")))
		   {
			   //Negative number
			   isNegative = true;
			   txt_display.append("-");
		   } 
		   else if(keys.toString().equals("=") || keys.toString().equals("clear") || keys.toString().equals("del") || keys.toString().equals("mplus") || keys.toString().equals("mr") || keys.toString().equals("mminus") || keys.toString().equals("mc"))
		   {
		    
		   }
		   else
		   {
			   txt_display.append(keys.toString());
		   }
		}
	   
	   //Push the values and operators to the list of arrays
	   
	   
	   //Analize the operators and push that into queue according to the BODMAS rules
	   // Divide - 1, Multiply - 2, Addition - 3, Subtract - 4
	   
	   if(keys.equals("/") || keys.equals("*") || keys.equals("+") || keys.equals("-"))
	   { 
		   if(isDecimal)
		   {
			   String dec = numbers.toString();
			   sizeOfNum = dec.length();
			   divideNum = Math.pow(10,sizeOfNum );
			   double addDec = Integer.parseInt(dec) / divideNum;
			   decimalNumber = Double.parseDouble(beforeDec) + addDec;
			   isDecimal = false;
			    
			    if(isNegative)
				{
				   double insertNeg = (-1) * decimalNumber;
				   _inputKeys.add(Double.toString(insertNeg));
				   numbers.delete(0, numbers.length());
				   isNegative = false;
				}
			    else 
			    {
			    	_inputKeys.add(Double.toString(decimalNumber));
			    	numbers.delete(0, numbers.length());
			    }
			    
			    isSet = false;
		   }
		   else
		   {
			   //If the number is negative, apply minus sign and push into the array
			   //list for calculation
			   if(isNegative)
			   {
				   getNumbers = numbers.toString();
				   double insertNeg = (-1) * Double.parseDouble(getNumbers);
				   _inputKeys.add(Double.toString(insertNeg));
				   numbers.delete(0, numbers.length());
				   isNegative = false;
			   }
			   else 
			   {
				   getNumbers = numbers.toString();
				   if(!getNumbers.equals("0") && !getNumbers.isEmpty())
				   {
					   _inputKeys.add(getNumbers.toString());
				   }
			       numbers.delete(0, numbers.length());
			   }
		    }
		   
		    //After the final answer or Memory retrive, if user wants to do the calculation again then, 
		   //add the final answer to the input key array list
		   if(isSet)
		   {
			   if(isMem){_inputKeys.add(Double.toString(memory));}else{_inputKeys.add(Double.toString(finalAnswer));}
			   
			   finalAnswer = 0;
			   isSet = false;
			   isMem = false;
			   isAfterAnswer = true;
		   }else
		   {
		   }
		   
		   _inputKeys.add(keys.toString());
		   
		   // Push onto the Queue
		   addToQueue( _inputKeys.size() - 1);
	   }
	   else if(keys.equals("."))
	   {
		   //Make the number decimal
		   
		   //Get the first part of the number
		   beforeDec = numbers.toString();
		   isDecimal = true;
		   numbers.delete(0, numbers.length()); 
		   
	   }
	   else if(keys.equals("neg"))
	   {
		   //make the number negative or positive
		   isNegative = true;
	   }
	   else if(keys.toString().equals("="))
	   {
		   if(isDecimal)
		   {
			   String dec = numbers.toString();
			   sizeOfNum = dec.length();
			   divideNum = Math.pow(10,sizeOfNum );
			   double addDec = Integer.parseInt(dec) / divideNum;
			   decimalNumber = Double.parseDouble(beforeDec) + addDec;
			    isDecimal = false;
			    
			    if(isNegative)
				{
				  double insertNeg = (-1) * decimalNumber;
				  _inputKeys.add(Double.toString(insertNeg));
				   numbers.delete(0, numbers.length());
				   isNegative = false;
				 }
			     else
				 {
			    	_inputKeys.add(Double.toString(decimalNumber));
					 numbers.delete(0, numbers.length());
				 }
		   }
		   else
		   {
			   if(isNegative)
			   {
				  getNumbers = numbers.toString();
				  double insertNeg = (-1) * Double.parseDouble(getNumbers);
				  _inputKeys.add(Double.toString(insertNeg));
				   numbers.delete(0, numbers.length());
				   isNegative = false;
			   }
			   
			   String getNumbers = numbers.toString();
			   _inputKeys.add(getNumbers.toString());
			   int len = numbers.length();
			   numbers.delete(0, len); 
		   }
		   
		   isEqual = true;
		   
		   //EVALUATION
		   evaluator();
	   }
	   else if(keys.equals("clear"))
	   {
		   clearAll();
	   }
	   else if(keys.toString().equals("mplus"))
	   {
			   //Adding to the memory
			   if(isDecimal)
			   {
				   String dec = numbers.toString();
				   sizeOfNum = dec.length();
				   divideNum = Math.pow(10,sizeOfNum );
				   double addDec = Integer.parseInt(dec) / divideNum;
				   decimalNumber = Double.parseDouble(beforeDec) + addDec;
				   isDecimal = false;
				   
				    if(isNegative)
					{
					   double insertNeg = (-1) * decimalNumber;
					   memory =  memory + insertNeg;
					   numbers.delete(0, numbers.length());
					   isNegative = false;
					}
				    else 
				    {
				    	memory =  memory + decimalNumber;
				    	numbers.delete(0, numbers.length());
				    }
			   }
			   else if(isNegative)
				{
				   double insertNeg = (-1) * decimalNumber;
				   memory =  memory + insertNeg;
				   numbers.delete(0, numbers.length());
				   isNegative = false;
				}
			   else if(isEqual)
			   {
				 //After the user got the answer
				   isMem = true;
				   memory =  memory + finalAnswer;
			   }
			   else
			   {
				   memory =  memory + Double.parseDouble(numbers.toString());
			   }
			   
			   txt_mem.setText("M=" + Double.toString(memory));
	   }
	   else if(keys.toString().equals("mminus"))
	   {
			   //Subtracting from the memory
			   if(isDecimal)
			   {
				   String dec = numbers.toString();
				   sizeOfNum = dec.length();
				   divideNum = Math.pow(10,sizeOfNum );
				   double addDec = Integer.parseInt(dec) / divideNum;
				   decimalNumber = Double.parseDouble(beforeDec) + addDec;
				   isDecimal = false;
				   
				    if(isNegative)
					{
					   double insertNeg = (-1) * decimalNumber;
					   memory =  memory - insertNeg;
					   numbers.delete(0, numbers.length());
					   isNegative = false;
					}
				    else 
				    {
				    	memory =  memory - decimalNumber;
				    	numbers.delete(0, numbers.length());
				    }
			   }
			   else if(isNegative)
				{
				   double insertNeg = (-1) * decimalNumber;
				   memory =  memory + insertNeg;
				   numbers.delete(0, numbers.length());
				   isNegative = false;
				}
			   else if(isEqual)
			   {
				 //After the user got the answer
				   isMem = true;
				   memory =  memory - finalAnswer;
			   }
			   else
			   {
				   memory =  memory - Double.parseDouble(numbers.toString());
			   }
			   
			   txt_mem.setText("M=" + Double.toString(memory));
	   }
	   else if(keys.toString().equals("mr"))
	   {
		   //Retrive from the memory, show in the display
		   
		   txt_display.setText(Double.toString(memory));
		   isSet = true;
		   isMem = true;
	   }
	   else if(keys.toString().equals("mc"))
	   {
		   //Clear the memory
		   memory = 0;
		   txt_mem.setText("");
	   }
	   else if(keys.toString().equals("del"))
	   {
		   //Delete character one by one
		   
		   if((displayText.length() > 0) && !isSet)
		   {
			   String newText = displayText.substring(0, displayText.length()-1);
			   
			   //Also remove the data
			   
			   if(!AlgOP_Queue.isEmpty() && !_inputKeys.isEmpty())
			   {
				   _inputKeys.remove( _inputKeys.size() - 1  );
				   AlgOP_Queue.pollLast(); 
			   }
			   
			   if(numbers.length() > 0)
			   {
				   numbers.deleteCharAt(numbers.length() - 1);
			   }
			   txt_display.setText(newText);
		   }
	   }
	   else
	   {
		   //Numbers
		   if(numbers.length()==0)
		   {
			   numbers.insert(0,keys.toString());
		   }else
		   {
			   numbers.append(keys.toString());
		   }
		   
		   if(isAfterAnswer)
		   {
			   getNumbers = numbers.toString();
			   if(!getNumbers.equals("0") && !getNumbers.isEmpty())
			   {
				   _inputKeys.add(getNumbers.toString());
			   }
		       numbers.delete(0, numbers.length());
		       isAfterAnswer = false;
		   }
	   }
	}
	
	public void addToQueue(int index_AlgOp)
	{
		
		//Following the BODMAS rules
		
		if(AlgOP_Queue.isEmpty())
		{
			AlgOP_Queue.add(index_AlgOp);
		}
		else
		{
			operator1 = priorityLevel( _inputKeys.get( AlgOP_Queue.getLast() ) );
			operator2 = priorityLevel( _inputKeys.get( index_AlgOp ) );
			
			//Swap the operators
			if( operator1 > operator2  )
			{
				/*
				 * Usecase : If * in the queue and insert +, then compare the operators and swap it
				 * */
				
				swapOperators(index_AlgOp);
			}
			else
			{
				AlgOP_Queue.add(index_AlgOp);
			}
		}
	}
	
	public int priorityLevel(String AlgOp)
	{
		int priority = 0;
		
		if(AlgOp.equals("/"))
		{
			priority = 4;
		}
		else if(AlgOp.equals("*"))
		{
			priority = 3;
		}
		else if(AlgOp.equals("+"))
		{
			priority = 2;
		}
		else
		{
			priority = 1;
		}
		
		return priority;
		
	}
	
	@SuppressLint("NewApi")
	public void evaluator()
	{	
		int i;
		String before_prev;
		//Iterate the queue until its empty
		for(i=0;AlgOP_Queue.size()>0;i++)
		{
			
				//Pop the stack and get the value
				symbol_value = AlgOP_Queue.pollLast();
				getSymbol = _inputKeys.get(symbol_value);
				//replace with @
				_inputKeys.set((symbol_value), "@");
			try 
			{
				//get the previous value and next value
				previous_value = _inputKeys.get(symbol_value - 1);
			}
			catch(Exception e)
			{}
			next_value = _inputKeys.get(symbol_value + 1);
			
			if(next_value.isEmpty())
			{
				next_value = "0";
			}

			if(getSymbol=="@")
			{
				
				if(!previous_value.equals("@") || !next_value.equals("@"))
				{
				
					//Look the before previous value(if negative applies)
					try {
						before_prev = _inputKeys.get(symbol_value - 2);
						if(before_prev.equals("-"))
						{
							previous_value = Double.toString(Double.parseDouble(previous_value) * (-1));
							//replace with @
							_inputKeys.set((symbol_value - 2), "@");
						}
						
					}catch(Exception e)
					{}
					
					//Add to the answer queue
					addToAnswerQueue(Double.parseDouble(previous_value));
			   }
			}
			else
			{
				if(previous_value=="@")
				{
					if(next_value!="@" && !next_value.isEmpty())
					{
						answerCompute(getSymbol,Double.parseDouble(next_value));
						//replace by @
						_inputKeys.set((symbol_value + 1), "@");
					}else{}
				}else
				{
					//Get the previous value
					//Look the before previous value(if negative applies)
					try {
						before_prev = _inputKeys.get(symbol_value - 2);
						if(before_prev.equals("-"))
						{
							previous_value = Double.toString(Double.parseDouble(previous_value) * (-1));
							//replace with @
							_inputKeys.set((symbol_value - 2), "@");
						}
						
					}catch(Exception e)
					{}
					number1 = Double.parseDouble(previous_value);
					try {
					    //replace that by @
						_inputKeys.set((symbol_value - 1), "@");
					}catch(Exception e)
					{}
				}
				
			    //Check in the next index
				if(previous_value!="@")
				{
					if(next_value=="@")
					{
						answerCompute(getSymbol,Double.parseDouble(previous_value));
						try {
							//replace by @
							_inputKeys.set((symbol_value - 1), "@");
						}catch(Exception e)
						{}
					}
					else if(next_value.isEmpty())
					{
						error = true;
					}
					else
					{
						number2 = Double.parseDouble(next_value);
					    //replace that by @
						try {
							_inputKeys.set((symbol_value + 1), "@");
						}catch(Exception e)
						{}
						
						//Do the calculation
						if(getSymbol.equals("/"))
						{
							_answer = number1 / number2;
						}
						else if(getSymbol.equals("*"))
						{
							_answer = number1 * number2;
						}
						else if(getSymbol.equals("+"))
						{
							_answer = number1 + number2;
						}
						else if(getSymbol.equals("-"))
						{
							_answer = number1 - number2;
						}
						else 
						{}
							
						//Push to the answer queue
						addToAnswerQueue(_answer);
					}
				}
				else
				{
				}
			}	
		}
		if(!error)
		{
			//Final computation
			finalComputation();
		}
	}
	
	@SuppressLint("NewApi")
	public void swapOperators(int Op)
	{
		/*(1) Pop the head element*/
		
		int poped_op =  AlgOP_Queue.pollLast();
		
		/*(2) Insert the element*/
		
		AlgOP_Queue.add(Op);
		
		/*(3) Now push the poped value*/
		
		AlgOP_Queue.add(poped_op);
	}
	public void addToAnswerQueue(double number)
	{
		Answer_Queue.add(number);
	}
	
	@SuppressLint("NewApi")
	public void answerCompute(String symbol,double number)
	{
		double answer = 0;
		//pop the head
		double headNumber = Answer_Queue.pollLast();
		if(symbol.equals("*"))
		{
			answer = headNumber * number;
		}else if(symbol.equals("/"))
		{
			answer = headNumber / number;
		}
		else if(symbol.equals("+"))
		{
			answer = headNumber + number;
		}else if(symbol.equals("-"))
		{
			answer = headNumber - number;
		}else
		{
			
		}
		
		//push back to the answer queue
		Answer_Queue.add(answer);
	}
	
	public void clearAll()
	{
		//Clear all the inputs
		_inputKeys.clear();
		//Clear operational List
		AlgOP_Queue.clear();
		//Clear answer list
		Answer_Queue.clear();
		//Set the text to "0"(reset)
		txt_display.setText("0");
	    finalAnswer = 0;
		isSet = false;
		isEqual = false;
		numbers.delete(0, numbers.length());
	}
	
	public void finalComputation()
	{	
		double number;
		iterator = Answer_Queue.iterator();
		while (iterator.hasNext())
		{
			number = (Double) iterator.next();
			finalAnswer = finalAnswer + number;
		}
		
		//Display the Answer
		txt_display.setText(Double.toString(finalAnswer));
		
		//Now clear all the data structures
		
		//Clear all the inputs
		_inputKeys.clear();
	    //Clear operational List
		AlgOP_Queue.clear();
		//Clear answer list
	    Answer_Queue.clear();
	    isSet = true;
	}
}
