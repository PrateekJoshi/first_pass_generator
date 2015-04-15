package assembler;

import java.util.*;
import java.io.*;

class ReadFile {
	private FileInputStream in;
	private FileOutputStream out;
	private String file_name;
	private Hashtable<String, String> final_table;

	public ReadFile(String fname, String oname) {
		try {
			in = new FileInputStream(fname);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			out = new FileOutputStream(oname);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		file_name = fname;
	}

	public void read(Hashtable<String, String> sysmbol_table,
			Hashtable<String, String> non_mri_table,
			Hashtable<String, String> mri_table) {
		final_table = new Hashtable();
		try {
			Scanner word_line = new Scanner(new File(file_name));
			Set<String> keys_mri = mri_table.keySet();
			while (word_line.hasNextLine()) {
				String line = word_line.nextLine();
				String[] words = line.split(" ");
				for (int i = 0; i < words.length; i++) {
					for (String key : keys_mri) {
						if (key.equalsIgnoreCase(words[i])) {
							final_table.put(words[i + 1], "");
						}
					}

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			try {
				in.close();
				out.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
	}

	public void print_final_table() {
		System.out.println(final_table);
	}

	public void put_address() {
		try {
			Scanner word_line = new Scanner(new File(file_name));
			while (word_line.hasNextLine()) {
				String line = word_line.nextLine();
				String[] words = line.split(" ");
				for (int i = 0; i < words.length; i++) {
					  if(words[i].contains(",")){
						  String temp_addr=words[i-1];
						  String[] temp = words[i].split(",");
						  final_table.put(temp[0],temp_addr);
						 // System.out.println(temp[0]);
					  }
					
				}
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			try {
				in.close();
				out.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

	}
	
	public void print_table(){
		System.out.println("LABEL"+"   "+"ADDRESS");
		Set<String> keys = final_table.keySet();
        for(String key: keys){
            System.out.println(key+"         "+final_table.get(key));
        }
	}

}

public class Main {

	public static void main(String args[]) {

		Hashtable<String, String> mri_table = new Hashtable();
		mri_table.put("LDA", "001");
		mri_table.put("ADD", "010");
		mri_table.put("AND", "000");
		mri_table.put("STA", "011");
		mri_table.put("BUN", "100");
		mri_table.put("BSA", "101");
		mri_table.put("ISZ", "110");

		Hashtable<String, String> non_mri_table = new Hashtable();
		non_mri_table.put("CLA", "7800");
		non_mri_table.put("CLE", "7400");
		non_mri_table.put("CMA", "7200");
		non_mri_table.put("CME", "7100");
		non_mri_table.put("CIR", "7080");
		non_mri_table.put("INC", "7020");
		non_mri_table.put("SPA", "7010");
		non_mri_table.put("SNA", "7008");
		non_mri_table.put("SZA", "7004");
		non_mri_table.put("SZE", "7002");
		non_mri_table.put("HLT", "7001");

		Hashtable<String, String> sysmbol_table = new Hashtable();
		sysmbol_table.put("INP", "F800");
		sysmbol_table.put("OUT", "F400");
		sysmbol_table.put("SKI", "F200");
		sysmbol_table.put("SKO", "F100");
		sysmbol_table.put("ION", "F080");
		sysmbol_table.put("IOF", "F040");

		String read_name = "E:/Java/assembler/input.asm";
		String write_name = "E:/Java/assembler/output.txt";
		ReadFile file = new ReadFile(read_name, write_name);
		file.read(sysmbol_table, non_mri_table, mri_table);
		
		file.put_address();		

		file.print_table();
		

	}
}
