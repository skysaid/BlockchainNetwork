import java.util.ArrayList;

public class Path extends ArrayList<Link> {
    /**
     * Path继承了ArrayList这个类，泛型是Link
     * 那么Path的数据结构是[link1,link2,....]
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param path 由起止节点的id组成 [start,end]
     */
    public Path(int[] path) {

        for (int i = 0; i < path.length - 1; i++) {
            this.add(Network.index.get(path[i], path[i + 1]));
        }

    }

    public void printPath(Path path) {
        System.out.print("the route is ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).getFrom() + "--");
        }
        System.out.print(path.get(path.size() - 1).getTo());
        System.out.println();
    }


    public boolean[] getCommonSlots() {
        /**
         * 获取共同可用slots
         */
        boolean[] slots = this.get(0).getSlots();
        for (Link link : this) {
            boolean[] curLinkSlots = link.getSlots();
            for (int i = 0; i < slots.length; i++) {
                slots[i] |= curLinkSlots[i];
            }
        }
        return slots;
    }


    public int get_max_continuous_slots_num() {
        int max_num = 0;
        int temp_num = 0;
        boolean[] slots = this.getCommonSlots();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == false) {
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

    /**
     * 获取第一个可用的连续的slots并返回其起止位置
     * @return
     */
    public int[] get_can_use_common_continuous_slots(int requirement){
        boolean[] commonSlots = getCommonSlots();
        int start=0;
        for(int i=0;i<commonSlots.length;i++){
            if (!commonSlots[i]){
                start=i;
                i++;
                while(i<commonSlots.length && !commonSlots[i] && i-start<requirement){
                    i++;
                }
                if (i-1-start+1>=requirement){
                    return new int[]{start,start+requirement-1};
                }
            }
        }
        return new int[]{-1,-1};
    }

    /**
     * 占用对应的slots
     * @param start
     * @param end
     */
    public void use_common_continuous_slots(int start,int end){
        for (Link link : this) {
            link.mask(start,end);
        }
    }

    /**
     * 释放对应的slots
     * @param start
     * @param end
     */
    public void free_common_continuous_slots(int start,int end){
        for (Link link : this) {
            link.unmask(start,end);
        }
    }

    public int get_common_free_slots_num() {
        int num = 0;
        boolean[] slots = this.getCommonSlots();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == false) {
                num++;
            } else {
                continue;
            }
        }
        return num;
    }

    public double get_average_free_slots_num() {
        int sum = 0;
        for (Link link : this
        ) {
            sum += link.get_free_slot_num();
        }
        return (double) (sum / this.size());
    }

    public static void main(String[] args) {
        Path path = new Path(new int[]{1, 3, 4});
        boolean[] slots = {false, true, false, false, true, false, true};
        int num = path.get_max_continuous_slots_num();
        System.out.println(num);

    }

}