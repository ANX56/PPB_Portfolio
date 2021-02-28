package com.duaempat.portofolio;

public class biodata {
    private int id;
    private String nama, nohp, email;
    private String keterangan;

    public biodata(int id, String nama, String email, String nohp, String keterangan){
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.nohp = nohp;
        this.keterangan = keterangan;
    }

    public int getId(){return id;}
    public String getNama(){return nama;}
    public String getEmail(){return email;}
    public String getNohp(){return nohp;}
    public String getKeterangan(){return keterangan;}
}
