package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class EmptyInputValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String val = (String) value;
		
		if(val == null || val.length() < 5) {
			FacesMessage msg = new FacesMessage("Field " + component.getId() + " can not be empty!");
			throw new ValidatorException(msg);
		}
		
	}

}
