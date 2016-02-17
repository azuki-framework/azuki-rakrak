package org.azkfw.rakrak.analyze;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.BeanPropertySetterRule;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreateRule;
import org.apache.commons.digester.SetNextRule;
import org.apache.commons.digester.SetPropertiesRule;
import org.azkfw.rakrak.analyze.entity.DDEntity;
import org.azkfw.rakrak.analyze.entity.DDTitleEntity;
import org.azkfw.rakrak.analyze.entity.MSGEntity;
import org.azkfw.rakrak.analyze.entity.ProgramEntity;
import org.azkfw.rakrak.analyze.entity.ProgramOptionEntity;
import org.azkfw.rakrak.analyze.entity.ProgramOptionParamEntity;
import org.azkfw.rakrak.analyze.entity.ProgramPPEntity;
import org.azkfw.rakrak.analyze.entity.ProgramPatternEntity;
import org.azkfw.rakrak.analyze.entity.ProgramTitleEntity;
import org.azkfw.rakrak.analyze.entity.XDDEntity;
import org.azkfw.rakrak.analyze.entity.XMDEntity;
import org.azkfw.rakrak.analyze.entity.XPDEntity;
import org.azkfw.rakrak.analyze.report.RakRakAnalyzeReport;
import org.xml.sax.SAXException;


public class RakRakAnalyzer {

	public static void main(final String[] args) {
		RakRakAnalyzer analyzer = new RakRakAnalyzer();
		RakRakAnalyzeResult result = analyzer.analyze(new File(args[0]));
		
		RakRakAnalyzeReport report = new RakRakAnalyzeReport();
		report.report(new File(args[1]), result);
	}

	private File baseDir;

	protected void warn(final String format, final Object... args) {
		String msg = String.format(format, args);
		System.out.println(msg);
	}

	public RakRakAnalyzeResult analyze(final File dir) {
		baseDir = dir;

		// Read system.properties
		File systemPropertiesFile = getSystemPropertyFile();
		SystemPropertyReader reader = new SystemPropertyReader();
		SystemProperty systemProperty =  reader.read(systemPropertiesFile, "MS932");

		RakRakAnalyzeResult result = new RakRakAnalyzeResult();

		List<XMDEntity> xmdList = readXMD(systemProperty.getXMDList());
		result.setXMDEntityList(xmdList);

		List<XDDEntity> xddList = readXDD(systemProperty.getXDDList());
		result.setXDDEntityList(xddList);

		List<XPDEntity> xpdList = readXPD(systemProperty.getXPDList());
		result.setXPDEntityList(xpdList);

		List<XPDEntity> xwdList = readXWD(systemProperty.getXWDList());
		result.setXWDEntityList(xwdList);

		Messages messages = new Messages();
		for (XMDEntity xmd : xmdList) {
			for (MSGEntity msg : xmd.getMSGList()) {
				messages.add(msg);
			}
		}
		result.setMessages(messages);

		Items items = new Items();
		for (XDDEntity xdd : xddList) {
			for (DDEntity dd : xdd.getDDList()) {
				items.add(dd);
			}
		}
		result.setItems(items);

		Programs programs = new Programs();
		for (XPDEntity xpd : xpdList) {
			for (ProgramEntity prg : xpd.getProgramList()) {
				programs.addWindow(prg);
			}
		}
		for (XPDEntity xwd : xwdList) {
			for (ProgramEntity prg : xwd.getProgramList()) {
				programs.addPopupWindow(prg);
			}
		}
		result.setPrograms(programs);

		return result;
	}

	private List<XMDEntity> readXMD(final List<String> names) {
		List<XMDEntity> list = new ArrayList<XMDEntity>();
		// Read XMD file
		for (String xmdFileName : names) {
			File xmdFile = getXMDFile(xmdFileName);
			if (xmdFile.isFile()) {
				XMDEntity e = readXML(xmdFile, getDigesterXMD());
				list.add(e);
			} else {
				warn("Not Found XMD file.[%s]", xmdFile.getAbsolutePath());
			}
		}
		return list;
	}

	private List<XDDEntity> readXDD(final List<String> names) {
		List<XDDEntity> list = new ArrayList<XDDEntity>();
		// Read XDD file
		for (String xddFileName : names) {
			File xddFile = getXDDFile(xddFileName);
			if (xddFile.isFile()) {
				XDDEntity e = readXML(xddFile, getDigesterXDD());
				list.add(e);
			} else {
				warn("Not Found XDD file.[%s]", xddFile.getAbsolutePath());
			}
		}
		return list;
	}

	private List<XPDEntity> readXPD(final List<String> names) {
		List<XPDEntity> list = new ArrayList<XPDEntity>();
		// Read XPD file
		for (String xpdFileName : names) {
			File xpdFile = getXPDFile(xpdFileName);
			if (xpdFile.isFile()) {
				XPDEntity e = readXML(xpdFile, getDigesterXPD());
				list.add(e);
			} else {
				warn("Not Found XPD file.[%s]", xpdFile.getAbsolutePath());
			}
		}
		return list;
	}

	private List<XPDEntity> readXWD(final List<String> names) {
		List<XPDEntity> list = new ArrayList<XPDEntity>();
		// Read XWD file
		for (String xwdFileName : names) {
			File xwdFile = getXWDFile(xwdFileName);
			if (xwdFile.isFile()) {
				XPDEntity e = readXML(xwdFile, getDigesterXPD());
				list.add(e);
			} else {
				warn("Not Found XWD file.[%s]", xwdFile.getAbsolutePath());
			}
		}
		return list;
	}

	private File getSystemPropertyFile() {
		File file = Paths.get(baseDir.getAbsolutePath(), "WEB-INF", "RakAppl", "def", "system.properties").toFile();
		return file;
	}

	private File getXMDFile(final String name) {
		File file = Paths.get(baseDir.getAbsolutePath(), "WEB-INF", "RakAppl", "def", name).toFile();
		return file;
	}

	private File getXDDFile(final String name) {
		File file = Paths.get(baseDir.getAbsolutePath(), "WEB-INF", "RakAppl", "def", name).toFile();
		return file;
	}

	private File getXPDFile(final String name) {
		File file = Paths.get(baseDir.getAbsolutePath(), "WEB-INF", "RakAppl", "def", name).toFile();
		return file;
	}

	private File getXWDFile(final String name) {
		File file = Paths.get(baseDir.getAbsolutePath(), "WEB-INF", "RakAppl", "def", name).toFile();
		return file;
	}

	private Digester getDigesterXMD() {
		Digester digester = new Digester();
		digester.addRule("XMD", new ObjectCreateRule(XMDEntity.class));
		digester.addRule("XMD/MSG", new ObjectCreateRule(MSGEntity.class));
		digester.addRule("XMD/MSG", new SetPropertiesRule("ID", "id"));
		digester.addRule("XMD/MSG", new SetPropertiesRule("Mode", "mode"));
		digester.addRule("XMD/MSG", new SetPropertiesRule("Lang", "lang"));
		digester.addRule("XMD/MSG", new BeanPropertySetterRule("value"));
		digester.addRule("XMD/MSG", new SetNextRule("add"));
		return digester;
	}

	private Digester getDigesterXDD() {
		Digester digester = new Digester();
		digester.addRule("XDD", new ObjectCreateRule(XDDEntity.class));
		digester.addRule("XDD/DD", new ObjectCreateRule(DDEntity.class));
		digester.addRule("XDD/DD", new SetPropertiesRule("Name", "name"));
		digester.addRule("XDD/DD/DBFIELD", new BeanPropertySetterRule("dbfield"));
		digester.addRule("XDD/DD/TYPE", new BeanPropertySetterRule("type"));
		digester.addRule("XDD/DD/SIZE", new BeanPropertySetterRule("size"));
		digester.addRule("XDD/DD/LENGTH", new BeanPropertySetterRule("length"));
		digester.addRule("XDD/DD/INPUTTYPE", new BeanPropertySetterRule("inputType"));
		digester.addRule("XDD/DD/ALIGN", new BeanPropertySetterRule("align"));
		digester.addRule("XDD/DD/SQL", new BeanPropertySetterRule("sql"));
		digester.addRule("XDD/DD/REFWINDOW", new BeanPropertySetterRule("refWindow"));
		digester.addRule("XDD/DD/REFTABLE", new BeanPropertySetterRule("refTable"));
		digester.addRule("XDD/DD/REFBUTTON", new BeanPropertySetterRule("refButtonValue"));
		digester.addRule("XDD/DD/PLUGIN", new BeanPropertySetterRule("plugin"));
		digester.addRule("XDD/DD", new SetNextRule("add"));

		digester.addRule("XDD/DD/TITLE", new ObjectCreateRule(DDTitleEntity.class));
		digester.addRule("XDD/DD/TITLE", new SetPropertiesRule("Lang", "lang"));
		digester.addRule("XDD/DD/TITLE", new BeanPropertySetterRule("value"));
		digester.addRule("XDD/DD/TITLE", new SetNextRule("addTitle"));
		return digester;
	}

	private Digester getDigesterXPD() {
		Digester digester = new Digester();
		digester.addRule("XPD", new ObjectCreateRule(XPDEntity.class));
		digester.addRule("XPD/Program", new ObjectCreateRule(ProgramEntity.class));
		digester.addRule("XPD/Program", new SetPropertiesRule("Name", "name"));
		digester.addRule("XPD/Program", new SetNextRule("add"));

		digester.addRule("XPD/Program/Title", new ObjectCreateRule(ProgramTitleEntity.class));
		digester.addRule("XPD/Program/Title", new SetPropertiesRule("Lang", "lang"));
		digester.addRule("XPD/Program/Title", new BeanPropertySetterRule("value"));
		digester.addRule("XPD/Program/Title", new SetNextRule("addTitle"));

		digester.addRule("XPD/Program/Pattern", new ObjectCreateRule(ProgramPatternEntity.class));
		digester.addRule("XPD/Program/Pattern", new SetPropertiesRule("Offset", "offset"));
		digester.addRule("XPD/Program/Pattern", new BeanPropertySetterRule("value"));
		digester.addRule("XPD/Program/Pattern", new SetNextRule("addPattern"));

		digester.addRule("XPD/Program/PP", new ObjectCreateRule(ProgramPPEntity.class));
		digester.addRule("XPD/Program/PP", new SetPropertiesRule("page", "page"));
		digester.addRule("XPD/Program/PP/Plugin", new BeanPropertySetterRule("plugin"));
		digester.addRule("XPD/Program/PP/KeyFields", new BeanPropertySetterRule("keyFields"));
		digester.addRule("XPD/Program/PP/KeyFields2", new BeanPropertySetterRule("keyFields2"));
		digester.addRule("XPD/Program/PP/KeyFields3", new BeanPropertySetterRule("keyFields3"));
		digester.addRule("XPD/Program/PP/KeyFields4", new BeanPropertySetterRule("keyFields4"));
		digester.addRule("XPD/Program/PP/ListKeyFields", new BeanPropertySetterRule("listKeyFields"));
		digester.addRule("XPD/Program/PP/ListKeyFields2", new BeanPropertySetterRule("listKeyFields2"));
		digester.addRule("XPD/Program/PP/ListKeyFields3", new BeanPropertySetterRule("listKeyFields3"));
		digester.addRule("XPD/Program/PP/ListKeyFields4", new BeanPropertySetterRule("listKeyFields4"));
		digester.addRule("XPD/Program/PP/ListFields", new BeanPropertySetterRule("listFields"));
		digester.addRule("XPD/Program/PP/ListFields2", new BeanPropertySetterRule("listFields2"));
		digester.addRule("XPD/Program/PP/ListFields3", new BeanPropertySetterRule("listFields3"));
		digester.addRule("XPD/Program/PP/ListFields4", new BeanPropertySetterRule("listFields4"));
		digester.addRule("XPD/Program/PP/InqPrintFields", new BeanPropertySetterRule("inqPrintFields"));
		digester.addRule("XPD/Program/PP/InqPrintFields2", new BeanPropertySetterRule("inqPrintFields2"));
		digester.addRule("XPD/Program/PP/InqPrintFields3", new BeanPropertySetterRule("inqPrintFields3"));
		digester.addRule("XPD/Program/PP/InqPrintFields4", new BeanPropertySetterRule("inqPrintFields4"));
		digester.addRule("XPD/Program/PP/InqHiddenFields", new BeanPropertySetterRule("inqHiddenFields"));
		digester.addRule("XPD/Program/PP/InqHiddenFields2", new BeanPropertySetterRule("inqHiddenFields2"));
		digester.addRule("XPD/Program/PP/InqHiddenFields3", new BeanPropertySetterRule("inqHiddenFields3"));
		digester.addRule("XPD/Program/PP/InqHiddenFields4", new BeanPropertySetterRule("inqHiddenFields4"));
		digester.addRule("XPD/Program/PP/EntInputFields", new BeanPropertySetterRule("entInputFields"));
		digester.addRule("XPD/Program/PP/EntInputFields2", new BeanPropertySetterRule("entInputFields2"));
		digester.addRule("XPD/Program/PP/EntInputFields3", new BeanPropertySetterRule("entInputFields3"));
		digester.addRule("XPD/Program/PP/EntInputFields4", new BeanPropertySetterRule("entInputFields4"));
		digester.addRule("XPD/Program/PP/EntDBFields", new BeanPropertySetterRule("entDBFields"));
		digester.addRule("XPD/Program/PP/EntDBFields2", new BeanPropertySetterRule("entDBFields2"));
		digester.addRule("XPD/Program/PP/EntDBFields3", new BeanPropertySetterRule("entDBFields3"));
		digester.addRule("XPD/Program/PP/EntDBFields4", new BeanPropertySetterRule("entDBFields4"));
		digester.addRule("XPD/Program/PP/EntHiddenFields", new BeanPropertySetterRule("entHiddenFields"));
		digester.addRule("XPD/Program/PP/EntHiddenFields2", new BeanPropertySetterRule("entHiddenFields2"));
		digester.addRule("XPD/Program/PP/EntHiddenFields3", new BeanPropertySetterRule("entHiddenFields3"));
		digester.addRule("XPD/Program/PP/EntHiddenFields4", new BeanPropertySetterRule("entHiddenFields4"));
		digester.addRule("XPD/Program/PP/UpdPrintFields", new BeanPropertySetterRule("updPrintFields"));
		digester.addRule("XPD/Program/PP/UpdPrintFields2", new BeanPropertySetterRule("updPrintFields2"));
		digester.addRule("XPD/Program/PP/UpdPrintFields3", new BeanPropertySetterRule("updPrintFields3"));
		digester.addRule("XPD/Program/PP/UpdPrintFields4", new BeanPropertySetterRule("updPrintFields4"));
		digester.addRule("XPD/Program/PP/UpdInputFields", new BeanPropertySetterRule("updInputFields"));
		digester.addRule("XPD/Program/PP/UpdInputFields2", new BeanPropertySetterRule("updInputFields2"));
		digester.addRule("XPD/Program/PP/UpdInputFields3", new BeanPropertySetterRule("updInputFields3"));
		digester.addRule("XPD/Program/PP/UpdInputFields4", new BeanPropertySetterRule("updInputFields4"));
		digester.addRule("XPD/Program/PP/UpdDBFields", new BeanPropertySetterRule("updDBFields"));
		digester.addRule("XPD/Program/PP/UpdDBFields2", new BeanPropertySetterRule("updDBFields2"));
		digester.addRule("XPD/Program/PP/UpdDBFields3", new BeanPropertySetterRule("updDBFields3"));
		digester.addRule("XPD/Program/PP/UpdDBFields4", new BeanPropertySetterRule("updDBFields4"));
		digester.addRule("XPD/Program/PP/UpdHiddenFields", new BeanPropertySetterRule("updHiddenFields"));
		digester.addRule("XPD/Program/PP/UpdHiddenFields2", new BeanPropertySetterRule("updHiddenFields2"));
		digester.addRule("XPD/Program/PP/UpdHiddenFields3", new BeanPropertySetterRule("updHiddenFields3"));
		digester.addRule("XPD/Program/PP/UpdHiddenFields4", new BeanPropertySetterRule("updHiddenFields4"));
		digester.addRule("XPD/Program/PP/DelPrintFields", new BeanPropertySetterRule("delPrintFields"));
		digester.addRule("XPD/Program/PP/DelPrintFields2", new BeanPropertySetterRule("delPrintFields2"));
		digester.addRule("XPD/Program/PP/DelPrintFields3", new BeanPropertySetterRule("delPrintFields3"));
		digester.addRule("XPD/Program/PP/DelPrintFields4", new BeanPropertySetterRule("delPrintFields4"));
		digester.addRule("XPD/Program/PP/DelHiddenFields", new BeanPropertySetterRule("delHiddenFields"));
		digester.addRule("XPD/Program/PP/DelHiddenFields2", new BeanPropertySetterRule("delHiddenFields2"));
		digester.addRule("XPD/Program/PP/DelHiddenFields3", new BeanPropertySetterRule("delHiddenFields3"));
		digester.addRule("XPD/Program/PP/DelHiddenFields4", new BeanPropertySetterRule("delHiddenFields4"));
		digester.addRule("XPD/Program/PP/FileInputFields", new BeanPropertySetterRule("fileInputFields"));
		digester.addRule("XPD/Program/PP/FileInputFields2", new BeanPropertySetterRule("fileInputFields2"));
		digester.addRule("XPD/Program/PP/FileInputFields3", new BeanPropertySetterRule("fileInputFields3"));
		digester.addRule("XPD/Program/PP/FileInputFields4", new BeanPropertySetterRule("fileInputFields4"));
		digester.addRule("XPD/Program/PP/RptPrintFields", new BeanPropertySetterRule("rptPrintFields"));
		digester.addRule("XPD/Program/PP/RptPrintFields2", new BeanPropertySetterRule("rptPrintFields2"));
		digester.addRule("XPD/Program/PP/RptPrintFields3", new BeanPropertySetterRule("rptPrintFields3"));
		digester.addRule("XPD/Program/PP/RptPrintFields4", new BeanPropertySetterRule("rptPrintFields4"));
		digester.addRule("XPD/Program/PP/HiddenFields", new BeanPropertySetterRule("hiddenFields"));
		digester.addRule("XPD/Program/PP/HiddenFields2", new BeanPropertySetterRule("hiddenFields2"));
		digester.addRule("XPD/Program/PP/HiddenFields3", new BeanPropertySetterRule("hiddenFields3"));
		digester.addRule("XPD/Program/PP/HiddenFields4", new BeanPropertySetterRule("hiddenFields4"));
		digester.addRule("XPD/Program/PP", new SetNextRule("addPP"));

		digester.addRule("XPD/Program/PP/Option", new ObjectCreateRule(ProgramOptionEntity.class));
		digester.addRule("XPD/Program/PP/Option", new SetNextRule("setOption"));

		digester.addRule("XPD/Program/PP/Option/Param", new ObjectCreateRule(ProgramOptionParamEntity.class));
		digester.addRule("XPD/Program/PP/Option/Param", new SetPropertiesRule("Name", "name"));
		digester.addRule("XPD/Program/PP/Option/Param", new BeanPropertySetterRule("value"));
		digester.addRule("XPD/Program/PP/Option/Param", new SetNextRule("addParam"));
		return digester;
	}

	@SuppressWarnings("unchecked")
	private <T> T readXML(final File file, final Digester digester) {
		T obj = null;
		try {
			obj = (T) digester.parse(file);
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return obj;
	}
}
