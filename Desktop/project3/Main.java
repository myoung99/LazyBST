import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner in;
		if(args.length != 2) {
			System.out.println("Error: Incorrect Arguments: " + Arrays.toString(args));
			System.exit(0);
		}
		
		try {
			//read in the files
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
			
			File outputFile =   new File(args[1]);
			PrintWriter out = new PrintWriter(outputFile);
			
			LazyBinarySearchTree LBST = new LazyBinarySearchTree();
			int lineNum  = 0;
			int key = 0;
			String input = "";
			String operation = "";
			
			whileloop:
				while(in.hasNext()) {
					lineNum++;
					operation = in.next();
					
					switch(operation) {
					case "Insert:":
						try {
							key = in.nextInt();
							Boolean newNode = LBST.insert(key);
							if(newNode == false) {
							//	System.out.println("False - NO INSERTION");
								out.println("False");
							} else {
							//	System.out.println("True - INSERTED");
								out.println("True");
							}
						} catch (Exception e) {
							//System.out.println("ERROR-INSERTION");
							out.println("Error in insert: IllegalArgumentException raised");
						}
						break;
					case "Delete:":
						try {
							key = in.nextInt();
							Boolean deleteNode = LBST.delete(key);
							if(deleteNode == false) {
							//	System.out.println("False-Deletion");
								out.println("False");
							} else {
							//	System.out.println("True-Deletion");
								out.println("True");
							}
						} catch (Exception e) {
							//System.out.println("ERROR-DELETION");
							out.println("ERROR");
						}
						break;
					case "PrintTree":
						try {
							//input = in.nextLine();
							String output = LBST.toString();
							//System.out.println(output);
							out.println(output);
						} catch(Exception e) {
							//System.out.println("ERROR-PRINTING");
							out.println("ERROR");
						}
						break;
					case "Size":
						int size = LBST.size();
						//System.out.println("Size: " + size);
						out.println(size);
						break;
					case "Contains:":
						try {
							key = in.nextInt();
							boolean isThere = LBST.contains(key);
							if(isThere == true){
								out.println("True");
						//		System.out.println("True-CONTAINS");
							} else {
								out.println("False");
						//		System.out.println("False-CONTAINS");
							}
						} catch (Exception e){
						//	System.out.println("ERROR-CONTAINS");
							out.println("ERROR");
						}
						break;
					case "FindMin":
						int min = LBST.findMin();
						//System.out.println("Min: " + min);
						out.println(min);
						break;
					case "FindMax":
						int max = LBST.findMax();
						//System.out.println("Max: "+max);
						out.println(max);
						break;
					case "Height":
						int height = LBST.height();
						//System.out.println("Height: " + height);
						out.println(height);
						break;
					default:
						out.println("Error in line: " + operation);
					}
				}
			in.close();
			out.close();
		} catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
			
		}
		
	}

}
