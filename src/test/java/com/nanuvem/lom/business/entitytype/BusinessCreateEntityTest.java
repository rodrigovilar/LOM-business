package com.nanuvem.lom.business.entitytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entitytype.CreateEntityTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessCreateEntityTest extends CreateEntityTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
