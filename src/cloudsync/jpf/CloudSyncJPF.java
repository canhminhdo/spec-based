package cloudsync.jpf;

import java.util.ArrayList;
import java.util.HashMap;

import cloudsync.main.Cloud;
import cloudsync.main.PC;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.FieldInfo;
import gov.nasa.jpf.vm.Heap;
import gov.nasa.jpf.vm.VM;
import jpf.common.HeapJPF;
import jpf.common.OC;

public class CloudSyncJPF extends HeapJPF {
	
	public CloudSyncJPF() {
		this.lookupTable = new HashMap<String, Integer>();
		this.isReady = true;
	}

	@Override
	public void startup(VM vm) {
		for (ElementInfo ei : vm.getHeap().liveObjects()) {
			String name = ei.getClassInfo().getName();
			if (name.equals("cloudsync.main.Cloud")) {
				lookupTable.put("Cloud", ei.getObjectRef());
			}
			if (name.equals("cloudsync.main.PC")) {
				String id = this.getStringType((ElementInfo) ei.getFieldValueObject("pid"));
				lookupTable.put(id, ei.getObjectRef());
			}
		}
		showLookupTable();
	}

	@Override
	public OC getConfiguration(Search search) {
		VM vm = search.getVM();
		CloudSyncConfiguration config = new CloudSyncConfiguration();
		config.setStateId(search.getStateId());
		config.setDepth(search.getDepth());
		Heap heap = vm.getHeap();
		
		// get Cloud information in JPF Heap
		{
			ElementInfo ei = heap.get(lookupTable.get("Cloud"));
			if (ei == null) {
				return null;
			}
			FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
			Cloud cloud = new Cloud();
			for (FieldInfo fi : fis) {
				switch (fi.getName()) {
					case "statusc":
						ElementInfo ei_statusc = (ElementInfo)fi.getValueObject(ei.getFields());
						String statusc = this.getEnumType(ei_statusc);
						cloud.setStatusc(Cloud.LabelC.valueOf(statusc));
						break;
					case "valc":
						cloud.setValc(ei.getIntField(fi));
						break;
				}
			}
			config.setCloud(cloud);
		}
		
		// get all PC information in JPF Heap
		ArrayList<PC> pcList = new ArrayList<PC>();
		{
			ElementInfo ei = heap.get(lookupTable.get("p1"));
			if (ei == null) {
				return null;
			}
			PC p1 = getPC(ei);
			pcList.add(p1);
		}
		{
			ElementInfo ei = heap.get(lookupTable.get("p2"));
			if (ei == null) {
				return null;
			}
			PC p2 = getPC(ei);
			pcList.add(p2);
		}
		{
			ElementInfo ei = heap.get(lookupTable.get("p3"));
			if (ei == null) {
				return null;
			}
			PC p3 = getPC(ei);
			pcList.add(p3);
		}
		config.setPcList(pcList);
		
		return config;
	}
	
	public PC getPC(ElementInfo ei) {
		FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
		PC pc = new PC();
		for (FieldInfo fi : fis) {
			switch (fi.getName()) {
				case "pid":
					ElementInfo ei_pid = (ElementInfo)fi.getValueObject(ei.getFields());
					String pid = this.getStringType(ei_pid);
					pc.setPid(pid);
					break;
				case "statusp":
					ElementInfo ei_statusp = (ElementInfo)fi.getValueObject(ei.getFields());
					String statusp = this.getEnumType(ei_statusp);
					pc.setStatusp(PC.LabelP.valueOf(statusp));
					break;
				case "valp":
					pc.setValp(ei.getIntField(fi));
					break;
				case "tmp":
					pc.setTmp(ei.getIntField(fi));
					break;
			}
		}
		return pc;
	}
}
