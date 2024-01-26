package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class SimpleCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public SimpleCompressorOutputStream(OutputStream os){
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

    public void write(byte[] b){
        byte[] compressedArray = compress(b);

        try {
            out.write(compressedArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public byte[] compress(byte[] bytes){
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

        byte zeroCounter = 0;
        byte oneCounter = 0;
        if (bytes[index] == 1){
            oneCounter++;
            res.add((byte)0);
        }
        else {
            zeroCounter++;
        }
        index++;
        for (int i =index ; i< bytes.length; i++){
            if(bytes[i] == 0 ){
                if(bytes[i-1] == 0){
                    if(zeroCounter == (byte)127){
                        res.add((byte)127);
                        zeroCounter = 0;
                    }
                    zeroCounter++;
                }
                else {
                    res.add(oneCounter);
                    zeroCounter++;
                    oneCounter=0;
                }

            }
            else {
                if(bytes[i-1] == 1){
                    if(oneCounter  == (byte) 127){
                        res.add((byte) 127);
                        oneCounter = 0;
                    }
                }
                else {
                    res.add(zeroCounter);
                    zeroCounter= 0;
                }
                oneCounter++;
            }
        }
        if (zeroCounter!= 0){
            res.add(zeroCounter);
        }
        if (oneCounter!=0){
            res.add(oneCounter);
        }
        byte[] result = new byte[res.size()];
        for (int i = 0; i < res.size(); i++){
            result[i] = res.get(i);
        }
        return result;
    }
}
