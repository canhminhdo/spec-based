package cloudsync.parser;
import java.util.ArrayList;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

import cloudsync.jpf.CloudSyncConfiguration;
import cloudsync.main.Cloud;
import cloudsync.main.PC;

public class MessageCloudSyncBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements CloudSyncVisitor<T> {
	
	CloudSyncParser parser;
	Cloud cloud;
	ArrayList<PC> pcList;
	
	public MessageCloudSyncBaseVisitor(CloudSyncParser parser) {
		this.parser = parser;
		cloud = new Cloud();
		pcList = new ArrayList<PC>();
	}

	@Override
	public T visitStart(CloudSyncParser.StartContext ctx) {
		visitChildren(ctx);
//		System.out.println(cloud);
//		System.out.println(pcList);
		CloudSyncConfiguration config = new CloudSyncConfiguration(cloud, pcList);
		return (T) config;
	}

	@Override
	public T visitOc(CloudSyncParser.OcContext ctx) {
		visitChildren(ctx);
		return null;
	}

	@Override
	public T visitCloud(CloudSyncParser.CloudContext ctx) {
		this.cloud.setStatusc(Cloud.LabelC.valueOf(ctx.LABELC().getText()));
		this.cloud.setValc(Integer.parseInt(ctx.INTEGER_NUMBER().getText()));
//		System.out.println(ctx.LABELC().getText());
//		System.out.println(ctx.INTEGER_NUMBER().getText());
		return null;
	}

	@Override
	public T visitPc(CloudSyncParser.PcContext ctx) {
		PC pc = new PC();
		pc.setCloud(this.cloud);
		pc.setPid(ctx.PID().getText());
		pc.setStatusp(PC.LabelP.valueOf(ctx.LABELP().getText()));
		pc.setValp(Integer.parseInt(ctx.INTEGER_NUMBER(0).getText()));
		pc.setTmp(Integer.parseInt(ctx.INTEGER_NUMBER(1).getText()));
		this.pcList.add(pc);
//		System.out.println(ctx.PID().getText());
//		System.out.println(ctx.LABELP().getText());
//		System.out.println(ctx.INTEGER_NUMBER(0).getText());
//		System.out.println(ctx.INTEGER_NUMBER(1).getText());
		return null;
	}
}
