package UnboundedKnapsackProblem;

/**
 * 
 * @author Zhao Zhengyang
 */
class Item {
    public final int id;
    public final String name;
    public final int weight;
    public final int value;
    public final int maxNum;
    public final double ValueWeightRatio;
    
    public Item(int id, String name, int weight, int value, int totalWeight){
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.maxNum = totalWeight / weight;
        // filter the items whose weight is larger than total weight
        if (maxNum == 0) {
            this.ValueWeightRatio = 0;
        }
        else {
            this.ValueWeightRatio = (double) value / weight ;
        }    
    }
}
