package com.nanuvem.lom.business.entity;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entity.PasswordValueTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessPasswordValueTest extends PasswordValueTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
