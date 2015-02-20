import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Snake {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Whats your Name?");
		String name = br.readLine();
		System.out.println("Hellow World");
		System.out.println(name);

	}

}
