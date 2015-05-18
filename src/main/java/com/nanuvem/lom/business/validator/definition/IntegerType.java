package com.nanuvem.lom.business.validator.definition;

import java.util.ArrayList;
import java.util.List;

import com.nanuvem.lom.api.PropertyType;
import com.nanuvem.lom.business.validator.MaximumValuePropertyTypeConfigurationValidator;
import com.nanuvem.lom.business.validator.MinimumValuePropertyTypeConfigurationValidator;
import com.nanuvem.lom.business.validator.configuration.PropertyTypeValidator;
import com.nanuvem.lom.business.validator.configuration.PropertyTypeValidatorWithValue;
import com.nanuvem.lom.business.validator.configuration.ConfigurationFieldValidator;
import com.nanuvem.lom.business.validator.configuration.MandatoryValidator;
import com.nanuvem.lom.business.validator.configuration.MinAndMaxValidator;

public class IntegerType implements TypeDefinition {

	public List<PropertyTypeValidator> getValidators() {
		List<PropertyTypeValidator> validators = new ArrayList<PropertyTypeValidator>();

		validators.add(new MandatoryValidator());
		
		validators.add(new ConfigurationFieldValidator(
				PropertyType.DEFAULT_CONFIGURATION_NAME, Integer.class));

		validators.add(new PropertyTypeValidatorWithValue<Integer>(
				PropertyType.MINVALUE_CONFIGURATION_NAME,
				PropertyType.DEFAULT_CONFIGURATION_NAME,
				new MinimumValuePropertyTypeConfigurationValidator(), Integer.class));
		validators.add(new PropertyTypeValidatorWithValue<Integer>(
				PropertyType.MAXVALUE_CONFIGURATION_NAME,
				PropertyType.DEFAULT_CONFIGURATION_NAME,
				new MaximumValuePropertyTypeConfigurationValidator(), Integer.class));
		validators.add(new MinAndMaxValidator(
				PropertyType.MAXVALUE_CONFIGURATION_NAME,
				PropertyType.MINVALUE_CONFIGURATION_NAME));

		validators.add(new ConfigurationFieldValidator(
				PropertyType.MANDATORY_CONFIGURATION_NAME, Boolean.class));

		return validators;
	}

	public boolean containsConfigurationField(String fieldName) {
		return PropertyType.MANDATORY_CONFIGURATION_NAME.equals(fieldName)
				|| PropertyType.DEFAULT_CONFIGURATION_NAME.equals(fieldName)
				|| PropertyType.MINVALUE_CONFIGURATION_NAME.equals(fieldName)
				|| PropertyType.MAXVALUE_CONFIGURATION_NAME.equals(fieldName);
	}

	public Class<?> getAttributeClass() {
		return Integer.class;
	}

}
