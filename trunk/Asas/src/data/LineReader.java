package data;

public class LineReader {
	private String[] st;
	int cnt;
	
	public void setLine(String line, String separator){
		cnt = 0;
		st = line.split(separator);
	}
	
	public String readString(){
		return st[cnt++];
	}
	
	public int readIntOrDefault(int def){
		try{
			return readInt();
		}catch(NumberFormatException ex){
			return def;
		}
	}
	
	public int readInt(){
		return Integer.parseInt(readString());
	}
	
	public double readDouble(){
		return Double.parseDouble(readString().replace(",", "."));
	}
}
