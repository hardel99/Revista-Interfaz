package com.example.hardel.revin;

public class nius {
    private String tit,descr,minid,fecha,biti,cat;

    public nius(String tit,String minid, String descr,String fecha,String cat, String biti) {
        this.tit = tit;
        this.descr = descr;
        this.minid=minid;
        this.fecha=fecha;
        this.cat = cat;
        this.biti = biti;
    }

    public String getTit() {
        return tit;
    }

    public String getDescr() {
        return descr;
    }

    public String getmimid() {
        return minid;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCate(){
        return cat;
    }

    public String getPata(){
        return biti;
    }
}
