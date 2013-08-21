package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ClassroomList {

	/*TODO: Rever a necessidade dessa função. Essa listagem é estática, e não conterá alterações
	 * feitas pelo usuário. Mas ao importar um estado do excel, o usuário pode não ter um estado
	 * atual. Então as salas parecem não fazer parte do estado.
	 */
	public static List<String> readClassroomListFromFile(){
		List<String> names = new LinkedList<String>();
		Scanner sc;
		try {
			sc = new Scanner(new File("configs/classrooms.in"));
			while(sc.hasNext()){
				String line = sc.nextLine();
				names.add(line.split("#")[0]);
			}
			Collections.sort(names);
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return names;
	}
}
