package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspInnerExpr extends AspAtom{
  AspExpr ae;

  AspInnerExpr(int n){
    super(n);
  }

  static AspInnerExpr parse(Scanner s){
    AspInnerExpr aie = new AspInnerExpr(s.curLineNum());
    enterParser("inner expr");
    skip(s, leftParToken);
    aie.ae = AspExpr.parse(s);
    skip(s, rightParToken);
    leaveParser("inner expr");
    return aie;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("(");
    ae.prettyPrint();
    prettyWrite(")");
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = ae.eval(curScope);
    return v;
  }
}
