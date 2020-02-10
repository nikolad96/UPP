package bzb.server.model;

import javax.persistence.*;

@Entity
public class Casopis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "oblast", nullable = false)
    private String oblast;

    @Column(name = "open", nullable = false)
    private Boolean open;

    public Casopis(Long id, String naziv, String oblast, Boolean open) {
        this.id = id;
        this.naziv = naziv;
        this.oblast = oblast;
        this.open = open;
    }

    public String getOblast() {
        return oblast;
    }

    public void setOblast(String oblast) {
        this.oblast = oblast;
    }

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

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Casopis() {
    }
}
