package com.nanuvem.lom.business.entitytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entitytype.CreateEntityTest;
import com.nanuvem.lom.business.BusinessFacade;
import com.nanuvem.lom.kernel.dao.MemoryDaoFactory;

public class BusinessCreateEntityTest extends CreateEntityTest {

	@Override
	public Facade createFacade() {
		 return new BusinessFacade(new MemoryDaoFactory());
//		return new BusinessFacade(new MySqlDaoFactory());
	}

}
