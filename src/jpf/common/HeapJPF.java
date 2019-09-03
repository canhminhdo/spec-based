package jpf.common;

import java.util.Map;

import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.VM;
import jpf.Logger;

public abstract class HeapJPF {

	public Map<String, Integer> lookupTable;

	public void showLookupTable() {
		Logger.info("Looup Table");
		Logger.info("-----------");
		for (Map.Entry<String, Integer> entry : lookupTable.entrySet()) {
			Logger.info(entry.getKey() + ":" + entry.getValue());
		}
		Logger.info("-----------");
	}
	
	public abstract void startup(VM vm);
	public abstract OC getConfiguration(Search search);
}
