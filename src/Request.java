import java.io.*;
import java.util.*;

public class Request implements Serializable {
    public  Network network=null;
    private static final long serialVersionUID = 1L;
    public int source;
    private static int[] sourceSet = {1, 2, 3, 4};
    public int destination;
    private static int[] destinationSet = {7,11,8,9,10};
    private int bandwidth;
    public int[] bandwidthSet = {1, 2, 3, 4, 5, 6};
    private Answer answer_for_request;
    private int data_size;//数据量
    private static int[] data_sizeSet = {1,2,3,4,5};
    private double maxDelay;//最大时延
    private static double[] maxDelaySet = {0.05, 0.1, 0.12, 0.14, 0.16, 0.18, 0.2};



    public Request(Random randomStream) {
        int sourceIndex =randomStream.nextInt(sourceSet.length);
        this.source = sourceSet[sourceIndex];

        int destinationIndex =randomStream.nextInt(destinationSet.length);
        this.destination = destinationSet[destinationIndex];

        int bandwidthIndex = randomStream.nextInt(bandwidthSet.length);
        this.bandwidth = bandwidthSet[bandwidthIndex];

        int data_sizeIndex = randomStream.nextInt(data_sizeSet.length);
        this.data_size = data_sizeSet[data_sizeIndex];

        int maxDelayIndex = randomStream.nextInt(maxDelaySet.length);
        this.maxDelay = maxDelaySet[maxDelayIndex];
    }


    public Request(int source, Random randomStream) {
        this.source = source;

        int destinationIndex =randomStream.nextInt(destinationSet.length);
        this.destination = destinationSet[destinationIndex];

        int bandwidthIndex = randomStream.nextInt(bandwidthSet.length);
        this.bandwidth = bandwidthSet[bandwidthIndex];

        int data_sizeIndex = randomStream.nextInt(data_sizeSet.length);
        this.data_size = data_sizeSet[data_sizeIndex];

        int maxDelayIndex = randomStream.nextInt(maxDelaySet.length);
        this.maxDelay = maxDelaySet[maxDelayIndex];
    }

    public Request(Network network, Random randomStream) {
        this.network=network;

        this.source = 1+randomStream.nextInt(network.getNumNodes());

        do{
            this.destination =1+randomStream.nextInt(network.getNumNodes());
        }
        while (this.destination==this.source);

        int bandwidthIndex = randomStream.nextInt(bandwidthSet.length);
        this.bandwidth = bandwidthSet[bandwidthIndex];

        int data_sizeIndex = randomStream.nextInt(data_sizeSet.length);
        this.data_size = data_sizeSet[data_sizeIndex];

        int maxDelayIndex = randomStream.nextInt(maxDelaySet.length);
        this.maxDelay = maxDelaySet[maxDelayIndex];
    }

    public int getData_size() {
        return data_size;
    }
    public double getMaxDelay() {
        return maxDelay;
    }

    public void setAnswer_for_request(Answer answer_for_request) {
        this.answer_for_request = answer_for_request;
    }

    public int getSource() {
        return source;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public int getDestination() {
        return destination;
    }

    public Answer getAnswer_for_request() {
        return answer_for_request;
    }

    public static void main(String[] args) {
        Random r=new Random();
        int i=0;
        while (i++<10)System.out.println(r.nextInt(2));
    }
}