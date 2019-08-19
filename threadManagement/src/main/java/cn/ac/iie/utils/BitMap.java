package cn.ac.iie.utils;

/**
 * @author vincent
 * @time 2019-08-04 22:49
 */
public class BitMap {

    private long length;
    private static int[] bitsMap;

    //构造函数中传入数据中的最大值
    public BitMap(long length) {
        this.length = length;
        // 根据长度算出，所需数组大小
        // 一个整型数据占 4 个字节，共 32 位
//        System.out.println("(length >> 5)====" + (length >> 5));
//        System.out.println("(length & 31)======" + (length & 31));
        bitsMap = new int[(int) (length >> 5) + ((length & 31) > 0 ? 1 : 0)];
    }

    public int getBit(long index) {
        int intData = bitsMap[(int) ((index - 1) >> 5)];
        int offset = (int) ((index - 1) & 31);
        return intData >> offset & 0x01;
    }

    public void setBit(long index) {
        // 求出该index - 1所在bitMap的下标
        int belowIndex = (int) ((index - 1) >> 5);
        // 求出该值的偏移量(求余)
        int offset = (int) ((index - 1) & 31);
        int inData = bitsMap[belowIndex];
        bitsMap[belowIndex] = inData | (0x01 << offset);
    }

    public static void main(String[] args) {
        BitMap bitMap = new BitMap(64);
        bitMap.setBit(32);
        System.out.println(bitMap.getBit(1));
        System.out.println(bitMap.getBit(32));
    }
}
