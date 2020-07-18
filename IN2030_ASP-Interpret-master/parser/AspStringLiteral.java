package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspStringLiteral extends AspAtom{
  String s;

  AspStringLiteral(int n){
    super(n);
  }

  static AspStringLiteral parse(Scanner s){
    AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
    enterParser("string literal");
    asl.s = s.curToken().stringLit;
    skip(s, stringToken);
    leaveParser("string literal");
    return asl;
  }

  @Override
  public void prettyPrint() {
    prettyWrite('"'+s+'"');
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
     return new RuntimeStringValue(s);
  }
}
