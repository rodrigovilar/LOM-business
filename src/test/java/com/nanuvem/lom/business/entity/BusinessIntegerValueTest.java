package com.nanuvem.lom.business.entity;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entity.IntegerValueTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessIntegerValueTest extends IntegerValueTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
