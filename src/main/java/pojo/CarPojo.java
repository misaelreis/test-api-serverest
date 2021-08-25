package pojo;

import java.util.ArrayList;

public class CarPojo {

    private ArrayList<ProductCarPojo> produtos = new ArrayList<ProductCarPojo>();

    public ArrayList<ProductCarPojo> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<ProductCarPojo> products) {
        this.produtos = products;
    }

    public void addProducts(ProductCarPojo produtos){
        this.produtos.add(produtos);
    }
}
