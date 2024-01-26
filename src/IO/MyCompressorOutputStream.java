package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.zip.*;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream os) {
        super();
        out = os;
    }


    @Override
    public void write(int b) {
        try {
            out.write(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(byte[] b) {
        byte[] compressedArray = compress(b);

        try {
            out.write(compressedArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] compress(byte[] bytes) {
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

        BitSet bitSet = new BitSet(bytes.length);
        int curr = 0;
        for (int i = index; i < bytes.length; i++) {
            if (bytes[i] == (byte) 0) {
                bitSet.set(curr);
            }
            curr++;
        }

        // Compress BitSet to byte array
        byte[] compressedBytes = bitSet.toByteArray();

        // Print the compressed byte array
        byte[] result = new byte[res.size()+compressedBytes.length];
        for (int j = 0; j<res.size(); j++){
            result[j] = res.get(j);
        }
        for (int k =0; k < compressedBytes.length; k++) {
            result[k+res.size()] = compressedBytes[k];
        }
        return result;
    }
}
