package com.nanuvem.lom.business.propertytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.propertytype.PasswordPropertyTypeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessPasswordPropertyTypeTest extends PasswordPropertyTypeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
