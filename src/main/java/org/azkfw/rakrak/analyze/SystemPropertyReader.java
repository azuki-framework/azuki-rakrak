package org.azkfw.rakrak.analyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.azkfw.util.StringUtility;

public class SystemPropertyReader {

	protected void warn(final String format, final Object... args) {
		String msg = String.format(format, args);
		System.out.println(msg);
	}

	public SystemProperty read(final File file, final String charset) {
		SystemProperty result = new SystemProperty();

		Set<String> setXPD = new HashSet<String>();
		Set<String> setXWD = new HashSet<String>();
		Set<String> setXMD = new HashSet<String>();
		Set<String> setXDD = new HashSet<String>();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));

			String buffer = null;
			while (null != (buffer = reader.readLine())) {
				if (buffer.trim().startsWith("#")) {
					continue;
				}

				int index = buffer.indexOf("=");
				if (-1 == index) {
					continue;
				}

				String key = buffer.substring(0, index).trim().toUpperCase();
				String value = buffer.substring(index + 1).trim();

				// System.out.println(String.format("%s = %s", key, value));

				if ("XPD".equals(key)) {
					List<String> names = split(value);
					for (String name : names) {
						String trmName = name;
						if (StringUtility.isNotEmpty(trmName)) {
							if (!setXPD.contains(trmName)) {
								setXPD.add(trmName);
							} else {
								warn("Duplicate XPD name.[%s]", trmName);
							}
						}
					}
				} else if ("XWD".equals(key)) {
					List<String> names = split(value);
					for (String name : names) {
						String trmName = name;
						if (StringUtility.isNotEmpty(trmName)) {
							if (!setXWD.contains(trmName)) {
								setXWD.add(trmName);
							} else {
								warn("Duplicate XWD name.[%s]", trmName);
							}
						}
					}
				} else if ("XMD".equals(key)) {
					List<String> names = split(value);
					for (String name : names) {
						String trmName = name;
						if (StringUtility.isNotEmpty(trmName)) {
							if (!setXMD.contains(trmName)) {
								setXMD.add(trmName);
							} else {
								warn("Duplicate XMD name.[%s]", trmName);
							}
						}
					}
				} else if ("XDD".equals(key)) {
					List<String> names = split(value);
					for (String name : names) {
						String trmName = name;
						if (StringUtility.isNotEmpty(trmName)) {
							if (!setXDD.contains(trmName)) {
								setXDD.add(trmName);
							} else {
								warn("Duplicate XDD name.[%s]", trmName);
							}
						}
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		List<String> listXPD = new ArrayList<String>();
		for (String xpd : setXPD) {
			listXPD.add(xpd);
		}
		Collections.sort(listXPD);
		result.setXPDList(listXPD);

		List<String> listXWD = new ArrayList<String>();
		for (String xwd : setXWD) {
			listXWD.add(xwd);
		}
		Collections.sort(listXWD);
		result.setXWDList(listXWD);

		List<String> listXMD = new ArrayList<String>();
		for (String xmd : setXMD) {
			listXMD.add(xmd);
		}
		Collections.sort(listXMD);
		result.setXMDList(listXMD);

		List<String> listXDD = new ArrayList<String>();
		for (String xdd : setXDD) {
			listXDD.add(xdd);
		}
		Collections.sort(listXDD);
		result.setXDDList(listXDD);

		return result;
	}
	
	private static final List<String> split(final String string) {
		List<String> result = new ArrayList<String>();
		if (null != string) {
			String[] split = string.split("[\\s\\t]*,[\\s\\t]*");
			for (String s : split) {
				String buffer = s.trim();
				if (0 < buffer.length()) {
					result.add(buffer);
				}
			}
		}
		return result;
	}
}
