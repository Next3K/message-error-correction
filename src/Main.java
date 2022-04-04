import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main class of the program
 */
public class Main {
    public static void main(String[] args) throws IOException {


        while(true) {
            System.out.println("Choose action: \n 1) encode file \n 2) decode file \n 3) Exit");
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

            String filename; // name of file to encode/decode

            switch (choice) {
                case 1 -> { // chosen action: encoding
                    System.out.println("Provide the name of file to encode: ");
                    filename = in.next();
                    String filePath = "files" + File.separator + filename; // file: ./files/filename.txt
                    String fullMessage = Helper.readSeriesOfBitsFromTextFile(filePath); // plain text read from file
                    String[] messageTokens = Helper.getMessageTokens(fullMessage); // split message into tokens
                    String[] encodedTokens = Encoder.encode(messageTokens); // encode tokens
                    Helper.writeToFile(filename, encodedTokens,Helper.TYPE.ENCODE); // write output to ./encoded/filename.txt
                }
                case 2 -> { // chosen action: decoding
                    System.out.println("Provide the name of file to decode: ");
                    filename = in.next();
                    String encodedMessage = Helper.readEncodedFromFile(filename); // file: ./encoded/filename.txt
                    String[] encodedMessageTokens = Helper.getEncodedMessageTokens(encodedMessage); // get tokens
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
