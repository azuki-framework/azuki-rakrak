package org.azkfw.rakrak.analyze.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.azkfw.rakrak.analyze.Item;
import org.azkfw.rakrak.analyze.Items;
import org.azkfw.rakrak.analyze.Message;
import org.azkfw.rakrak.analyze.Messages;
import org.azkfw.rakrak.analyze.Program;
import org.azkfw.rakrak.analyze.Programs;
import org.azkfw.rakrak.analyze.RakRakAnalyzeResult;
import org.azkfw.rakrak.analyze.entity.DDEntity;
import org.azkfw.rakrak.analyze.entity.MSGEntity;
import org.azkfw.rakrak.analyze.entity.ProgramEntity;
import org.azkfw.rakrak.analyze.entity.ProgramPPEntity;

public class RakRakAnalyzeReport {

	public void report(final File file, final RakRakAnalyzeResult result) {
		// Workbook write
		Workbook workbook = null;
		if (file.getAbsolutePath().toLowerCase().endsWith(".xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (file.getAbsolutePath().toUpperCase().endsWith(".xls")) {
			workbook = new HSSFWorkbook();
		}

		writeSheetMessage(result.getMessages(), workbook.createSheet("メッセージ一覧"), workbook);
		writeSheetItem(result.getItems(), workbook.createSheet("DD一覧"), workbook);
		writeSheetProgram(result.getPrograms().toListWindow(), workbook.createSheet("画面一覧"), workbook);
		writeSheetProgram(result.getPrograms().toListPopupWindow(), workbook.createSheet("ポップアップ一覧"), workbook);
		writeSheetDDProgram(result.getItems(), result.getPrograms(), workbook.createSheet("DD - Program"), workbook);

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			workbook.write(out);
		} catch (IOException e) {
			System.out.println(e.toString());
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}

	private void writeSheetMessage(final Messages messages, final Sheet sheet, final Workbook workbook) {
		// create cell style
		CellStyle headeCellStyle = workbook.createCellStyle();
		headeCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headeCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		// header
		Row headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("No");
		headRow.getCell(0).setCellStyle(headeCellStyle);
		headRow.createCell(1).setCellValue("Id");
		headRow.getCell(1).setCellStyle(headeCellStyle);
		headRow.createCell(2).setCellValue("Lang");
		headRow.getCell(2).setCellStyle(headeCellStyle);
		headRow.createCell(3).setCellValue("Mode");
		headRow.getCell(3).setCellStyle(headeCellStyle);
		headRow.createCell(4).setCellValue("Message");
		headRow.getCell(4).setCellStyle(headeCellStyle);

		// data
		int row = 1;
		for (Message message: messages.toList()) {
			for (MSGEntity msg : message.getEntitys()) {
				Row dataRow = sheet.createRow(row);
				dataRow.createCell(0).setCellValue(String.format("%d", row));
				dataRow.createCell(1).setCellValue(msg.getId());
				dataRow.createCell(2).setCellValue(msg.getLang());
				dataRow.createCell(3).setCellValue(msg.getMode());
				dataRow.createCell(4).setCellValue(msg.getValue());
				row++;
			}
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		
		// ウインドウ固定
		sheet.createFreezePane(0, 1);
	}

	private void writeSheetItem(final Items items, final Sheet sheet, final Workbook workbook) {
		// create cell style
		CellStyle headeCellStyle = workbook.createCellStyle();
		headeCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headeCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		// header
		Row headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("No");
		headRow.getCell(0).setCellStyle(headeCellStyle);
		headRow.createCell(1).setCellValue("Name");
		headRow.getCell(1).setCellStyle(headeCellStyle);
		headRow.createCell(2).setCellValue("Title(def)");
		headRow.getCell(2).setCellStyle(headeCellStyle);
		headRow.createCell(3).setCellValue("DB Field");
		headRow.getCell(3).setCellStyle(headeCellStyle);
		headRow.createCell(4).setCellValue("Type");
		headRow.getCell(4).setCellStyle(headeCellStyle);
		headRow.createCell(5).setCellValue("Size");
		headRow.getCell(5).setCellStyle(headeCellStyle);
		headRow.createCell(6).setCellValue("Length");
		headRow.getCell(6).setCellStyle(headeCellStyle);
		headRow.createCell(7).setCellValue("Align");
		headRow.getCell(7).setCellStyle(headeCellStyle);
		headRow.createCell(8).setCellValue("Ref Button");
		headRow.getCell(8).setCellStyle(headeCellStyle);
		headRow.createCell(9).setCellValue("Ref Window");
		headRow.getCell(9).setCellStyle(headeCellStyle);
		headRow.createCell(10).setCellValue("Ref Table");
		headRow.getCell(10).setCellStyle(headeCellStyle);

		// data
		int row = 1;
		for (Item item : items.toList()) {
			DDEntity dd = item.getEntity();
			Row dataRow = sheet.createRow(row);
			dataRow.createCell(0).setCellValue(s(String.format("%d", row)));
			dataRow.createCell(1).setCellValue(s(item.getName()));
			dataRow.createCell(2).setCellValue(s(item.getDefaultTitle()));
			dataRow.createCell(3).setCellValue(s(dd.getDbfield()));
			dataRow.createCell(4).setCellValue(s(dd.getType()));
			dataRow.createCell(5).setCellValue(s(dd.getSize()));
			dataRow.createCell(6).setCellValue(s(dd.getLength()));
			dataRow.createCell(7).setCellValue(s(dd.getAlign()));
			dataRow.createCell(8).setCellValue(s(dd.getRefButton()));
			dataRow.createCell(9).setCellValue(s(dd.getRefWindow()));
			dataRow.createCell(10).setCellValue(s(dd.getRefTable()));
			row++;
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(7);
		
		// ウインドウ固定
		sheet.createFreezePane(3, 1);
	}

	private void writeSheetProgram(final List<Program> list, final Sheet sheet, final Workbook workbook) {
		// create cell style
		CellStyle headeCellStyle = workbook.createCellStyle();
		headeCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headeCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		// header
		Row headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("No");
		headRow.getCell(0).setCellStyle(headeCellStyle);
		headRow.createCell(1).setCellValue("Name");
		headRow.getCell(1).setCellStyle(headeCellStyle);
		headRow.createCell(2).setCellValue("Title(def)");
		headRow.getCell(2).setCellStyle(headeCellStyle);
		headRow.createCell(3).setCellValue("Plugins");
		headRow.getCell(3).setCellStyle(headeCellStyle);

		// data
		int row = 1;
		for (Program program : list) {
			StringBuilder plugins = new StringBuilder();
			for (String plugin : program.getEntity().getPlugins()) {
				if (0 < plugins.length()) {
					plugins.append(", ");
				}
				plugins.append(plugin);
			}

			Row dataRow = sheet.createRow(row);
			dataRow.createCell(0).setCellValue(String.format("%d", row));
			dataRow.createCell(1).setCellValue(program.getName());
			dataRow.createCell(2).setCellValue(program.getDefaultTitle().getValue());
			dataRow.createCell(3).setCellValue(plugins.toString());

			row++;
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		// ウインドウ固定
		sheet.createFreezePane(0, 1);
	}

	private void writeSheetDDProgram(final Items items, final Programs programs, final Sheet sheet,
			final Workbook workbook) {
		// create cell style
		CellStyle headeCellStyle1 = workbook.createCellStyle();
		headeCellStyle1.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headeCellStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		CellStyle headeCellStyle2 = workbook.createCellStyle();
		headeCellStyle2.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headeCellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headeCellStyle2.setRotation((short) 90);

		List<Program> list = programs.toList();

		final int colSize = 4 + list.size();

		// header
		Row headRow = sheet.createRow(0);
		int colNum = 0;
		headRow.setHeightInPoints(120);
		headRow.createCell(colNum++).setCellValue("No");
		headRow.getCell(0).setCellStyle(headeCellStyle1);
		headRow.createCell(colNum++).setCellValue("DD");
		headRow.getCell(1).setCellStyle(headeCellStyle1);
		headRow.createCell(colNum++).setCellValue("Name");
		headRow.getCell(2).setCellStyle(headeCellStyle1);
		headRow.createCell(colNum++).setCellValue("");
		headRow.getCell(3).setCellStyle(headeCellStyle1);
		for (Program program : list) {
			Cell cell = headRow.createCell(colNum++);
			cell.setCellValue(program.getName());
			cell.setCellStyle(headeCellStyle2);
		}

		// Data
		String startCol = CellReference.convertNumToColString(4);
		String endCol = CellReference.convertNumToColString(4 - 1 + list.size());
		int rowNum = 1;
		for (Item item : items.toList()) {
			Row dataRow = sheet.createRow(rowNum);
			dataRow.createCell(0).setCellValue(rowNum);
			dataRow.createCell(1).setCellValue(item.getName());
			dataRow.createCell(2).setCellValue(item.getDefaultTitle());
			dataRow.createCell(3).setCellFormula(String.format("COUNTA(%s%d:%s%d)", startCol, rowNum + 1, endCol, rowNum + 1));

			colNum = 4;
			for (Program program : list) {
				ProgramEntity e = program.getEntity();
				Cell cell = dataRow.createCell(colNum);
				boolean find = false;
				for (ProgramPPEntity pp : e.getPPList()) {
					if (pp.isKeyField(item.getName())) {
						find = true;
						break;
					}
				}
				if (find) {
					cell.setCellValue("●");
				}
				colNum++;
			}

			rowNum++;
		}

		// ウインドウ固定
		sheet.createFreezePane(4, 1);
		// 幅調整
		for (int i = 0; i < colSize; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	protected static final String s(final String string) {
		String s = "";
		if (null != string) {
			s = string;
		}
		return s;
	}
	protected static final String s(final Integer integer) {
		String s = "";
		if (null != integer) {
			s = integer.toString();
		}
		return s;
	}
}
