import java.util.*;

public class Simulation {

    public static int distance_per_hop = 40;//每一条的距离是40km
    public static int  trafficLoad; //网络负载
    public static int simulationLength = 50000; //业务请求数


    public  void startSimulation(Network network, int trafficLoad, int simulationLength, int userNum){
        Algorithms algorithms=new Algorithms();
        algorithms.pathProvision(network,trafficLoad,simulationLength,userNum);
    }


    public static void main(String[] args) {

        //store the topology resource and k shortest paths
        Network network = new Network("usnet.txt");
//        Network network = new Network("simple_net.txt");
        Simulation simulation = new Simulation();
        simulation.startSimulation(network,200,10000,2);
    }

}