package com.nanuvem.lom.business.entitytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entitytype.CreateEntityTypeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessCreateEntityTypeTest extends CreateEntityTypeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
