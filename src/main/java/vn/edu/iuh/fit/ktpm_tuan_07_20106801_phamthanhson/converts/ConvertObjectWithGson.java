package vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.converts;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.models.Product;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.models.ProductReceive;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConvertObjectWithGson {
    private final Gson gson = new Gson();

    public String ObjectToJson(ProductReceive productReceive) {
        return gson.toJson(productReceive);
    }
    public  ProductReceive JsonToObject(String json){
        return gson.fromJson(json,ProductReceive.class);
    }
    public String ListObjectToJson(List<ProductReceive> productReceives){
        return gson.toJson(productReceives);
    }
    public List<ProductReceive> JsonToListObject(String json){
        Type listType = new TypeToken<List<ProductReceive>>(){}.getType();

        return gson.fromJson(json, listType);
    }

}
