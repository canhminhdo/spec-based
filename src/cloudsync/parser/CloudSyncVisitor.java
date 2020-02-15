package cloudsync.parser;
// Generated from CloudSync.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CloudSyncParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CloudSyncVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CloudSyncParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(CloudSyncParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link CloudSyncParser#oc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOc(CloudSyncParser.OcContext ctx);
	/**
	 * Visit a parse tree produced by {@link CloudSyncParser#cloud}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloud(CloudSyncParser.CloudContext ctx);
	/**
	 * Visit a parse tree produced by {@link CloudSyncParser#pc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPc(CloudSyncParser.PcContext ctx);
}