package App_name;

import java.util.*;

public class App_name {
    // TODO: Replace the following dummy variable for app name
    private static String appName = "[APP NAME]";
    public static String[] mainMenuOptions = {"Sign in to my account", "Create a new account"};

    private static Scanner sc = new Scanner(System.in);

    public static void signUp() {
        String[] outputs = {"Full Name (*): ", "Email (*): ", "Phone: "};
        List<String> inputs = new ArrayList<>();

        System.out.println("Please enter the following information and press enter. Fields marked (*) are required.");
        for (String output: outputs) {
            System.out.println(output);
            String input = sc.nextLine();
            inputs.add(input);
        }

        System.out.println("Thanks for signing up! Your account has been successfully created, " + inputs.get(0) + ".");
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to " + appName);
            for (int i = 1; i <= mainMenuOptions.length; i++) {
                System.out.println(i + ") " + mainMenuOptions[i - 1]);
            }
            System.out.println("\n");
            signUp();

            break;
        }
    }
}
