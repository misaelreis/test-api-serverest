package pojo;
import lombok.Data;

@Data
public class ProductCarPojo {
    private String idProduto;
    private Integer quantidade;

    public ProductCarPojo(String idProduto, Integer quantidade){
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }
}
