package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;

public class RandomCodeGenerator {
    public static final String getCode(String in, int len){
        String retval = "";
        try {
            byte[] bytesOfMessage = in.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            retval = thedigest.toString();
        }catch (Exception e){
            Log.d("RandomCodeGenerator", e.getMessage());
        }
        return retval;
    }

    public static final String getPasswordHash(String in){
        String retval = "";
        try {
            byte[] bytesOfMessage = in.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            BigInteger bigInt = new BigInteger(1,thedigest);
            String hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while(hashtext.length() < 32 ){
                hashtext = "0"+hashtext;
            }
            retval = hashtext;
        }catch (Exception e){
            Log.d("RandomCodeGenerator", e.getMessage());
        }
        return retval;
    }
}
