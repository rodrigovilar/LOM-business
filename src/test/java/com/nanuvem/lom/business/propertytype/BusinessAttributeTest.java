package com.nanuvem.lom.business.propertytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.propertytype.AttributeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessAttributeTest extends AttributeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
