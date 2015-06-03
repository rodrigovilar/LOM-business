package com.nanuvem.lom.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import com.nanuvem.lom.api.PropertyType;
import com.nanuvem.lom.api.Property;
import com.nanuvem.lom.api.EntityType;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.MetadataException;
import com.nanuvem.lom.api.dao.PropertyDao;
import com.nanuvem.lom.api.dao.DaoFactory;
import com.nanuvem.lom.api.dao.EntityDao;
import com.nanuvem.lom.api.util.JsonNodeUtil;
import com.nanuvem.lom.business.validator.ValidationError;
import com.nanuvem.lom.business.validator.configuration.TypeValidator;
import com.nanuvem.lom.business.validator.configuration.PropertyTypeValidator;
import com.nanuvem.lom.business.validator.definition.TypeDefinition;
import com.nanuvem.lom.business.validator.definition.TypeDefinitionManager;

public class EntityServiceImpl {

	private final String PREFIX_EXCEPTION_MESSAGE_VALUE = "Invalid value for the Instance. ";

	private EntityDao instanceDao;
	private PropertyDao attributeValueDao;
	private EntityTypeServiceImpl entityService;
	private PropertyTypeServiceImpl attributeService;
	private TypeDefinitionManager definitionManager;

	EntityServiceImpl(DaoFactory daoFactory,
			EntityTypeServiceImpl entityService,
			PropertyTypeServiceImpl attributeService,
			TypeDefinitionManager definitionManager) {
		this.entityService = entityService;
		this.attributeService = attributeService;
		this.definitionManager = definitionManager;
		this.instanceDao = new InstanceDaoDecorator(
				daoFactory.createEntityDao());
		this.attributeValueDao = new AttributeValueDaoDecorator(
				daoFactory.createPropertyDao());
	}

	public Entity create(Entity instance) {
		List<Property> values = validateEntityAndAttributeValueOnInstance(instance);

		List<Property> originalValues = new ArrayList<Property>(values);
		values.clear();
		Entity newInstance = this.instanceDao.create(instance);

		for (Property value : originalValues) {
			value.setEntity(newInstance);
			this.attributeValueDao.create(value);
		}

		return instanceDao.findEntityById(newInstance.getId());
	}

	private List<Property> validateEntityAndAttributeValueOnInstance(
			Entity instance) {
		if (instance.getEntityType() == null) {
			throw new MetadataException(
					"Invalid value for Instance entity: The entity is mandatory");
		}
		EntityType entity;
		try {
			entity = this.entityService.findById(instance.getEntityType()
					.getId());
		} catch (MetadataException e) {
			throw new MetadataException("Unknown entity id: "
					+ instance.getEntityType().getId());
		}
		instance.setEntityType(entity);
		validateAndAssignDefaultValueInAttributesValues(instance, entity);
		List<Property> values = instance.getProperties();
		for (Property value : values) {
			PropertyType attribute = attributeService
					.findPropertyTypeById(value.getPropertyType().getId());
			validateValue(attribute.getConfiguration(), value);
		}
		return values;
	}

	public Entity update(Entity instance) {
		validateEntityAndAttributeValueOnInstance(instance);

		this.instanceDao.update(instance);

		for (Property value : instance.getProperties()) {
			value.setEntity(instance);
			if (value.getId() == null) {
				this.attributeValueDao.create(value);
			} else {
				this.attributeValueDao.update(value);
			}
		}
		return instanceDao.findEntityById(instance.getId());
	}

	private EntityType validateExistenceOfTheEntity(Entity instance) {
		if (instance.getEntityType() == null) {
			throw new MetadataException(
					"Invalid value for Instance entity: The entity is mandatory");
		}
		EntityType entity;
		try {
			entity = this.entityService.findById(instance.getEntityType()
					.getId());
		} catch (MetadataException e) {
			throw new MetadataException("Unknown entity id: "
					+ instance.getEntityType().getId());
		}
		instance.setEntityType(entity);
		return entity;
	}

	private void validateValue(String configuration, Property value) {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		TypeDefinition definition = definitionManager.get(value
				.getPropertyType().getType().name());
		TypeValidator typeValidator = new TypeValidator(
				definition.getAttributeClass());
		typeValidator.validateValue(errors, null, value);

		if (configuration != null && !configuration.isEmpty()) {
			JsonNode jsonNode = load(configuration);

			for (PropertyTypeValidator validator : definition.getValidators()) {
				validator.validateValue(errors, jsonNode, value);
			}
		}

		Util.throwValidationErrors(errors, PREFIX_EXCEPTION_MESSAGE_VALUE);

	}

	private void validateAndAssignDefaultValueInAttributesValues(
			Entity instance, EntityType entity) {

		for (Property attributeValue : instance.getProperties()) {
			if (!(entity.getPropertiesTypes().contains(attributeValue
					.getPropertyType()))) {
				throw new MetadataException("Unknown attribute for "
						+ instance.getEntityType().getFullName() + ": "
						+ attributeValue.getPropertyType().getName());
			}

			String configuration = attributeValue.getPropertyType()
					.getConfiguration();

			if (configuration != null && !configuration.isEmpty()) {
				JsonNode jsonNode = load(configuration);
				this.applyDefaultValueWhenAvailable(attributeValue, jsonNode);
			}
		}
	}

	private JsonNode load(String configuration) {
		JsonNode jsonNode = JsonNodeUtil.validate(configuration,
				"Invalid value for Attribute configuration: " + configuration);
		return jsonNode;
	}

	private void applyDefaultValueWhenAvailable(Property attributeValue,
			JsonNode jsonNode) {

		String defaultConfiguration = PropertyType.DEFAULT_CONFIGURATION_NAME;
		if (jsonNode.has(defaultConfiguration)) {
			String defaultField = jsonNode.get(defaultConfiguration).asText();

			if (attributeValue.getValue() == null && defaultField != null) {
				attributeValue.setValue(defaultField);
			}
		}
	}

	public Entity findInstanceById(Long id) {
		return this.instanceDao.findEntityById(id);
	}

	public List<Entity> findInstancesByEntityId(Long entityId) {
		return this.instanceDao.findEntitiesByEntityTypeId(entityId);
	}

	public List<Entity> findEntityByNameOfPropertiesTypeAndByValueOfProperties(
			String fullnameEntityType,
			Map<String, String> nameByPropertiesTypesAndValuesOfProperties) {

		return instanceDao
				.findEntityByNameOfPropertiesTypeAndByValueOfProperties(
						fullnameEntityType,
						nameByPropertiesTypesAndValuesOfProperties);
	}
}

class InstanceDaoDecorator implements EntityDao {

	private EntityDao instanceDao;

	public InstanceDaoDecorator(EntityDao instanceDao) {
		this.instanceDao = instanceDao;
	}

	public Entity create(Entity instance) {
		Entity createdInstance = Util.clone(instanceDao.create(Util
				.clone(instance)));
		Util.removeDefaultNamespace(createdInstance);
		return createdInstance;
	}

	public Entity findEntityById(Long id) {
		Entity instance = Util.clone(instanceDao.findEntityById(id));
		Util.removeDefaultNamespace(instance);
		return instance;
	}

	public Entity update(Entity instance) {
		Entity updatedInstance = Util.clone(instanceDao.update(Util
				.clone(instance)));
		Util.removeDefaultNamespace(updatedInstance);
		return updatedInstance;
	}

	public void delete(Long id) {
		instanceDao.delete(id);
	}

	public List<Entity> findEntitiesByEntityTypeId(Long entityId) {
		List<Entity> instances = Util.clone(instanceDao
				.findEntitiesByEntityTypeId(entityId));
		Util.removeDefaultNamespaceForInstance(instances);
		return instances;
	}

	public List<Entity> findEntityByNameOfPropertiesTypeAndByValueOfProperties(
			String fullnameEntityType,
			Map<String, String> nameByPropertiesTypesAndValuesOfProperties) {

		List<Entity> instances = Util.clone(instanceDao
				.findEntityByNameOfPropertiesTypeAndByValueOfProperties(
						fullnameEntityType,
						nameByPropertiesTypesAndValuesOfProperties));
		Util.removeDefaultNamespaceForInstance(instances);
		return instances;

	}
}

class AttributeValueDaoDecorator implements PropertyDao {

	private PropertyDao attributeValueDao;

	public AttributeValueDaoDecorator(PropertyDao attributeValueDao) {
		this.attributeValueDao = attributeValueDao;
	}

	public Property create(Property value) {
		Property createdValue = Util.clone(attributeValueDao.create(Util
				.clone(value)));
		Util.removeDefaultNamespace(createdValue);
		return createdValue;
	}

	public Property update(Property value) {
		Property updatedValue = Util.clone(attributeValueDao.update(Util
				.clone(value)));
		Util.removeDefaultNamespace(updatedValue);
		return updatedValue;

	}
}