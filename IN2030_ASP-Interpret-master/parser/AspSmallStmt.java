package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspSmallStmt extends AspSyntax{

  AspSmallStmt(int n){
    super(n);
  }

  static AspSmallStmt parse(Scanner s){
    enterParser("small stmt");

    //Assignment and expr stmt
    if(s.curToken().kind == nameToken){
      //Assignment
      if(s.anyEqualToken()){
        AspSmallStmt ass = AspAssignment.parse(s);
        leaveParser("small stmt");
        return ass;
      }

      //expr stmt
      else if(!s.anyEqualToken()){
        AspSmallStmt ass = AspExprStmt.parse(s);
        leaveParser("small stmt");
        return ass;
      }
    }

    //pass stmt
    else if(s.curToken().kind == passToken){
      AspSmallStmt ass = AspPassStmt.parse(s);
      leaveParser("small stmt");
      return ass;
    }

    //return stmt
    else if(s.curToken().kind == returnToken){
      AspSmallStmt ass = AspReturnStmt.parse(s);
      leaveParser("small stmt");
      return ass;
    }

    return null;
  }
}
