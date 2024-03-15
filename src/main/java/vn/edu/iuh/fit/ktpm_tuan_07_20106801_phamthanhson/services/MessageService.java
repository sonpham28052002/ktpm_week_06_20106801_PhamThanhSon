package vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.services;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.converts.CodingEncryption;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.converts.ConvertObjectWithGson;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.models.Product;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.models.ProductOrder;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.models.ProductReceive;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.repositories.ProductOrderRepository;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Autowired
    private SendMail sendMail;
    @JmsListener(destination = "animal_Shop")
    public void receiveMessage(final Message jsonMessage) throws JMSException {
        CodingEncryption encryption = new CodingEncryption();
        ConvertObjectWithGson gson = new ConvertObjectWithGson();
        if (jsonMessage instanceof TextMessage) {
            //1. read message data
            String messageData = ((TextMessage) jsonMessage).getText();
            //2. ==> decode
            String json = encryption.decode(messageData);
            List<ProductReceive> productReceives = gson.JsonToListObject(json);
            //3. check for quantity
            List<Product> productList = new ArrayList<>();
            for (ProductReceive productReceive :productReceives) {
                Product product = productRepository.findById(productReceive.getProduct().getId()).get();
                if (productReceive.getQuantity() > product.getQuantity()){
                    productList.add(product);
                }
            }
            //4. make order or reject
            String textNotification ="";
            if (productList.isEmpty()){
                textNotification+="Đơn hàng của bạn đã được lập thành công. \n ";
                double total = 0;
                for (ProductReceive productReceive:productReceives) {
                    total+=productReceive.getProduct().getPrice()*productReceive.getQuantity();
                    textNotification += "\t\t"+productReceive.getProduct().getName()+":\t"+productReceive.getProduct().getPrice()*productReceive.getQuantity()+"\n";
                    ProductOrder productOrder = new ProductOrder();
                    productOrderRepository.save(productOrder);
                }
                textNotification += "\t\tTotal: \t"+total;

            }else {
                textNotification+="Đơn hàng của bạn không thể lập.\n \t\tLý do:\n ";
                for (Product product:productList) {
                    textNotification+="\t\t- "+product.getName();
                }
                textNotification+="=> không đủ số lượng yêu cầu";
            }
            System.out.println(textNotification);
            //5. send email

            sendMail.sendMail(productReceives.get(0).getEmail(),"THÔNG BÁO ĐƠN HÀNG TỪ ANIMAL SHOP",textNotification);
        }
    }
}


