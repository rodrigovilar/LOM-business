package com.nanuvem.lom.business.entitytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entitytype.ReadEntityTest;
import com.nanuvem.lom.business.BusinessFacade;
import com.nanuvem.lom.kernel.dao.MemoryDaoFactory;

public class BusinessReadEntityTest extends ReadEntityTest {

	@Override
	public Facade createFacade() {
		return new BusinessFacade(new MemoryDaoFactory());
//		return new BusinessFacade(new MySqlDaoFactory());
	}

}
