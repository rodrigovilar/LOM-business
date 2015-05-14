package com.nanuvem.lom.business.entitytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entitytype.UpdateEntityTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessUpdateEntityTest extends UpdateEntityTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
