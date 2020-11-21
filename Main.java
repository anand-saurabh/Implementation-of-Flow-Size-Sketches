import java.util.Scanner;

public class Main {

    public static void main(String [] args)
    {
        Scanner in = new Scanner(System.in);
        int option;
        while(true) {
            System.out.println("Enter 1: Count Min 2: Counter Sketch 3: Active Counter 4: exit");
            option = Integer.parseInt(in.nextLine());
            switch (option) {
                case 1: CountMin.callCountMin();
                    break;
                case 2: CounterSketch.callCounterSketch();
                    break;
                case 3: ActiveCounter.callActiveCounter();
                    break;
                case 4: System.exit(0);
                    break;
                default: System.out.println("Please Enter a Valid Option");
            }
        }
    }
}