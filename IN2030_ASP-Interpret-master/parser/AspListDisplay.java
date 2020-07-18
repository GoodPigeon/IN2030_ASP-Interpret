package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspListDisplay extends AspAtom{
  ArrayList<AspExpr> exprs = new ArrayList<>();

  AspListDisplay(int n){
    super(n);
  }

  static AspListDisplay parse(Scanner s){
    AspListDisplay ald = new AspListDisplay(s.curLineNum());
    enterParser("list display");
    skip(s, leftBracketToken);
    if(s.curToken().kind != rightBracketToken){
      while(true){
        ald.exprs.add(AspExpr.parse(s));
        if(s.curToken().kind != commaToken) break;
        skip(s, commaToken);
      }
    }
    skip(s, rightBracketToken);
    leaveParser("list display");
    return ald;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("[");
    if(exprs.size() > 0){
      int nPrinted = 0;
      for(AspExpr ae : exprs){
        if(nPrinted > 0){
          prettyWrite(",");
        }
        ae.prettyPrint();
        ++nPrinted;
      }
    }
    prettyWrite("]");
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    ArrayList<RuntimeValue> rtvalues = new ArrayList<>();

    for(int i = 0; i < exprs.size(); i++){
      rtvalues.add(exprs.get(i).eval(curScope));
    }

    RuntimeListValue v = new RuntimeListValue();
    for(RuntimeValue x : rtvalues){
      v.addToList(x);
    }
    return v;
  }
}
