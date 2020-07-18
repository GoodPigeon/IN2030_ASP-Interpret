package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspStmt extends AspSyntax{


  AspStmt(int n){
    super(n);
  }

  static AspStmt parse(Scanner s){
    enterParser("stmt");

    if(s.curToken().kind == ifToken ||
      s.curToken().kind == forToken ||
      s.curToken().kind == whileToken ||
      s.curToken().kind == defToken){
        AspStmt as = AspCompoundStmt.parse(s);
        leaveParser("stmt");
        return as;
    }
    else{
      AspStmt as = AspSmallStmtList.parse(s);
      leaveParser("stmt");
      return as;
    }
  }
}
