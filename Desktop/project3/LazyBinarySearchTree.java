import java.util.ArrayList;

public class LazyBinarySearchTree {
	
	private TreeNode root; //the starting point of the tree
	boolean isInserted = false;
	private int size; //I'll be updating the size as I go
	String str = ""; //this will be used in the toString method
	
	public LazyBinarySearchTree() {
		root = null; //Initialize the size  to 0 and the root to null 
		size = 0;
		
	}
	
	private class TreeNode {
		
		public TreeNode leftChild;
		public TreeNode rightChild;
		public int key;
		public boolean deleted;
		
		//each node needs these variables
		public TreeNode(TreeNode lc, TreeNode rc, int num, boolean d) {
			leftChild = lc;
			rightChild = rc;
			key = num;
			deleted = d;
			
		}
		
		//getters and setters for all of the variables
		public TreeNode getLeftChild() {
			return leftChild;
		}
		public void setLeftChild(TreeNode leftChild) {
			this.leftChild = leftChild;
		}
		public TreeNode getRightChild() {
			return rightChild;
		}
		public void setRightChild(TreeNode rightChild) {
			this.rightChild = rightChild;
		}
		public int getKey() {
			return key;
		}
		public void setKey(int key) {
			this.key = key;
		}
		public boolean isDeleted() {
			return deleted;
		}
		public void setDeleted(boolean deleted) {
			this.deleted = deleted;
		}
		
		
	}

	/*
	 * insert a leaf into the tree if the key is between 1-99.
	 * if there is a duplicate don't insert
	 * if its a duplicate but its deleted then undelete
	 * return true or false if it was inserted or not
	 */
	public boolean insert(int i) throws IllegalArgumentException{
		if(i < 1 || i > 99) {  //range is 1->99
			throw new IllegalArgumentException("IllegalArgumentException raise"); //out of range error
		}

		if(root == null) {
			root = new TreeNode(null, null, i, false); //if there is no root... insert to the root
			isInserted = true; //it was inserted
			size++; //increase the size
			return isInserted;
		}
		
		TreeNode temp = root;
		TreeNode keepPrev = root; //keep track of the parent
		
		//boolean isLeft = true;
		while(temp != null) {
			if(temp.getKey() < i) {
				temp = temp.getRightChild();
				if(temp == null) {
					//insert the new node at this spot
					TreeNode newRightChild = new TreeNode(null,null,i,false);
					isInserted = true;
					//need to connect the new node to the parent
					keepPrev.setRightChild(newRightChild);
					size++;
					return isInserted;
				}  else {
					keepPrev = keepPrev.getRightChild();
				}
			} else if (temp.getKey() > i) {
				temp = temp.getLeftChild();
				if(temp == null){ //insert the new node at this spots
					TreeNode newLeftChild = new TreeNode(null, null, i, false);
					isInserted = true;
					//need to connect the new node to the parent
					keepPrev.setLeftChild(newLeftChild);
					size++;
					return isInserted;
				} else {
					keepPrev = keepPrev.getLeftChild();
				}
			} else if(temp.getKey() == i) { //if inserting it would create a duplicate... do not insert
				isInserted = false;
				return isInserted;
			} else if(temp.deleted == true && temp.getKey() == i) {
				temp.setDeleted(false);
				isInserted = true; //this will "undelete" the one that is already there 
				//System.out.println("Undelete");
				return isInserted;
			}
			
		}
		return isInserted;
	}
	

	
	 /*
	  * this will not physically delete the element but will instead mark it as deleted
	  * if it is already marked as deleted it won't do anything
	  */
	 
	public boolean delete(int i) throws IllegalArgumentException{
		if(i < 1 || i > 99) { //make sure it's in range
			throw new IllegalArgumentException("IllegalArgumentException raise");
		}
		
		boolean isDeleted = true;
		TreeNode temp = root;
		
		if(root == null) { //if the root is null, there is nothing to deleted
			isDeleted = false;
			return isDeleted;
		} else if(root.getKey() == i) { //if we want to delete the root, we can do that first and avoid the loop
			root.setDeleted(true);
			isDeleted = true;
			return isDeleted;
		}
		
		/*
		 * iterate through the tree until we find the one we are looking for
		 * once we find it we have to make sure it's not already deleted
		 * 		if it is already deleted, return false
		 * 		if it is not already deleted, delete it, and return true
		 */
		while(temp != null) { //iterate through the tree
			if(temp.getKey() < i) { //go to the right side of the tree
				if(temp.getRightChild() == null) { //if there is nothing on the right side
					isDeleted = false; //false because it is not in the tree
					return isDeleted;
				} else if(temp.getRightChild() != null) { //if there is more on the right
					temp = temp.getRightChild(); //get the right child
					if(temp.getKey() == i && temp.deleted == true) { //if it is the one but it is deleted
						isDeleted = false; //return false as you cannot "delete it again"
						return isDeleted;
					} else if(temp.getKey() == i) { //if it just matches and is not already deleted
						temp.setDeleted(true); //delete it 
						isDeleted = true; //return true
						return isDeleted;
					}
				}
			} else if(temp.getKey() > i) { // go to the left side of the tree
				if(temp.getLeftChild() == null) { //if there is nothing on the left side
					isDeleted = false; //return false is it cannot be in the tree
					return isDeleted;
				} else if(temp.getLeftChild() != null) { //if there is something on the left
					temp = temp.getLeftChild();
					if(temp.getKey() == i && temp.deleted == true) { //see if it matches and if its already deleted
						isDeleted = false; //return false
						return isDeleted;
					} else if(temp.getKey() == i) { //if it just matches and is not deleted
						temp.setDeleted(true); //delete it
						isDeleted = true;//return true
						return isDeleted;
					}
				}
			}
		}
		return isDeleted;
	}
	
	/*
	 * preorder traversal through the tree- return root,left,right pattern
	 * decided to use a private helper method to take in the root and create the string recursively 
	 */
	private String rtnString(TreeNode root) {
		
		if(root == null) { //if the root is empty
			return null; //return null
		} else if(root != null) { //if the root is not null
			if(root.deleted == true) { //if it deleted need to add the *
				str = str + "*" + root.getKey() + " ";
			} else if(root.deleted == false){ //if it is not deleted just concatenate the string
				str = str + root.getKey() + " ";
			}
		}
		
		rtnString(root.getLeftChild()); //recursively call to the right and left
		rtnString(root.getRightChild());
		return str;
		
		
	}
	
	/*
	 * calls private helper method
	 */
	public String toString() {
		str = ""; //clear the string out from before
		return rtnString(root); //call the rtnString method with the root passed through
	}
	
	//I continuously update the size as each element is added so here all we have to do is return it
	public int size() {
		return size; //return size
	}
	
	/*
	 * decided to create a private helper method so I can pass in both the root and the int
	 */
	private boolean contain(TreeNode root, int i) {
		/*if(i < 1 || i > 99) { // if i is out of range, return this error
			throw new IllegalArgumentException("Error in contain: IllegalArgumentException");
		}*/
		
		boolean isThere = false;
		if(root == null) {
			isThere = false;  //if the root is null it contains nothing so return false
			return false;
		}
		
		if(root.getKey() > i) { // go to the left side of tree
			if(root.getLeftChild() != null) {
				isThere = contain(root.getLeftChild(), i); //Recursively call the method again to go through the left side
			} else {
				isThere = false;
			}
		} else if(root.getKey() < i) { //go to the right side of tree
			if(root.getRightChild() != null) {
				isThere = contain(root.getRightChild(),i); //Recursively call the method again to go through the right side
			} else {
				isThere = false;
			}
		} else { //in this case the root would equal i as it is not < or >
			if(root.isDeleted() == true) {
				isThere = false;
			} else {
				isThere = true;
			}
		}
		return isThere; 
	}
	
	public boolean contains(int i) throws IllegalArgumentException{
		if(i < 1 || i > 99) { //if i is out of range there is no need to call the other method
			throw new IllegalArgumentException("IllegalArgumentException raised");
		} else {
			return contain(root,i);
		}
	}
	
	
	
	/*
	 * iterate through the entire left hand side of the tree until the last one
	 */
	public int findMin() {
		TreeNode temp = root;
		TreeNode parent = root;
		if(root == null) {
			return -1; //if nothing exists, return -1
		} else if(root.getLeftChild() == null) { //if there is nothing to the left
			return root.getKey(); //return the root
		}
		while(temp != null) {
			parent = temp;
			temp = temp.getLeftChild();
			if(temp.getLeftChild() == null && temp.deleted == false) {
				return temp.getKey();
			} else if (temp.getLeftChild() == null && temp.deleted == true) { //ignore deleted ones
				return parent.getKey();
			}
			parent = temp;
		}
		return temp.getKey();
		
	}
	
	/*
	 * iterate through the entire right side of the tree until the last one 
	 */
	public int findMax() {
		TreeNode temp = root;
		TreeNode parent = root;
		if(root == null) {
			return -1; //if nothing exists, return -1
		}else if(root.getRightChild() == null) { //if there is nothing to the  right
			return root.getKey(); //return the root 
		}
		while(temp != null) {
			parent = temp;
			temp = temp.getRightChild();
			if(temp.getRightChild() == null && temp.deleted == false) {
				return temp.getKey();
			} else if (temp.getRightChild() == null && temp.deleted == true) { //ignore deleted ones
				return parent.getKey();
			}
			parent = temp;
		}
		return temp.getKey();
	}
	
	/*
	 * I used recursion to iterate through the tree and get the height from each side
	 */
	private int pHeight(TreeNode root) {
		if(root == null) {
			return -1; //if there is nothing there return -1
		}
		int heightLeft = pHeight(root.getLeftChild());
		//System.out.println(heightLeft);
		int heightRight = pHeight(root.getRightChild());
		//System.out.println(heightRight);
		
		if(heightLeft < heightRight) {
			return heightRight + 1;
		} else {
			return heightLeft + 1;
		}
	}
	
	/*
	 * calls the private helper method and returns the height of the tree
	 */
	public int height() {
			return pHeight(root); //otherwise, call the private helper method
	}
	
}
