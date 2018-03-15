package apiChangeExtractor;

import org.eclipse.jdt.core.dom.MethodInvocation;

import apiChangeExtractor.bean.JdtMethodCall;
import apiChangeExtractor.bean.RenameList;
import apiChangeExtractor.gumtreeParser.GumTreeDiffParser;

import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.actions.model.Delete;
import com.github.gumtreediff.actions.model.Insert;
import com.github.gumtreediff.actions.model.Move;
import com.github.gumtreediff.actions.model.Update;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;

public class PatternMatcher 
{
	public enum ChangeType
	{
		NOT_FOUND,
		CHANGE_PARAMETER_NUM,
		RETURN_VALUE_ASSIGNMENT,
		CHANGE_CLASS,
		CHANGE_METHOD,
		CHANGE_PAREMETER
	}
	
    public ChangeType matchOneAction(Action a, TreeContext dstTC , GumTreeDiffParser diff, RenameList rl)
    {
    	ITree node = a.getNode();
		ITree parent = node.getParent();
		int position  = parent.getChildPosition(node);

		if(a instanceof Update && "TypeDeclaration".equals(dstTC.getTypeLabel(parent)))
		{
			ITree newNode = null; 
			ITree oldNode = null;
			oldNode = a.getNode();
			newNode = diff.getMapping().getDst(oldNode);
			rl.renameMap.put(oldNode.getLabel(), newNode.getLabel());					
		}
		
		if(a instanceof Insert)
		{
			Insert insert = (Insert)a;
			ITree insertNode = insert.getNode();
			if(insert.getPosition()>1 && "MethodInvocation".equals(dstTC.getTypeLabel(node)))	
			{			
				ITree oldParentNode = null;
				ITree newParentNode = null;
				newParentNode=insertNode.getParent();				
				oldParentNode=diff.getMapping().getSrc(newParentNode);
				if(oldParentNode!=null)
					if(oldParentNode.getChildren()!=null)
						if(oldParentNode.getChildren().size()>1)
							if(oldParentNode.getChildren().get(0) == diff.getMapping().getSrc(newParentNode.getChildren().get(0))
							&& oldParentNode.getChildren().get(1) == diff.getMapping().getSrc(newParentNode.getChildren().get(1)))
								return ChangeType.CHANGE_PARAMETER_NUM;
			}		
		}
		if(a instanceof Delete)
		{
			Delete delete = (Delete)a;
			ITree deleteNode = delete.getNode();
			if(deleteNode.getParent().getChildPosition(deleteNode)>1 && "MethodInvocation".equals(dstTC.getTypeLabel(node)))
			{	
				ITree oldParentNode = null;
				ITree newParentNode = null;
				oldParentNode=deleteNode.getParent();
				newParentNode=diff.getMapping().getDst(oldParentNode);
				if(newParentNode!=null)
					if(newParentNode.getChildren()!=null)
						if(newParentNode.getChildren().size()>1)
							if(newParentNode.getChildren().get(0) == diff.getMapping().getSrc(oldParentNode.getChildren().get(0))
							&& newParentNode.getChildren().get(1) == diff.getMapping().getSrc(oldParentNode.getChildren().get(1)))
								return ChangeType.CHANGE_PARAMETER_NUM;	
			}
		}
		if(a instanceof Move
				&& "MethodInvocation".equals(dstTC.getTypeLabel(node))
				){
			Move move = (Move)a;
			ITree moveNode = move.getNode();
			if("MethodInvocation".equals(dstTC.getTypeLabel(moveNode))
					&& "VariableDeclarationFragment".equals(dstTC.getTypeLabel(move.getParent()))
					){
				ITree statement = node;
				if(statement!=null){
					return ChangeType.RETURN_VALUE_ASSIGNMENT;
				}
			}
			if("MethodInvocation".equals(dstTC.getTypeLabel(moveNode)))
			{
              if(CheckReturn(move.getParent(),dstTC))
            	  return ChangeType.RETURN_VALUE_ASSIGNMENT;		
			}
		}
		//
		if(a instanceof Update 
				&& "MethodInvocation".equals(dstTC.getTypeLabel(parent))
				&& position == 0){
			
			ITree newNode = null; 
			ITree oldNode = null;
			oldNode = a.getNode();
			newNode = diff.getMapping().getDst(oldNode);
			if(oldNode!=null && newNode!= null)
			{
				JdtMethodCall oldCall = null;
				JdtMethodCall newCall = null;
				if(((Tree)oldNode.getParent()).getAstNode() instanceof MethodInvocation){	
					MethodInvocation tempMI = (MethodInvocation)((Tree)oldNode.getParent()).getAstNode();
					oldCall = diff.getJdkMethodCall(tempMI);
				}
				if(((Tree)newNode.getParent()).getAstNode() instanceof MethodInvocation){
					MethodInvocation tempMI = (MethodInvocation)((Tree)newNode.getParent()).getAstNode();
					newCall = diff.getJdkMethodCall(tempMI);
				}
				if(oldCall!=null && newCall!=null)
				{
					if(oldCall.getInvoker().toString().equals(newCall.getInvoker().toString()))
						return ChangeType.NOT_FOUND;
				}
			}

			ITree statement = node;
			if(statement!=null){
				return ChangeType.CHANGE_CLASS;
			}
		}
		if(a instanceof Update 
				&& "MethodInvocation".equals(dstTC.getTypeLabel(parent))
				&& position == 1){
			ITree statement = node;
			if(statement!=null){
				return ChangeType.CHANGE_METHOD;
			}
		}
		if(a instanceof Update 
				&& "MethodInvocation".equals(dstTC.getTypeLabel(parent))
				&& position > 1){
			Update update = (Update)a;
			ITree updateNode = update.getNode();		
			ITree oldParentNode = null;
			ITree newParentNode = null;
			oldParentNode=updateNode.getParent();
			newParentNode=diff.getMapping().getDst(oldParentNode);
			if(rl.renameMap.get(update.getNode().getLabel())!=null)
			    if(rl.renameMap.get(update.getNode().getLabel()).equals(update.getValue()))
                   return ChangeType.NOT_FOUND;		
			if(newParentNode!=null)
				if(oldParentNode.getChildren().get(0) == diff.getMapping().getSrc(newParentNode.getChildren().get(0))
				&& oldParentNode.getChildren().get(1) == diff.getMapping().getSrc(newParentNode.getChildren().get(1)))
					return ChangeType.CHANGE_PAREMETER;
		}	
	
		return ChangeType.NOT_FOUND;
    }
    
    public boolean CheckReturn(ITree parent,TreeContext dstTC)
    {
    	if("IfStatement".equals(dstTC.getTypeLabel(parent))
    	||"ForStatement".equals(dstTC.getTypeLabel(parent)))
    		return true;
    	else if(dstTC.getTypeLabel(parent).contains("Expression"))	
    	{
    		CheckReturn(parent.getParent(),dstTC);
    		return false;
    	}
    	else			
    	    return false;
    }
}
