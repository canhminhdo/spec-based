package nslpk.parser;
// Generated from Nspk.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link NslpkParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface NslpkVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link NslpkParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(NslpkParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by the {@code networkOC}
	 * labeled alternative in {@link NslpkParser#oc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNetworkOC(NslpkParser.NetworkOCContext ctx);
	/**
	 * Visit a parse tree produced by the {@code randOC}
	 * labeled alternative in {@link NslpkParser#oc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRandOC(NslpkParser.RandOCContext ctx);
	/**
	 * Visit a parse tree produced by the {@code noncesOC}
	 * labeled alternative in {@link NslpkParser#oc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoncesOC(NslpkParser.NoncesOCContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prinsOC}
	 * labeled alternative in {@link NslpkParser#oc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrinsOC(NslpkParser.PrinsOCContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rwOC}
	 * labeled alternative in {@link NslpkParser#oc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRwOC(NslpkParser.RwOCContext ctx);
	/**
	 * Visit a parse tree produced by {@link NslpkParser#rw}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRw(NslpkParser.RwContext ctx);
	/**
	 * Visit a parse tree produced by {@link NslpkParser#rulelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRulelist(NslpkParser.RulelistContext ctx);
	/**
	 * Visit a parse tree produced by {@link NslpkParser#message}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMessage(NslpkParser.MessageContext ctx);
	/**
	 * Visit a parse tree produced by {@link NslpkParser#messagelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMessagelist(NslpkParser.MessagelistContext ctx);
	/**
	 * Visit a parse tree produced by {@link NslpkParser#prin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrin(NslpkParser.PrinContext ctx);
	/**
	 * Visit a parse tree produced by {@link NslpkParser#prinslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrinslist(NslpkParser.PrinslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link NslpkParser#cipher}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCipher(NslpkParser.CipherContext ctx);
	/**
	 * Visit a parse tree produced by {@link NslpkParser#nonce}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonce(NslpkParser.NonceContext ctx);
	/**
	 * Visit a parse tree produced by {@link NslpkParser#noncelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoncelist(NslpkParser.NoncelistContext ctx);
	/**
	 * Visit a parse tree produced by {@link NslpkParser#randlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRandlist(NslpkParser.RandlistContext ctx);
}