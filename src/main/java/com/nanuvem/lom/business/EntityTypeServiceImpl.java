package com.nanuvem.lom.business;

import java.util.List;
import java.util.regex.Pattern;

import com.nanuvem.lom.api.EntityType;
import com.nanuvem.lom.api.MetadataException;
import com.nanuvem.lom.api.dao.DaoFactory;
import com.nanuvem.lom.api.dao.EntityTypeDao;

public class EntityTypeServiceImpl {

	private EntityTypeDao dao;

	public static final String DEFAULT_NAMESPACE = "default";

	EntityTypeServiceImpl(DaoFactory factory) {
		this.dao = new EntityDaoDecorator(factory.createEntityTypeDao());
	}

	public EntityType create(EntityType entity) {
		validateEntity(entity);
		EntityType createdEntity = dao.create(entity);
		return createdEntity;
	}

	private void validateEntity(EntityType entity) {
		if (entity.getName() == null || entity.getName().equals("")) {
			throw new MetadataException("The name of an Entity is mandatory");
		}

		if (entity.getNamespace() == null) {
			entity.setNamespace("");
		}

		lowerCase(entity);

		validadeNameAndNamespacePattern(entity);
		validadeEntityDuplication(entity);
	}

	private void lowerCase(EntityType entity) {
		entity.setName(entity.getName().toLowerCase());
		entity.setNamespace(entity.getNamespace().toLowerCase());
	}

	private void validadeNameAndNamespacePattern(EntityType entity) {
		String namespace = entity.getNamespace();

		if (!namespace.equals("")
				&& !Pattern.matches("[a-zA-Z1-9.]{1,}", namespace)) {
			throw new MetadataException("Invalid value for Entity namespace: "
					+ namespace);
		}

		if (!Pattern.matches("[a-zA-Z1-9]{1,}", entity.getName())) {
			throw new MetadataException("Invalid value for Entity name: "
					+ entity.getName());
		}
	}

	private void validadeEntityDuplication(EntityType entity) {
		EntityType found = null;
		try {
			found = findByFullName(entity.getFullName());
		} catch (MetadataException me) {
			found = null;
		}

		if (found != null && !found.getId().equals(entity.getId())) {
			StringBuilder message = new StringBuilder();
			message.append("The ");
			message.append(found.getFullName());
			message.append(" Entity already exists");
			throw new MetadataException(message.toString());
		}
	}

	// There is no test case for classFullName = null. How should the message
	// being thrown exception in this case?
	public EntityType findByFullName(String classFullName) {
		String namespace = null;
		String name = null;

		if (classFullName.contains(".")) {
			namespace = classFullName.substring(0,
					classFullName.lastIndexOf("."));
			name = classFullName.substring(classFullName.lastIndexOf(".") + 1,
					classFullName.length());
		} else {
			namespace = "";
			name = classFullName;
		}

		if (!Pattern.matches("[a-zA-Z1-9.]{1,}", namespace)
				&& !namespace.isEmpty()) {
			this.formatStringAndThrowsExceptionInvalidKeyForEntity(classFullName);
		}

		if (!Pattern.matches("[a-zA-Z1-9]{1,}", name) && !name.isEmpty()) {
			this.formatStringAndThrowsExceptionInvalidKeyForEntity(classFullName);
		}

		if (namespace.isEmpty()) {
			namespace = "";
		}

		EntityType classByNamespaceAndName = dao.findByFullName(classFullName);

		if (classByNamespaceAndName == null) {
			if (classFullName.startsWith(".")) {
				classFullName = classFullName.substring(1);
			}
			if (classFullName.endsWith(".")) {
				classFullName = classFullName.substring(0,
						classFullName.length() - 1);
			}
			throw new MetadataException("Entity not found: " + classFullName);
		}

		return classByNamespaceAndName;
	}

	public EntityType findById(Long id) {
		EntityType entity = this.dao.findById(id);
		return entity;
	}

	public List<EntityType> listAll() {
		List<EntityType> list = dao.listAll();
		return list;
	}

	public List<EntityType> listByFullName(String fragment) {
		if (fragment == null) {
			fragment = "";
		}

		if (!Pattern.matches("[a-zA-Z1-9.]{1,}", fragment)
				&& !fragment.isEmpty()) {
			throw new MetadataException("Invalid value for Entity full name: "
					+ fragment);
		}

		List<EntityType> list = this.dao.listByFullName(fragment);
		return list;
	}

	private void formatStringAndThrowsExceptionInvalidKeyForEntity(String value) {
		if (value.startsWith(".")) {
			value = value.substring(1);
		}
		if (value.endsWith(".")) {
			value = value.substring(0, value.length() - 1);
		}
		throw new MetadataException("Invalid key for Entity: " + value);

	}

	public EntityType update(EntityType entity) {
		this.validateEntityOnUpdate(entity);
		this.validateEntity(entity);
		EntityType updatedEntity = this.dao.update(entity);
		return updatedEntity;
	}

	private void validateEntityOnUpdate(EntityType updateEntity) {
		if (updateEntity.getId() == null && updateEntity.getVersion() == null) {
			throw new MetadataException(
					"The version and id of an Entity are mandatory on update");
		} else if (updateEntity.getId() == null) {
			throw new MetadataException(
					"The id of an Entity is mandatory on update");
		} else if (updateEntity.getVersion() == null) {
			throw new MetadataException(
					"The version of an Entity is mandatory on update");
		}
	}

	public void delete(long id) {
		if (this.dao.findById(id) == null) {
			throw new MetadataException("Unknown id for Entity: " + id);
		}
		this.dao.delete(id);
	}

}

class EntityDaoDecorator implements EntityTypeDao {

	private EntityTypeDao entityDao;

	public EntityDaoDecorator(EntityTypeDao entityDao) {
		this.entityDao = entityDao;
	}

	public EntityType create(EntityType entity) {
		EntityType entityClone = Util.clone(entity);
		Util.setDefaultNamespace(entityClone);

		EntityType createdEntity = entityDao.create(entityClone);

		EntityType createdEntityClone = Util.clone(createdEntity);
		Util.removeDefaultNamespace(createdEntityClone);
		return createdEntityClone;
	}

	public List<EntityType> listAll() {
		List<EntityType> list = Util.clone(entityDao.listAll());
		Util.removeDefaultNamespace(list);
		return list;
	}

	public EntityType findById(Long id) {
		EntityType entity = Util.clone(entityDao.findById(id));
		Util.removeDefaultNamespace(entity);
		return entity;
	}

	public List<EntityType> listByFullName(String fragment) {
		List<EntityType> list = Util.clone(entityDao.listByFullName(fragment));
		Util.removeDefaultNamespace(list);
		return list;
	}

	public EntityType findByFullName(String fullName) {
		fullName = Util.setDefaultNamespace(fullName);

		EntityType entity = entityDao.findByFullName(fullName);
		if (entity != null) {
			entity = Util.clone(entity);
			Util.removeDefaultNamespace(entity);
		}
		return entity;
	}

	public EntityType update(EntityType entity) {
		EntityType entityClone = Util.clone(entity);
		Util.setDefaultNamespace(entityClone);

		EntityType updatedEntity = entityDao.update(entityClone);

		EntityType updatedEntityClone = Util.clone(updatedEntity);
		Util.removeDefaultNamespace(updatedEntityClone);
		return updatedEntityClone;
	}

	public void delete(Long id) {
		entityDao.delete(id);
	}

}