package apiChangeExtractor.gumtreeParser;

import java.util.List;

import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.actions.model.Delete;
import com.github.gumtreediff.actions.model.Insert;
import com.github.gumtreediff.actions.model.Move;
import com.github.gumtreediff.actions.model.Update;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;

public class Classify {

/*	
	return值列表
	1   参数个数
    2 A.a→A.b
    3 A.a→B.a
    4 A.a→B.b
    5 A.a→a'=A.a
*/

	public Integer parameterNum(TreeContext con, List<Action> actions)
	{
	    //单个在insert 在 method at 2
		for(int i=0; i<actions.size(); i++)
		{
			Action temp = actions.get(i);
			if(temp instanceof Insert)
			{
				Insert insert = (Insert)temp;
				ITree insertNode = insert.getNode();
				if(insert.getPosition()>=1 && "MethodInvocation".equals(con.getTypeLabel(insertNode.getParent())) )					
					return 1;				
			}
			if(temp instanceof Update)
			{
				Update update = (Update)temp;
				ITree updateNode = update.getNode();
				ITree parent = updateNode.getParent();
				parent.getChildPosition(updateNode);
				//条件不全
				if(parent.getChildPosition(updateNode)==1 && "MethodInvocation".equals(con.getTypeLabel(updateNode.getParent())) )
					return 2;
				if(parent.getChildPosition(updateNode)==0 && "MethodInvocation".equals(con.getTypeLabel(updateNode.getParent())) )
					return 3;
				
			}
		    if(temp instanceof Move)
		    {
		    	
		    	
		    	
		    	
		    }
		}
		
	
		
		
		return 0;		
	}
	
	
	
}
