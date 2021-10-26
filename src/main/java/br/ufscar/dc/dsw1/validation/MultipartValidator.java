package br.ufscar.dc.dsw1.validation;

import br.ufscar.dc.dsw1.dao.ILojaDAO;
import br.ufscar.dc.dsw1.domain.Loja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


@Component
public class MultipartValidator implements ConstraintValidator<Multipart, List<MultipartFile>> {

    @Override
    public boolean isValid(List<MultipartFile> list, ConstraintValidatorContext context) {
        if (list.size() > 1) {
            return true;
        } else if (list.size() == 1){
            return !list.get(0).isEmpty();
        }else {
            return false;
        }

    }
}
