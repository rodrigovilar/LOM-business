package com.nanuvem.lom.business.entitytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entitytype.ReadEntityTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessReadEntityTest extends ReadEntityTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
