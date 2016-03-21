
package usolibsigar;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.NfsFileSystem;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarProxyCache;
 
public class InfoSistemaArchivos {
    private SigarProxy proxy;
    private Sigar sigar;
 
    public InfoSistemaArchivos() {
        sigar = new Sigar();
        proxy = SigarProxyCache.newInstance(sigar);
    }
 
    public void imprimirInfo() throws SigarException {
        FileSystem[] listaSistemaArchivos = proxy.getFileSystemList();
        System.out.println("\ndispos.|total|usado|disponible|%uso|dir|tipo\n");
        for (int i = 0; i < listaSistemaArchivos.length; i++)
            imprimirSistemaArchivos(listaSistemaArchivos[i]);
        System.out.println("listaSistemaArchivos= "+listaSistemaArchivos);
    }
 
    public void imprimirSistemaArchivos(FileSystem sistemaArchivos)
            throws SigarException {
        long usado, disponible, total, porcentaje;
 
        try {
            FileSystemUsage uso;
            if (sistemaArchivos instanceof NfsFileSystem) {
                NfsFileSystem nfs = (NfsFileSystem) sistemaArchivos;
                if (!nfs.ping()) {
                    System.out.println(nfs.getUnreachableMessage());
                    return;
                }
            }
            uso = sigar.getFileSystemUsage(sistemaArchivos.getDirName());
 
            usado = uso.getTotal() - uso.getFree();
            disponible = uso.getAvail();
            total = uso.getTotal();
 
            porcentaje = (long) (uso.getUsePercent() * 100);
        } catch (SigarException e) {
            // por ejemplo, si en al procesar D:\ en windows falla
            // con "Device not ready"
            usado = disponible = total = porcentaje = 0;
        }
 
        String porcentajeUso;
        if (porcentaje == 0)
            porcentajeUso = "-";
        else
            porcentajeUso = porcentaje + "%";
 
        System.out.print(sistemaArchivos.getDevName());
        System.out.print("|" + total);
        System.out.print("|" + usado);
        System.out.print("|" + disponible);
        System.out.print("|" + porcentajeUso);
        System.out.print("|" + sistemaArchivos.getDirName());
        System.out.println("|" + sistemaArchivos.getSysTypeName());
    }
}
