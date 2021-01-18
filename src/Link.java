
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * ������·���ԣ�����Դ�ڵ㣨from��,Ŀ�Ľڵ㣨to��,��·��slot������numSlots��,slot��ռ�������slots[]����
 */
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;
    private int from;
    private int to;
    private int distance;
    private boolean[] slots;//falseΪδ��ռ�ã�trueΪ��ռ��
    public static int numSlots = 50; //Ƶ��ʱ϶
    //�������У�����һ����·����һ�����˶ԣ�����ÿ�����˵���Ƶ�׿��Ϊ 4THz��
    // ����ÿ��Ƶ϶��Ƶ�׿������Ϊ 12.5GHz����ÿ����·����320�����б��������Ƶ϶��
    // �ڷ����У��������Ĵ�������ƽ���ֲ���10��400Gb/s֮�䡣


    public Link(int from, int to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;

        //δ��ռ�ã�Ĭ��false��
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

    //ʣ��Ŀ���slot�����������
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


    public void mask(boolean[] slots) { //ռ��slot
        for (int i = 0; i < slots.length; i++) {
            this.slots[i] |= slots[i];
        }
    }

    /**
     * ������ֹλ��ռ��slot
     * ע��������ұ�����
     */
    public void mask(int start, int end) { //ռ��slot
        for (int i = start; i <= end; i++) {
            this.slots[i] = true;
        }
    }


    public void unmask(boolean[] slots) {//�ͷ�slot
        for (int i = 0; i < slots.length; i++) {
            this.slots[i] &= (!slots[i]);
        }
    }

    /**
     * ������ֹλ�ý��slot
     * ע��������ұ�����
     */
    public void unmask(int start, int end) {//�ͷ�slot
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