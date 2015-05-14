package com.nanuvem.lom.business.entity;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entity.InstanceTest;
import com.nanuvem.lom.business.FacadeFactory;

public class BusinessInstanceTest extends InstanceTest {

	@Override
	public Facade createFacade() {
		return new FacadeFactory().createFacade();
	}

}
