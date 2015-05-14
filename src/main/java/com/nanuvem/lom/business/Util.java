package com.nanuvem.lom.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import com.nanuvem.lom.api.PropertyType;
import com.nanuvem.lom.api.Property;
import com.nanuvem.lom.api.EntityType;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.MetadataException;
import com.nanuvem.lom.business.validator.ValidationError;

public class Util {

	static void removeDefaultNamespace(EntityType entity) {
		if (entity == null) {
			return;
		}

		String namespace = entity.getNamespace();

		if (namespace == null) {
			return;
		}

		if (namespace.equals(EntityTypeServiceImpl.DEFAULT_NAMESPACE)) {
			entity.setNamespace("");
		}
	}

	static void removeDefaultNamespace(List<EntityType> list) {
		for (EntityType entity : list) {
			removeDefaultNamespace(entity);
		}
	}

	static void setDefaultNamespace(EntityType entity) {
		if (entity == null) {
			return;
		}

		if (entity.getNamespace() == null || entity.getNamespace().equals("")) {
			entity.setNamespace(EntityTypeServiceImpl.DEFAULT_NAMESPACE);
		}
	}

	@SuppressWarnings("unchecked")
	static <T extends Serializable> T clone(T t) {
		return (T) SerializationUtils.clone(t);
	}

	static <T extends Serializable> List<T> clone(List<T> ts) {
		List<T> clones = new ArrayList<T>();

		for (T t : ts) {
			clones.add(clone(t));
		}

		return clones;
	}

	static String setDefaultNamespace(String fullName) {
		if (fullName != null && !fullName.contains(".")) {
			fullName = EntityTypeServiceImpl.DEFAULT_NAMESPACE + "." + fullName;
		}
		return fullName;
	}

	static void removeDefaultNamespace(PropertyType attribute) {
		if (attribute != null) {
			removeDefaultNamespace(attribute.getEntityType());
		}
	}

	public static void removeDefaultNamespace(Entity instance) {
		if (instance != null) {
			removeDefaultNamespace(instance.getEntityType());
		}
	}

	public static void removeDefaultNamespace(Property value) {
		if (value != null) {
			removeDefaultNamespace(value.getEntity());
		}
	}

	static void throwValidationErrors(List<ValidationError> errors,
			String message) {
		if (!errors.isEmpty()) {
			String errorMessage = "";
			for (ValidationError error : errors) {
				if (errorMessage.isEmpty()) {
					errorMessage += message + error.getMessage();
				} else {
					errorMessage += ", " + error.getMessage();
				}
			}
			throw new MetadataException(errorMessage);
		}
	}

    public static void removeDefaultNamespaceForInstance(List<Entity> instances) {
        for (Entity instance : instances) {
            removeDefaultNamespace(instance);
        }
    }
}