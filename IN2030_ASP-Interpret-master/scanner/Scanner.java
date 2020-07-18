package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
  private LineNumberReader sourceFile = null;
  private String curFileName;
  private ArrayList<Token> curLineTokens = new ArrayList<>();
  //Lists of special cases with rules for tokens
  private char[] specialChars = {'!', '<', '>', '='};
  private char[] parenthesisChars = {'(', ')', '[', ']', '{', '}'};
  private Stack<Integer> indents = new Stack<>();
  private final int TABDIST = 4;
  private Boolean skip = false;

  public Scanner(String fileName) {
    curFileName = fileName;
	  indents.push(0);
    try {
      sourceFile = new LineNumberReader(
                    new InputStreamReader(
                    new FileInputStream(fileName),
                    "UTF-8"));
    } catch (IOException e) {
      scannerError("Cannot read " + fileName + "!");
    }
  }

  private void scannerError(String message) {
    String m = "Asp scanner error";
    if (curLineNum() > 0)
    m += " on line " + curLineNum();
    m += ": " + message;
    Main.error(m);
  }

  public Token curToken() {
    while (curLineTokens.isEmpty()) {
      readNextLine();
    }
    return curLineTokens.get(0);
  }

  public void readNextToken() {
    if (! curLineTokens.isEmpty()) curLineTokens.remove(0);
  }

  private void readNextLine() {
    curLineTokens.clear();

    // Read the next line:
    String line = null;
    try {
      line = sourceFile.readLine();
      if (line == null) {
        sourceFile.close();
        sourceFile = null;
      } else {
        Main.log.noteSourceLine(curLineNum(), line);
      }
    } catch (IOException e) {
      sourceFile = null;
      scannerError("Unspecified I/O error!");
    }

    //-- Must be changed in part 1:
    // Checks if line is not Null, if not, then create tokens
    if(line != null){
      int pos = 0;
      String indentStr = expandLeadingTabs(line);
      int i = 0;
      Character ws;
      try{
        while(Character.isWhitespace(line.charAt(i))){
          i++;
        }
        if(line.charAt(i) == '#'){
          skip = true;
        }
        else{
          indentDedent(line);
        }
      }catch(Exception e){}

      while(pos < line.length()) {
        char c = line.charAt(pos++);
        String word = "";
        String number = "";
        String specialString = "";
        String str = "";

        // Checks if #, which means it is a comment
        if (c == '#' && line.charAt(pos-1) != '"') {
          // stops reading line if true
          break;
        }

        // String literal
        // if current character equals " or ' it will start making a string-token.
        if (c == '"' || c == '\'') {
          try{
            boolean strlit = true;
            while(strlit) {
              if (line.charAt(pos) == '"' || line.charAt(pos) == '\'') {
                strlit = false;
                pos+=1;
                break;
              }
              str += line.charAt(pos++);
            }

            if(strlit == false) {
              Token st = new Token(stringToken,(curLineNum()));
              st.stringLit = str;
              curLineTokens.add(st);
              str="";
            }
          } catch(Exception e){
          }
        }

        //KEYWORDS
        if(isLetterAZ(c)) {
          word += c;
          try {
            while ((isLetterAZ(line.charAt(pos)) || isDigit(line.charAt(pos)))){
              word += line.charAt(pos++);
            }
              // Checks if keyword
            for(TokenKind tk : EnumSet.range(andToken, whileToken)) {
              if(tk.toString().equals(word)) {
                curLineTokens.add(new Token(tk, curLineNum()));
                word="";
              }
            }
          } catch(Exception e) {}
        }

        //NameTokens
        for(TokenKind tk : EnumSet.range(andToken, whileToken)) {
          if(tk.toString().equals(word)) {
            curLineTokens.add(new Token(tk, curLineNum()));
            word="";
          }
        }
        if(word != ""){
          Token nt = new Token(nameToken,(curLineNum()));
          nt.name = word;
          curLineTokens.add(nt);
          word = "";
        }

        //Integer and float token
        if(isDigit(c)){
          Boolean isFloat = false;
          number += c;
          try{
            while(isDigit(line.charAt(pos)) || line.charAt(pos) == '.'){
              if(line.charAt(pos) == '.'){
                isFloat = true;
              }
              number += line.charAt(pos++);
            }
            if(isFloat == true){
              Token ft = new Token(floatToken,(curLineNum()));
              ft.floatLit = Double.parseDouble(number);
              curLineTokens.add(ft);
              number = "";
            }
            if(isFloat == false){
              Token it = new Token(integerToken,(curLineNum()));
              it.integerLit = Integer.parseInt(number);
              curLineTokens.add(it);
              number = "";
            }
          } catch(Exception e) {}

            //Last digit
            if(number != ""){
              if(isFloat == true){
                Token ft = new Token(floatToken,(curLineNum()));
                ft.floatLit = Double.parseDouble(number);
                curLineTokens.add(ft);
                number = "";
              }
              if(isFloat == false){
                Token it = new Token(integerToken,(curLineNum()));
                it.integerLit = Integer.parseInt(number);
                curLineTokens.add(it);
                number = "";
              }
            }
        }

        //Operators and delimiters
        if(c != ' ' && !isLetterAZ(c) && !isDigit(c)){
          Boolean isFinished = false;

          //Brace, Par and Bracket-tokens
          for(char pc : parenthesisChars){
            if(c == pc){
              for(TokenKind tk : EnumSet.range(leftBraceToken, rightParToken)) {
                specialString = String.valueOf(c);
                if(tk.toString().equals(specialString)) {
                  curLineTokens.add(new Token(tk, curLineNum()));
                  specialString = "";
                  isFinished = true;
                }
              }
            }
          }

          //equalTokens
          for(char sc : specialChars){
            if(c == sc){
              try{
                if(line.charAt(pos++) == '='){
                  for(TokenKind tk : EnumSet.range(doubleEqualToken, notEqualToken)) {
                    specialString = String.valueOf(c) + '=';
                    if(tk.toString().equals(specialString)) {
                      curLineTokens.add(new Token(tk, curLineNum()));
                      specialString = "";
                      isFinished = true;
                    }
                  }
                }
                else{
                  pos--;
                }
              }catch(Exception e){}
            }
          }

          //doubleSlashToken
          if(c == '/'){
            try{
              if(line.charAt(pos++) == '/'){
                curLineTokens.add(new Token(doubleSlashToken, curLineNum()));
                isFinished = true;
              }
              else{
                pos--;
              }
            }catch(Exception e){}
          }

          //The rest of the delimiters
          if(!isFinished){
            for(TokenKind tk : EnumSet.range(astToken, semicolonToken)) {
              specialString = String.valueOf(c);
              if(tk.toString().equals(specialString)) {
                curLineTokens.add(new Token(tk, curLineNum()));
                specialString = "";
                isFinished = true;
              }
            }
          }
        }
      }

      // Terminate line:
      if (!line.isEmpty() && !skip){
        if(line.charAt(0) != '#') {
          curLineTokens.add(new Token(newLineToken,curLineNum()));
        }
      }

      //if line == null, creates eofToken
    } else {
      while(!indents.isEmpty()){
        if(indents.pop()>0){
          curLineTokens.add(new Token(TokenKind.dedentToken));
        }
      }
      curLineTokens.add(new Token(TokenKind.eofToken));
    }
    skip = false;

    for (Token t: curLineTokens)
    Main.log.noteToken(t);

    for (Token tk : curLineTokens) {
      System.out.println(tk.kind.image);
    }
  }

  public int curLineNum() {
    return sourceFile!=null ? sourceFile.getLineNumber() : 0;
  }

  private void indentDedent(String line) {
    String tabs = expandLeadingTabs(line);
    int n = findIndent(tabs);

    // indents from the previous line.
    if(n > indents.peek()) {
      indents.push(n);
      curLineTokens.add(new Token(indentToken,curLineNum()));
    }
    else{
      while(n < indents.peek()){
        indents.pop();
        curLineTokens.add(new Token(dedentToken,curLineNum()));
      }
    }
    if(n!= indents.peek()){
      System.out.println("Wrong indents");
    }
  }

  private int findIndent(String s) {
    int indent = 0;
    while (indent<s.length() && s.charAt(indent)==' ') indent++;
    return indent;
  }

  private String expandLeadingTabs(String s) {
    String newS = "";
    for (int i = 0;  i < s.length();  i++) {
      char c = s.charAt(i);
	    if (c == '\t') {
        do {
          newS += " ";
        } while (newS.length()%TABDIST > 0);
      } else if (c == ' ') {
        newS += " ";
      } else {
        newS += s.substring(i);
        break;
      }
    }
    return newS;
  }

  private boolean isLetterAZ(char c) {
    return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
  }

  private boolean isDigit(char c) {
    return '0'<=c && c<='9';
  }

  public boolean isCompOpr() {
    TokenKind k = curToken().kind;
    if(k == lessToken || k == greaterToken || k == doubleEqualToken || k == greaterEqualToken || k == lessEqualToken || k == notEqualToken){
      return true;
    }
    return false;
  }

  public boolean isFactorPrefix() {
    TokenKind k = curToken().kind;
    if(k == plusToken || k == minusToken){
      return true;
    }

    return false;
  }

  public boolean isFactorOpr() {
    TokenKind k = curToken().kind;
    if(k == percentToken || k == slashToken || k == doubleSlashToken || k == astToken){
      return true;
    }
    return false;
  }

  public boolean isTermOpr() {
    TokenKind k = curToken().kind;
    if(k == plusToken || k == minusToken){
      return true;
    }
    return false;
  }

  public boolean anyEqualToken() {
    for (Token t: curLineTokens) {
      if (t.kind == equalToken) return true;
      if (t.kind == semicolonToken) return false;
    }
    return false;
  }
}
