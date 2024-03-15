package vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.converts;

import java.util.Base64;

public class CodingEncryption {
    public String decode(String textEnCoded){
        return new String(Base64.getDecoder().decode(textEnCoded.getBytes()));
    }
    public String enCode(String text){
        return new String(Base64.getEncoder().encode(text.getBytes()));
    }
}
