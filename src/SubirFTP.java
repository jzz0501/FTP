import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SubirFTP {
    public static void main(String[] args) throws IOException {
        FTPClient cliente = new FTPClient();
        String srvftp = "localhost";
        String usuario = "aed";
        String password = "aed";
        String directorio = "/ftp";

        try {
            // Nos conectamos
            cliente.connect(srvftp);
            // Login
            boolean login = cliente.login(usuario, password);
            if (login) {
                // System.out.println("El usuario es correcto");
                cliente.changeWorkingDirectory(directorio);
                cliente.setFileType(FTP.BINARY_FILE_TYPE);
                // Stream para subir archivos
                File[] files = new File("./Subir").listFiles();
                if (files!=null) {
                    for (File file : files) {
                        BufferedInputStream bis = new BufferedInputStream(
                                new FileInputStream("./Subir/" + file.getName()));
                        cliente.storeFile(file.getName(), bis);
                        bis.close();
                    }
                }
                cliente.logout();
            } else {
                System.out.println("Usuario incorrecto");

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        cliente.disconnect();
    }
}
