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

	private EntityTypeServiceImpl entityService;
	private PropertyTypeServiceImpl attributeService;
	private EntityServiceImpl instanceService;

	private DaoFactory daoFactory;

	public BusinessFacade(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
		entityService = new EntityTypeServiceImpl(daoFactory);
		TypeDefinitionManager deployers = new TypeDefinitionManager();
		attributeService = new PropertyTypeServiceImpl(daoFactory,
				entityService, deployers);
		instanceService = new EntityServiceImpl(daoFactory, entityService,
				attributeService, deployers);
	}

	public EntityTypeServiceImpl getEntityService() {
		return this.entityService;
	}

	public EntityType create(EntityType entity) {
		return entityService.create(entity);
	}

	public EntityType findEntityTypeById(Long id) {
		return entityService.findById(id);
	}

	public EntityType findEntityTypeByFullName(String fullName) {
		return entityService.findByFullName(fullName);
	}

	public List<EntityType> listAllEntitiesTypes() {
		return entityService.listAll();
	}

	public List<EntityType> listEntitiesTypesByFullName(String fragment) {
		return entityService.listByFullName(fragment);
	}

	public EntityType update(EntityType entity) {
		return entityService.update(entity);
	}

	public void deleteEntityType(Long id) {
		entityService.delete(id);
	}

	public PropertyType create(PropertyType attribute) {
		return attributeService.create(attribute);
	}

	public PropertyType findPropertyTypeById(Long id) {
		return attributeService.findPropertyTypeById(id);
	}

	public PropertyType findPropertyTypeByNameAndFullnameEntityType(
			String name, String fullEntityName) {
		return attributeService.findPropertyTypeByNameAndEntityTypeFullName(
				name, fullEntityName);
	}

	public List<PropertyType> findPropertiesTypesByFullNameEntityType(
			String fullnameEntity) {
		return attributeService.findAttributesByFullNameEntity(fullnameEntity);
	}

	public PropertyType update(PropertyType attribute) {
		return attributeService.update(attribute);
	}

	public Entity update(Entity instance) {
		return instanceService.update(instance);
	}

	public Entity create(Entity instance) {
		return instanceService.create(instance);
	}

	public Entity findEntityById(Long id) {
		return instanceService.findInstanceById(id);
	}

	public List<Entity> findEntitiesByEntityTypeId(Long entityId) {
		return instanceService.findInstancesByEntityId(entityId);
	}

	@Override
	public DaoFactory getDaoFactory() {
		return this.daoFactory;
	}

	@Override
	public List<Entity> pesquisarEntityPeloNomeDePropertiesTypeEPeloValorDasProperties(
			String fullnameEntityType,
			Map<String, String> nomesDasPropertiesTypesEValoresDasProperties) {

		return null;
	}

}
