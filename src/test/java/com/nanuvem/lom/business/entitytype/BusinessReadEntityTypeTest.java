package com.nanuvem.lom.business.entitytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entitytype.ReadEntityTypeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessReadEntityTypeTest extends ReadEntityTypeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
