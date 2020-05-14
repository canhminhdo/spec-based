package nspk.jpf;

import java.util.HashMap;

import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.FieldInfo;
import gov.nasa.jpf.vm.Heap;
import gov.nasa.jpf.vm.ReferenceArrayFields;
import gov.nasa.jpf.vm.VM;
import jpf.common.HeapJPF;
import jpf.common.OC;
import nspk.main.*;

public class NspkJPF extends HeapJPF {
	
	public NspkJPF() {
		this.lookupTable = new HashMap<String, Integer>();
		this.isReady = false;
	}

	@Override
	public void startup(VM vm) {
		if (!this.isReady) {
			for (ElementInfo ei : vm.getHeap().liveObjects()) {
				String name = ei.getClassInfo().getName();
				if (name.equals("nspk.main.NSPK")) {
					this.isReady = true;
				}
			}
		}
		if (this.isReady) {
			for (ElementInfo ei : vm.getHeap().liveObjects()) {
				String name = ei.getClassInfo().getName();
				if (name.contains("Intruder") || name.contains("Principal")) {
					String id = this.getStringType((ElementInfo) ei.getFieldValueObject("id"));
					lookupTable.put(id, ei.getObjectRef());
				}
			}
			showLookupTable();
		}
	}

	@Override
	public OC getConfiguration(Search search) {
		VM vm = search.getVM();
		NspkConfiguration config = new NspkConfiguration();
		config.setStateId(search.getStateId());
		config.setDepth(search.getDepth());
		config.setReady(this.isReady);
		
		Heap heap = vm.getHeap();
		{
			ElementInfo ei = heap.get(lookupTable.get("intrdr"));
			if (ei == null) {
				return null;
			}
			FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
			for (FieldInfo fi : fis) {
				switch (fi.getName()) {
					case "nw":
						ElementInfo ei_nw = (ElementInfo)fi.getValueObject(ei.getFields());
						ElementInfo ei_multiset = (ElementInfo)ei_nw.getFieldValueObject("multiset");
						ReferenceArrayFields nw_rafs = this.getArrayListRefs(ei_multiset);
						MultiSet<String> nw = new MultiSet<String>(Constants.nw);
						for (int i : (int[])nw_rafs.getValues()) {
							if (i > 0) {
								ElementInfo ei_network = vm.getHeap().get(i);
								ElementInfo ei_name = (ElementInfo) ei_network.getFieldValueObject("name");
								String name = getStringType(ei_name);
								ElementInfo ei_creator = (ElementInfo) ei_network.getFieldValueObject("creator");
								String creator = getIdOfOC(ei_creator);
								ElementInfo ei_sender = (ElementInfo) ei_network.getFieldValueObject("sender");
								String sender = getIdOfOC(ei_sender);
								ElementInfo ei_receiver = (ElementInfo) ei_network.getFieldValueObject("receiver");
								String receiver = getIdOfOC(ei_receiver);
								
								ElementInfo ei_cipher = (ElementInfo) ei_network.getFieldValueObject("cipher");
								String cipher = "";
								if (ei_cipher.getClassInfo().getSimpleName().equals(Constants.cipher1)) {
									ElementInfo ei_p = (ElementInfo) ei_cipher.getFieldValueObject("p");
									String p = getIdOfOC(ei_p);
									
									ElementInfo ei_q = (ElementInfo) ei_cipher.getFieldValueObject("q");
									String q = getIdOfOC(ei_q);
									
									ElementInfo ei_n = (ElementInfo) ei_cipher.getFieldValueObject("n");
									String n = getNonce(ei_n);
									String cipher1 = "c1(" + p + "," + n + "," + q + ")";
									cipher = cipher1;
								}
								
								if (ei_cipher.getClassInfo().getSimpleName().equals(Constants.cipher2)) {
									ElementInfo ei_p = (ElementInfo) ei_cipher.getFieldValueObject("p");
									String p = getIdOfOC(ei_p);
									
									ElementInfo ei_n1 = (ElementInfo) ei_cipher.getFieldValueObject("n1");
									String n1 = getNonce(ei_n1);
									
									ElementInfo ei_n2 = (ElementInfo) ei_cipher.getFieldValueObject("n2");
									String n2 = getNonce(ei_n2);
									
									ElementInfo ei_q = (ElementInfo) ei_cipher.getFieldValueObject("q");
									String q = getIdOfOC(ei_q);
									
									String cipher2 = "c2(" + p + "," + n1 + "," + n2 + "," + q + ")";
									cipher = cipher2;
								}
								
								if (ei_cipher.getClassInfo().getSimpleName().equals(Constants.cipher3)) {
									ElementInfo ei_p = (ElementInfo) ei_cipher.getFieldValueObject("p");
									String p = getIdOfOC(ei_p);
									
									ElementInfo ei_n = (ElementInfo) ei_cipher.getFieldValueObject("n");
									String n = getNonce(ei_n);
									
									String cipher3 = "c3(" + p + "," + n + ")";
									cipher = cipher3;
								}
								nw.add(name + "(" + creator + "," + sender + "," + receiver + "," + cipher + ")");
							}
						}
						config.setNw(nw);
						break;
					case "nonces":
						ElementInfo ei_nonces = (ElementInfo)fi.getValueObject(ei.getFields());
						MultiSet<String> nonces = getNonces(vm, ei_nonces);
						config.setNonces(nonces);
						break;
	
					case "rand":
						// rand
						ElementInfo ei_rand = (ElementInfo)fi.getValueObject(ei.getFields());
						MultiSet<String> rand = getMultiset(vm, Constants.rand, ei_rand);
						config.setRand(rand);
						break;

					case "prins":
						// prins
						ElementInfo ei_prins = (ElementInfo)fi.getValueObject(ei.getFields());
						MultiSet<String> prins = getMultiset(vm, Constants.prins, ei_prins);
						config.setPrins(prins);
						break;
					case "rwController":
						ElementInfo ei_intrdr_rw = (ElementInfo)fi.getValueObject(ei.getFields());
						Controller<String> intrdr_rw = this.getRewriteRuleList(vm, ei_intrdr_rw);
						config.setIntrdrRwController(intrdr_rw);
						break;
				}
			}
		}
		{
			ElementInfo ei = heap.get(lookupTable.get("p"));
			if (ei == null) {
				return null;
			}
			FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
			for (FieldInfo fi : fis) {
				switch (fi.getName()) {
					case "rwController":
						ElementInfo ei_p_rw = (ElementInfo)fi.getValueObject(ei.getFields());
						Controller<String> p_rw = this.getRewriteRuleList(vm, ei_p_rw);
						config.setpRwController(p_rw);
						break;
				}
			}
		}
		{
			ElementInfo ei = heap.get(lookupTable.get("q"));
			if (ei == null) {
				return null;
			}
			FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
			for (FieldInfo fi : fis) {
				switch (fi.getName()) {
					case "rwController":
						ElementInfo ei_q_rw = (ElementInfo)fi.getValueObject(ei.getFields());
						Controller<String> q_rw = this.getRewriteRuleList(vm, ei_q_rw);
						config.setqRwController(q_rw);
						break;
				}
			}
		}
		return config;
	}
	
	private Controller<String> getRewriteRuleList(VM vm, ElementInfo ei_rw) {
		Controller<String> rw = new Controller<String>();
		ElementInfo ei_list = (ElementInfo)ei_rw.getFieldValueObject("list");
		ReferenceArrayFields rw_rafs = this.getArrayListRefs(ei_list);
		for (int i : (int[])rw_rafs.getValues()) {
			if (i > 0) {
				ElementInfo ei_rule = vm.getHeap().get(i);
				rw.add(ei_rule.getClassInfo().getSimpleName());
			}
		}
		return rw;
	}
	
	private MultiSet<String> getMultiset(VM vm, String key, ElementInfo ei_rand) {
		ElementInfo ei_multiset = (ElementInfo) ei_rand.getFieldValueObject("multiset");
		ReferenceArrayFields multiset_rafs = this.getArrayListRefs(ei_multiset);
		MultiSet<String> oc = new MultiSet<String>(key);
		for (int i : (int[]) multiset_rafs.getValues()) {
			if (i > 0) {
				ElementInfo ei_rf = vm.getHeap().get(i);
				oc.add(getIdOfOC(ei_rf));
			}
		}
		return oc;
	}

	public MultiSet<String> getNonces(VM vm, ElementInfo ei_nonces) {
		ElementInfo ei_multiset = (ElementInfo) ei_nonces.getFieldValueObject("multiset");
		ReferenceArrayFields multiset_rafs = this.getArrayListRefs(ei_multiset);
		MultiSet<String> nonces = new MultiSet<String>(Constants.nonces);
		for (int i : (int[]) multiset_rafs.getValues()) {
			if (i > 0) {
				ElementInfo ei_nonce = vm.getHeap().get(i);
				nonces.add(getNonce(ei_nonce));
			}
		}
		return nonces;
	}

	public String getNonce(ElementInfo ei_nonce) {
		ElementInfo ei_prin_p1 = (ElementInfo) ei_nonce.getFieldValueObject("p1");
		String p1 = getIdOfOC(ei_prin_p1);
		ElementInfo ei_prin_p2 = (ElementInfo) ei_nonce.getFieldValueObject("p2");
		String p2 = getIdOfOC(ei_prin_p2);
		ElementInfo ei_rand = (ElementInfo) ei_nonce.getFieldValueObject("r");
		String r = getIdOfOC(ei_rand);
		return "n(" + p1.toString() + "," + p2.toString() + "," + r.toString() + ")";
	}

	private String getIdOfOC(ElementInfo ei) {
		ElementInfo ei_id = (ElementInfo) ei.getFieldValueObject("id");
		return getStringType(ei_id);
	}

}
