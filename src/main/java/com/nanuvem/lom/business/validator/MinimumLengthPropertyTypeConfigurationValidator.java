package com.nanuvem.lom.business.validator;

import java.util.List;

import com.nanuvem.lom.business.validator.configuration.PropertyTypeValidator;
import com.nanuvem.lom.business.validator.configuration.ConfigurationFieldValidator;

public class MinimumLengthPropertyTypeConfigurationValidator implements
		ValueValidator<Integer> {

	public void validate(List<ValidationError> errors, String attribute,
			String value, Integer minLength, boolean defaultValue) {

		if (value != null && value.length() < minLength) {
			String message = (defaultValue) ? "the default value is smaller than minlength"
					: "The value for '" + attribute
							+ "' must have a minimum length of " + minLength
							+ " characters";
			ValidationError.addError(errors, message);
		}
	}

	public PropertyTypeValidator createFieldValidator(String field) {
		return new ConfigurationFieldValidator(field, Integer.class);
	}

}
