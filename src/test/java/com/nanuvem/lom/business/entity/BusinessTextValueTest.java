package com.nanuvem.lom.business.entity;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entity.TextValueTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessTextValueTest extends TextValueTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
