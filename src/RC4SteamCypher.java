public class RC4SteamCypher {

    private char[] key;
    private static final int S_LENGTH = 256;
    private static final int MIN_KEY_LENGTH = 5;

    public RC4SteamCypher(String key) throws InvalidKeyException {
        checkKey(key);
    }

    // Pseudo-random generation algorithm
    public char[] encrypt(final char[] msg) {
        int[] s = initS(key);
        char[] result = new char[msg.length];
        int i = 0;
        int j = 0;
        for (int n = 0; n < msg.length; n++) {
            i = (i + 1) % S_LENGTH;
            j = (j + s[i]) % S_LENGTH;
            swap(i, j, s);
            int rand = s[(s[i] + s[j]) % S_LENGTH];
            result[n] = (char) (rand ^ (int) msg[n]);
        }
        return result;
    }

    public char[] decrypt(final char[] msg) {
        return encrypt(msg);
    }

    //Key-scheduling algorithm
    private int[] initS(char[] key) {
        int[] s = new int[S_LENGTH];
        int j = 0;

        for (int i = 0; i < S_LENGTH; i++) {
            s[i] = i;
        }

        for (int i = 0; i < S_LENGTH; i++) {
            j = (j + s[i] + key[i % key.length]) % S_LENGTH;
            swap(i, j, s);
        }
        return s;
    }

    //Swaps 2 values in s array
    private void swap(int i, int j, int[] s) {
        int temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }

    //Checks if key length is correct
    public void checkKey(String key) throws InvalidKeyException {
        if (!(key.length() >= MIN_KEY_LENGTH && key.length() < S_LENGTH)) {
            throw new InvalidKeyException("Key length has to be between "
                    + MIN_KEY_LENGTH + " and " + (S_LENGTH - 1));
        }
        this.key = key.toCharArray();
    }

}
