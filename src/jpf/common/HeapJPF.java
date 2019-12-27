package jpf.common;

import java.util.Map;

import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.CharArrayFields;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.ReferenceArrayFields;
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
	
	public String getStringType(ElementInfo ei) {
		ElementInfo ei_value = (ElementInfo) ei.getFieldValueObject("value");
		CharArrayFields caf = (CharArrayFields) ei_value.getArrayFields();
		return String.valueOf((char[]) caf.getValues());
	}
	
	public ReferenceArrayFields getArrayListRefs(ElementInfo ei) {
		ElementInfo ei_elementData = (ElementInfo) ei.getFieldValueObject("elementData");
		ReferenceArrayFields rafs = (ReferenceArrayFields) ei_elementData.getArrayFields();
		return rafs;
	}
}
