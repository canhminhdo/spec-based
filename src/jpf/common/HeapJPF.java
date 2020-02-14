package jpf.common;

import java.util.Map;

import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.CharArrayFields;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.FieldInfo;
import gov.nasa.jpf.vm.ReferenceArrayFields;
import gov.nasa.jpf.vm.VM;

public abstract class HeapJPF {
	
	public boolean isReady;
	public Map<String, Integer> lookupTable;

	public void showLookupTable() {
		if (lookupTable.size() > 0) {
			System.out.println("Looup Table");
			System.out.println("-----------");
			for (Map.Entry<String, Integer> entry : lookupTable.entrySet()) {
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			System.out.println("-----------");
		}
	}
	
	public abstract void startup(VM vm);
	public abstract OC getConfiguration(Search search);
	
	public String getEnumType(ElementInfo ei) {
		ElementInfo ei_enum = (ElementInfo) ei.getFieldValueObject("name");
		return this.getStringType(ei_enum);
	}
	
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
	
	public void debug(ElementInfo ei) {
		System.out.println("ElementInfo: " + ei);
		FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
		for (FieldInfo fi : fis) {
			System.out.println("FieldInfo: " + fi);
		}
	}
}
