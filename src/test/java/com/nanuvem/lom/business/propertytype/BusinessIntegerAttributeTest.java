package com.nanuvem.lom.business.propertytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.propertytype.IntegerAttributeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessIntegerAttributeTest extends IntegerAttributeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
