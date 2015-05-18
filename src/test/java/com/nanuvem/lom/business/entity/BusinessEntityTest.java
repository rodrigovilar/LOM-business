package com.nanuvem.lom.business.entity;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entity.EntityTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessEntityTest extends EntityTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
