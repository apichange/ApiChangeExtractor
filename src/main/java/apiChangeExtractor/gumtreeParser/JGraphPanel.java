package apiChangeExtractor.gumtreeParser;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;

import com.github.gumtreediff.actions.model.Action;

public class JGraphPanel {
    private JGraphModelAdapter m_jgAdapter;
    private DirectedGraph data;
    private List<Set<Action>> actions;
    
    public JGraphPanel(DirectedGraph data, List<Set<Action>> actions){
    	this.data = data;
    	this.actions = actions;
    }
    /**
     * @see java.applet.Applet#init().
     */
    public void init() {
    	JFrame frame=new JFrame("Cluster");
        m_jgAdapter = new JGraphModelAdapter(data);
        JGraph jgraph = new JGraph(m_jgAdapter);
        JScrollPane scrollPane = new JScrollPane(jgraph);
        
        frame.setContentPane(scrollPane);
        frame.setSize(1000,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        int startX = 50;
        int startY = 50;
        for(Set<Action> as : actions){
        	startX = 50;
			for(Action a: as){
				if(a!=null)
					positionVertexAt(a, startX+=100, startY);
			}
			startY+=100;
		}
    }

    private void positionVertexAt(Object vertex, int x, int y) {
        DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
        Map attr = cell.getAttributes();
        Rectangle2D b = GraphConstants.getBounds(attr);
        GraphConstants.setBounds(attr, new Rectangle(x,y,100,40));
        Map cellAttr = new HashMap();
        cellAttr.put(cell, attr);
        m_jgAdapter.edit(cellAttr, null, null, null);
    }
}
