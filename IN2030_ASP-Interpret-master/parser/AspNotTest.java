package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspNotTest extends AspSyntax{
  AspComparison c;
  Boolean hasNot = false;

  AspNotTest(int n){
    super(n);
  }

  static AspNotTest parse(Scanner s){
    enterParser("Not test");
    AspNotTest ant = new AspNotTest(s.curLineNum());
    if(s.curToken().kind == notToken){
      skip(s, notToken);
      ant.hasNot = true;
    }
    ant.c = AspComparison.parse(s);
    leaveParser("Not test");
    return ant;
  }



  @Override
  void prettyPrint(){
    if(hasNot){
      prettyWrite("not ");
    }
    c.prettyPrint();

  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = c.eval(curScope);
    if(hasNot){
      v = v.evalNot(this);
    }
    return v;
  }

}
