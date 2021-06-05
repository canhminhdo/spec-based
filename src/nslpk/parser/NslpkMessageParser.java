package nslpk.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class NslpkMessageParser {
	
	public static MessageOC parse(String str) {
		// create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(str);

        // create a lexer that feeds off of input CharStream
        NslpkLexer lexer = new NslpkLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        NslpkParser parser = new NslpkParser(tokens);

        ParseTree tree = parser.start(); // begin parsing at init rule
        
        MessageNslpkBaseVisitor<MessageOC> visitor = new MessageNslpkBaseVisitor<MessageOC>(parser);
        
        MessageOC oc = visitor.visit(tree);
        
        return oc;
	}
}
