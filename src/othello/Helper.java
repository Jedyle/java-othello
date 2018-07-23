package othello;

import java.util.LinkedList;


public class Helper {

	static boolean isInList(OthelloAction a, LinkedList<OthelloAction> list){
		
		for (int i = 0; i < list.size(); i++){
			OthelloAction tmp = list.get(i);
			if ((tmp.getRow() == a.getRow()) && (tmp.getColumn() == a.getColumn())){
				return true;
			}
		}
		
		return false;
		
	}
	
}
