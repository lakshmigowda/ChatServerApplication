package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class Client {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String args[]) throws Exception {
		Properties props = new Properties();
		InputStream in = Client.class.getResourceAsStream("client.properties");
		props.load(in);
		String propertryString = props.getProperty("server");
		String server = propertryString.split("@")[0];
		String port = propertryString.split("@")[1];

		System.out.println("Establishing socket connection to : "
				+ propertryString);
		Socket soc = new Socket(InetAddress.getByName(server),
				Integer.valueOf(port));
		System.out.println("Connection established to : " + propertryString);

		transferfileClient t = new transferfileClient(soc);
		t.chat();
	}
}

class transferfileClient {
	Socket ClientSoc;

	DataInputStream din;
	DataOutputStream dout;
	BufferedReader br;

	transferfileClient(Socket soc) {
		try {
			ClientSoc = soc;
			din = new DataInputStream(ClientSoc.getInputStream());
			dout = new DataOutputStream(ClientSoc.getOutputStream());
			br = new BufferedReader(new InputStreamReader(System.in));
		} catch (Exception ex) {
		}
	}

	public void chat() throws Exception {
		while (true) {
			String info = br.readLine();
			dout.writeUTF(info);
			System.out.println(din.readUTF());
		}
	}

}