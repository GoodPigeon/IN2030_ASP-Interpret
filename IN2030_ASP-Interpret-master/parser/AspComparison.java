package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspComparison extends AspSyntax{
  ArrayList<AspTerm> terms = new ArrayList<>();
  ArrayList<AspCompOpr> compOprs = new ArrayList<>();


  AspComparison(int n){
    super(n);
  }

  static AspComparison parse(Scanner s){
    enterParser("comparison");
    AspComparison ac = new AspComparison(s.curLineNum());

    while(true){
      ac.terms.add(AspTerm.parse(s));
      if(s.isCompOpr()){
        ac.compOprs.add(AspCompOpr.parse(s));
      }
      else{
        break;
      }
    }

    leaveParser("comparison");
    return ac;
  }

  @Override
  public void prettyPrint() {
    if(compOprs.size() == 0){
      terms.get(0).prettyPrint();
    }
    else if(compOprs.size() == 1){
      terms.get(0).prettyPrint();
      compOprs.get(0).prettyPrint();
      terms.get(1).prettyPrint();
    }
    else{
      int i = 1;
      while(compOprs.size() >= i){
        terms.get(i-1).prettyPrint();
        compOprs.get(i-1).prettyPrint();
        i++;
      }
      terms.get(i-1).prettyPrint();
    }
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = terms.get(0).eval(curScope);

    for(int i = 1; i < terms.size(); ++i) {
      v = terms.get(i-1).eval(curScope);

      TokenKind k = compOprs.get(i-1).kind;

      switch(k) {
        case lessToken:
          v = v.evalLess(terms.get(i).eval(curScope), this);
          break;
        case greaterToken:
          v = v.evalGreater(terms.get(i).eval(curScope), this);
          break;
        case doubleEqualToken:
          v = v.evalEqual(terms.get(i).eval(curScope),this);
          break;
        case greaterEqualToken:
          v = v.evalGreaterEqual(terms.get(i).eval(curScope), this);
          break;
        case lessEqualToken:
          v = v.evalLessEqual(terms.get(i).eval(curScope), this);
          break;
        case notEqualToken:
          v = v.evalNotEqual(terms.get(i).eval(curScope), this);
          break;
        default:
          Main.panic("Illegal comparison operator: " + k + "!");
      }
      if(!v.getBoolValue("and operator", this)){
        return v;
      }
    }
    return v;
  }

}
