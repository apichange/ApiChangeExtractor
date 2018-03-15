package apiChangeExtractor.gumtreeParser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;

import apiChangeExtractor.bean.JdtMethodCall;

import com.github.gumtreediff.actions.ActionClusterFinder;
import com.github.gumtreediff.actions.ActionGenerator;
import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.actions.model.Delete;
import com.github.gumtreediff.actions.model.Insert;
import com.github.gumtreediff.actions.model.Move;
import com.github.gumtreediff.actions.model.Update;
import com.github.gumtreediff.client.Run;
import com.github.gumtreediff.jdt.JdtTreeGenerator;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;
import com.github.gumtreediff.tree.TreeUtils;

public class GumTreeDiffParser {
	File oldFile;
	File newFile;
	public TreeContext srcTC;
	public TreeContext dstTC;
	public ITree src;
	public ITree dst;
	public List<Action> actions;
	public MappingStore mapping;
	public ActionClusterFinder finder;
	
	public GumTreeDiffParser(String oldFileName, String newFileName){
		this.oldFile = new File(oldFileName);
		this.newFile = new File(newFileName);
	}
	public GumTreeDiffParser(File oldFile, File newFile){
		this.oldFile = oldFile;
		this.newFile = newFile;
	}
	
	public void init(){
		Run.initGenerators();
		try {
			JdtTreeGenerator parser1 = new JdtTreeGenerator(oldFile.getPath());
			srcTC = parser1.generateFromFile(oldFile);
			src = srcTC.getRoot();
			JdtTreeGenerator parser2 = new JdtTreeGenerator(newFile.getPath());
			dstTC = parser2.generateFromFile(newFile);
			dst = dstTC.getRoot();
			Matcher m = Matchers.getInstance().getMatcher(src, dst); // retrieve the default matcher
			m.match();
			mapping = m.getMappings();
			ActionGenerator g = new ActionGenerator(src, dst, mapping);
			g.generate();
			actions = g.getActions();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void listStartNodes(){
		for(Action a : finder.getStartNodes()){
			System.out.println(a.toString());
		}
	}
	public List<Set<Action>> getCluster(){
		return finder.getClusters();
	}
	public List<Action> getActions(){
		return actions;
	}
	public MappingStore getMapping(){
		return mapping;
	}
	public String getOldTreeString(){
		return src.toTreeString();
	}
	public String getPrettyOldTreeString(){
		return toTreeString(srcTC, src);
	}
	public String getNewTreeString(){
		return dst.toTreeString();
	}
	public String getPrettyNewTreeString(){
		return toTreeString(dstTC, dst);
	}
	public void printActions(List<Action> actions){
		System.out.println(actions.size());
		for(Action a : actions){
			printOneAction(a);
		}
	}
	public void printOneAction(Action a){
		if(a instanceof Delete){
			System.out.print("Delete>");
			Delete delete = (Delete)a;
			ITree deleteNode = delete.getNode();
			System.out.println(prettyString(dstTC,deleteNode)+" from "+prettyString(dstTC,deleteNode.getParent()));//old
			System.out.println(delete.toString());
		}
		if(a instanceof Insert){
			System.out.print("Insert>");
			Insert insert = (Insert)a;
			ITree insertNode = insert.getNode();
			System.out.println(prettyString(dstTC,insertNode)+" to "+prettyString(dstTC,insertNode.getParent())+" at "+ insert.getPosition());
			System.out.println(insert.toString());
			System.out.println("----------------Insert.getParent(Old Tree)----------------------------");
			System.out.println(prettyString(dstTC, insert.getParent()));//old
		}
		if(a instanceof Move){
			System.out.print("Move>");
			Move move = (Move)a;
			ITree moveNode = move.getNode();
			System.out.println(prettyString(dstTC,moveNode)+" to "+prettyString(dstTC,move.getParent())+" at "+ move.getPosition());//old
			System.out.println(move.toString());
			System.out.println(move.getParent()==move.getNode().getParent());

			System.out.println("----------------Move.getParent(New Tree)----------------------------");
			System.out.println(prettyString(dstTC, move.getParent()));//new
		}
		if(a instanceof Update){
			System.out.print("Update>");
			Update update = (Update)a;
			ITree updateNode = update.getNode();
			System.out.println("from "+updateNode.getLabel()+" to "+update.getValue());//old
//			System.out.println("testLineNumber**************"+((Tree)updateNode).getStartLineNumber());
		}
		System.out.println("----------------Action.getNode----------------------------");
		System.out.println(prettyString(dstTC, a.getNode()));//move-old,update-old,insert-new,delete-old
//		System.out.println(toTreeString(dstTC, a.getNode()));
		System.out.println("-----------------Action.getNode.getParent---------------------------");
		System.out.println(prettyString(dstTC, a.getNode().getParent()));//move-old,update-old,insert-new,delete-old
//		System.out.println(toTreeString(dstTC, a.getNode().getParent()));
		System.out.println("============================================");
	}
	
	public String prettyString(TreeContext con, ITree node){
		if("MethodInvocation".equals(con.getTypeLabel(node))){
			JdtMethodCall temp = getJdkMethodCall((MethodInvocation)((Tree)node).getAstNode());
			if(temp != null)
				return node.getId()+". "+con.getTypeLabel(node)+":"+node.getLabel()+"("+getStartLineNum(con,node)+"-"+getEndLineNum(con,node)+")"+"|\n"+temp.toString()+"|"+temp.isJdk();
		}
		return node.getId()+". "+con.getTypeLabel(node)+":"+node.getLabel()+"("+getStartLineNum(con,node)+"-"+getEndLineNum(con,node)+")";
	}
	private String indent(ITree t) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < t.getDepth(); i++)
            b.append("\t");
        return b.toString();
    }
    public String toTreeString(TreeContext con, ITree tree) {
        StringBuilder b = new StringBuilder();
        for (ITree t : TreeUtils.preOrder(tree))
            b.append(indent(t) + prettyString(con, t) + "\n");
        return b.toString();
    }
    
    public int getStartLineNum(TreeContext con, ITree node){
    	ASTNode n = ((Tree) node).getAstNode();
		return con.getCu().getLineNumber(n.getStartPosition());
	}
    public int getEndLineNum(TreeContext con, ITree node){
    	ASTNode n = ((Tree) node).getAstNode();
		return con.getCu().getLineNumber(n.getStartPosition()+n.getLength()-1);
	}
    
    public JdtMethodCall getJdkMethodCall(MethodInvocation md){
    	IMethodBinding mb = md.resolveMethodBinding();
        if(mb!=null&&md.getExpression()!=null){
        	JdtMethodCall jdtBinding = new JdtMethodCall(md.getExpression().resolveTypeBinding().getQualifiedName(),
        			mb.getName(), mb.getReturnType().getQualifiedName(), mb.getDeclaringClass().getQualifiedName());
            ITypeBinding[] list = mb.getParameterTypes();
            for(int i = 0; i < list.length; i++){
            	jdtBinding.addParameter(list[i].getQualifiedName());
            }
            jdtBinding.setJdk(isJdk(md.getExpression().resolveTypeBinding().getQualifiedName()));
            return jdtBinding;
        }else{
        	return null;
        }
    }
    private boolean isJdk(String s){
    	try {
    		String temp = s;
    		if(temp.contains("<")){
    			temp = temp.split("<")[0];
    		}
			Class.forName(temp);
			return true;
		} catch (ClassNotFoundException e) {
	    	return false;
		}
    }   
    
	public static void main(String[] args) {
		String file1 = "src/test/java/resources/StringBuilderCase1.java";
		String file2 = "src/test/java/resources/StringBuilderCase2.java";
		GumTreeDiffParser diff = new GumTreeDiffParser(file1,file2);
		diff.init();
		System.out.println("---------------------------------Old Tree------------------------------------");
		System.out.println(diff.getOldTreeString());
		System.out.println("---------------------------------New Tree------------------------------------");
		System.out.println(diff.getNewTreeString());
		System.out.println("---------------------------------------------------------------------");
		System.out.println("---------------------------------Old Tree------------------------------------");
		System.out.println(diff.getPrettyOldTreeString());
		System.out.println("---------------------------------New Tree------------------------------------");
		System.out.println(diff.getPrettyNewTreeString());
		System.out.println("---------------------------------------------------------------------");
		diff.printActions(diff.getActions());
	}

}
