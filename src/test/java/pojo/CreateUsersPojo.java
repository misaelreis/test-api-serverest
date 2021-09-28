package pojo;
import lombok.Data;

@Data
public class CreateUsersPojo {
    private String nome;
    private String email;
    private String password;
    private String administrador;
}
