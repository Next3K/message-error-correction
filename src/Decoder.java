
import java.util.Arrays;

/**
 * Message decoder class.
 */
public class Decoder {

    /**
     * Decodes previously encoded tokens.
     * @param encodedTokens array of Strings representing 16 bits of encoded message (message bits + parity bits)
     * @return array of Strings representing 8 bits of decoded message (only message bits)
     */
    public static String[] decode(String[] encodedTokens) {
        String[] decodedTokens = new String[encodedTokens.length];
        for (int i = 0; i < encodedTokens.length; i++) {
            int[] messageBits = Helper.toIntegerToken(encodedTokens[i].substring(0, 8));
            int[] answerVector = Helper.answerVector(Helper.toIntegerToken(encodedTokens[i]));
            boolean isAllZeros = true;
            for (var number: answerVector) {
                if (number != 0) {
                    isAllZeros = false;
                    break;
                }
            }
            if (!isAllZeros) {
                correctError(answerVector,messageBits);
            }

            String token8bit = Helper.toStringToken(messageBits);
            decodedTokens[i] = Helper.bitStringToCharString(token8bit);
        }

        System.out.println("Message decoded successfully: " + Arrays.toString(decodedTokens));
        return decodedTokens;
    }

    /**
     * Corrects bit errors inside message.
     * Can correct up to two bit errors inside 8-bit piece of message.
     * @param correctnessVector vector representing error hidden inside the message.
     * @param messageBits message containing error.
     */
    private static void correctError(int[] correctnessVector, int[] messageBits) {
        var columnsContainingError = Helper.getColumnsContainingError(correctnessVector); // find columns with errors
        for (var errorIndex: columnsContainingError) {
            if (errorIndex < 8) { // correct bits if error is inside the message
                messageBits[errorIndex] ^=  1; // flip the error bit
            }
        }
    }

}
