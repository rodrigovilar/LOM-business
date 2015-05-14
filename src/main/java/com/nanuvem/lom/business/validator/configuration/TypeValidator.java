package com.nanuvem.lom.business.validator.configuration;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;

import com.nanuvem.lom.api.Property;
import com.nanuvem.lom.business.validator.ValidationError;

public class TypeValidator implements AttributeValidator {

	private Class<?> clazz;

	public TypeValidator(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void validateDefault(List<ValidationError> errors,
			JsonNode configuration) {
		//Do not need to validate default value
	}

	public void validateValue(List<ValidationError> errors,
			JsonNode configuration, Property value) {
		
		if (value.getValue() == null) {
			return;
		}
		
		if (clazz.isAssignableFrom(Boolean.class)) {
			if (!"true".equals(value.getValue())
					|| !"false".equals(value.getValue())) {
				ValidationError.addError(errors, "The value for the '"
						+ value.getPropertyType().getName()
						+ "' attribute must be a boolean");
			}
		}

		if (clazz.isAssignableFrom(Integer.class)) {
			if (!StringUtils.isNumeric(value.getValue())) {
				ValidationError.addError(errors, "The value for the '"
						+ value.getPropertyType().getName()
						+ "' attribute must be an int");
			}
		}
	}

}
