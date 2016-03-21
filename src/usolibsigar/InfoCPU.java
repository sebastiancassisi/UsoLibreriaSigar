
package usolibsigar;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
 
public class InfoCPU {
    private Sigar sigar;
 
    public void imprimirInfoCPU() {
        sigar = new Sigar();
        CpuInfo[] infos = null;
        CpuPerc[] cpus = null;
        try {
            infos = sigar.getCpuInfoList();
            cpus = sigar.getCpuPercList();
        } catch (SigarException e) {
            e.printStackTrace();
        }
 
        CpuInfo info = infos[0];
        long tamanioCache = info.getCacheSize();
        System.out.println("Fabricante:\t\t" + info.getVendor());
        System.out.println("Modelo\t\t\t" + info.getModel());
        System.out.println("Mhz\t\t\t" + info.getMhz());
        System.out.println("Total CPUs\t\t" + info.getTotalCores());
        if ((info.getTotalCores() != info.getTotalSockets())
                || (info.getCoresPerSocket() > info.getTotalCores())) {
            System.out.println("CPUs fisiscas\t\t" + info.getTotalSockets());
            System.out
                    .println("Nucleos por CPU\t\t" + info.getCoresPerSocket());
        }
 
        if (tamanioCache != Sigar.FIELD_NOTIMPL)
            System.out.println("Tamanio cache\t\t" + tamanioCache);
        System.out.println("");
 
        for (int i = 0; i < cpus.length; i++)
            System.out.println("Consumo de CPU " + i + "\t"
                    + CpuPerc.format(cpus[i].getUser()));
 
        try {
            System.out.println("Consumo total de CPU\t"
                    + CpuPerc.format(sigar.getCpuPerc().getUser()));
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }
}
