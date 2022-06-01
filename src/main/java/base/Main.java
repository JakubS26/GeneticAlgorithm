package base;

import base.utils.Utils;
import java.util.Scanner;

public class Main {
    public static final String prompt = "?>";
    public static void main(String[] args) {
        MainProcessor processor = new MainProcessor();
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
         do {
             String[] command = Utils.splitTrim(scanner.nextLine(), " ");
             if (processor.processCommand(command) == 0) {
                break;
             }
             System.out.print(prompt);
        } while (scanner.hasNext());
    }
}
