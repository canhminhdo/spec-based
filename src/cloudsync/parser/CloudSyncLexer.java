package cloudsync.parser;
// Generated from CloudSync.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CloudSyncLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		CLOUD_ID=1, PC_ID=2, LABELC=3, LABELP=4, PID=5, LESS_THAN=6, GREATER_THAN=7, 
		PARENTHESES_OPEN=8, PARENTHESES_CLOSE=9, CURLY_BRACKETS_OPEN=10, CURLY_BRACKETS_CLOSE=11, 
		SQUARE_BRACKETS_OPEN=12, SQUARE_BRACKETS_CLOSE=13, INTEGER_NUMBER=14, 
		COLON=15, COMMA=16, EMPTY=17, WS=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"CLOUD_ID", "PC_ID", "LABELC", "LABELP", "PID", "LESS_THAN", "GREATER_THAN", 
		"PARENTHESES_OPEN", "PARENTHESES_CLOSE", "CURLY_BRACKETS_OPEN", "CURLY_BRACKETS_CLOSE", 
		"SQUARE_BRACKETS_OPEN", "SQUARE_BRACKETS_CLOSE", "INTEGER_NUMBER", "DIGIT", 
		"COLON", "COMMA", "EMPTY", "WS"
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


	public CloudSyncLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CloudSync.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\24z\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\5\4<\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5P\n\5\3\6\3\6\3\6\3\7\3\7\3\b\3"+
		"\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\6\17f\n\17"+
		"\r\17\16\17g\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\23\3\24\6"+
		"\24u\n\24\r\24\16\24v\3\24\3\24\2\2\25\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21"+
		"\n\23\13\25\f\27\r\31\16\33\17\35\20\37\2!\21#\22%\23\'\24\3\2\3\5\2\13"+
		"\f\17\17\"\"\2}\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\3)\3\2\2\2\5/\3\2\2\2\7;\3\2\2\2\tO\3\2\2"+
		"\2\13Q\3\2\2\2\rT\3\2\2\2\17V\3\2\2\2\21X\3\2\2\2\23Z\3\2\2\2\25\\\3\2"+
		"\2\2\27^\3\2\2\2\31`\3\2\2\2\33b\3\2\2\2\35e\3\2\2\2\37i\3\2\2\2!k\3\2"+
		"\2\2#m\3\2\2\2%o\3\2\2\2\'t\3\2\2\2)*\7e\2\2*+\7n\2\2+,\7q\2\2,-\7w\2"+
		"\2-.\7f\2\2.\4\3\2\2\2/\60\7r\2\2\60\61\7e\2\2\61\6\3\2\2\2\62\63\7k\2"+
		"\2\63\64\7f\2\2\64\65\7n\2\2\65\66\7g\2\2\66<\7e\2\2\678\7d\2\289\7w\2"+
		"\29:\7u\2\2:<\7{\2\2;\62\3\2\2\2;\67\3\2\2\2<\b\3\2\2\2=>\7k\2\2>?\7f"+
		"\2\2?@\7n\2\2@A\7g\2\2AP\7r\2\2BC\7i\2\2CD\7q\2\2DE\7v\2\2EF\7x\2\2FG"+
		"\7c\2\2GP\7n\2\2HI\7w\2\2IJ\7r\2\2JK\7f\2\2KL\7c\2\2LM\7v\2\2MN\7g\2\2"+
		"NP\7f\2\2O=\3\2\2\2OB\3\2\2\2OH\3\2\2\2P\n\3\2\2\2QR\7r\2\2RS\5\35\17"+
		"\2S\f\3\2\2\2TU\7>\2\2U\16\3\2\2\2VW\7@\2\2W\20\3\2\2\2XY\7*\2\2Y\22\3"+
		"\2\2\2Z[\7+\2\2[\24\3\2\2\2\\]\7}\2\2]\26\3\2\2\2^_\7\177\2\2_\30\3\2"+
		"\2\2`a\7]\2\2a\32\3\2\2\2bc\7_\2\2c\34\3\2\2\2df\5\37\20\2ed\3\2\2\2f"+
		"g\3\2\2\2ge\3\2\2\2gh\3\2\2\2h\36\3\2\2\2ij\4\62;\2j \3\2\2\2kl\7<\2\2"+
		"l\"\3\2\2\2mn\7.\2\2n$\3\2\2\2op\7g\2\2pq\7o\2\2qr\7r\2\2r&\3\2\2\2su"+
		"\t\2\2\2ts\3\2\2\2uv\3\2\2\2vt\3\2\2\2vw\3\2\2\2wx\3\2\2\2xy\b\24\2\2"+
		"y(\3\2\2\2\7\2;Ogv\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}