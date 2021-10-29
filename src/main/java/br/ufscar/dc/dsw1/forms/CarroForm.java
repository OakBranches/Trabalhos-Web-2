package br.ufscar.dc.dsw1.forms;

import br.ufscar.dc.dsw1.validation.Multipart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class CarroForm {
    @NotNull (message = "{NotBlank}")
    private float valor;

    @NotNull (message = "{NotBlank}")
    private float km;

    @NotBlank (message = "{NotBlank}")
    @Size(min = 7, max = 7, message="{Size.CarroForm.placa}")
    private String placa;

    @NotBlank (message="{Size.CarroForm.modelo}")
    private String modelo;

    @NotBlank (message="{NotBlank}")
    private String descricao;

    @NotBlank (message = "{NotBlank}")
    private String chassi;

    @NotNull (message = "{NotBlank}")
    private int ano;

    @NotNull (message = "{NotBlank}")
    @NotEmpty (message = "{NotBlank}")
    @Multipart (message = "{Multipart}")
    private List<MultipartFile> imagens;

    @Override
    public String toString() {
        return "CarroForm{" +
                "valor=" + valor +
                ", km=" + km +
                ", placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", chassi='" + chassi + '\'' +
                ", ano=" + ano +
                ", imagens=" + imagens +
                '}';
    }
}
