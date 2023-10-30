package ra.model;

import ra.model.Catalog;

import java.io.Serializable;

import static ra.service.impl.ProductServiceIMPL.productList;


public class Product implements Serializable {
    private static int newId = 1;
    private int productId;
    private String productName;
    private int categoryId;
    private String description;
    private double unitPrice;
    private int stock;
    private boolean status = true;
    private Catalog catalog;

    public Product() {
        if (productList.isEmpty()) {
            this.productId = 1;
        } else {
            this.productId = (productList.get(productList.size() - 1).getProductId()) + 1;
        }
    }

    public Product(int newId, int productId, String productName, int categoryId, String description, double unitPrice, int stock, boolean status, Catalog catalog) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.status = status;
        this.catalog = catalog;
    }

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", stock=" + stock +
                ", status=" + status +
                ", catalog=" + catalog.getCatalogName() +
                '}';
    }
}