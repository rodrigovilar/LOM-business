package com.nanuvem.lom.business.propertytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.propertytype.IntegerAttributeTest;
import com.nanuvem.lom.business.BusinessFacade;
import com.nanuvem.lom.kernel.dao.MemoryDaoFactory;

public class BusinessIntegerAttributeTest extends IntegerAttributeTest {

	@Override
	public Facade createFacade() {
		 return new BusinessFacade(new MemoryDaoFactory());
//		return new BusinessFacade(new MySqlDaoFactory());
	}

}
