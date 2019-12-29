package nspk.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Test {

	public static void main(String[] args) {
		String str = "{nw: (m1(p, p, intrdr, c1(intrdr, n(p, intrdr, r1), p)) m1(intrdr, p, q, c1(q, n(p, intrdr, r1), p)) m2(q, q, p, c2(p, n(p, intrdr, r1), n(q, p, r2), intrdr)) m2(intrdr, intrdr, p, c2(p, n(p, intrdr, r1), n(q, p, r2), intrdr)) m3(p, p, intrdr, c3(intrdr, n(q, p, r2)))) rand: r1 r2 nonces: (n(p, intrdr, r1) n(q, p, r2)) prins: (p q intrdr) rw_p: (Challenge Confirmation) rw_q: (Confirmation) rw_intrdr: emp}";
//		String str = "nw: emp rand: r1 r2 nonces: (n(p, intrdr, r1) n(q, p, r2)) prins: (p q intrdr)";
		
		// create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(str);

        // create a lexer that feeds off of input CharStream
        NspkLexer lexer = new NspkLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        NspkParser parser = new NspkParser(tokens);

        ParseTree tree = parser.start(); // begin parsing at init rule
//        System.out.println(tree.toStringTree(parser)); // print LISP-style tree
        
        MessageNspkBaseVisitor<MessageOC> visitor = new MessageNspkBaseVisitor<MessageOC>(parser);
        
        MessageOC oc = visitor.visit(tree);
        
        oc.debug();
	}

}
