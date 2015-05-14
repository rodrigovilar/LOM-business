package com.nanuvem.lom.business.propertytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.propertytype.ObjectAttributeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessObjectAttributeTest extends ObjectAttributeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
