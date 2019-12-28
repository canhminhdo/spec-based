package nspk.parser;
// Generated from Nspk.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class NspkLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, RW_P=15, RW_Q=16, RW_INTRDR=17, 
		RULE=18, MESSAGENAME=19, RAND=20, EMPTY=21, WS=22;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "RW_P", "RW_Q", "RW_INTRDR", 
		"RULE", "MESSAGENAME", "RAND", "EMPTY", "WS"
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


	public NspkLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Nspk.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\30\u00b7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\2\3"+
		"\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\5\23\u009d\n\23\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u00a5\n\24\3\25\3"+
		"\25\3\25\3\25\5\25\u00ab\n\25\3\26\3\26\3\26\3\26\3\27\6\27\u00b2\n\27"+
		"\r\27\16\27\u00b3\3\27\3\27\2\2\30\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30"+
		"\3\2\3\5\2\13\f\17\17\"\"\2\u00bd\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\2+\3\2\2\2\2-\3\2\2\2\3/\3\2\2\2\5\63\3\2\2\2\79\3\2\2\2\tA\3\2\2\2"+
		"\13H\3\2\2\2\rJ\3\2\2\2\17L\3\2\2\2\21N\3\2\2\2\23P\3\2\2\2\25R\3\2\2"+
		"\2\27Y\3\2\2\2\31\\\3\2\2\2\33_\3\2\2\2\35b\3\2\2\2\37d\3\2\2\2!j\3\2"+
		"\2\2#p\3\2\2\2%\u009c\3\2\2\2\'\u00a4\3\2\2\2)\u00aa\3\2\2\2+\u00ac\3"+
		"\2\2\2-\u00b1\3\2\2\2/\60\7p\2\2\60\61\7y\2\2\61\62\7<\2\2\62\4\3\2\2"+
		"\2\63\64\7t\2\2\64\65\7c\2\2\65\66\7p\2\2\66\67\7f\2\2\678\7<\2\28\6\3"+
		"\2\2\29:\7p\2\2:;\7q\2\2;<\7p\2\2<=\7e\2\2=>\7g\2\2>?\7u\2\2?@\7<\2\2"+
		"@\b\3\2\2\2AB\7r\2\2BC\7t\2\2CD\7k\2\2DE\7p\2\2EF\7u\2\2FG\7<\2\2G\n\3"+
		"\2\2\2HI\7*\2\2I\f\3\2\2\2JK\7+\2\2K\16\3\2\2\2LM\7.\2\2M\20\3\2\2\2N"+
		"O\7r\2\2O\22\3\2\2\2PQ\7s\2\2Q\24\3\2\2\2RS\7k\2\2ST\7p\2\2TU\7v\2\2U"+
		"V\7t\2\2VW\7f\2\2WX\7t\2\2X\26\3\2\2\2YZ\7e\2\2Z[\7\63\2\2[\30\3\2\2\2"+
		"\\]\7e\2\2]^\7\64\2\2^\32\3\2\2\2_`\7e\2\2`a\7\65\2\2a\34\3\2\2\2bc\7"+
		"p\2\2c\36\3\2\2\2de\7t\2\2ef\7y\2\2fg\7a\2\2gh\7r\2\2hi\7<\2\2i \3\2\2"+
		"\2jk\7t\2\2kl\7y\2\2lm\7a\2\2mn\7s\2\2no\7<\2\2o\"\3\2\2\2pq\7t\2\2qr"+
		"\7y\2\2rs\7a\2\2st\7k\2\2tu\7p\2\2uv\7v\2\2vw\7t\2\2wx\7f\2\2xy\7t\2\2"+
		"yz\7<\2\2z$\3\2\2\2{|\7E\2\2|}\7j\2\2}~\7c\2\2~\177\7n\2\2\177\u0080\7"+
		"n\2\2\u0080\u0081\7g\2\2\u0081\u0082\7p\2\2\u0082\u0083\7i\2\2\u0083\u009d"+
		"\7g\2\2\u0084\u0085\7T\2\2\u0085\u0086\7g\2\2\u0086\u0087\7u\2\2\u0087"+
		"\u0088\7r\2\2\u0088\u0089\7q\2\2\u0089\u008a\7p\2\2\u008a\u008b\7u\2\2"+
		"\u008b\u009d\7g\2\2\u008c\u008d\7E\2\2\u008d\u008e\7q\2\2\u008e\u008f"+
		"\7p\2\2\u008f\u0090\7h\2\2\u0090\u0091\7k\2\2\u0091\u0092\7t\2\2\u0092"+
		"\u0093\7o\2\2\u0093\u0094\7c\2\2\u0094\u0095\7v\2\2\u0095\u0096\7k\2\2"+
		"\u0096\u0097\7q\2\2\u0097\u009d\7p\2\2\u0098\u0099\7H\2\2\u0099\u009a"+
		"\7c\2\2\u009a\u009b\7m\2\2\u009b\u009d\7g\2\2\u009c{\3\2\2\2\u009c\u0084"+
		"\3\2\2\2\u009c\u008c\3\2\2\2\u009c\u0098\3\2\2\2\u009d&\3\2\2\2\u009e"+
		"\u009f\7o\2\2\u009f\u00a5\7\63\2\2\u00a0\u00a1\7o\2\2\u00a1\u00a5\7\64"+
		"\2\2\u00a2\u00a3\7o\2\2\u00a3\u00a5\7\65\2\2\u00a4\u009e\3\2\2\2\u00a4"+
		"\u00a0\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a5(\3\2\2\2\u00a6\u00a7\7t\2\2\u00a7"+
		"\u00ab\7\63\2\2\u00a8\u00a9\7t\2\2\u00a9\u00ab\7\64\2\2\u00aa\u00a6\3"+
		"\2\2\2\u00aa\u00a8\3\2\2\2\u00ab*\3\2\2\2\u00ac\u00ad\7g\2\2\u00ad\u00ae"+
		"\7o\2\2\u00ae\u00af\7r\2\2\u00af,\3\2\2\2\u00b0\u00b2\t\2\2\2\u00b1\u00b0"+
		"\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4"+
		"\u00b5\3\2\2\2\u00b5\u00b6\b\27\2\2\u00b6.\3\2\2\2\7\2\u009c\u00a4\u00aa"+
		"\u00b3\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}