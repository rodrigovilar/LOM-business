package com.nanuvem.lom.business.validator;

import java.util.List;

import com.nanuvem.lom.business.validator.configuration.PropertyTypeValidator;
import com.nanuvem.lom.business.validator.configuration.ConfigurationFieldValidator;

public class MinimumNumbersPropertyTypeConfigurationValidator implements
		ValueValidator<Integer> {

	public void validate(List<ValidationError> errors, String attribute,
			String value, Integer minNumbers, boolean defaultValue) {

		if (value != null) {
			int numericCharacterCounter = 0;
			for (int i = 0; i < value.length(); i++) {
				if (Character.isDigit(value.toCharArray()[i])) {
					numericCharacterCounter++;
				}
			}
			if (numericCharacterCounter < minNumbers) {
				String messagePlural = minNumbers > 1 ? "s" : "";

				String message = (defaultValue) ? "the default value must have at least "
						+ minNumbers + " numerical character" + messagePlural
						: "The value for '" + attribute + "' must be at least "
								+ minNumbers + " number" + messagePlural;

				ValidationError.addError(errors, message);
			}
		}
	}

	public PropertyTypeValidator createFieldValidator(String field) {
		return new ConfigurationFieldValidator(field, Integer.class);
	}

}
