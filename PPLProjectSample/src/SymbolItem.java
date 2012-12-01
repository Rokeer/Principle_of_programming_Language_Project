/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Winters
 */
public class SymbolItem {

    public int lineNumber;
    public SYMBOL type=null;

    public int iValue;
    public String lpValue="";

    public SymbolItem(){

    }
    
    public SymbolItem(int lineNumber,SYMBOL type,int iValue){
        this.lineNumber=lineNumber;
        this.type=type;
        this.iValue=iValue;
    }
    
}
