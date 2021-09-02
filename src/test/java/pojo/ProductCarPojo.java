package pojo;

public class ProductCarPojo {

    private String idProduto;
    private Integer quantidade;

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public ProductCarPojo(String idProduto, Integer quantidade){
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }
}
