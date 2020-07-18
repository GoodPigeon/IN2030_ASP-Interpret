package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspPrimarySuffix extends AspSyntax{


  AspPrimarySuffix(int n){
    super(n);
  }

  static AspPrimarySuffix parse(Scanner s){
    enterParser("primary suffix");

    if(s.curToken().kind == leftBracketToken){
      AspPrimarySuffix as = AspSubscription.parse(s);
      leaveParser("primary suffix");
      return as;
    }
    else if(s.curToken().kind == leftParToken){
      AspPrimarySuffix as = AspArguments.parse(s);
      leaveParser("primary suffix");
      return as;
    }
    return null;
  }
}
