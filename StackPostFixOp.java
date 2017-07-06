


	/**
	 * Implements the Stack interface which forces every stack to contain certain methods that make each implementation a true stack
	 * Uses an array to hold the values in a stack, but will only add a value to the end of an array which acts as the top of the stack
	 * @author Patrick
	 *
	 * @param <E>
	 */
	public class StackPostFixOp<E> implements Stack<E>
	{
		private E[] S; //array of specified element
		private int Size = 0; // size of the array
		private int Top = -1; // top will always be Size -1 because the array starts at 0
		private int Capacity; // maximum capacity of array 
		
		@SuppressWarnings("unchecked")
		public StackPostFixOp(int cap)
		{
			Capacity = cap; // sets capacity to the member variable
			S = (E[]) new Object[cap]; // creates a new array of the element
		}

		@Override
		public int Size() // returns the size of the array
		{
			
			return Size;
		}

		@Override
		public boolean isEmpty() // tells whether the array is empty or not
		{
			
			return Size == 0; 
		}

		@Override
		public E top() // returns the top element which is the element last pushed into the array
		{
			return S[Top];
		}
		
		/**
		 * Pushes a specified element into the array if the array is not full
		 */
		@Override
		public void push(E element) throws IndexOutOfBoundsException
		{
			if ( Top + 1 == Capacity) // if the array is full no elements can be put in
			{
				throw new IndexOutOfBoundsException("Reached maximum capacity of " + Capacity);
			}
				
			Top++; // increase top by 1 because a new element is put in
			Size++; // increase size
			S[Top] = element; // insert new element to the next open cell of the array
		}
		/**
		 * Removes and returns the last element pushed into the array
		 */
		@Override
		public E pop() 
		{
			if(isEmpty()) // if the stack is empty no element to be popped
			{
				return null;
			}
			
			E temp = S[Top]; // sets temporary value to the element that was last pushed
			S[Top] = null; // sets the cell in the array that contained that element to null since it is being removed
			Top--; // top is one less now
			Size--; // size is one less
			return temp;
		}

		

}


