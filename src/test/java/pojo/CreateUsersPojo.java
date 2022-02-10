package pojo;
import lombok.Data;

@Data
public class CreateUsersPojo extends LoginPojo {
    private String nome;
    private String administrador;
}
