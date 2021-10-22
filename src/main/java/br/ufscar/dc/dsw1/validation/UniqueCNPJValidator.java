package br.ufscar.dc.dsw1.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.ufscar.dc.dsw1.dao.ILojaDAO;
import br.ufscar.dc.dsw1.domain.Loja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UniqueCNPJValidator implements ConstraintValidator<UniqueCNPJ, String> {

    @Autowired
    private ILojaDAO dao;

    @Override
    public boolean isValid(String CNPJ, ConstraintValidatorContext context) {
        if (dao != null) {
            Loja loja = dao.findByCnpj(CNPJ);
            return loja == null;
        } else {
            // Durante a execução da classe LivrariaMvcApplication
            // não há injeção de dependência
            return true;
        }

    }
}
