package nspk.parser;
// Generated from Nspk.g4 by ANTLR 4.7.1
import java.util.ArrayList;
import java.util.Arrays;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

import nspk.main.Challenge;
import nspk.main.Cipher;
import nspk.main.Cipher1;
import nspk.main.Cipher2;
import nspk.main.Cipher3;
import nspk.main.Confirmation;
import nspk.main.Constants;
import nspk.main.Fake;
import nspk.main.Intruder;
import nspk.main.Message;
import nspk.main.MultiSet;
import nspk.main.Network;
import nspk.main.Nonce;
import nspk.main.Principal;
import nspk.main.Rand;
import nspk.main.Response;
import nspk.main.RewriteRule;


/**
 * This class provides an empty implementation of {@link NspkVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public class MessageNspkBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements NspkVisitor<T> {

	// Observer components
	Network<Message<Cipher>> nw = new Network<Message<Cipher>>(Constants.nw);;
	MultiSet<Rand> rand = new MultiSet<Rand>(Constants.rand);
	MultiSet<Nonce> nonces = new MultiSet<Nonce>(Constants.nonces);
	MultiSet<Principal> prins = new MultiSet<Principal>(Constants.prins);
	
	NspkParser parser;
	
	boolean nw_flag = false;
	boolean rand_flag = false;
	boolean nonces_flag = false;
	boolean prin_flag = false;
	boolean rw_p_flag = false;
	boolean rw_q_flag = false;
	boolean rw_intrdr_flag = false;
	
	// Initialize objects
	Intruder intrdr = new Intruder(Constants.intrdr);
	Principal p = new Principal(Constants.p);
	Principal q = new Principal(Constants.q);
	Rand r1 = new Rand(Constants.r1);
	Rand r2 = new Rand(Constants.r2);
	Challenge challenge = new Challenge();
	Confirmation confirmation = new Confirmation();
	Fake fake = new Fake();
	Response response = new Response();
	
	ArrayList<RewriteRule> rewriteRuleList = new ArrayList<RewriteRule>(Arrays.asList(new Challenge(), new Response(), new Confirmation(), new Fake()));
	
	public MessageNspkBaseVisitor(NspkParser parser) {
		this.parser = parser;
		this.prins._add(this.p);
		this.prins._add(this.q);
		this.prins._add(this.intrdr);
		
		this.p.setNw(nw);
		this.q.setNw(nw);
		this.intrdr.setNw(nw);
		
		this.p.setRand(rand);
		this.q.setRand(rand);
		this.intrdr.setRand(rand);
		
		this.p.setNonces(nonces);
		this.q.setNonces(nonces);
		this.intrdr.setNonces(nonces);
		
		this.p.setPrins(prins);
		this.q.setPrins(prins);
		this.intrdr.setPrins(prins);
	}
	
	private Principal getPrin(String id) {
		if (this.p.toString().equals(id))
			return this.p;
		
		if (this.q.toString().equals(id))
			return this.q;
		
		if (this.intrdr.toString().equals(id))
			return this.intrdr;
		
		return null;
	}
	
	private Rand getRand(String id) {
		if (this.r1.toString().equals(id))
			return this.r1;
		
		if (this.r2.toString().equals(id))
			return this.r2;
		
		return null;
	}
	
	private RewriteRule getRewriteRule(String id) {
		for (RewriteRule rewriteRule : rewriteRuleList) {
			if (rewriteRule.toString().equals(id))
				return rewriteRule;
		}
		
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitStart(NspkParser.StartContext ctx) {
		visitChildren(ctx);
//		System.out.println(this.nw);
//		System.out.println(this.rand);
//		System.out.println(this.nonces);
//		System.out.println(this.prins);
//		System.out.println(this.parser.getVocabulary().getLiteralName(this.parser.RW_P).replaceAll("\'","") + " "+ this.p.getRwController());
//		System.out.println(this.parser.getVocabulary().getLiteralName(this.parser.RW_Q).replaceAll("\'","") + " "+ this.q.getRwController());
//		System.out.println(this.parser.getVocabulary().getLiteralName(this.parser.RW_INTRDR).replaceAll("\'","") + " "+ this.intrdr.getRwController());
		MessageOC oc = new MessageOC(this.p, this.q, this.intrdr);
		return (T) oc;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitNetworkOC(NspkParser.NetworkOCContext ctx) {
		this.nw_flag = true;
		T t = visitChildren(ctx);
		this.nw_flag = false;
		return t;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitRandOC(NspkParser.RandOCContext ctx) {
		this.rand_flag = true;
		T t = visitChildren(ctx);
		this.rand_flag = false;
		return t;
		
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitNoncesOC(NspkParser.NoncesOCContext ctx) {
		this.nonces_flag = true;
		T t = visitChildren(ctx);
		this.nonces_flag = false;
		return t;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitPrinsOC(NspkParser.PrinsOCContext ctx) { 
		return visitChildren(ctx);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitMessage(NspkParser.MessageContext ctx) {
		String message_name = ctx.MESSAGENAME().getText();
		Principal creator = this.getPrin(ctx.prin(0).getText());
		Principal sender = this.getPrin(ctx.prin(1).getText());
		Principal receiver = this.getPrin(ctx.prin(2).getText());
		Cipher c = (Cipher) visit(ctx.cipher());
		Message<Cipher> m = new Message<Cipher>(message_name, creator, sender, receiver, c);
		this.nw._add(m);
		return null;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitMessagelist(NspkParser.MessagelistContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitPrin(NspkParser.PrinContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitPrinslist(NspkParser.PrinslistContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitCipher(NspkParser.CipherContext ctx) {
		if (ctx.getChild(0).getText().equals(Constants.c1)) {
			Principal p = this.getPrin(ctx.prin(0).getText());
			Principal q = this.getPrin(ctx.prin(1).getText());
			Nonce n = (Nonce) visit(ctx.nonce(0));
			Cipher1 c1 = new Cipher1(p, n, q);
			return (T) c1;
		}
		
		if (ctx.getChild(0).getText().equals(Constants.c2)) {
			Principal p = this.getPrin(ctx.prin(0).getText());
			Principal q = this.getPrin(ctx.prin(1).getText());
			Nonce n1 = (Nonce) visit(ctx.nonce(0));
			Nonce n2 = (Nonce) visit(ctx.nonce(1));
			Cipher2 c2 = new Cipher2(p, n1, n2, q);
			
			return (T) c2;
		}
		
		if (ctx.getChild(0).getText().equals(Constants.c3)) {
			Principal p = this.getPrin(ctx.prin(0).getText());
			Nonce n = (Nonce) visit(ctx.nonce(0));
			Cipher3 c3 = new Cipher3(p, n);
			return (T) c3;
		}

		return null;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitNonce(NspkParser.NonceContext ctx) { 
		if (this.nonces_flag) {
			Principal p1 = this.getPrin(ctx.prin(0).getText());
			Principal p2 = this.getPrin(ctx.prin(1).getText());
			Rand r = this.getRand(ctx.RAND().getText());
			Nonce n = new Nonce(p1, p2, r);
			this.nonces._add(n);
		}
		
		if (this.nw_flag) {
			Principal p1 = this.getPrin(ctx.prin(0).getText());
			Principal p2 = this.getPrin(ctx.prin(1).getText());
			Rand r = this.getRand(ctx.RAND().getText());
			Nonce n = new Nonce(p1, p2, r);
			return (T) n;
		}
		return visitChildren(ctx);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitNoncelist(NspkParser.NoncelistContext ctx) {
		return visitChildren(ctx);
		
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitRandlist(NspkParser.RandlistContext ctx) {
		if (this.rand_flag) {
			if (ctx.RAND() != null) {
				Rand r = this.getRand(ctx.RAND().getText());
				if (r != null)
					this.rand._add(r);
			}
		}
		return visitChildren(ctx);
	}
	
	public void enableRewriteRule(NspkParser.RwOCContext ctx, boolean flag) {
		if (this.parser.getVocabulary().getLiteralName(this.parser.RW_P).replaceAll("\'","").equals(ctx.rw().getText())) {
			this.rw_p_flag = flag;
		}
		if (this.parser.getVocabulary().getLiteralName(this.parser.RW_Q).replaceAll("\'","").equals(ctx.rw().getText())) {
			this.rw_q_flag = flag;
		}
		if (this.parser.getVocabulary().getLiteralName(this.parser.RW_INTRDR).replaceAll("\'","").equals(ctx.rw().getText())) {
			this.rw_intrdr_flag = flag;
		}
	}
	
	@Override
	public T visitRwOC(NspkParser.RwOCContext ctx) {
		this.enableRewriteRule(ctx, true);
		visitChildren(ctx);
		this.enableRewriteRule(ctx, false);
		return null;
	}

	@Override
	public T visitRulelist(NspkParser.RulelistContext ctx) {
		if (ctx.RULE() != null) {
			RewriteRule rw = this.getRewriteRule(ctx.RULE().getText());
			if (rw != null) {
				if (this.rw_p_flag) {
					this.p.getRwController().add(rw);
				}
				if (this.rw_q_flag) {
					this.q.getRwController().add(rw);
				}
				if (this.rw_intrdr_flag) {
					this.intrdr.getRwController().add(rw);
				}
			}
		}
		return visitChildren(ctx);
	}

	@Override
	public T visitRw(NspkParser.RwContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}
}