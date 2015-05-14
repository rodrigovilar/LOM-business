package com.nanuvem.lom.business.propertytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.propertytype.LongTextAttributeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessLongTextAttributeTest extends LongTextAttributeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
