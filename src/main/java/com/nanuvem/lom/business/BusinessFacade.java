package com.nanuvem.lom.business;

import java.util.List;
import java.util.Map;

import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.EntityType;
import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.PropertyType;
import com.nanuvem.lom.api.dao.DaoFactory;
import com.nanuvem.lom.business.validator.definition.TypeDefinitionManager;

public class BusinessFacade implements Facade {

	private EntityTypeServiceImpl entityTypeService;
	private PropertyTypeServiceImpl propertyTypeService;
	private EntityServiceImpl entityService;

	private DaoFactory daoFactory;

	public BusinessFacade(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;

		entityTypeService = new EntityTypeServiceImpl(daoFactory);

		TypeDefinitionManager deployers = new TypeDefinitionManager();
		propertyTypeService = new PropertyTypeServiceImpl(daoFactory,
				entityTypeService, deployers);

		entityService = new EntityServiceImpl(daoFactory, entityTypeService,
				propertyTypeService, deployers);
	}

	public EntityTypeServiceImpl getEntityService() {
		return this.entityTypeService;
	}

	public EntityType create(EntityType entity) {
		return entityTypeService.create(entity);
	}

	public EntityType findEntityTypeById(Long id) {
		return entityTypeService.findById(id);
	}

	public EntityType findEntityTypeByFullName(String fullName) {
		return entityTypeService.findByFullName(fullName);
	}

	public List<EntityType> listAllEntitiesTypes() {
		return entityTypeService.listAll();
	}

	public List<EntityType> listEntitiesTypesByFullName(String fragment) {
		return entityTypeService.listByFullName(fragment);
	}

	public EntityType update(EntityType entity) {
		return entityTypeService.update(entity);
	}

	public void deleteEntityType(Long id) {
		entityTypeService.delete(id);
	}

	public PropertyType create(PropertyType attribute) {
		return propertyTypeService.create(attribute);
	}

	public PropertyType findPropertyTypeById(Long id) {
		return propertyTypeService.findPropertyTypeById(id);
	}

	public PropertyType findPropertyTypeByNameAndFullnameEntityType(
			String name, String fullEntityName) {
		return propertyTypeService.findPropertyTypeByNameAndEntityTypeFullName(
				name, fullEntityName);
	}

	public List<PropertyType> findPropertiesTypesByFullNameEntityType(
			String fullnameEntity) {
		return propertyTypeService
				.findAttributesByFullNameEntity(fullnameEntity);
	}

	public PropertyType update(PropertyType attribute) {
		return propertyTypeService.update(attribute);
	}

	public Entity update(Entity instance) {
		return entityService.update(instance);
	}

	public Entity create(Entity instance) {
		return entityService.create(instance);
	}

	public Entity findEntityById(Long id) {
		return entityService.findInstanceById(id);
	}

	public List<Entity> findEntitiesByEntityTypeId(Long entityId) {
		return entityService.findInstancesByEntityId(entityId);
	}

	@Override
	public List<Entity> findEntityByNameOfPropertiesTypeAndByValueOfProperties(
			String fullnameEntityType,
			Map<String, String> nameByPropertiesTypesAndValuesOfProperties) {

		return entityService
				.findEntityByNameOfPropertiesTypeAndByValueOfProperties(fullnameEntityType, nameByPropertiesTypesAndValuesOfProperties);
	}

	@Override
	public DaoFactory getDaoFactory() {
		return this.daoFactory;
	}
}
