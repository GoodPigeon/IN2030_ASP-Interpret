package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspNoneLiteral extends AspAtom{
  String noneString = "None";
  TokenKind kind;

  AspNoneLiteral(int n){
    super(n);
  }

  static AspNoneLiteral parse(Scanner s){
    AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
    enterParser("none literal");
    skip(s, noneToken);
    anl.kind = noneToken;
    leaveParser("none literal");
    return anl;
  }

  @Override
  public void prettyPrint() {
    prettyWrite(noneString);
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
     return null;
  }
}
