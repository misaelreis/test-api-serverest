package pojo;

import java.util.ArrayList;
import lombok.Data;

@Data
public class CarPojo {

    private ArrayList<ProductCarPojo> produtos = new ArrayList<ProductCarPojo>();

    public void addProducts(ProductCarPojo produtos){
        this.produtos.add(produtos);
    }
}
