package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspSmallStmtList extends AspStmt{
 ArrayList<AspSmallStmt> smallStmts = new ArrayList<>();
 Boolean hasSemicolon = false;

 AspSmallStmtList(int n){
   super(n);
 }

 static AspSmallStmtList parse(Scanner s){
   enterParser("small stmt list");
   AspSmallStmtList assl = new AspSmallStmtList(s.curLineNum());

   while(true){
     assl.smallStmts.add(AspSmallStmt.parse(s));
     if(s.curToken().kind != semicolonToken){
       break;
     }
     else{
       skip(s, semicolonToken);
       if(s.curToken().kind == newLineToken){
         assl.hasSemicolon = true;
         skip(s,newLineToken);
         break;
       }
     }
   }
   if(!assl.hasSemicolon){
     skip(s,newLineToken);
   }
   leaveParser("small stmt list");
   return assl;
 }

 @Override
 void prettyPrint(){
   int nPrinted = 0;
   for(AspSmallStmt smallStmt : smallStmts){
     if(nPrinted > 0){
       prettyWrite(";");
     }
     smallStmt.prettyPrint();
     ++nPrinted;
   }
   if(hasSemicolon){
     prettyWrite(";");
   }
   prettyWriteLn();

 }

 @Override
 public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
   //Del 4
   RuntimeValue v = smallStmts.get(0).eval(curScope);
   for (int i = 1; i < smallStmts.size(); ++i) {
     v = smallStmts.get(i).eval(curScope);
   }
   return v;
 }
}
