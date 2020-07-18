package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactor extends AspSyntax{
  ArrayList<AspFactorPrefix> factorPrefixes = new ArrayList<>();
  ArrayList<AspPrimary> primaries = new ArrayList<>();
  ArrayList<AspFactorOpr> factorOprs = new ArrayList<>();


  AspFactor(int n){
    super(n);
  }

  static AspFactor parse(Scanner s){
    enterParser("factor");
    AspFactor af = new AspFactor(s.curLineNum());
    while(true){
      if(s.isFactorPrefix()){
        af.factorPrefixes.add(AspFactorPrefix.parse(s));
      }
      else{
        af.factorPrefixes.add(null);
      }
      af.primaries.add(AspPrimary.parse(s));

      if(!s.isFactorOpr()){
        break;
      }
      af.factorOprs.add(AspFactorOpr.parse(s));
    }
    leaveParser("factor");
    return af;
  }

  @Override
  public void prettyPrint() {
    for(int i = 0; i < primaries.size(); i++){
      if(factorPrefixes.get(i) != null){
        factorPrefixes.get(i).prettyPrint();
      }
      primaries.get(i).prettyPrint();
      if(i < factorOprs.size()){
        factorOprs.get(i).prettyPrint();
      }
    }

  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = primaries.get(0).eval(curScope);

    if(factorPrefixes.size() != 0){
      if(factorPrefixes.get(0) != null){
        TokenKind k = factorPrefixes.get(0).kind;
        switch(k){
          case minusToken:
          v = v.evalNegate(this);
          break;

          case plusToken:
          v = v.evalPositive(this);
          break;
          default:
          Main.panic("Illegal term operator: " + k + "!");
        }
      }
    }

    for(int i = 1; i < primaries.size(); ++i){
      TokenKind k2 = factorOprs.get(i-1).tokenKind;

      switch (k2) {
				case astToken:
				v = v.evalMultiply(primaries.get(i).eval(curScope), this);
				break;
				case slashToken:
				v = v.evalDivide(primaries.get(i).eval(curScope), this);
        break;
				case percentToken:
				v = v.evalModulo(primaries.get(i).eval(curScope), this);
        break;
				case doubleSlashToken:
				v = v.evalIntDivide(primaries.get(i).eval(curScope), this);
        break;
				default:
				Main.panic("Illegal term operator: " + k2 + "!");
			}
    }
    return v;
  }


}
