
public class Node implements Comparable<Node> {

	private int nodeID;
	public Integer nodeDegree;
	public Integer nodeCarriedTraffic = 0;
	


	public int compareTo(Node node){

		return this.nodeCarriedTraffic.compareTo(node.nodeCarriedTraffic);
	}

	public Node(int nodeID){
		this.nodeID = nodeID;
	}

	public void reset(){
		nodeCarriedTraffic = 0;
	}

	public int getNodeID(){
		return nodeID;
	}



	public static void main(String[] args){
		Node node = new Node(1);
		System.out.println("the node ID is " + node.getNodeID());
	}

}