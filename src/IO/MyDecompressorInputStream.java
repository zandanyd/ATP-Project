package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.zip.*;
import java.io.ByteArrayOutputStream;


public class MyDecompressorInputStream extends InputStream{
    private InputStream in;

    public MyDecompressorInputStream(InputStream is){
        super();
        in = is;
    }


    @Override
    public int read() {
        try {
            return in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public int read(byte[] b){
        byte[] compressedArray = decompress(b);

        try {
           return in.read(compressedArray);
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
        for (int i =0; i<res.size(); i++){
            b[i] = res.get(i);

        }
        byte[] curr = new byte[bytes.length - res.size()];
        for (int j = 0; j<curr.length; j++){
            curr[j] = bytes[j+res.size()];
        }
        // Decompress byte array back to BitSet
        BitSet decompressedBitSet = BitSet.valueOf(curr);

        // Convert BitSet back to binary array

        for (int i = 0; i < b.length- res.size(); i++) {
            if(decompressedBitSet.get(i)) {
                b[i+res.size()] = 0;
            }
            else {
                b[i+res.size()] = 1;
            }
        }
        return b;
    }

}

