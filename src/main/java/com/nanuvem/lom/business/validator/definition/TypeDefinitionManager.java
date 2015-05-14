package com.nanuvem.lom.business.validator.definition;

import java.util.HashMap;
import java.util.Map;

import com.nanuvem.lom.api.Type;

public class TypeDefinitionManager {

	private Map<String, TypeDefinition> definitions = new HashMap<String, TypeDefinition>();

	public TypeDefinitionManager() {
		add(Type.TEXT.name(), new TextType());
		add(Type.LONGTEXT.name(), new LongTextType());
		add(Type.PASSWORD.name(), new PasswordType());
		add(Type.INTEGER.name(), new IntegerType());

	}

	public void add(String name, TypeDefinition deployer) {
		definitions.put(name, deployer);
	}

	public TypeDefinition get(String name) {
		return definitions.get(name);
	}
}
