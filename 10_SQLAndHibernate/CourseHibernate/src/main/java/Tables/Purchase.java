package Tables;

import Helpers.PurchaseID;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "PurchaseList")
public class Purchase implements Serializable {

    @EmbeddedId
    private PurchaseID id;

    private int price;

    @Column(name = "subscription_date")
    private Date subDate;

    public int getPrice() {
        return price;
    }

    public PurchaseID getId() {
        return id;
    }

    public void setId(PurchaseID id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getSubDate() {
        return subDate;
    }

    public void setSubDate(Date subDate) {
        this.subDate = subDate;
    }

    @Override
    public String toString() {
        return getId().getStudentName() + " - " + getId().getCourseName()
                + " - " + new SimpleDateFormat("yyyy-MM-dd").format(subDate) + " - " + price + " руб";
    }
}
