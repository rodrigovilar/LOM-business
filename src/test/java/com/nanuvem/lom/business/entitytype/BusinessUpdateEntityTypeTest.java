package com.nanuvem.lom.business.entitytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entitytype.UpdateEntityTypeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessUpdateEntityTypeTest extends UpdateEntityTypeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
