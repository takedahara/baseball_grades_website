




import java.util.Scanner;

public class tameshi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("何か入力してください: ");
        String input = scanner.nextLine();
        System.out.println("入力された文字列は " + input + " です。");
        scanner.close();
    }
}