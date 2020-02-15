package cloudsync.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import cloudsync.jpf.CloudSyncConfiguration;

public class CloudSyncMessageParser {
	
	public static CloudSyncConfiguration parse(String str) {
		// create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(str);

        // create a lexer that feeds off of input CharStream
        CloudSyncLexer lexer = new CloudSyncLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        CloudSyncParser parser = new CloudSyncParser(tokens);

        ParseTree tree = parser.start(); // begin parsing at init rule
        
        MessageCloudSyncBaseVisitor<CloudSyncConfiguration> visitor = new MessageCloudSyncBaseVisitor<CloudSyncConfiguration>(parser);
        
        CloudSyncConfiguration config = visitor.visit(tree);
        
        return config;
	}
}
