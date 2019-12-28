package nspk.parser;
// Generated from Nspk.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class NspkParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, RW_P=15, RW_Q=16, RW_INTRDR=17, 
		RULE=18, MESSAGENAME=19, RAND=20, EMPTY=21, WS=22;
	public static final int
		RULE_start = 0, RULE_oc = 1, RULE_rw = 2, RULE_rulelist = 3, RULE_message = 4, 
		RULE_messagelist = 5, RULE_prin = 6, RULE_prinslist = 7, RULE_cipher = 8, 
		RULE_nonce = 9, RULE_noncelist = 10, RULE_randlist = 11;
	public static final String[] ruleNames = {
		"start", "oc", "rw", "rulelist", "message", "messagelist", "prin", "prinslist", 
		"cipher", "nonce", "noncelist", "randlist"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'nw:'", "'rand:'", "'nonces:'", "'prins:'", "'('", "')'", "','", 
		"'p'", "'q'", "'intrdr'", "'c1'", "'c2'", "'c3'", "'n'", "'rw_p:'", "'rw_q:'", 
		"'rw_intrdr:'", null, null, null, "'emp'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "RW_P", "RW_Q", "RW_INTRDR", "RULE", "MESSAGENAME", 
		"RAND", "EMPTY", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Nspk.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public NspkParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StartContext extends ParserRuleContext {
		public List<OcContext> oc() {
			return getRuleContexts(OcContext.class);
		}
		public OcContext oc(int i) {
			return getRuleContext(OcContext.class,i);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(24);
				oc();
				}
				}
				setState(27); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << RW_P) | (1L << RW_Q) | (1L << RW_INTRDR))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OcContext extends ParserRuleContext {
		public OcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oc; }
	 
		public OcContext() { }
		public void copyFrom(OcContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NetworkOCContext extends OcContext {
		public MessagelistContext messagelist() {
			return getRuleContext(MessagelistContext.class,0);
		}
		public NetworkOCContext(OcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitNetworkOC(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NoncesOCContext extends OcContext {
		public NoncelistContext noncelist() {
			return getRuleContext(NoncelistContext.class,0);
		}
		public NoncesOCContext(OcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitNoncesOC(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RandOCContext extends OcContext {
		public RandlistContext randlist() {
			return getRuleContext(RandlistContext.class,0);
		}
		public RandOCContext(OcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitRandOC(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RwOCContext extends OcContext {
		public RwContext rw() {
			return getRuleContext(RwContext.class,0);
		}
		public RulelistContext rulelist() {
			return getRuleContext(RulelistContext.class,0);
		}
		public RwOCContext(OcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitRwOC(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrinsOCContext extends OcContext {
		public PrinslistContext prinslist() {
			return getRuleContext(PrinslistContext.class,0);
		}
		public PrinsOCContext(OcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitPrinsOC(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OcContext oc() throws RecognitionException {
		OcContext _localctx = new OcContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_oc);
		try {
			setState(40);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				_localctx = new NetworkOCContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(29);
				match(T__0);
				setState(30);
				messagelist();
				}
				break;
			case T__1:
				_localctx = new RandOCContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(31);
				match(T__1);
				setState(32);
				randlist();
				}
				break;
			case T__2:
				_localctx = new NoncesOCContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(33);
				match(T__2);
				setState(34);
				noncelist();
				}
				break;
			case T__3:
				_localctx = new PrinsOCContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(35);
				match(T__3);
				setState(36);
				prinslist();
				}
				break;
			case RW_P:
			case RW_Q:
			case RW_INTRDR:
				_localctx = new RwOCContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(37);
				rw();
				setState(38);
				rulelist();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RwContext extends ParserRuleContext {
		public TerminalNode RW_P() { return getToken(NspkParser.RW_P, 0); }
		public TerminalNode RW_Q() { return getToken(NspkParser.RW_Q, 0); }
		public TerminalNode RW_INTRDR() { return getToken(NspkParser.RW_INTRDR, 0); }
		public RwContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rw; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitRw(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RwContext rw() throws RecognitionException {
		RwContext _localctx = new RwContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_rw);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RW_P) | (1L << RW_Q) | (1L << RW_INTRDR))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RulelistContext extends ParserRuleContext {
		public TerminalNode RULE() { return getToken(NspkParser.RULE, 0); }
		public RulelistContext rulelist() {
			return getRuleContext(RulelistContext.class,0);
		}
		public TerminalNode EMPTY() { return getToken(NspkParser.EMPTY, 0); }
		public RulelistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rulelist; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitRulelist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulelistContext rulelist() throws RecognitionException {
		RulelistContext _localctx = new RulelistContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_rulelist);
		try {
			setState(52);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				match(RULE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				match(RULE);
				setState(46);
				rulelist();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(47);
				match(T__4);
				setState(48);
				rulelist();
				setState(49);
				match(T__5);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(51);
				match(EMPTY);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MessageContext extends ParserRuleContext {
		public TerminalNode MESSAGENAME() { return getToken(NspkParser.MESSAGENAME, 0); }
		public List<PrinContext> prin() {
			return getRuleContexts(PrinContext.class);
		}
		public PrinContext prin(int i) {
			return getRuleContext(PrinContext.class,i);
		}
		public CipherContext cipher() {
			return getRuleContext(CipherContext.class,0);
		}
		public MessageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_message; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitMessage(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MessageContext message() throws RecognitionException {
		MessageContext _localctx = new MessageContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_message);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(MESSAGENAME);
			setState(55);
			match(T__4);
			setState(56);
			prin();
			setState(57);
			match(T__6);
			setState(58);
			prin();
			setState(59);
			match(T__6);
			setState(60);
			prin();
			setState(61);
			match(T__6);
			setState(62);
			cipher();
			setState(63);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MessagelistContext extends ParserRuleContext {
		public MessageContext message() {
			return getRuleContext(MessageContext.class,0);
		}
		public MessagelistContext messagelist() {
			return getRuleContext(MessagelistContext.class,0);
		}
		public TerminalNode EMPTY() { return getToken(NspkParser.EMPTY, 0); }
		public MessagelistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_messagelist; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitMessagelist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MessagelistContext messagelist() throws RecognitionException {
		MessagelistContext _localctx = new MessagelistContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_messagelist);
		try {
			setState(74);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(65);
				message();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(66);
				message();
				setState(67);
				messagelist();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(69);
				match(T__4);
				setState(70);
				messagelist();
				setState(71);
				match(T__5);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(73);
				match(EMPTY);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrinContext extends ParserRuleContext {
		public PrinContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prin; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitPrin(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrinContext prin() throws RecognitionException {
		PrinContext _localctx = new PrinContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_prin);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrinslistContext extends ParserRuleContext {
		public PrinContext prin() {
			return getRuleContext(PrinContext.class,0);
		}
		public PrinslistContext prinslist() {
			return getRuleContext(PrinslistContext.class,0);
		}
		public TerminalNode EMPTY() { return getToken(NspkParser.EMPTY, 0); }
		public PrinslistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prinslist; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitPrinslist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrinslistContext prinslist() throws RecognitionException {
		PrinslistContext _localctx = new PrinslistContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_prinslist);
		try {
			setState(87);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				prin();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				prin();
				setState(80);
				prinslist();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				match(T__4);
				setState(83);
				prinslist();
				setState(84);
				match(T__5);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(86);
				match(EMPTY);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CipherContext extends ParserRuleContext {
		public List<PrinContext> prin() {
			return getRuleContexts(PrinContext.class);
		}
		public PrinContext prin(int i) {
			return getRuleContext(PrinContext.class,i);
		}
		public List<NonceContext> nonce() {
			return getRuleContexts(NonceContext.class);
		}
		public NonceContext nonce(int i) {
			return getRuleContext(NonceContext.class,i);
		}
		public CipherContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cipher; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitCipher(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CipherContext cipher() throws RecognitionException {
		CipherContext _localctx = new CipherContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_cipher);
		try {
			setState(116);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(89);
				match(T__10);
				setState(90);
				match(T__4);
				setState(91);
				prin();
				setState(92);
				match(T__6);
				setState(93);
				nonce();
				setState(94);
				match(T__6);
				setState(95);
				prin();
				setState(96);
				match(T__5);
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(98);
				match(T__11);
				setState(99);
				match(T__4);
				setState(100);
				prin();
				setState(101);
				match(T__6);
				setState(102);
				nonce();
				setState(103);
				match(T__6);
				setState(104);
				nonce();
				setState(105);
				match(T__6);
				setState(106);
				prin();
				setState(107);
				match(T__5);
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 3);
				{
				setState(109);
				match(T__12);
				setState(110);
				match(T__4);
				setState(111);
				prin();
				setState(112);
				match(T__6);
				setState(113);
				nonce();
				setState(114);
				match(T__5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NonceContext extends ParserRuleContext {
		public List<PrinContext> prin() {
			return getRuleContexts(PrinContext.class);
		}
		public PrinContext prin(int i) {
			return getRuleContext(PrinContext.class,i);
		}
		public TerminalNode RAND() { return getToken(NspkParser.RAND, 0); }
		public NonceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonce; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitNonce(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonceContext nonce() throws RecognitionException {
		NonceContext _localctx = new NonceContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_nonce);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			match(T__13);
			setState(119);
			match(T__4);
			setState(120);
			prin();
			setState(121);
			match(T__6);
			setState(122);
			prin();
			setState(123);
			match(T__6);
			setState(124);
			match(RAND);
			setState(125);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NoncelistContext extends ParserRuleContext {
		public NonceContext nonce() {
			return getRuleContext(NonceContext.class,0);
		}
		public NoncelistContext noncelist() {
			return getRuleContext(NoncelistContext.class,0);
		}
		public TerminalNode EMPTY() { return getToken(NspkParser.EMPTY, 0); }
		public NoncelistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_noncelist; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitNoncelist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NoncelistContext noncelist() throws RecognitionException {
		NoncelistContext _localctx = new NoncelistContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_noncelist);
		try {
			setState(136);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(127);
				nonce();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(128);
				nonce();
				setState(129);
				noncelist();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(131);
				match(T__4);
				setState(132);
				noncelist();
				setState(133);
				match(T__5);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(135);
				match(EMPTY);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RandlistContext extends ParserRuleContext {
		public TerminalNode RAND() { return getToken(NspkParser.RAND, 0); }
		public RandlistContext randlist() {
			return getRuleContext(RandlistContext.class,0);
		}
		public TerminalNode EMPTY() { return getToken(NspkParser.EMPTY, 0); }
		public RandlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_randlist; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NspkVisitor ) return ((NspkVisitor<? extends T>)visitor).visitRandlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RandlistContext randlist() throws RecognitionException {
		RandlistContext _localctx = new RandlistContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_randlist);
		try {
			setState(146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				match(RAND);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				match(RAND);
				setState(140);
				randlist();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(141);
				match(T__4);
				setState(142);
				randlist();
				setState(143);
				match(T__5);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(145);
				match(EMPTY);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\30\u0097\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\3\2\6\2\34\n\2\r\2\16\2\35\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\5\3+\n\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\5\5\67\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\5\7M\n\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\5\tZ\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\nw\n\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\5\f\u008b\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0095\n\r\3\r"+
		"\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30\2\4\3\2\21\23\3\2\n\f\2\u00a0\2"+
		"\33\3\2\2\2\4*\3\2\2\2\6,\3\2\2\2\b\66\3\2\2\2\n8\3\2\2\2\fL\3\2\2\2\16"+
		"N\3\2\2\2\20Y\3\2\2\2\22v\3\2\2\2\24x\3\2\2\2\26\u008a\3\2\2\2\30\u0094"+
		"\3\2\2\2\32\34\5\4\3\2\33\32\3\2\2\2\34\35\3\2\2\2\35\33\3\2\2\2\35\36"+
		"\3\2\2\2\36\3\3\2\2\2\37 \7\3\2\2 +\5\f\7\2!\"\7\4\2\2\"+\5\30\r\2#$\7"+
		"\5\2\2$+\5\26\f\2%&\7\6\2\2&+\5\20\t\2\'(\5\6\4\2()\5\b\5\2)+\3\2\2\2"+
		"*\37\3\2\2\2*!\3\2\2\2*#\3\2\2\2*%\3\2\2\2*\'\3\2\2\2+\5\3\2\2\2,-\t\2"+
		"\2\2-\7\3\2\2\2.\67\7\24\2\2/\60\7\24\2\2\60\67\5\b\5\2\61\62\7\7\2\2"+
		"\62\63\5\b\5\2\63\64\7\b\2\2\64\67\3\2\2\2\65\67\7\27\2\2\66.\3\2\2\2"+
		"\66/\3\2\2\2\66\61\3\2\2\2\66\65\3\2\2\2\67\t\3\2\2\289\7\25\2\29:\7\7"+
		"\2\2:;\5\16\b\2;<\7\t\2\2<=\5\16\b\2=>\7\t\2\2>?\5\16\b\2?@\7\t\2\2@A"+
		"\5\22\n\2AB\7\b\2\2B\13\3\2\2\2CM\5\n\6\2DE\5\n\6\2EF\5\f\7\2FM\3\2\2"+
		"\2GH\7\7\2\2HI\5\f\7\2IJ\7\b\2\2JM\3\2\2\2KM\7\27\2\2LC\3\2\2\2LD\3\2"+
		"\2\2LG\3\2\2\2LK\3\2\2\2M\r\3\2\2\2NO\t\3\2\2O\17\3\2\2\2PZ\5\16\b\2Q"+
		"R\5\16\b\2RS\5\20\t\2SZ\3\2\2\2TU\7\7\2\2UV\5\20\t\2VW\7\b\2\2WZ\3\2\2"+
		"\2XZ\7\27\2\2YP\3\2\2\2YQ\3\2\2\2YT\3\2\2\2YX\3\2\2\2Z\21\3\2\2\2[\\\7"+
		"\r\2\2\\]\7\7\2\2]^\5\16\b\2^_\7\t\2\2_`\5\24\13\2`a\7\t\2\2ab\5\16\b"+
		"\2bc\7\b\2\2cw\3\2\2\2de\7\16\2\2ef\7\7\2\2fg\5\16\b\2gh\7\t\2\2hi\5\24"+
		"\13\2ij\7\t\2\2jk\5\24\13\2kl\7\t\2\2lm\5\16\b\2mn\7\b\2\2nw\3\2\2\2o"+
		"p\7\17\2\2pq\7\7\2\2qr\5\16\b\2rs\7\t\2\2st\5\24\13\2tu\7\b\2\2uw\3\2"+
		"\2\2v[\3\2\2\2vd\3\2\2\2vo\3\2\2\2w\23\3\2\2\2xy\7\20\2\2yz\7\7\2\2z{"+
		"\5\16\b\2{|\7\t\2\2|}\5\16\b\2}~\7\t\2\2~\177\7\26\2\2\177\u0080\7\b\2"+
		"\2\u0080\25\3\2\2\2\u0081\u008b\5\24\13\2\u0082\u0083\5\24\13\2\u0083"+
		"\u0084\5\26\f\2\u0084\u008b\3\2\2\2\u0085\u0086\7\7\2\2\u0086\u0087\5"+
		"\26\f\2\u0087\u0088\7\b\2\2\u0088\u008b\3\2\2\2\u0089\u008b\7\27\2\2\u008a"+
		"\u0081\3\2\2\2\u008a\u0082\3\2\2\2\u008a\u0085\3\2\2\2\u008a\u0089\3\2"+
		"\2\2\u008b\27\3\2\2\2\u008c\u0095\7\26\2\2\u008d\u008e\7\26\2\2\u008e"+
		"\u0095\5\30\r\2\u008f\u0090\7\7\2\2\u0090\u0091\5\30\r\2\u0091\u0092\7"+
		"\b\2\2\u0092\u0095\3\2\2\2\u0093\u0095\7\27\2\2\u0094\u008c\3\2\2\2\u0094"+
		"\u008d\3\2\2\2\u0094\u008f\3\2\2\2\u0094\u0093\3\2\2\2\u0095\31\3\2\2"+
		"\2\n\35*\66LYv\u008a\u0094";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}