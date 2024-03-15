package vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductReceive {
    private Product product;
    private String email;
    private int quantity;
}
