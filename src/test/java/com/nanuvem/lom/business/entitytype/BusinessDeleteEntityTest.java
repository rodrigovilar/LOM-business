package com.nanuvem.lom.business.entitytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entitytype.DeleteEntityTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessDeleteEntityTest extends DeleteEntityTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
