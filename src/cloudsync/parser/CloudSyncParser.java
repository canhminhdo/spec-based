package cloudsync.parser;
// Generated from CloudSync.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CloudSyncParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		CLOUD_ID=1, PC_ID=2, LABELC=3, LABELP=4, PID=5, LESS_THAN=6, GREATER_THAN=7, 
		PARENTHESES_OPEN=8, PARENTHESES_CLOSE=9, CURLY_BRACKETS_OPEN=10, CURLY_BRACKETS_CLOSE=11, 
		SQUARE_BRACKETS_OPEN=12, SQUARE_BRACKETS_CLOSE=13, INTEGER_NUMBER=14, 
		COLON=15, COMMA=16, EMPTY=17, WS=18;
	public static final int
		RULE_start = 0, RULE_oc = 1, RULE_cloud = 2, RULE_pc = 3;
	public static final String[] ruleNames = {
		"start", "oc", "cloud", "pc"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'cloud'", "'pc'", null, null, null, "'<'", "'>'", "'('", "')'", 
		"'{'", "'}'", "'['", "']'", null, "':'", "','", "'emp'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "CLOUD_ID", "PC_ID", "LABELC", "LABELP", "PID", "LESS_THAN", "GREATER_THAN", 
		"PARENTHESES_OPEN", "PARENTHESES_CLOSE", "CURLY_BRACKETS_OPEN", "CURLY_BRACKETS_CLOSE", 
		"SQUARE_BRACKETS_OPEN", "SQUARE_BRACKETS_CLOSE", "INTEGER_NUMBER", "COLON", 
		"COMMA", "EMPTY", "WS"
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
	public String getGrammarFileName() { return "CloudSync.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CloudSyncParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StartContext extends ParserRuleContext {
		public TerminalNode CURLY_BRACKETS_OPEN() { return getToken(CloudSyncParser.CURLY_BRACKETS_OPEN, 0); }
		public TerminalNode CURLY_BRACKETS_CLOSE() { return getToken(CloudSyncParser.CURLY_BRACKETS_CLOSE, 0); }
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
			if ( visitor instanceof CloudSyncVisitor ) return ((CloudSyncVisitor<? extends T>)visitor).visitStart(this);
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
			setState(8);
			match(CURLY_BRACKETS_OPEN);
			setState(10); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(9);
				oc();
				}
				}
				setState(12); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CLOUD_ID) | (1L << PC_ID) | (1L << PARENTHESES_OPEN))) != 0) );
			setState(14);
			match(CURLY_BRACKETS_CLOSE);
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
		public TerminalNode PARENTHESES_OPEN() { return getToken(CloudSyncParser.PARENTHESES_OPEN, 0); }
		public OcContext oc() {
			return getRuleContext(OcContext.class,0);
		}
		public TerminalNode PARENTHESES_CLOSE() { return getToken(CloudSyncParser.PARENTHESES_CLOSE, 0); }
		public CloudContext cloud() {
			return getRuleContext(CloudContext.class,0);
		}
		public PcContext pc() {
			return getRuleContext(PcContext.class,0);
		}
		public OcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oc; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CloudSyncVisitor ) return ((CloudSyncVisitor<? extends T>)visitor).visitOc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OcContext oc() throws RecognitionException {
		OcContext _localctx = new OcContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_oc);
		try {
			setState(22);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PARENTHESES_OPEN:
				enterOuterAlt(_localctx, 1);
				{
				setState(16);
				match(PARENTHESES_OPEN);
				setState(17);
				oc();
				setState(18);
				match(PARENTHESES_CLOSE);
				}
				break;
			case CLOUD_ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(20);
				cloud();
				}
				break;
			case PC_ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(21);
				pc();
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

	public static class CloudContext extends ParserRuleContext {
		public TerminalNode CLOUD_ID() { return getToken(CloudSyncParser.CLOUD_ID, 0); }
		public TerminalNode COLON() { return getToken(CloudSyncParser.COLON, 0); }
		public TerminalNode LESS_THAN() { return getToken(CloudSyncParser.LESS_THAN, 0); }
		public TerminalNode LABELC() { return getToken(CloudSyncParser.LABELC, 0); }
		public TerminalNode COMMA() { return getToken(CloudSyncParser.COMMA, 0); }
		public TerminalNode INTEGER_NUMBER() { return getToken(CloudSyncParser.INTEGER_NUMBER, 0); }
		public TerminalNode GREATER_THAN() { return getToken(CloudSyncParser.GREATER_THAN, 0); }
		public CloudContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cloud; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CloudSyncVisitor ) return ((CloudSyncVisitor<? extends T>)visitor).visitCloud(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloudContext cloud() throws RecognitionException {
		CloudContext _localctx = new CloudContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_cloud);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(CLOUD_ID);
			setState(25);
			match(COLON);
			setState(26);
			match(LESS_THAN);
			setState(27);
			match(LABELC);
			setState(28);
			match(COMMA);
			setState(29);
			match(INTEGER_NUMBER);
			setState(30);
			match(GREATER_THAN);
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

	public static class PcContext extends ParserRuleContext {
		public TerminalNode PC_ID() { return getToken(CloudSyncParser.PC_ID, 0); }
		public TerminalNode SQUARE_BRACKETS_OPEN() { return getToken(CloudSyncParser.SQUARE_BRACKETS_OPEN, 0); }
		public TerminalNode PID() { return getToken(CloudSyncParser.PID, 0); }
		public TerminalNode SQUARE_BRACKETS_CLOSE() { return getToken(CloudSyncParser.SQUARE_BRACKETS_CLOSE, 0); }
		public TerminalNode COLON() { return getToken(CloudSyncParser.COLON, 0); }
		public TerminalNode LESS_THAN() { return getToken(CloudSyncParser.LESS_THAN, 0); }
		public TerminalNode LABELP() { return getToken(CloudSyncParser.LABELP, 0); }
		public List<TerminalNode> COMMA() { return getTokens(CloudSyncParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(CloudSyncParser.COMMA, i);
		}
		public List<TerminalNode> INTEGER_NUMBER() { return getTokens(CloudSyncParser.INTEGER_NUMBER); }
		public TerminalNode INTEGER_NUMBER(int i) {
			return getToken(CloudSyncParser.INTEGER_NUMBER, i);
		}
		public TerminalNode GREATER_THAN() { return getToken(CloudSyncParser.GREATER_THAN, 0); }
		public PcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pc; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CloudSyncVisitor ) return ((CloudSyncVisitor<? extends T>)visitor).visitPc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PcContext pc() throws RecognitionException {
		PcContext _localctx = new PcContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_pc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			match(PC_ID);
			setState(33);
			match(SQUARE_BRACKETS_OPEN);
			setState(34);
			match(PID);
			setState(35);
			match(SQUARE_BRACKETS_CLOSE);
			setState(36);
			match(COLON);
			setState(37);
			match(LESS_THAN);
			setState(38);
			match(LABELP);
			setState(39);
			match(COMMA);
			setState(40);
			match(INTEGER_NUMBER);
			setState(41);
			match(COMMA);
			setState(42);
			match(INTEGER_NUMBER);
			setState(43);
			match(GREATER_THAN);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\24\60\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\3\2\3\2\6\2\r\n\2\r\2\16\2\16\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\5\3\31\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\2\2\6\2\4\6\b\2\2\2.\2\n\3"+
		"\2\2\2\4\30\3\2\2\2\6\32\3\2\2\2\b\"\3\2\2\2\n\f\7\f\2\2\13\r\5\4\3\2"+
		"\f\13\3\2\2\2\r\16\3\2\2\2\16\f\3\2\2\2\16\17\3\2\2\2\17\20\3\2\2\2\20"+
		"\21\7\r\2\2\21\3\3\2\2\2\22\23\7\n\2\2\23\24\5\4\3\2\24\25\7\13\2\2\25"+
		"\31\3\2\2\2\26\31\5\6\4\2\27\31\5\b\5\2\30\22\3\2\2\2\30\26\3\2\2\2\30"+
		"\27\3\2\2\2\31\5\3\2\2\2\32\33\7\3\2\2\33\34\7\21\2\2\34\35\7\b\2\2\35"+
		"\36\7\5\2\2\36\37\7\22\2\2\37 \7\20\2\2 !\7\t\2\2!\7\3\2\2\2\"#\7\4\2"+
		"\2#$\7\16\2\2$%\7\7\2\2%&\7\17\2\2&\'\7\21\2\2\'(\7\b\2\2()\7\6\2\2)*"+
		"\7\22\2\2*+\7\20\2\2+,\7\22\2\2,-\7\20\2\2-.\7\t\2\2.\t\3\2\2\2\4\16\30";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}