package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspDictDisplay extends AspAtom{
  ArrayList<AspStringLiteral> stringLiterals = new ArrayList<>();
  ArrayList<AspExpr> exprs = new ArrayList<>();

  AspDictDisplay(int n){
    super(n);
  }

  static AspDictDisplay parse(Scanner s){
    AspDictDisplay add = new AspDictDisplay(s.curLineNum());
    enterParser("dict display");
    skip(s, leftBraceToken);


    if(s.curToken().kind != rightBraceToken){
      while(true){
        add.stringLiterals.add(AspStringLiteral.parse(s));
        skip(s, colonToken);
        add.exprs.add(AspExpr.parse(s));
        if(s.curToken().kind != commaToken) break;
        skip(s, commaToken);
      }
    }


    skip(s, rightBraceToken);
    leaveParser("dict display");
    return add;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("{");
    if(stringLiterals.size()>0){
      for(int i = 0; i<stringLiterals.size();i++){
        stringLiterals.get(i).prettyPrint();
        prettyWrite(":");
        exprs.get(i).prettyPrint();
      }
    }
    prettyWrite("}");
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    ArrayList<RuntimeValue> stringLiteralValues = new ArrayList<>();
    ArrayList<RuntimeValue> exprValues = new ArrayList<>();

    RuntimeDictValue v = null;
    for(int i = 0; i < stringLiterals.size(); i++){
      stringLiteralValues.add(stringLiterals.get(i).eval(curScope));
      exprValues.add(exprs.get(i).eval(curScope));
    }
    v = new RuntimeDictValue(stringLiteralValues, exprValues);

    return v;
  }
}
