package openS0311;
 

public class kuir {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args[0].equals("-c")) {
			makeCollection mkCol = new makeCollection(args[1]);
		}
		else if(args[0].equals("-k")) {
			makeKeyword mkKey = new makeKeyword(args[1]);
		}
		else if(args[0].equals("-i")) {
			indexer mkMap = new indexer(args[1]);
		}
	}
}
