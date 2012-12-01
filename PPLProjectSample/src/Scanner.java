import java.io.*;
import java.util.*;

public class Scanner {

	private static int nError = 0;

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<SymbolItem> SI = new ArrayList<SymbolItem>();
		ArrayList<String> Derived=new ArrayList<String>();
		
		
		try {
			File rFile = new File("input.txt");
			BufferedReader Reader = new BufferedReader(new FileReader(rFile));
			// ***************************

			int lineNumber = 0;
			String nameValue = "";
			char readChar;
			String tempString;

			

			System.out.println("\n进行词法分析  -->-->-->-->-->-->-->--> ");

			while ((tempString = Reader.readLine()) != null) {
				lineNumber++;
				for (int i = 0; i < tempString.length(); i++) {
					readChar = tempString.charAt(i);
					if (iscsymf(readChar)) {
						nameValue="";
						do {
							nameValue += readChar;
							readChar = tempString.charAt(++i);
							if (i >= tempString.length())
								break;
						} while (iscsymf(readChar) || isdigit(readChar));
						i--;

						SymbolItem newItem = new SymbolItem();
						newItem.lineNumber = lineNumber;
						if ((newItem.type = GetReserveWord(nameValue)) == null) {
							newItem.type = SYMBOL.IDENT;
							newItem.lpValue = nameValue;
						}
						SI.add(newItem);

					} else if (isdigit(readChar)) {
						nameValue="";
						do {
							nameValue += readChar;
							readChar = tempString.charAt(++i);
							if (i >= tempString.length())
								break;
						} while (isdigit(readChar));
						i--;

						AddSymbolNode(SI, lineNumber, SYMBOL.INTCON, Integer
								.parseInt(nameValue));
					} else
						switch (readChar) {
						case '	':
						case ' ':
							break;
						case '\n':
							break;
						case '\r':
							lineNumber++;
							break;
//						case ':':
//							if (i >= tempString.length())
//								break;
//							readChar = tempString.charAt(++i);
//							if (readChar == '=')
//								AddSymbolNode(SI, lineNumber, SYMBOL.BECOMES, 0);
//							else {
//								i--;
//								AddSymbolNode(SI, lineNumber, SYMBOL.COLON, 0);
//							}
//							break;
						case '<':
							if (i >= tempString.length())
								break;
							readChar = tempString.charAt(++i);
							if (readChar == '=')
								AddSymbolNode(SI, lineNumber, SYMBOL.LEQ, 0);
							else if (readChar == '>')
								AddSymbolNode(SI, lineNumber, SYMBOL.NEQ, 0);
							else {
								i--;
								AddSymbolNode(SI, lineNumber, SYMBOL.LSS, 0);
							}
							break;
						case '>':
							if (i >= tempString.length())
								break;
							readChar = tempString.charAt(++i);
							if (readChar == '=')
								AddSymbolNode(SI, lineNumber, SYMBOL.GEQ, 0);
							else {
								i--;
								AddSymbolNode(SI, lineNumber, SYMBOL.GTR, 0);
							}
							break;
//						case '.':
//							if (i >= tempString.length()) {
//								AddSymbolNode(SI, lineNumber, SYMBOL.PERIOD, 0);
//								break;
//							}
//							readChar = tempString.charAt(++i);
//							if (readChar == '.')
//								AddSymbolNode(SI, lineNumber, SYMBOL.DPOINT, 0);
//							else {
//								i--;
//								AddSymbolNode(SI, lineNumber, SYMBOL.PERIOD, 0);
//							}
//							break;
//						case '\'':
//							readChar = tempString.charAt(++i);
//							if (i >= tempString.length())
//								break;
//							if (tempString.charAt(++i) == '\'')
//								AddSymbolNode(SI, lineNumber, SYMBOL.CHARCON,
//										readChar);
//							else
//								error(1);//////////
//								break;
						case '!':
							if (i >= tempString.length()) {
								AddSymbolNode(SI, lineNumber, SYMBOL.CONVERT, 0);
								break;
							}
							readChar = tempString.charAt(++i);
							if (readChar == '=')
								AddSymbolNode(SI, lineNumber, SYMBOL.UNEQUAL, 0);
							else {
								i--;
								AddSymbolNode(SI, lineNumber, SYMBOL.CONVERT, 0);
							}
							break;
						case '+':
							AddSymbolNode(SI, lineNumber, SYMBOL.PLUS, 0);
							break;
						case '-':
							AddSymbolNode(SI, lineNumber, SYMBOL.MINUS, 0);
							break;
						case '*':
							AddSymbolNode(SI, lineNumber, SYMBOL.TIMES, 0);
							break;
						case '/':
							AddSymbolNode(SI, lineNumber, SYMBOL.DIVSYM, 0);
							break;
						case '(':
							AddSymbolNode(SI, lineNumber, SYMBOL.LPAREN, 0);
							break;
						case ')':
							AddSymbolNode(SI, lineNumber, SYMBOL.RPAREN, 0);
							break;
						case '=':
							if (i >= tempString.length())
								break;
							readChar = tempString.charAt(++i);
							if (readChar == '=')
								AddSymbolNode(SI, lineNumber, SYMBOL.EQL, 0);
							else {
								i--;
								AddSymbolNode(SI, lineNumber, SYMBOL.BECOMES, 0);
							}
							break;
//							AddSymbolNode(SI, lineNumber, SYMBOL.EQL, 0);
//							break;
						case '|':
							if (i >= tempString.length())
								break;
							readChar = tempString.charAt(++i);
							if (readChar == '|')
								AddSymbolNode(SI, lineNumber, SYMBOL.OFSYM, 0);
							else {
								error(1);//////////
								break;
							}
//							AddSymbolNode(SI, lineNumber, SYMBOL.EQL, 0);
//							break;
						case '&':
							if (i >= tempString.length())
								break;
							readChar = tempString.charAt(++i);
							if (readChar == '&')
								AddSymbolNode(SI, lineNumber, SYMBOL.ANDSYM, 0);
							else {
								error(1);//////////
								break;
							}
						case '[':
							AddSymbolNode(SI, lineNumber, SYMBOL.LBRACK, 0);
							break;
						case ']':
							AddSymbolNode(SI, lineNumber, SYMBOL.RBRACK, 0);
							break;
						case '{':
							AddSymbolNode(SI, lineNumber, SYMBOL.LBRACE, 0);
							break;
						case '}':
							AddSymbolNode(SI, lineNumber, SYMBOL.RBRACE, 0);
							break;
						case ';':
							AddSymbolNode(SI, lineNumber, SYMBOL.SEMICOLON, 0);
							break;
						case ',':
							AddSymbolNode(SI, lineNumber, SYMBOL.COMMA, 0);
							break;
						default:
							error(38);
						}
				}
			}

			// Symbols=head.next;
			// CurSymbol=Symbols;

			if (nError != 0) {
				System.out.println(nError + " errors found.");
				System.exit(2);
			} else
				System.out.println("词法分析成功！\n\n");
				for(int i=0;i<SI.size();i++)
					System.out.println(SI.get(i).type.name()+":\t"+SI.get(i).iValue+"\t"+SI.get(i).lpValue);
			// ***************************
			Reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		

		Derived.add("program");	//Initial Derived String

		int pos=0;
		int input_pointer=0;
		int i,j;
		
		//****************************************************
//		File rFile = new File("Parsing table.txt");
//		BufferedReader Reader = new BufferedReader(new FileReader(rFile));
//		
//		String tempLine;
//		while((tempLine=Reader.readLine())!=null)
//		{
//			String[] tempLineString=tempLine.split(",");
//			for(int m=0;m<tempLineString.length;m++)
//				;
//		}
//		colume=tempLine.length;
		
		String parser_table[][]={
				{"", "id", "basic", "if", "while", "do", "break", "else", "num", "real", "true", "false", "[", "]", "{", "}", "(", ")", "||", "&&", "=", "!=", "==", "<", "<=", ">", ">=",	"+", "-", "*", "/", "!", ";", "$"},
				{"program", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "block", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n"},
				{"block", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "{ decls stmts }", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n"},
				{"decls", "decls'", "decls'", "decls'", "decls'", "decls'", "decls'", "n", "n", "n", "n", "n", "n", "n", "decls'", "decls'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n"},
				{"decls'", "", "decl decls'", "n", "", "", "", "n", "n", "n", "n", "n", "n", "n", "", "", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n"},
				{"decl", "n", "type id ;", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n"},
				{"type", "n", "basic type'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n"},
				{"type'", "", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "[ num ] type'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "n", "n", "n", "n", "n", "n"},
				{"stmts", "stmts'", "n", "stmts'", "stmts'", "stmts'", "stmts'", "n", "n", "n", "n", "n", "n", "n", "stmts'", "stmts'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "n", "n", "n", "n", "n", "n"},
				{"stmts'", "stmt stmts'", "n", "stmt stmts'", "stmt stmts'", "stmt stmts'", "stmt stmts'", "n", "n", "n", "n", "n", "n", "n", "stmt stmts'", "", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "n", "n", "n", "n", "n", "n"},
				{"stmt", "loc = bool ;", "n", "if ( bool ) stmt stmt'", "while ( bool ) stmt", "do stmt while ( bool ) ;", "break ;", "n", "n", "n", "n", "n", "n", "n", "block", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "n", "n", "n", "n", "n", "n"},
				{"stmt'", "stmt stmts'", "n", "stmt stmts'", "stmt stmts'", "stmt stmts'", "stmt stmts'", "n", "n", "n", "n", "n", "n", "n", "stmt stmts'", "", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "n", "n", "n", "n", "n", "n"},
				{"loc", "id loc'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n"},
				{"loc'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "[ bool ] loc'", "", "n", "n", "n", "", "", "", "", "", "", "", "", "", "",	"", "", "", "", "n", "", "n"},
				{"bool", "join bool'", "n", "n", "n", "n", "n", "n", "join bool'", "join bool'", "join bool'", "join bool'", "n", "n", "n", "n", "join bool'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "join bool'", "n", "n", "join bool'", "n", "n"},
				{"bool'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "", "n", "n", "n", "n", "", "|| join bool'", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "n", "n", "n", "n", "", "n"},
				{"join", "equality join'", "n", "n", "n", "n", "n", "n", "equality join'", "equality join'", "equality join'", "equality join'", "n", "n", "n", "n", "equality join'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "equality join'", "n", "n", "equality join'", "n", "n"},
				{"join'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "", "n", "n", "n", "", "", "&& equality join'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "", "n"},
				{"equality", "rel equality'", "n", "n", "n", "n", "n", "n", "rel equality'", "rel equality'", "rel equality'", "rel equality'", "n", "n", "n", "n", "rel equality'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "rel equality'", "n", "n", "rel equality'", "n", "n"},
				{"equality'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "", "n", "n", "n", "", "", "", "n", "!= rel equality'", "== rel equality'", "n", "n", "n", "n",	"n", "n", "n", "n", "n", "", "n"},
				{"rel", "expr rel'", "n", "n", "n", "n", "n", "n", "expr rel'", "expr rel'", "expr rel'", "expr rel'", "n", "n", "n", "n", "expr rel'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "expr rel'", "n", "n", "expr rel'", "n", "n"},
				{"rel'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "", "n", "n", "n", "", "", "", "n", "", "", "< expr ", "<= expr ", "> expr ", ">= expr ",	"n", "n", "n", "n", "n", "", "n"},
				{"expr", "term expr'", "n", "n", "n", "n", "n", "n", "term expr'", "term expr'", "term expr'", "term expr'", "n", "n", "n", "n", "term expr'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "term expr'", "n", "n", "term expr'", "n", "n"},
				{"expr'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "", "n", "n", "n", "", "", "", "n", "", "", "", "", "", "",	"+ term  expr'", "- term  expr'", "n", "n", "n", "", "n"},
				{"term", "unary term'", "n", "n", "n", "n", "n", "n", "unary term'", "unary term'", "unary term'", "unary term'", "n", "n", "n", "n", "unary term'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "unary term'", "n", "n", "unary term'", "n", "n"},
				{"term'", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "", "n", "n", "n", "", "", "", "n", "", "", "", "", "", "",	"", "", "* unary term'", "/ unary term'", "n", "", "n"},
				{"unary", "factor", "n", "n", "n", "n", "n", "n", "factor", "factor", "factor", "factor", "n", "n", "n", "n", "factor", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "- unary", "n", "n", "! unary", "n", "n"},
				{"factory", "loc ", "n", "n", "n", "n", "n", "n", "num", "real", "true", "false", "n", "n", "n", "n", "( bool )", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",	"n", "n", "n", "n", "n", "n", "n"}};
		System.out.println("Lookahead\t\tDerived");
		//*****************************************************
		
		while(true)
		{
			pos=0;
			while(!Derived.get(pos).equals("program") && 
				!Derived.get(pos).equals("block") && 
				!Derived.get(pos).equals("decls") && 
				!Derived.get(pos).equals("decls'") && 
				!Derived.get(pos).equals("decl") &&
				!Derived.get(pos).equals("type") &&
				!Derived.get(pos).equals("type'") &&
				!Derived.get(pos).equals("stmts") &&
				!Derived.get(pos).equals("stmts'") &&
				!Derived.get(pos).equals("stmt") &&
				!Derived.get(pos).equals("stmt'") &&
				!Derived.get(pos).equals("loc") &&
				!Derived.get(pos).equals("loc'") &&
				!Derived.get(pos).equals("bool") &&
				!Derived.get(pos).equals("bool'") &&
				!Derived.get(pos).equals("join") &&
				!Derived.get(pos).equals("join'") &&
				!Derived.get(pos).equals("equality") &&
				!Derived.get(pos).equals("equality'") &&
				!Derived.get(pos).equals("rel") &&
				!Derived.get(pos).equals("rel'") &&
				!Derived.get(pos).equals("expr") &&
				!Derived.get(pos).equals("expr'") &&
				!Derived.get(pos).equals("term") &&
				!Derived.get(pos).equals("term'") &&
				!Derived.get(pos).equals("unary") &&
				!Derived.get(pos).equals("factory"))
			{
				pos++;
				if(pos>=Derived.size())break;
			}
			
			input_pointer=pos;
			
			if(pos>=Derived.size())
				break;
			
			for(i=1;i<28;i++)
				if(Derived.get(pos).equals(parser_table[i][0]))
					break;
			
			//Converting**********************
			String tempString="";
			switch (SI.get(input_pointer).type){
			case IDENT:
				tempString="id";
				break;
			case TIMES:
				tempString="*";
				break;
			case DIVSYM:
				tempString="/";
				break;
			case MINUS:
				tempString="-";
				break;
			case PLUS:
				tempString="+";
				break;
			case LSS:
				tempString="<";
				break;
			case LEQ:
				tempString="<=";
				break;
			case GTR:
				tempString=">";
				break;
			case GEQ:
				tempString=">=";
				break;
			case SEMICOLON:
				tempString=";";
				break;
			case IFSYM:
				tempString="if";
				break;
			case THENSYM:
				tempString="then";
				break;
			case ELSESYM:
				tempString="else";
				break;
			case WHILESYM:
				tempString="while";
				break;
			case DOSYM:
				tempString="do";
				break;
			case TYPESYM:
				tempString="type";
				break;
			case BASICSYM:
				tempString="basic";
				break;
			case BREAKSYM:
				tempString="break";
				break;
			case REALSYM:
				tempString="real";
				break;
			case INTCON:
				tempString="num";
				break;
			case TRUESYM:
				tempString="true";
				break;
			case FALSESYM:
				tempString="false";
				break;
			case LBRACE:
				tempString="{";
				break;
			case RBRACE:
				tempString="}";
				break;
			case LBRACK:
				tempString="[";
				break;
			case RBRACK:
				tempString="]";
				break;
			case LPAREN:
				tempString="(";
				break;
			case RPAREN:
				tempString=")";
				break;
			case UNEQUAL:
				tempString="!=";
				break;
			case CONVERT:
				tempString="!";
				break;
			case ANDSYM:
				tempString="&&";
				break;
			case ORSYM:
				tempString="||";
				break;
			case BECOMES:
				tempString="=";
				break;
			case EQL:
				tempString="==";
				break;
			default:
				error(1);
			}
			
			
			//*********************************
			
			for(j=1;j<34;j++)
				if(tempString.equals(parser_table[0][j]))
					break;
			
			/**********************
			****Exception Alarm****
			**********************/
			if(parser_table[i][j].equals("n"))
			{
				System.out.println("Production does not exits: "+parser_table[i][j]);
				System.exit(0);
			}
			
			//***********************
			Derived.remove(pos);
			//*************Handle add-in***********
			String[] addin=parser_table[i][j].split(" ");
			for(int index=0;index<addin.length;index++)
			{
				if(!addin[index].equals(""))
					Derived.add(pos+index, addin[index]);
			}
			
			
			//System.out.println(SI.get(input_pointer).type.name()+"\t\t"+Derived);
			System.out.println(Derived);
			
		}
		
		
	}

	private static boolean iscsymf(char intchar) {
		if ((intchar >= 97 && intchar <= 122)
				|| (intchar >= 65 && intchar <= 90) || intchar == 95)
			return true;
		else
			return false;
	}

	private static boolean isdigit(char intchar) {
		if (intchar >= 48 && intchar <= 57)
			return true;
		else
			return false;
	}

	private static SYMBOL GetReserveWord(String nameValue) {
		String[] reserveWord = { "and", "begin", "const", "else", "if", "not",
				"or", "program", "type", "while", "array", "call", "do", "end",
				"mod", "of", "procedure", "then", "basic", "break", 
				"real", "true", "false"};
		SYMBOL[] reserveType = { SYMBOL.ANDSYM, SYMBOL.BEGINSYM,
				SYMBOL.CONSTSYM, SYMBOL.ELSESYM, SYMBOL.IFSYM, SYMBOL.NOTSYM,
				SYMBOL.ORSYM, SYMBOL.PROGRAMSYM, SYMBOL.TYPESYM,
				SYMBOL.WHILESYM, SYMBOL.ARRAYSYM, SYMBOL.CALLSYM, SYMBOL.DOSYM,
				SYMBOL.ENDSYM, SYMBOL.MODSYM, SYMBOL.OFSYM, SYMBOL.PROCSYM,
				SYMBOL.THENSYM, SYMBOL.BASICSYM, SYMBOL.BREAKSYM, 
				SYMBOL.REALSYM, SYMBOL.TRUESYM, SYMBOL.FALSESYM};

		for (int i = 0; i < reserveWord.length; i++)
			if (reserveWord[i].compareToIgnoreCase(nameValue) == 0)
				return reserveType[i];
		return null;
	}

	private static void AddSymbolNode(ArrayList<SymbolItem> SI, int lineNumber,
			SYMBOL type, int iValue) {
		if (!SI.add(new SymbolItem(lineNumber, type, iValue))) {
			System.out.print("Error!");
			System.exit(0);
		}
	}

	private static void error(int errCode) // 负责显示出错信息的函数
	{
		String errorScript[] = { "应该是\':\'",// 0
				"应该是\';\'",// 1
				"应该是\')\'",// 2
				"应该是\'(\'",// 3
				"应该是\'[\'",// 4
				"应该是\']\'",// 5
				"应该是\'=\'",// 6
				"应该是\':=\'",// 7
				"应该是\'.\'",// 8
				"应该是\'do\'",// 9
				"应该是\'of\'",// 10
				"应该是\'then\'",// 11
				"应该是\'end\'",// 12
				"应该是\'program\'",// 13
				"应该是标识符",// 14
				"应该是类型标识符",// 15
				"应该是变量",// 16
				"应该是常量或常量标识符",// 17
				"应该是过程名",// 18
				"数组上下界大小关系错误",// 19
				"数组定义时，下标元素类型错误",// 20
				"数组定义时，上下界类型不一致",// 21
				"引用时，数组元素下标元素类型出错",// 22
				"下标个数不正确",// 23
				"数组表溢出",// 24
				"名字表溢出",// 25
				"程序体表溢出",// 26
				"系统为本编译程序分配的堆不够用",// 27
				"实参与形参个数不等",// 28
				"实参与形参类型不一致",// 29
				"实参个数不够",// 30
				"程序体内符号重定义",// 31
				"if或while后面表达式必须为布尔类型",// 32
				"标识符没有定义",// 33
				"过程名或类型名不能出现在表达式中",// 34
				"操作数类型错误",// 35
				"类型定义出错",// 36
				"类型不一致",// 37
				"不认识的字符出现在源程序",// 38
				"不能打开.lst文件",// 39
				"不能打开.pld文件",// 40
				"不能打开.lab文件",// 41
				"应该是\'..\'",// 42
				"分析时缺少标识符",// 43
		};

		// if(CurSymbol && CurSymbol->lineNumber>0)
		// printf("\n<<<<<<  Line number %d ",CurSymbol->lineNumber);
		// printf("found error %d : %s !  >>>>>>\n\n",errCode,errorScript[errCode]);
		nError++;
	}
}
