package cloudsync.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Test {

	public static void main(String[] args) {
		
		String str = "{(cloud: < busy,122 >) (pc[p1]: < updated,1,10 >) (pc[p2]: < gotval,2,0 >) (pc[p3]: < idlep,3,323 >)}";
		
		// create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(str);

        // create a lexer that feeds off of input CharStream
        CloudSyncLexer lexer = new CloudSyncLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        CloudSyncParser parser = new CloudSyncParser(tokens);

        ParseTree tree = parser.start(); // begin parsing at init rule
//        System.out.println(tree.toStringTree(parser)); // print LISP-style tree
        
        MessageCloudSyncBaseVisitor<Object> visitor = new MessageCloudSyncBaseVisitor<Object>(parser);
        visitor.visit(tree);
	}

}
