import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.*;


public class Network implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int numNodes, numLinks;
	public static DoubleHash<Integer, Integer, Link> index;
	public static DoubleHash<Integer, Integer, Link> reverseIndex;
	public static DoubleHash<Integer, Integer, ArrayList<Path>> candidatePaths;
	public static HashMap<Request, Connection> ongoingConnections = new HashMap<>();
	public int networkCapacity = 0;
	private int countNumber = 0;
	public int intervalNumber = 0;
    public static double capcity_for_slot = 6.25;  //   GB/slot
	public static int dropTime=2;//丢弃包的阈值


	public Network(String networkFileName){

		index = new DoubleHash<Integer, Integer, Link>();
		reverseIndex = new DoubleHash<Integer, Integer, Link>();

		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new FileReader(networkFileName));
			String line;
			while((line = inputStream.readLine()) != null){
//				str.trim() 去掉头尾字符串
				String[] edge = line.split("\\s+"); // \\s+匹配任意空白格
				int from = Integer.parseInt(edge[0]);
				int to = Integer.parseInt(edge[1]);
				int distance = Integer.parseInt(edge[2]);
				
				Link newLink = new Link(from, to, distance);
				index.put(from, to, newLink);
				reverseIndex.put(to, from, newLink);
			}
//			inputStream.close();
		} catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}

		numNodes = index.keySet().size();
		numLinks = index.size();
		
		
		candidatePaths = new DoubleHash<Integer, Integer, ArrayList<Path>>();
		
		try{
			String pathFileName=networkFileName.split("\\.")[0]+"_k_paths.txt";
			inputStream = new BufferedReader(new FileReader(pathFileName));
//			if(networkFileName.equals("simple_net.txt")){
//				inputStream = new BufferedReader(new FileReader("simple_net_k_paths.txt"));
//			}
//			if(networkFileName.equals("usnet.txt")){
//				inputStream = new BufferedReader(new FileReader("usnet_k_paths.txt"));
//			}
			String line;
			while((line = inputStream.readLine()) != null){
				String[] token = line.split("\\s+");
				int numHops = token.length;
				int[] path = new int[numHops];
				for(int i = 0; i < numHops; i++){
					path[i] = Integer.parseInt(token[i]);
				}

				if(!candidatePaths.containsKey(path[0], path[numHops-1])){
					candidatePaths.put(path[0], path[numHops-1], new ArrayList<Path>());
				}
				candidatePaths.get(path[0], path[numHops-1]).add(new Path(path));
			}
			inputStream.close();
		} catch (Exception e){
			System.out.println(e);
		}
		

	}


	public static DoubleHash<Integer, Integer, ArrayList<Path>> getCandidatePaths() {
		return candidatePaths;
	}

	public int getNumNodes(){
		return numNodes;
	}
	
	public int getNumLinks(){
		return numLinks;
	}
	
	public int getCountNumber(){
		return countNumber;
	}
	
	public Link link(int from, int to){
		return index.get(from, to);
	}
	
	public Link reverseLink(int to, int from){
		return reverseIndex.get(to, from);
	}

	public static Collection<Link> getOutLink(int from){
		return index.get(from).values();
	}

	public static Collection<Link> getInLink(int to){
		return reverseIndex.get(to).values();
	}

	public void reset(){
		for(Link link : index.getValues()){
			link.reset();
		}
		ongoingConnections.clear();
	}
	
	

	public static void main(String[] args){
		Network network = new Network("simple_net.txt");
		System.out.println(network.getNumNodes());
		System.out.println(network.getNumLinks());

	}
			
}