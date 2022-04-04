import java.io.IOException;
import java.util.Arrays;

/**
 * Message encoder class.
 */
public class Encoder {

    /**
     * Encode array of message tokens (8bit tokens) into encoded 16bit tokens (8bit message + 8bit parity bits).
     * @param notEncodedTokens array of message tokens (8bit tokens).
     * @return encoded tokens (8bit message + 8bit parity bits).
     * @throws IOException Any IO error.
     */
    public static String[] encode(String[] notEncodedTokens) throws IOException {

        String[] parityBits = new String[notEncodedTokens.length];

        for (int j = 0; j < notEncodedTokens.length; j++) {
            String message8bit = notEncodedTokens[j];
            int[] token8bit = Helper.toIntegerToken(message8bit);
            int[] parityBitsForMessage = Helper.getParityBitsForMessage(token8bit);
            int[] token16bit = Arrays.copyOf(token8bit, 16);
            System.arraycopy(parityBitsForMessage, 0, token16bit, 8, 8);
            StringBuilder tmp = new StringBuilder();
            for (var number: token16bit) {
                tmp.append(number);
            }
            parityBits[j] = tmp.toString();
        }
        System.out.println("Message encoded successfully: " + Arrays.toString(parityBits));

        return parityBits;
    }
}
