package funciones;

import java.net.InetSocketAddress;
import java.util.LinkedList;

import modelo.Usuario;

public class Comparador {
	public static Usuario comparar(LinkedList <Usuario> usuarios, InetSocketAddress dir2) {
		for(Usuario usuario: usuarios) {
			if(usuario.getDireccion().equals(dir2)) {
				return usuario;
			}
		}
		return null;
		
	}
}
