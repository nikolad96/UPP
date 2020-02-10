package bzb.server.model;

import javax.persistence.*;

@Entity
public class Rad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "oblast", nullable = false)
    private String oblast;

    @Column(name = "apstrakt", nullable = false)
    private String apstrakt;

    @Column(name = "pdf")
    @Lob
    private byte[] pdf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOblast() {
        return oblast;
    }

    public void setOblast(String oblast) {
        this.oblast = oblast;
    }

    public String getApstrakt() {
        return apstrakt;
    }

    public void setApstrakt(String apstrakt) {
        this.apstrakt = apstrakt;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public Rad() {
    }

    public Rad(Long id, String naziv, String oblast, String apstrakt, byte[] pdf) {
        this.id = id;
        this.naziv = naziv;
        this.oblast = oblast;
        this.apstrakt = apstrakt;
        this.pdf = pdf;
    }
}
