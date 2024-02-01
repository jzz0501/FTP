import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DescargarFTP {
    final static String SITE = "localhost"; //Con el filezilla en pc profe
    final static String USER = "aed";
    final static String PASSW = "aed";

    public static void main(String[] args) {

        // Intentamos conexi�n ftp an�nima

        FTPClient ftpclient = new FTPClient();
        try {

            ftpclient.connect(SITE);

            if (FTPReply.isPositiveCompletion(ftpclient.getReplyCode())) {
                System.out.println(ftpclient.getReplyString());
                if (ftpclient.login(USER, PASSW)) {
                    ListFiles(ftpclient);
                    Descarga(ftpclient);
                } else {
                    System.out.println("Usuario incorrecto");
                }
            } else {
                System.out.println("Error de acceso, desconectamos");
                ftpclient.disconnect();
            }
        } catch (IOException e) {
            // mensaje error E/S, gestionar
        }

    }

    //Para descargar SOLO ficheros en este ejemplo

    private static void Descarga(FTPClient ftpclient)throws IOException {

        BufferedOutputStream bo;

        FTPFile[] remotefiles = ftpclient.listFiles();

        for (FTPFile f : remotefiles) {
            if (f.isFile()) {
                bo = new BufferedOutputStream(new FileOutputStream("./Descargar/"+f.getName()));
                if (ftpclient.retrieveFile(f.getName(), bo))
                    System.out.println("Descarga de " + f.getName()
                            + " correcta");
                else
                    System.err.println("Descarga de " + f.getName()
                            + " incorrecta");
                bo.flush();
                bo.close();
            }
        }
    }

    //Para obtener info de archivos y directorios
    private static void ListFiles(FTPClient ftpclient) throws IOException {

        //String currentDir = ftpclient.printWorkingDirectory();

        FTPFile[] remotefiles = ftpclient.listFiles();

        System.out.println("Se han encontrado " + remotefiles.length
                + " ficheros/directorios");
        for (FTPFile f : remotefiles) {
            System.out.println(f);
            // O con m�todos...
            // System.out.println(f.getName());
        }
    }
}
