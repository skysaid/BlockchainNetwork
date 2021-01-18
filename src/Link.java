
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 定义链路属性，包括源节点（from）,目的节点（to）,链路上slot总数（numSlots）,slot的占用情况（slots[]）；
 */
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;
    private int from;
    private int to;
    private int distance;
    private boolean[] slots;//false为未被占用，true为被占用
    public static int numSlots = 50; //频率时隙
    //在网络中，假设一条链路仅有一个光纤对，其中每根光纤的总频谱宽度为 4THz，
    // 并将每个频隙的频谱宽度设置为 12.5GHz，故每个链路中有320个具有保护带宽的频隙。
    // 在仿真中，网络服务的传输速率平均分布在10到400Gb/s之间。


    public Link(int from, int to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;

        //未被占用，默认false；
        slots = new boolean[numSlots];
        for (int i = 0; i < numSlots; i++) {
            slots[i] = false;
        }
    }

    public Link(Link another) {
        this.from = another.getFrom();
        this.to = another.getTo();
        this.distance = another.getDistance();
        this.slots = another.getSlots();
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }

    public boolean[] getSlots() {
        boolean[] slotsCopy = new boolean[slots.length];
        System.arraycopy(slots, 0, slotsCopy, 0, slots.length);
        return slotsCopy;
    }

    public void reset() {
        for (int i = 0; i < slots.length; i++) {
            slots[i] = false;
        }
    }

    public void setSlots(boolean[] slots) {
        System.arraycopy(slots, 0, this.slots, 0, slots.length);
    }

    //剩余的空闲slot，不代表可用
    public int get_free_slot_num() {
        int num = 0;
        for (int i = 0; i < slots.length; i++) {
            if (!slots[i]) {
                num = num + 1;
            }
        }
        return num;
    }


    public int get_max_continuous_slots_num() {
        int max_num = 0;
        int temp_num = 0;
        for (int i = 0; i < slots.length; i++) {
            if (!slots[i]) {
                temp_num++;
                if (i == slots.length - 1) {
                    if (temp_num > max_num) {
                        max_num = temp_num;
                        temp_num = 0;
                    }
                } else {
                    continue;
                }
            } else {
                if (temp_num > max_num) {
                    max_num = temp_num;
                    temp_num = 0;
                } else {
                    temp_num = 0;
                }
            }
        }
        return max_num;

    }


    public void mask(boolean[] slots) { //占用slot
        for (int i = 0; i < slots.length; i++) {
            this.slots[i] |= slots[i];
        }
    }

    /**
     * 根据起止位置占用slot
     * 注意是左闭右闭区间
     */
    public void mask(int start, int end) { //占用slot
        for (int i = start; i <= end; i++) {
            this.slots[i] = true;
        }
    }


    public void unmask(boolean[] slots) {//释放slot
        for (int i = 0; i < slots.length; i++) {
            this.slots[i] &= (!slots[i]);
        }
    }

    /**
     * 根据起止位置解放slot
     * 注意是左闭右闭区间
     */
    public void unmask(int start, int end) {//释放slot
        for (int i = start; i <= end; i++) {
            this.slots[i] &= (!slots[i]);
        }
    }

    public static void main(String[] args) {
        Link a = new Link(1, 14, 1);
        Link b = new Link(a);
        List<Link> linkList = new ArrayList<Link>();
        linkList.add(a);
        linkList.add(b);
        boolean[] slots = new boolean[320];
        for (int i = 0; i <= 4; i++
        ) {
            slots[i] = true;
        }
        System.out.println(Arrays.toString(a.slots));
        a.mask(slots);
        System.out.println(Arrays.toString(a.slots));
        a.unmask(slots);
        System.out.println(Arrays.toString(a.slots));
    }

}