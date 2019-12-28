package nspk.parser;
// Generated from Nspk.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link NspkParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface NspkVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link NspkParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(NspkParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by the {@code networkOC}
	 * labeled alternative in {@link NspkParser#oc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNetworkOC(NspkParser.NetworkOCContext ctx);
	/**
	 * Visit a parse tree produced by the {@code randOC}
	 * labeled alternative in {@link NspkParser#oc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRandOC(NspkParser.RandOCContext ctx);
	/**
	 * Visit a parse tree produced by the {@code noncesOC}
	 * labeled alternative in {@link NspkParser#oc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoncesOC(NspkParser.NoncesOCContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prinsOC}
	 * labeled alternative in {@link NspkParser#oc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrinsOC(NspkParser.PrinsOCContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rwOC}
	 * labeled alternative in {@link NspkParser#oc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRwOC(NspkParser.RwOCContext ctx);
	/**
	 * Visit a parse tree produced by {@link NspkParser#rw}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRw(NspkParser.RwContext ctx);
	/**
	 * Visit a parse tree produced by {@link NspkParser#rulelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRulelist(NspkParser.RulelistContext ctx);
	/**
	 * Visit a parse tree produced by {@link NspkParser#message}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMessage(NspkParser.MessageContext ctx);
	/**
	 * Visit a parse tree produced by {@link NspkParser#messagelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMessagelist(NspkParser.MessagelistContext ctx);
	/**
	 * Visit a parse tree produced by {@link NspkParser#prin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrin(NspkParser.PrinContext ctx);
	/**
	 * Visit a parse tree produced by {@link NspkParser#prinslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrinslist(NspkParser.PrinslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link NspkParser#cipher}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCipher(NspkParser.CipherContext ctx);
	/**
	 * Visit a parse tree produced by {@link NspkParser#nonce}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonce(NspkParser.NonceContext ctx);
	/**
	 * Visit a parse tree produced by {@link NspkParser#noncelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoncelist(NspkParser.NoncelistContext ctx);
	/**
	 * Visit a parse tree produced by {@link NspkParser#randlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRandlist(NspkParser.RandlistContext ctx);
}