package usolibsigar;

import org.hyperic.sigar.SigarException;
public class InfoSistema {
    public static void main(String[] args) {
        try {
            System.out.println("====Informacion del sistema====");
            new InfoSO().imprimirInfo();
            System.out.println("\n==== Informacion de la CPU ====");
            new InfoCPU().imprimirInfoCPU();
            System.out.println("\n====Informacion del sistema de archivos====");
            new InfoSistemaArchivos().imprimirInfo();
            System.out.println("\n====Informacion de la memoria====");
            new InfoMemoria().imprimirInfo();
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }
}