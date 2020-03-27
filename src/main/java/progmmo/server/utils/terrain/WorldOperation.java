package progmmo.server.utils.terrain;

public class WorldOperation {

    public WorldManipulation worldManipulation;
    public int threshold;
    byte[] seed = new byte[12];
    public String name;

    public WorldOperation() {
        this.threshold = 50;
        this.seed = hexStringToByteArray("000000000000");
    };

    public WorldOperation(byte[] seed, int threshold, String name) {
        this.threshold = threshold;
        this.seed = seed;
        this.worldManipulation = Transform.Operations.get(name);
        this.name = name;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
