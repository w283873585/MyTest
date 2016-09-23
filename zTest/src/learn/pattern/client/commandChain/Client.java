package learn.pattern.client.commandChain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
	public static void main(String[] args) throws IOException {
		/**
		 * 
		 * 
		 * Invoker根据commandStr产生commandVO对象,
		 * 
		 * 再根据commandVO对象与CommandEnum对象, 生成Command(用来产生commandName链的工具对象).
		 * 
		 * 然后执行command, 即执行commandName链, 根据链上的单个commandName对象返回正确的结果.
		 * 
		 */
		
		Invoker invoker = new Invoker();
		while (true) {
			System.out.print("#");
			
			String input = (new BufferedReader(new InputStreamReader(
					System.in))).readLine();
			
			if (input.equals("quit") || input.equals("exit")) {
				return;
			}
			
			System.out.println(invoker.exec(input));
		}
	}
}
