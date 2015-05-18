package com.nanuvem.lom.business.propertytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.propertytype.TextPropertyTypeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessTextPropertyTypeTest extends TextPropertyTypeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
