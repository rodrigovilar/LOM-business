package com.nanuvem.lom.business.entity;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entity.InstanceTest;
import com.nanuvem.lom.business.BusinessFacade;
import com.nanuvem.lom.kernel.dao.MemoryDaoFactory;

public class BusinessInstanceTest extends InstanceTest {

	@Override
	public Facade createFacade() {
		return new BusinessFacade(new MemoryDaoFactory());
//		return new BusinessFacade(new MySqlDaoFactory());
	}

}
