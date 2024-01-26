package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;

    public SimpleDecompressorInputStream(InputStream is){
        super();
        in = is;
    }
    public int read(byte[] b){
        byte[] compressedArray = decompress(b);
        try {
            return in.read(compressedArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int read() {
        try {
            return in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decompress(byte[] b){
        byte[] bytes = new byte[b.length];
        try {
            in.read(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Byte> res = new ArrayList<>();
        int isOver = 0;
        int index = 0;
        while (isOver != 2){
            if(bytes[index] != (byte)127){
                isOver++;
            }
            res.add(bytes[index]);
            index++;
        }
        for (int i = index; i < bytes.length; i++){
            for(int k = 0; k < bytes[i]; k++) {
                if(i % 2 == 0) {
                    res.add((byte)0);
                }
                if(i % 2 == 1) {
                    res.add((byte)1);
                }
            }
        }

        System.out.println();
        for (int i = 0; i < res.size(); i++){
            b[i] = res.get(i);

        }
        return b;
    }
}
