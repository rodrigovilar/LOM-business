package com.nanuvem.lom.business.entity;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entity.IntegerValueTest;
import com.nanuvem.lom.business.BusinessFacade;
import com.nanuvem.lom.kernel.dao.MySqlDaoFactory;

public class BusinessIntegerValueTest extends IntegerValueTest {

	@Override
	public Facade createFacade() {
		return new BusinessFacade(new MySqlDaoFactory());
	}

}
