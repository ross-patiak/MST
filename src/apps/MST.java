package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	

		Vertex[] vertices = graph.vertices;
		PartialTreeList treeList = new PartialTreeList();
		
		for(Vertex v : vertices) {
			PartialTree tmp = new PartialTree(v);
			v.parent = tmp.getRoot();
			
			MinHeap<PartialTree.Arc> q = new MinHeap<PartialTree.Arc>();
			q = tmp.getArcs();
			
			for(Vertex.Neighbor nbh = v.neighbors; nbh != null; nbh = nbh.next) {
				PartialTree.Arc edge = new PartialTree.Arc(v, nbh.vertex, nbh.weight);
				
				q.insert(edge);
				
				
			}
			
			treeList.append(tmp);
		}
		
		return treeList;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		/* COMPLETE THIS METHOD */
		
		ArrayList<PartialTree.Arc>  mstlist = new ArrayList<PartialTree.Arc>();
		
		while(ptlist.size() > 1) {
			PartialTree ptx = ptlist.remove();
			boolean found = false;
		
			do {				
			
				PartialTree.Arc topEdge = ptx.getArcs().deleteMin();
				
				if(!(topEdge.v2.getRoot().equals(topEdge.v1.getRoot()))) {		//v2 does not belong in current tree
					
					found = true;
					mstlist.add(topEdge);
					
					PartialTree pty = ptlist.removeTreeContaining(topEdge.v2);
					topEdge.v2.parent = ptx.getRoot().parent;		//might be superfluous
					
					ptx.merge(pty);						//merge() method already merges arcs and changes roots
					ptlist.append(ptx);
				} 
				
				
			} while(found == false);
		}//end of outer-while()
		
		System.out.println(mstlist);
		return mstlist;
		
	}
		

}

