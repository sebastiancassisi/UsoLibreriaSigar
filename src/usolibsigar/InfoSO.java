
package usolibsigar;

import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
 
public class InfoSO {
    private Sigar sigar = new Sigar();
    public void imprimirInfo() {
        OperatingSystem sys = OperatingSystem.getInstance();
        System.out.println("Descripcion del SO\t" + sys.getDescription());
        System.out.println("Nombre del SO\t\t" + sys.getName());
        System.out.println("Arquitectura del SO\t" + sys.getArch());
        System.out.println("Version del SO\t\t" + sys.getVersion());
        System.out.println("Nivel de parches\t" + sys.getPatchLevel());
        System.out.println("Fabricante\t\t" + sys.getVendor());
        System.out.println("Version SO\t\t" + sys.getVendorVersion());
        try {
            imprimirUptime();
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }
    public void imprimirUptime() throws SigarException {
        double uptime = sigar.getUptime().getUptime();
        String resultado = "";
        int dias = (int) uptime / (60 * 60 * 24);
        int minutos, horas;
        if (dias != 0)
            resultado += dias + " " + ((dias > 1) ? "dias" : "dia") + ", ";
        minutos = (int) uptime / 60;
        horas = minutos / 60;
        horas %= 24;
        minutos %= 60;
        if (horas != 0)
            resultado += horas + ":" + (minutos < 10 ? "0" + minutos : minutos);
        else
            resultado += minutos + " min";
        System.out.println("Encendido durante:\t" + resultado);
    }
}
