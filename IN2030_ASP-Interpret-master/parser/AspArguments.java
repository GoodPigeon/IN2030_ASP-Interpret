package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspArguments extends AspPrimarySuffix {
  ArrayList<AspExpr> exprs = new ArrayList<>();

  AspArguments(int n) {
    super(n);
  }

  static AspArguments parse(Scanner s) {
    enterParser("arguments");
    AspArguments aa = new AspArguments(s.curLineNum());
    skip(s, leftParToken);

    if(s.curToken().kind != rightParToken) {
      while(true){
        aa.exprs.add(AspExpr.parse(s));
        if(s.curToken().kind != commaToken) break;
        skip(s, commaToken);
      }
    }
    skip(s, rightParToken);
    leaveParser("arguments");
    return aa;
  }

  @Override
  void prettyPrint() {
    prettyWrite("(");
    int nPrinted = 0;
    for(AspExpr ae : exprs) {
      if(nPrinted > 0) {
        prettyWrite(",");
      }
      ae.prettyPrint();
      ++nPrinted;
    }
    prettyWrite(")");
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    ArrayList<RuntimeValue> parameters = new ArrayList<>();
    RuntimeListValue rlv = null;
    RuntimeValue rv = null;

    if(!exprs.isEmpty()){
      for(int i = 0; i < exprs.size(); i++){
        parameters.add(exprs.get(i).eval(curScope));
      }
    }
    return new RuntimeListValue(parameters);
  }
}
