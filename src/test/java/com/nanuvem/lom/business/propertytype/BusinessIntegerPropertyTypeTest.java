package com.nanuvem.lom.business.propertytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.propertytype.IntegerPropertyTypeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessIntegerPropertyTypeTest extends IntegerPropertyTypeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
