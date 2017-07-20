
public class FoolishSamuraiWarrior {

	public static void main(String[] args) {
		System.out.println("A foolish samurai warrior wielding ");
		for (int i = 0; i < 10000000; i++) {
			System.out.print("a foolish samurai warrior wielding ");
			if(i % 10 == 0){
				System.out.println();
			}
		}
		System.out.println("a magic sword.");
	}

}
