package com.nanuvem.lom.business.propertytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.propertytype.ObjectPropertyTypeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessObjectPropertyTypeTest extends ObjectPropertyTypeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
