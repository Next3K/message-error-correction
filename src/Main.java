import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main class of the program
 */
public class Main {
    public static void main(String[] args) throws IOException {

        // run program in loop
        while(true) {
            System.out.println("Choose what  to do: \n 1) encode file \n 2) decode file \n 3) Exit");
            Scanner in = new Scanner(System.in);
            int choice;
            while(true) {
                choice = in.nextInt();
                if (!(choice == 1 || choice == 2 || choice == 3)) {
                    System.out.println("Illegal argument chosen, please choose 1, 2 or 3.");
                } else {
                    break;
                }
            }


            String filename;
            Scanner scanner2 = new Scanner(System.in);

            switch (choice) {
                case 1 -> {
                    System.out.println("Provide the name of file: ");
                    filename = scanner2.next();
                    String filePath = "files" + File.separator + filename; // file inside ./files directory
                    String fullMessage = Helper.readPlainTextFromFile(filePath); // text written from file
                    String[] messageTokens = Helper.getMessageTokens(fullMessage); // split message into tokens
                    String[] encodedTokens = Encoder.encode(messageTokens); // encode tokens
                    Helper.writeToFile(filename, encodedTokens,Helper.TYPE.ENCODE); // write encoded to file
                }
                case 2 -> {
                    System.out.println("Provide name of file to decode: ");
                    filename = scanner2.next();
                    String encodedMessage = Helper.readEncodedFromFile(filename); // file inside ./files directory
                    String[] encodedMessageTokens = Helper.getEncodedMessageTokens(encodedMessage); // get tokens from text
                    String[] decodedTokens = Decoder.decode(encodedMessageTokens); // decode tokens
                    Helper.writeToFile(filename, decodedTokens,Helper.TYPE.DECODE); // write decoded tokens to file
                }
                case 3 -> System.exit(0);
                default -> {
                    System.out.println("Illegal argument! Exit."); // Exit program
                    System.exit(1);
                }
            }
        }

    }
}
