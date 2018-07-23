package br.com.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.model.Produto;

public class ProdutoValidation implements Validator{

	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return Produto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmpty(errors, "titulo", "fild.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "fild.required");
		ValidationUtils.rejectIfEmpty(errors, "paginas", "fild.required");
		
		Produto produto = (Produto) target;
		
		if (produto.getPaginas() ==  null || produto.getPaginas() <= 0) {
			errors.rejectValue("paginas", "fild.required");
		}			
		
	}

}
