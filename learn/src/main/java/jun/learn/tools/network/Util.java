package jun.learn.tools.network;

public class Util {
	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];  
		targets[3] = (byte) (res & 0xff);		// 最低位   
		targets[2] = (byte) ((res >> 8) & 0xff);// 次低位   
		targets[1] = (byte) ((res >> 16) & 0xff);// 次高位   
		targets[0] = (byte) (res >>> 24);		// 最高位,无符号右移。   
		return targets;   
	}
	
	public static int byte2Int(byte[] b) {
        int intValue = 0;
        for (int i = 0; i < b.length; i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return intValue;
    }
	
	public static void main(String[] args) {
		System.out.println(byte2Int(int2byte(5)));
	}
}
