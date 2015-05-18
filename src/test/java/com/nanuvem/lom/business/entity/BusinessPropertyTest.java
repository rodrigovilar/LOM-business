package com.nanuvem.lom.business.entity;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.property.PropertyTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessPropertyTest extends PropertyTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
