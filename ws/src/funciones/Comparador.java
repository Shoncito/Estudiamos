package funciones;

import java.net.InetSocketAddress;
import java.util.LinkedList;

import modelo.dto.Usuario;

public class Comparador {
	public static Usuario comparar(LinkedList <Usuario> usuarios, InetSocketAddress dir2) {
		for(Usuario usuario: usuarios) {
			System.out.println(usuario.getDireccion().getHostString()+"|"+dir2.getHostString());
			if(usuario.getDireccion().getHostString().equals(dir2.getHostString())) {
				return usuario;
			}
		}
		return null;
		
	}
}
