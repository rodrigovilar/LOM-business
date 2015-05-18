package com.nanuvem.lom.business.propertytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.propertytype.LongTextPropertyTypeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessLongTextPropertyTypeTest extends LongTextPropertyTypeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
