package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils;

import android.util.Log;

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
}
