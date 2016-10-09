package com.epam.jmp.maven.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @Column(name = "FILM_NAME")
    private String filmName;

    private LocalDate date;
    private Integer seat;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    public Reservation() {
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (filmName != null ? !filmName.equals(that.filmName) : that.filmName != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (seat != null ? !seat.equals(that.seat) : that.seat != null) return false;
        return price != null ? price.equals(that.price) : that.price == null;

    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (filmName != null ? filmName.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
