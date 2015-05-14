package com.nanuvem.lom.business.entity;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.property.AttributeValueTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessAttributeValueTest extends AttributeValueTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
