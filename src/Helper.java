import java.io.*;
import java.util.*;

/**
 * Class containing static helper functions.
 */
public class Helper {

    /**
     * Matrix used to correct message errors.
     */
    private static final int[][] H = {
            {1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1}};

    /**
     * type of tokens: encoded tokens / decoded tokens
     */
    public enum TYPE {
        DECODE, ENCODE
    }

    /**
     * Writes tokens to file.
     * @param filename name of file.
     * @param tokens tokens to be written to the file.
     * @param type type of written tokens: encoded/decoded.
     * @throws IOException Any IO error.
     */
    public static void writeToFile(String filename, String[] tokens, Helper.TYPE type) throws IOException {
        String directory = (type == TYPE.ENCODE) ? "encoded" : "decoded";
        File fileReport = new File(directory + File.separator +  filename + ".txt");
        FileWriter fwReport = new FileWriter(fileReport);
        PrintWriter pw = new PrintWriter(fwReport);
        StringBuilder builder = new StringBuilder();
        for (var token : tokens) {
            builder.append(token);
        }
        pw.print(builder);
        pw.close();
    }

    /**
     * Reads text from file inside ./files/ directory
     * @param filename file inside ./files/ directory
     * @return text.
     * @throws IOException Any IO error.
     */
    public static String readPlainTextFromFile(String filename) throws IOException {
        StringBuilder builder = readText(filename);
        String fullText = builder.toString();
        System.out.println("Char tokens read successfully: " + fullText);
        return Helper.charStringToBitString(fullText);
    }

    /**
     * Reads contents of the file.
     * @param filename path to file.
     * @return builder.
     * @throws FileNotFoundException Any IO error.
     */
    private static StringBuilder readText(String filename) throws FileNotFoundException {
        File input = new File(filename + ".txt");
        Scanner scanner = new Scanner(input);
        List<String> lines = new LinkedList<>();
        for (int i = 0; i < 100; i++) { // max 'i' just in case
            if (!scanner.hasNextLine()) break;
            String data = scanner.nextLine();
            lines.add(data);
        }
        StringBuilder builder = new StringBuilder();
        for (var line: lines) {
            builder.append(line);
        }
        return builder;
    }

    /**
     * Reads encoded text from file inside ./encoded/ directory
     * @param filename file inside ./encoded/ directory
     * @return text.
     * @throws IOException Any IO error.
     */
    public static String readEncodedTextFromFile(String filename) throws IOException {
        StringBuilder builder = readText(filename);
        return builder.toString();
    }

    /**
     * Reads encoded text from file inside ./encoded/ directory
     * @param filename file inside ./encoded/ directory
     * @return text.
     * @throws IOException Any IO error.
     */
    public static String readEncodedFromFile(String filename) throws IOException {
        return readEncodedTextFromFile("encoded" + File.separator + filename);
    }

    /**
     * Divide String representing eries of bits into message tokens of 8 bits.
     * @param message text message.
     * @return Array of message tokens (each 8bits)
     */
    public static String[] getMessageTokens(String message) { // 8 bits of message

        int length = message.length(); // length of input string message
        int offsetBits = length % 8; // check message = 8 * n bits
        int bitsToAdd = (offsetBits == 0) ? 0 : (8 - offsetBits);
        String[] tokens = new String[(length + bitsToAdd) / 8];
        StringBuilder builder;
        for (int i = 0; i < (length + bitsToAdd); i += 8) {
            builder = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                int index = i + j;
                Character character = (index >= length) ? '0' : message.charAt(index);
                builder.append(character);
            }
            tokens[i / 8] = builder.toString();
        }
        return tokens;
    }

    /**
     * Split encoded text into array of tokens each having 16 bits.
     * @param encodedMessage encoded text.
     * @return tokens of 16 bits.
     */
    public static String[] getEncodedMessageTokens(String encodedMessage) {
        String[] tokens = new String[encodedMessage.length() / 16];
        StringBuilder builder;
        for (int i = 0; i < encodedMessage.length(); i += 16) {
            builder = new StringBuilder();
            for (int j = 0; j < 16; j++) {
                int index = i + j;
                Character character = encodedMessage.charAt(index);
                builder.append(character);
            }
            tokens[i / 16] = builder.toString();
        }
        return tokens;
    }

    /**
     * Computes parity bits for given 8 bit message using H matrix.
     * @param message8bit 8 bit message.
     * @return array of parity bits.
     */
    public static int[] getParityBitsForMessage(int[] message8bit) {
        assert message8bit.length == 8;
        int[] parityBits = new int[8];
        for (int j = 0; j < 8; j++) {
            int[] row = Helper.H[j];
            int sum = 0;
            for (int i = 0; i < 8; i++) {
                sum += (row[i] & message8bit[i]);
            }
            parityBits[j] = sum % 2;
        }
        return parityBits;
    }

    /**
     * Convert string of bits into array of bits.
     * @param token string of bits.
     * @return array of bits.
     */
    public static int[] toIntegerToken(String token) {
        int[] array = new int[token.length()];
        for (int i = 0; i < token.length(); i++) {
            int numericValue = Character.getNumericValue(token.charAt(i));
            array[i] = numericValue;
        }
        return array;
    }

    /**
     * Converts array of zeros and ones into corresponding string.
     * @param token array of zeros and ones.
     * @return  corresponding string.
     */
    public static String  toStringToken(int[] token) {
        StringBuilder builder = new StringBuilder();
        for (int j : token) {
            builder.append(j);
        }
        return builder.toString();
    }

    /**
     * Computes array of bits representing error vector.
     * Column of matrix H that is identical to the computed array is a column containing an error.
     * @param message16bits 16bit token (8 bit message + 8 parity bits).
     * @return array of length 8, providing information about possible errors.
     */
    public static int[] answerVector(int[] message16bits) {
        int[] answerVector = new int[8];
        int sum;
        for(int i = 0; i < 8; i++) {
            sum = 0;
            for (int j = 0; j < 16; j++) {
                sum += (message16bits[j] & Helper.H[i][j]);
            }
            answerVector[i] = (sum % 2);
        }
        return answerVector;
    }

    /**
     * Finds columns containing errors.
     * @param answerVector Vector providing error information.
     * @return List containing indexes of incorrect bits.
     */
    public static List<Integer> getColumnsContainingError(int[] answerVector) { // analyse 8 bit answer vector
        List<Integer> columnsWithErrors = new LinkedList<>();

        List<int[]> hArrays = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            hArrays.add(new int[8]);
            boolean columnMatchingAnswerVector = true;
            for (int j = 0; j < 8; j++) {
                hArrays.get(i)[j] = Helper.H[j][i];
                if (answerVector[j] != Helper.H[j][i]) {
                    columnMatchingAnswerVector = false;
                }

            }
            if (columnMatchingAnswerVector) {
                columnsWithErrors.add(i);
                return columnsWithErrors; // we found the column that contains this single error
            }
        }

        for (int i = 0; i < hArrays.size(); i++) { // check which two columns combine into answer vector (error vector)
            for (int j = i + 1; j < hArrays.size(); j++) {
                int[] column1 = hArrays.get(i);
                int[] column2 = hArrays.get(j);
                boolean matchingColumns = true;
                for (int k = 0; k < answerVector.length; k++) {
                    if (!(answerVector[k] == ((column1[k] + column2[k]) % 2))) {
                        matchingColumns = false;
                        break;
                    }
                }
                if (matchingColumns) {
                    columnsWithErrors.add(i); // found first column containing error.
                    columnsWithErrors.add(j); // found second column containing error.
                    return columnsWithErrors;
                }
            }
        }

        return columnsWithErrors;
    }

    /**
     * Converts text containing bits to text containing corresponding characters.
     * @param text text containing bits.
     * @return text containing chars.
     */
    public static String bitStringToCharString(String text) {
        assert text.length() % 8 == 0;
        int numberOfTokens = text.length() / 8;
        char[] chars = new char[numberOfTokens];
        for (int i = 0; i < numberOfTokens; i++) {
            int beginIndex = i * 8;
            int value = Integer.parseInt(text.substring(beginIndex,beginIndex + 8), 2);
            chars[i] = (char) value;
        }
        return String.valueOf(chars);
    }

    /**
     * Converts text containing chars to text containing bits corresponding to characters.
     * @param text ext containing chars.
     * @return text containing bits.
     */
    public static String charStringToBitString(String text) {
        char[] chars = text.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char character : chars) {
            builder.append(charToBinarySequence(character));
        }
        return builder.toString();
    }

    /**
     * Converts single character into String representing 8bits corresponding to char (ASCII).
     * @param c character to get binary representation of.
     * @return String with bit representation.
     */
    public static String charToBinarySequence(char c) {
        byte b2 = (byte) c;
        return String.format("%8s", Integer.toBinaryString(b2 & 0xFF)).replace(' ', '0');
    }
}
