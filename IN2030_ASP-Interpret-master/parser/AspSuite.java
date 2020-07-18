package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSuite extends AspSyntax{
  ArrayList<AspStmt> stmts = new ArrayList<>();
  AspSmallStmtList assl = null;


  AspSuite(int n){
    super(n);
  }

  static AspSuite parse(Scanner s){
    enterParser("suite");
    AspSuite as = new AspSuite(s.curLineNum());
    if(s.curToken().kind == newLineToken){
      skip(s, newLineToken);
      skip(s, indentToken);
      while(s.curToken().kind != dedentToken){
        as.stmts.add(AspStmt.parse(s));
      }
      skip(s, dedentToken);
    }
    else{
      as.assl = AspSmallStmtList.parse(s);
    }
    leaveParser("suite");
    return as;
  }

  @Override
  public void prettyPrint() {
    if(assl == null){
      prettyWriteLn();
      prettyIndent();
      for(AspStmt s : stmts){
        s.prettyPrint();
      }
      prettyDedent();
    }
    else{
      assl.prettyPrint();
    }
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    if(assl != null){
      RuntimeValue v = assl.eval(curScope);
      return v;
    }
    else{
      RuntimeValue v = null;
      for(AspStmt s : stmts){
        v = s.eval(curScope);
      }
      return v;
    }
  }
}
