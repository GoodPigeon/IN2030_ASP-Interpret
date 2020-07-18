package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspAtom extends AspStmt{


  AspAtom(int n){
    super(n);
  }

  static AspAtom parse(Scanner s){
    enterParser("atom");

    //name
    if(s.curToken().kind == nameToken){
      AspAtom aa = AspName.parse(s);
      leaveParser("atom");
      return aa;
    }

    //integer literal
    else if(s.curToken().kind == integerToken){
      AspAtom aa = AspIntegerLiteral.parse(s);
      leaveParser("atom");
      return aa;
    }

    //float literal
    else if(s.curToken().kind == floatToken){
      AspAtom aa = AspFloatLiteral.parse(s);
      leaveParser("atom");
      return aa;
    }

    //string literal
    else if(s.curToken().kind == stringToken){
      AspAtom aa = AspStringLiteral.parse(s);
      leaveParser("atom");
      return aa;
    }

    //Boolean literal
    else if(s.curToken().kind == trueToken || s.curToken().kind == falseToken){
      AspAtom aa = AspBooleanLiteral.parse(s);
      leaveParser("atom");
      return aa;
    }

    //none literal
    else if(s.curToken().kind == noneToken){
      AspAtom aa = AspNoneLiteral.parse(s);
      leaveParser("atom");
      return aa;
    }

    //inner expr
    else if(s.curToken().kind == leftParToken){
      AspAtom aa = AspInnerExpr.parse(s);
      leaveParser("atom");
      return aa;
    }

    //list display
    else if(s.curToken().kind == leftBracketToken){
      AspAtom aa = AspListDisplay.parse(s);
      leaveParser("atom");
      return aa;
    }

    //dict display
    else if(s.curToken().kind == leftBraceToken){
      AspAtom aa = AspDictDisplay.parse(s);
      leaveParser("atom");
      return aa;
    }
    return null;
  }
}
