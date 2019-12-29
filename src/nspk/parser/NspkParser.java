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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, RW_P=17, 
		RW_Q=18, RW_INTRDR=19, RULE=20, MESSAGENAME=21, RAND=22, EMPTY=23, WS=24;
	public static final int
		RULE_start = 0, RULE_oc = 1, RULE_rw = 2, RULE_rulelist = 3, RULE_message = 4, 
		RULE_messagelist = 5, RULE_prin = 6, RULE_prinslist = 7, RULE_cipher = 8, 
		RULE_nonce = 9, RULE_noncelist = 10, RULE_randlist = 11;
	public static final String[] ruleNames = {
		"start", "oc", "rw", "rulelist", "message", "messagelist", "prin", "prinslist", 
		"cipher", "nonce", "noncelist", "randlist"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'nw:'", "'rand:'", "'nonces:'", "'prins:'", "'('", 
		"')'", "','", "'p'", "'q'", "'intrdr'", "'c1'", "'c2'", "'c3'", "'n'", 
		"'rw_p:'", "'rw_q:'", "'rw_intrdr:'", null, null, null, "'emp'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, "RW_P", "RW_Q", "RW_INTRDR", "RULE", "MESSAGENAME", 
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
			setState(24);
			match(T__0);
			setState(26); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(25);
				oc();
				}
				}
				setState(28); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << RW_P) | (1L << RW_Q) | (1L << RW_INTRDR))) != 0) );
			setState(30);
			match(T__1);
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
			setState(43);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				_localctx = new NetworkOCContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(32);
				match(T__2);
				setState(33);
				messagelist();
				}
				break;
			case T__3:
				_localctx = new RandOCContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(34);
				match(T__3);
				setState(35);
				randlist();
				}
				break;
			case T__4:
				_localctx = new NoncesOCContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(36);
				match(T__4);
				setState(37);
				noncelist();
				}
				break;
			case T__5:
				_localctx = new PrinsOCContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(38);
				match(T__5);
				setState(39);
				prinslist();
				}
				break;
			case RW_P:
			case RW_Q:
			case RW_INTRDR:
				_localctx = new RwOCContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(40);
				rw();
				setState(41);
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
			setState(45);
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
			setState(55);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(47);
				match(RULE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				match(RULE);
				setState(49);
				rulelist();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(50);
				match(T__6);
				setState(51);
				rulelist();
				setState(52);
				match(T__7);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(54);
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
			setState(57);
			match(MESSAGENAME);
			setState(58);
			match(T__6);
			setState(59);
			prin();
			setState(60);
			match(T__8);
			setState(61);
			prin();
			setState(62);
			match(T__8);
			setState(63);
			prin();
			setState(64);
			match(T__8);
			setState(65);
			cipher();
			setState(66);
			match(T__7);
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
			setState(77);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				message();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				message();
				setState(70);
				messagelist();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(72);
				match(T__6);
				setState(73);
				messagelist();
				setState(74);
				match(T__7);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(76);
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
			setState(79);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << T__11))) != 0)) ) {
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
			setState(90);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(81);
				prin();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(82);
				prin();
				setState(83);
				prinslist();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(85);
				match(T__6);
				setState(86);
				prinslist();
				setState(87);
				match(T__7);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(89);
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
			setState(119);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__12:
				enterOuterAlt(_localctx, 1);
				{
				setState(92);
				match(T__12);
				setState(93);
				match(T__6);
				setState(94);
				prin();
				setState(95);
				match(T__8);
				setState(96);
				nonce();
				setState(97);
				match(T__8);
				setState(98);
				prin();
				setState(99);
				match(T__7);
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 2);
				{
				setState(101);
				match(T__13);
				setState(102);
				match(T__6);
				setState(103);
				prin();
				setState(104);
				match(T__8);
				setState(105);
				nonce();
				setState(106);
				match(T__8);
				setState(107);
				nonce();
				setState(108);
				match(T__8);
				setState(109);
				prin();
				setState(110);
				match(T__7);
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 3);
				{
				setState(112);
				match(T__14);
				setState(113);
				match(T__6);
				setState(114);
				prin();
				setState(115);
				match(T__8);
				setState(116);
				nonce();
				setState(117);
				match(T__7);
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
			setState(121);
			match(T__15);
			setState(122);
			match(T__6);
			setState(123);
			prin();
			setState(124);
			match(T__8);
			setState(125);
			prin();
			setState(126);
			match(T__8);
			setState(127);
			match(RAND);
			setState(128);
			match(T__7);
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
			setState(139);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(130);
				nonce();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(131);
				nonce();
				setState(132);
				noncelist();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(134);
				match(T__6);
				setState(135);
				noncelist();
				setState(136);
				match(T__7);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(138);
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
			setState(149);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(141);
				match(RAND);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(142);
				match(RAND);
				setState(143);
				randlist();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(144);
				match(T__6);
				setState(145);
				randlist();
				setState(146);
				match(T__7);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(148);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\32\u009a\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\3\2\3\2\6\2\35\n\2\r\2\16\2\36\3\2\3\2\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3.\n\3\3\4\3\4\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\5\5:\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7P\n\7\3\b\3\b\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\5\t]\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n"+
		"z\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\5\f\u008e\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0098"+
		"\n\r\3\r\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30\2\4\3\2\23\25\3\2\f\16\2"+
		"\u00a3\2\32\3\2\2\2\4-\3\2\2\2\6/\3\2\2\2\b9\3\2\2\2\n;\3\2\2\2\fO\3\2"+
		"\2\2\16Q\3\2\2\2\20\\\3\2\2\2\22y\3\2\2\2\24{\3\2\2\2\26\u008d\3\2\2\2"+
		"\30\u0097\3\2\2\2\32\34\7\3\2\2\33\35\5\4\3\2\34\33\3\2\2\2\35\36\3\2"+
		"\2\2\36\34\3\2\2\2\36\37\3\2\2\2\37 \3\2\2\2 !\7\4\2\2!\3\3\2\2\2\"#\7"+
		"\5\2\2#.\5\f\7\2$%\7\6\2\2%.\5\30\r\2&\'\7\7\2\2\'.\5\26\f\2()\7\b\2\2"+
		").\5\20\t\2*+\5\6\4\2+,\5\b\5\2,.\3\2\2\2-\"\3\2\2\2-$\3\2\2\2-&\3\2\2"+
		"\2-(\3\2\2\2-*\3\2\2\2.\5\3\2\2\2/\60\t\2\2\2\60\7\3\2\2\2\61:\7\26\2"+
		"\2\62\63\7\26\2\2\63:\5\b\5\2\64\65\7\t\2\2\65\66\5\b\5\2\66\67\7\n\2"+
		"\2\67:\3\2\2\28:\7\31\2\29\61\3\2\2\29\62\3\2\2\29\64\3\2\2\298\3\2\2"+
		"\2:\t\3\2\2\2;<\7\27\2\2<=\7\t\2\2=>\5\16\b\2>?\7\13\2\2?@\5\16\b\2@A"+
		"\7\13\2\2AB\5\16\b\2BC\7\13\2\2CD\5\22\n\2DE\7\n\2\2E\13\3\2\2\2FP\5\n"+
		"\6\2GH\5\n\6\2HI\5\f\7\2IP\3\2\2\2JK\7\t\2\2KL\5\f\7\2LM\7\n\2\2MP\3\2"+
		"\2\2NP\7\31\2\2OF\3\2\2\2OG\3\2\2\2OJ\3\2\2\2ON\3\2\2\2P\r\3\2\2\2QR\t"+
		"\3\2\2R\17\3\2\2\2S]\5\16\b\2TU\5\16\b\2UV\5\20\t\2V]\3\2\2\2WX\7\t\2"+
		"\2XY\5\20\t\2YZ\7\n\2\2Z]\3\2\2\2[]\7\31\2\2\\S\3\2\2\2\\T\3\2\2\2\\W"+
		"\3\2\2\2\\[\3\2\2\2]\21\3\2\2\2^_\7\17\2\2_`\7\t\2\2`a\5\16\b\2ab\7\13"+
		"\2\2bc\5\24\13\2cd\7\13\2\2de\5\16\b\2ef\7\n\2\2fz\3\2\2\2gh\7\20\2\2"+
		"hi\7\t\2\2ij\5\16\b\2jk\7\13\2\2kl\5\24\13\2lm\7\13\2\2mn\5\24\13\2no"+
		"\7\13\2\2op\5\16\b\2pq\7\n\2\2qz\3\2\2\2rs\7\21\2\2st\7\t\2\2tu\5\16\b"+
		"\2uv\7\13\2\2vw\5\24\13\2wx\7\n\2\2xz\3\2\2\2y^\3\2\2\2yg\3\2\2\2yr\3"+
		"\2\2\2z\23\3\2\2\2{|\7\22\2\2|}\7\t\2\2}~\5\16\b\2~\177\7\13\2\2\177\u0080"+
		"\5\16\b\2\u0080\u0081\7\13\2\2\u0081\u0082\7\30\2\2\u0082\u0083\7\n\2"+
		"\2\u0083\25\3\2\2\2\u0084\u008e\5\24\13\2\u0085\u0086\5\24\13\2\u0086"+
		"\u0087\5\26\f\2\u0087\u008e\3\2\2\2\u0088\u0089\7\t\2\2\u0089\u008a\5"+
		"\26\f\2\u008a\u008b\7\n\2\2\u008b\u008e\3\2\2\2\u008c\u008e\7\31\2\2\u008d"+
		"\u0084\3\2\2\2\u008d\u0085\3\2\2\2\u008d\u0088\3\2\2\2\u008d\u008c\3\2"+
		"\2\2\u008e\27\3\2\2\2\u008f\u0098\7\30\2\2\u0090\u0091\7\30\2\2\u0091"+
		"\u0098\5\30\r\2\u0092\u0093\7\t\2\2\u0093\u0094\5\30\r\2\u0094\u0095\7"+
		"\n\2\2\u0095\u0098\3\2\2\2\u0096\u0098\7\31\2\2\u0097\u008f\3\2\2\2\u0097"+
		"\u0090\3\2\2\2\u0097\u0092\3\2\2\2\u0097\u0096\3\2\2\2\u0098\31\3\2\2"+
		"\2\n\36-9O\\y\u008d\u0097";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}