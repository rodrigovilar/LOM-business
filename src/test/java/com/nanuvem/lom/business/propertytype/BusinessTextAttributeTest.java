package com.nanuvem.lom.business.propertytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.propertytype.TextAttributeTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessTextAttributeTest extends TextAttributeTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
