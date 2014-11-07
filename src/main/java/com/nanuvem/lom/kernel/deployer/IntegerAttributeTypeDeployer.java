package com.nanuvem.lom.kernel.deployer;

import java.util.ArrayList;
import java.util.List;

import com.nanuvem.lom.kernel.validator.AttributeConfigurationValidator;
import com.nanuvem.lom.kernel.validator.BooleanAttributeConfigurationValidator;
import com.nanuvem.lom.kernel.validator.IntegerAttributeConfigurationValidatorWithDefault;
import com.nanuvem.lom.kernel.validator.IntegerAttributeConfigurationValidator;
import com.nanuvem.lom.kernel.validator.MaximumValueAttributeConfigurationValidator;
import com.nanuvem.lom.kernel.validator.MinAndMaxConfigurationValidator;
import com.nanuvem.lom.kernel.validator.MinimumValueAttributeConfigurationValidator;

public class IntegerAttributeTypeDeployer implements AttributeTypeDeployer {

	public List<AttributeConfigurationValidator> getValidators() {
		List<AttributeConfigurationValidator> validators = new ArrayList<AttributeConfigurationValidator>();

		validators.add(new IntegerAttributeConfigurationValidator(
				DEFAULT_CONFIGURATION_NAME));

		validators.add(new IntegerAttributeConfigurationValidatorWithDefault(
				MINVALUE_CONFIGURATION_NAME, DEFAULT_CONFIGURATION_NAME,
				new MinimumValueAttributeConfigurationValidator()));
		validators.add(new IntegerAttributeConfigurationValidatorWithDefault(
				MAXVALUE_CONFIGURATION_NAME, DEFAULT_CONFIGURATION_NAME,
				new MaximumValueAttributeConfigurationValidator()));
		validators.add(new MinAndMaxConfigurationValidator(
				MAXVALUE_CONFIGURATION_NAME, MINVALUE_CONFIGURATION_NAME));

		validators.add(new BooleanAttributeConfigurationValidator(
				MANDATORY_CONFIGURATION_NAME));

		return validators;
	}

	public boolean containsConfigurationField(String fieldName) {
		return MANDATORY_CONFIGURATION_NAME.equals(fieldName)
				|| DEFAULT_CONFIGURATION_NAME.equals(fieldName)
				|| MINVALUE_CONFIGURATION_NAME.equals(fieldName)
				|| MAXVALUE_CONFIGURATION_NAME.equals(fieldName);
	}

	public Class<?> getAttributeClass() {
		return Integer.class;
	}

}
