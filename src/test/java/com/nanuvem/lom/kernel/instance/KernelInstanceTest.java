package com.nanuvem.lom.kernel.instance;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.instance.InstanceTest;
import com.nanuvem.lom.kernel.KernelFacade;
import com.nanuvem.lom.kernel.dao.MemoryDaoFactory;

public class KernelInstanceTest extends InstanceTest {

	private MemoryDaoFactory daoFactory;

	@Override
	public Facade createFacade() {
		daoFactory = new MemoryDaoFactory();
		return new KernelFacade(daoFactory);
	}

}
