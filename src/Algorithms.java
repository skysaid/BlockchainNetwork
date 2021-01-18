import java.util.Date;
import java.util.PriorityQueue;
import java.util.Random;

public class Algorithms {
    public PriorityQueue<Event> eventPriorityQueue;

    public void pathProvision(Network network, int trafficLoad, int simulationLength,int userNum){
        //新建最小堆，按是否存活排列event
        PriorityQueue<Event> eventQue =new PriorityQueue<>((a,b)->{
            if (a.getConnection().isAlive())return -1;
            else return 1;
        });
        this.eventPriorityQueue=eventQue;
        //设置请求起始时间
        double startTime=new Date().getTime();
        //
        int netoworkLinks=network.getNumLinks();
        int banwidthMax=netoworkLinks*Link.numSlots;
        int bandwidthCount=0;
        double bandwidthUtilization=0;
        int bandwidthUtilizationCountTimes=0;
        //被阻塞的业务
        int blockedNum=0;
        //失败的业务（这里指被阻塞次数超过Network.droptime的业务数量）
        int failNum=0;
        //计数请求总数
        int numcount=0;
        //现在的userId
        int userNow=-1;
        while(numcount<simulationLength){
            while(eventPriorityQueue.size()<trafficLoad){
                if(userNow<userNum){
                    userNow++;
                }else {
                    userNow=0;
                }
                numcount++;
                Request requestNow=new Request(network,new Random());
                int[] pathIndex=new int[2];

                for (Path path : network.getCandidatePaths().get(requestNow.getSource(),requestNow.getDestination())){
                    pathIndex=path.get_can_use_common_continuous_slots(requestNow.getBandwidth());
                    if (pathIndex[0]!=-1){
                        path.use_common_continuous_slots(pathIndex[0],pathIndex[1]);
                        eventPriorityQueue.add(new Event(userNow,startTime++,new Connection(true,requestNow,path,pathIndex[0],pathIndex[1])));

                        //计算带宽占用数
                        bandwidthCount+=requestNow.getBandwidth();

                        break;
                    }
                }
                if (pathIndex[0]==-1) {
                    //这个请求现在阻塞了
                    blockedNum++;
                    Event temp=new Event(userNow, startTime++, new Connection(false, requestNow, null, pathIndex[0], pathIndex[1]));
                    temp.addBlockTime();
                    eventPriorityQueue.add(temp);
                }
            }
            //----待添加，这里可以统计资源利用情况
            bandwidthUtilization+=(double) bandwidthCount/banwidthMax;
            bandwidthUtilizationCountTimes++;
//            System.out.println("bandwidth utilization: " + (double) bandwidthCount/(double) banwidthMax);
            //----

            //去掉已经生效的event
            while(!eventPriorityQueue.isEmpty()&&eventPriorityQueue.peek().getConnection().isAlive()){
                Event event=eventPriorityQueue.poll();
                Connection connection=event.getConnection();
                Path path=connection.getPath();
                path.free_common_continuous_slots(connection.getSlots_start(),connection.getSlots_end());
                //计算释放掉的带宽
                bandwidthCount-=connection.getRequest().getBandwidth();
            }

            //对剩余request进行分配
            PriorityQueue<Event> temp= new PriorityQueue<>((a,b)->{
                if (a.getConnection().isAlive())return -1;
                else return 1;
            });
            while(!eventPriorityQueue.isEmpty()){
                Event currentEvent = eventPriorityQueue.poll();
                Request currentRequest = currentEvent.getConnection().getRequest();

                int[] pathIndex=new int[2];

                for (Path path : network.getCandidatePaths().get(currentRequest.getSource(),currentRequest.getDestination())){
                    pathIndex=path.get_can_use_common_continuous_slots(currentRequest.getBandwidth());
                    if (pathIndex[0]!=-1){
                        path.use_common_continuous_slots(pathIndex[0],pathIndex[1]);
                        temp.add(new Event(userNow,startTime++,new Connection(true,currentRequest,path,pathIndex[0],pathIndex[1])));

                        //计算带宽占用数
                        bandwidthCount+=currentRequest.getBandwidth();
                        break;
                    }
                }
                if (pathIndex[0]==-1) {
                    //这个请求现在阻塞了
                    Event tempEvent=new Event(userNow, startTime++, new Connection(false, currentRequest, null, pathIndex[0], pathIndex[1]));
                    tempEvent.addBlockTime();
                    if (tempEvent.getBlockTime()<Network.dropTime){
                        temp.add(tempEvent);
                    }else {
                        failNum++;
                    }
                }
            }
            eventPriorityQueue=temp;
            temp=null;
        }
        System.out.println("bandwidth utilization: "+ bandwidthUtilization/bandwidthUtilizationCountTimes);
        System.out.println("blocked rate: " + (double)blockedNum/simulationLength);
    }
}
