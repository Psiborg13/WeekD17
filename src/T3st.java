import java.util.Arrays;

public class T3st {

	public static void main(String[] args) {
		System.out.println(whichLine(1, 0));
	}
	
	public static int whichLine(int i1, int i2){
		int[] temp = new int[2];
		temp[0]= i1;
		temp[1] = i2;
		Arrays.sort(temp);
		for (int i = 0; i < temp.length; i++) {
			if(temp[i] != i){
				return i;
			}
		}
		return 2;
	}

}
