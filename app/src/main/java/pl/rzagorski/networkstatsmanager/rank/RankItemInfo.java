package pl.rzagorski.networkstatsmanager.rank;

public class RankItemInfo {

    private long allWifiRx;
    private long allMobileRx;
    private long allWifiTx;
    private long allMobileTx;
    private long packageWifiRx;
    private long packageMobileRx;
    private long packageWifiTx;
    private long packageMobileTx;
    private long packageWifiTotal;
    private long packageMobileTotal;
    private String name;
    private String packageName;

    public long getAllWifiRx() {
        return allWifiRx;
    }

    public void setAllWifiRx(long allWifiRx) {
        this.allWifiRx = allWifiRx;
    }

    public long getAllMobileRx() {
        return allMobileRx;
    }

    public void setAllMobileRx(long allMobileRx) {
        this.allMobileRx = allMobileRx;
    }

    public long getAllWifiTx() {
        return allWifiTx;
    }

    public void setAllWifiTx(long allWifiTx) {
        this.allWifiTx = allWifiTx;
    }

    public long getAllMobileTx() {
        return allMobileTx;
    }

    public void setAllMobileTx(long allMobileTx) {
        this.allMobileTx = allMobileTx;
    }

    public long getPackageWifiRx() {
        return packageWifiRx;
    }

    public void setPackageWifiRx(long packageWifiRx) {
        this.packageWifiRx = packageWifiRx;
    }

    public long getPackageMobileRx() {
        return packageMobileRx;
    }

    public void setPackageMobileRx(long packageMobileRx) {
        this.packageMobileRx = packageMobileRx;
    }

    public long getPackageWifiTx() {
        return packageWifiTx;
    }

    public void setPackageWifiTx(long packageWifiTx) {
        this.packageWifiTx = packageWifiTx;
    }

    public long getPackageMobileTx() {
        return packageMobileTx;
    }

    public void setPackageMobileTx(long packageMobileTx) {
        this.packageMobileTx = packageMobileTx;
    }

    public long getPackageWifiTotal() {
        return packageWifiTotal;
    }

    public void setPackageWifiTotal(long packageWifiTotal) {
        this.packageWifiTotal = packageWifiTotal;
    }

    public long getPackageMobileTotal() {
        return packageMobileTotal;
    }

    public void setPackageMobileTotal(long packageMobileTotal) {
        this.packageMobileTotal = packageMobileTotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
