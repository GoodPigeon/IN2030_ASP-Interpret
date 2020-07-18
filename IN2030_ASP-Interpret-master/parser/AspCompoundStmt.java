package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspCompoundStmt extends AspStmt{


  AspCompoundStmt(int n){
    super(n);
  }

  static AspCompoundStmt parse(Scanner s){
    enterParser("compound stmt");

    //for stmt
    if(s.curToken().kind == forToken){
      AspCompoundStmt acs = AspForStmt.parse(s);
      leaveParser("compound stmt");
      return acs;
    }

    //if stmt
    else if(s.curToken().kind == ifToken){
      AspCompoundStmt acs = AspIfStmt.parse(s);
      leaveParser("compound stmt");
      return acs;
    }

    //while stmt
    else if(s.curToken().kind == whileToken){
      AspCompoundStmt acs = AspWhileStmt.parse(s);
      leaveParser("compound stmt");
      return acs;
    }

    //func def
    else if(s.curToken().kind == defToken){
      AspCompoundStmt acs = AspFuncDef.parse(s);
      leaveParser("compound stmt");
      return acs;
    }

    return null;
  }
}
