package ra.model;

import java.io.Serializable;
import java.util.List;

import static ra.service.impl.CatalogServiceIMPL.catalogList;

public class Catalog implements Serializable {
    private int newID = 1;
    private int catalogId;
    private String catalogName;
    private String description;
    private boolean status = true;

    public Catalog() {
        if (catalogList.isEmpty()) {
            this.catalogId = 1;
        } else {
            this.catalogId = (catalogList.get(catalogList.size() - 1).getCatalogId()) + 1;
        }
    }

    public Catalog(int catalogId, String catalogName, String description, boolean status) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.description = description;
        this.status = status;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "catalogId=" + catalogId +
                ", catalogName='" + catalogName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + (status ? "Mở" : "Ẩn") +
                '}';
    }
}
