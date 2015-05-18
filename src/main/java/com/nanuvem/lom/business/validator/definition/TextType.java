package com.nanuvem.lom.business.validator.definition;

import java.util.ArrayList;
import java.util.List;

import com.nanuvem.lom.api.PropertyType;
import com.nanuvem.lom.business.validator.MaximumLengthPropertyTypeConfigurationValidator;
import com.nanuvem.lom.business.validator.MinimumLengthPropertyTypeConfigurationValidator;
import com.nanuvem.lom.business.validator.RegexPropertyTypeConfigurationValidator;
import com.nanuvem.lom.business.validator.configuration.PropertyTypeValidator;
import com.nanuvem.lom.business.validator.configuration.PropertyTypeValidatorWithValue;
import com.nanuvem.lom.business.validator.configuration.ConfigurationFieldValidator;
import com.nanuvem.lom.business.validator.configuration.MandatoryValidator;
import com.nanuvem.lom.business.validator.configuration.MinAndMaxValidator;

public class TextType implements TypeDefinition {

	public List<PropertyTypeValidator> getValidators() {
		List<PropertyTypeValidator> validators = new ArrayList<PropertyTypeValidator>();
		validators.add(new MandatoryValidator());

		validators.add(new PropertyTypeValidatorWithValue<String>(
				PropertyType.REGEX_CONFIGURATION_NAME,
				PropertyType.DEFAULT_CONFIGURATION_NAME,
				new RegexPropertyTypeConfigurationValidator(), String.class));

		validators.add(new ConfigurationFieldValidator(
				PropertyType.DEFAULT_CONFIGURATION_NAME, String.class));
		validators.add(new PropertyTypeValidatorWithValue<Integer>(
				PropertyType.MINLENGTH_CONFIGURATION_NAME,
				PropertyType.DEFAULT_CONFIGURATION_NAME,
				new MinimumLengthPropertyTypeConfigurationValidator(), Integer.class));
		validators.add(new PropertyTypeValidatorWithValue<Integer>(
				PropertyType.MAXLENGTH_CONFIGURATION_NAME,
				PropertyType.DEFAULT_CONFIGURATION_NAME,
				new MaximumLengthPropertyTypeConfigurationValidator(), Integer.class));
		validators.add(new MinAndMaxValidator(
				PropertyType.MAXLENGTH_CONFIGURATION_NAME,
				PropertyType.MINLENGTH_CONFIGURATION_NAME));

		validators.add(new ConfigurationFieldValidator(
				PropertyType.MANDATORY_CONFIGURATION_NAME, Boolean.class));

		return validators;
	}

	public boolean containsConfigurationField(String fieldName) {
		return PropertyType.MANDATORY_CONFIGURATION_NAME.equals(fieldName)
				|| PropertyType.DEFAULT_CONFIGURATION_NAME.equals(fieldName)
				|| PropertyType.MINLENGTH_CONFIGURATION_NAME.equals(fieldName)
				|| PropertyType.MAXLENGTH_CONFIGURATION_NAME.equals(fieldName)
				|| PropertyType.REGEX_CONFIGURATION_NAME.equals(fieldName);
	}

	public Class<?> getAttributeClass() {
		return String.class;
	}

}
