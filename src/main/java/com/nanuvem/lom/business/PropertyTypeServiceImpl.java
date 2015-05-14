package com.nanuvem.lom.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;

import com.nanuvem.lom.api.PropertyType;
import com.nanuvem.lom.api.EntityType;
import com.nanuvem.lom.api.MetadataException;
import com.nanuvem.lom.api.dao.PropertyTypeDao;
import com.nanuvem.lom.api.dao.DaoFactory;
import com.nanuvem.lom.api.util.JsonNodeUtil;
import com.nanuvem.lom.business.validator.ValidationError;
import com.nanuvem.lom.business.validator.configuration.AttributeValidator;
import com.nanuvem.lom.business.validator.definition.TypeDefinition;
import com.nanuvem.lom.business.validator.definition.TypeDefinitionManager;

public class PropertyTypeServiceImpl {

	private final Integer MINIMUM_PROPERTY_TYPE_SEQUENCE = 1;

	private final String PREFIX_EXCEPTION_MESSAGE_CONFIGURATION = "Invalid configuration for attribute";

	private PropertyTypeDao propertyTypeDao;
	private EntityTypeServiceImpl entityTypeService;
	private TypeDefinitionManager definitionManager;

	PropertyTypeServiceImpl(DaoFactory dao, EntityTypeServiceImpl entityService,
			TypeDefinitionManager definitionManager) {
		this.entityTypeService = entityService;
		this.definitionManager = definitionManager;
		this.propertyTypeDao = new AttributeDaoDecorator(
				dao.createPropertyTypeDao());

	}

	private void validateCreate(PropertyType propertyType) {
		validateDuplicatedPropertyType(propertyType);
		definePropertyTypeSequenceNumber(propertyType);

		validatePropertyTypeName(propertyType);

		if (propertyType.getType() == null) {
			throw new MetadataException("The type of an Attribute is mandatory");
		}
		validatePropertyTypeConfiguration(propertyType);
	}

	private void definePropertyTypeSequenceNumber(PropertyType propertyType) {
		int currentNumberOfPropertiesTypes = propertyType.getEntityType()
				.getPropertiesTypes().size();
		if (propertyType.getSequence() != null) {
			boolean minValueForSequence = propertyType.getSequence() < MINIMUM_PROPERTY_TYPE_SEQUENCE;
			boolean maxValueForSequence = currentNumberOfPropertiesTypes + 1 < propertyType
					.getSequence();

			if (minValueForSequence || maxValueForSequence) {
				throw new MetadataException(
						"Invalid value for Attribute sequence: "
								+ propertyType.getSequence());
			}
		} else {
			propertyType.setSequence(currentNumberOfPropertiesTypes + 1);
		}
	}

	private void validatePropertyTypeName(PropertyType propertyType) {
		if (propertyType.getName() == null || propertyType.getName().isEmpty()) {
			throw new MetadataException("The name of an Attribute is mandatory");
		}

		if (!Pattern.matches("[a-zA-Z1-9]{1,}", propertyType.getName())) {
			throw new MetadataException("Invalid value for Attribute name: "
					+ propertyType.getName());
		}
	}

	private List<PropertyType> findAllAttributesForEntity(EntityType entityType) {
		if (entityType != null && !entityType.getFullName().isEmpty()) {
			EntityType foundEntityType = entityTypeService
					.findByFullName(entityType.getFullName());
			if (foundEntityType != null
					&& foundEntityType.getPropertiesTypes() != null
					&& foundEntityType.getPropertiesTypes().size() > 0) {
				return foundEntityType.getPropertiesTypes();
			}
		}
		return null;
	}

	private void validatePropertyTypeConfiguration(PropertyType propertyType) {
		String configuration = propertyType.getConfiguration();
		if (configuration != null && !configuration.isEmpty()) {
			JsonNode jsonNode = JsonNodeUtil.validate(configuration,
					"Invalid value for Attribute configuration: "
							+ configuration);

			TypeDefinition definition = definitionManager
					.get(propertyType.getType().name());
			validateFieldNames(definition, propertyType, jsonNode);
			validateFieldValues(definition, propertyType, jsonNode);
		}
	}

	private void validateFieldNames(TypeDefinition definition,
			PropertyType propertyType, JsonNode jsonNode) {

		Iterator<String> fieldNames = jsonNode.getFieldNames();
		while (fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			if (!definition.containsConfigurationField(fieldName)) {
				throw new MetadataException(
						"Invalid configuration for attribute "
								+ propertyType.getName() + ": the " + fieldName
								+ " configuration attribute is unknown");
			}
		}
	}

	private void validateFieldValues(TypeDefinition definition,
			PropertyType propertyType, JsonNode jsonNode) {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		for (AttributeValidator validator : definition.getValidators()) {
			validator.validateDefault(errors, jsonNode);
		}

		Util.throwValidationErrors(
				errors,
				PREFIX_EXCEPTION_MESSAGE_CONFIGURATION + " "
						+ propertyType.getName() + ": ");
	}

	private EntityType validateExistingEntityForAttribute(
			PropertyType propertyType) {
		EntityType entity = null;
		try {
			entity = entityTypeService.findById(propertyType.getEntityType()
					.getId());
		} catch (MetadataException e) {
			throw new MetadataException("Invalid Entity: "
					+ propertyType.getEntityType().getFullName());
		}
		return entity;
	}

	private void validateDuplicatedPropertyType(PropertyType propertyType) {
		List<PropertyType> foundAttributes = this
				.findAllAttributesForEntity(propertyType.getEntityType());
		if (foundAttributes != null) {
			for (PropertyType at : foundAttributes) {
				if (at.getName().equalsIgnoreCase(propertyType.getName())) {
					this.throwMetadataExceptionOnAttributeDuplication(propertyType);
				}
			}
		}
	}

	private void validateExistingAttributeNotInEntityOnUpdate(
			PropertyType propertyType) {
		if (propertyType.getId() != null) {
			PropertyType foundAttributes = this
					.findPropertyTypeByNameAndEntityTypeFullName(propertyType
							.getName(), propertyType.getEntityType()
							.getFullName());
			if (foundAttributes != null) {
				if (!propertyType.getId().equals(foundAttributes.getId())) {
					this.throwMetadataExceptionOnAttributeDuplication(propertyType);
				}
			}
		}
	}

	private void throwMetadataExceptionOnAttributeDuplication(
			PropertyType propertyType) {
		throw new MetadataException("Attribute duplication on "
				+ propertyType.getEntityType().getFullName()
				+ " Entity. It already has an attribute "
				+ StringUtils.lowerCase(propertyType.getName() + "."));
	}

	public PropertyType create(PropertyType propertyType) {
		EntityType entity = validateExistingEntityForAttribute(propertyType);
		propertyType.setEntityType(entity);
		validateCreate(propertyType);
		PropertyType createdPropertyType = this.propertyTypeDao
				.create(propertyType);
		entityTypeService.update(createdPropertyType.getEntityType());
		return createdPropertyType;
	}

	public List<PropertyType> listAllAttributes(String entityFullName) {
		EntityType entity = entityTypeService.findByFullName(entityFullName);
		return entity.getPropertiesTypes();
	}

	public PropertyType findPropertyTypeById(Long id) {
		if (id != null) {
			PropertyType propertyType = this.propertyTypeDao
					.findPropertyTypeById(id);
			return propertyType;
		} else {
			return null;
		}
	}

	public PropertyType findPropertyTypeByNameAndEntityTypeFullName(
			String namePropertyType, String entityTypeFullName) {

		if ((namePropertyType != null && !namePropertyType.isEmpty())
				&& (entityTypeFullName != null && !entityTypeFullName.isEmpty())) {

			PropertyType propertyType = this.propertyTypeDao
					.findPropertyTypeByNameAndEntityTypeFullName(namePropertyType,
							entityTypeFullName);
			return propertyType;
		}
		return null;
	}

	public PropertyType update(PropertyType propertyType) {
		this.validatePropertyTypeName(propertyType);
		// this.validateUpdateSequence(attribute);
		this.validateUpdateType(propertyType);
		this.validateExistingAttributeNotInEntityOnUpdate(propertyType);
		this.validatePropertyTypeConfiguration(propertyType);

		PropertyType updatedPropertyType = this.propertyTypeDao
				.update(propertyType);
		entityTypeService.update(updatedPropertyType.getEntityType());
		return updatedPropertyType;
	}

	private void validateUpdateType(PropertyType propertyType) {
		PropertyType propertyTypeFound = this.findPropertyTypeById(propertyType
				.getId());

		if (!propertyTypeFound.getType().equals(propertyType.getType())) {
			throw new MetadataException(
					"Can not change the type of an attribute");
		}
	}

//	private void validateUpdateSequence(PropertyType propertyType) {
//		EntityType entityType = entityTypeService.findById(propertyType
//				.getEntityType().getId());
//		int currentNumberOfAttributes = entityType.getPropertiesTypes()
//				.get(entityType.getPropertiesTypes().size() - 1).getSequence();
//
//		if (propertyType.getSequence() != null) {
//			boolean minValueForSequence = propertyType.getSequence() < MINIMUM_PROPERTY_TYPE_SEQUENCE;
//			boolean maxValueForSequence = currentNumberOfAttributes < propertyType
//					.getSequence();
//
//			if (!(minValueForSequence || maxValueForSequence)) {
//				return;
//			}
//		}
//		throw new MetadataException("Invalid value for Attribute sequence: "
//				+ propertyType.getSequence());
//	}

	public List<PropertyType> findAttributesByFullNameEntity(
			String fullnameEntity) {
		return this.propertyTypeDao
				.findPropertiesTypesByFullNameEntityType(fullnameEntity);
	}
}

class AttributeDaoDecorator implements PropertyTypeDao {

	private PropertyTypeDao propertyTypeDao;

	public AttributeDaoDecorator(PropertyTypeDao propertyType) {
		this.propertyTypeDao = propertyType;
	}

	public PropertyType create(PropertyType propertyType) {
		PropertyType createdPropertyType = Util.clone(propertyTypeDao.create(Util
				.clone(propertyType)));
		Util.removeDefaultNamespace(createdPropertyType);
		return createdPropertyType;
	}

	public PropertyType findPropertyTypeById(Long id) {
		PropertyType attribute = Util.clone(propertyTypeDao.findPropertyTypeById(id));
		Util.removeDefaultNamespace(attribute);
		return Util.clone(attribute);
	}

	public PropertyType findPropertyTypeByNameAndEntityTypeFullName(
			String propertyTypeName, String fullnameEntityType) {
		fullnameEntityType = Util.setDefaultNamespace(fullnameEntityType);
		PropertyType propertyType = Util.clone(propertyTypeDao
				.findPropertyTypeByNameAndEntityTypeFullName(propertyTypeName,
						fullnameEntityType));
		Util.removeDefaultNamespace(propertyType);
		return Util.clone(propertyType);
	}

	public List<PropertyType> findPropertiesTypesByFullNameEntityType(
			String fullnameEntitytype) {
		List<PropertyType> propertiesTypes = propertyTypeDao
				.findPropertiesTypesByFullNameEntityType(fullnameEntitytype);
		if (propertiesTypes != null) {
			for (PropertyType at : propertiesTypes) {
				Util.removeDefaultNamespace(at);
			}
			propertiesTypes = Util.clone(propertiesTypes);
		}
		return propertiesTypes;
	}

	public PropertyType update(PropertyType propertyType) {
		PropertyType updatedPropertyType = Util.clone(propertyTypeDao.update(Util
				.clone(propertyType)));
		Util.removeDefaultNamespace(updatedPropertyType);
		return Util.clone(updatedPropertyType);
	}
}