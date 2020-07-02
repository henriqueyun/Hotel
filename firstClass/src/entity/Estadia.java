package entity;

import java.time.LocalDate;

public class Estadia implements Comparable<Estadia> {

    private int codigo;
    private LocalDate dataCheckin;
    private LocalDate dataCheckout;
    private String status;
    private int reserva;

    public Estadia() {}

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public LocalDate getDataCheckin() {
        return dataCheckin;
    }
    public void setDataCheckin(LocalDate dataCheckin) {
        this.dataCheckin = dataCheckin;
    }

    public LocalDate getDataCheckout() {
        return dataCheckout;
    }
    public void setDataCheckout(LocalDate dataCheckout) {
        this.dataCheckout = dataCheckout;
    }

    public int getReserva() {
        return reserva;
    }
    public void setReserva(int reserva) {
        this.reserva = reserva;
    }
    @Override
    public int compareTo(Estadia estadia) {
        return (this.codigo == estadia.codigo)?1:0;
    }
}