package ra.service.impl;

import ra.config.WriteReadFile;
import ra.model.Catalog;
import ra.model.Product;
import ra.service.ICatalogService;

import java.util.ArrayList;
import java.util.List;

public class CatalogServiceIMPL implements ICatalogService {
    static WriteReadFile<List<Catalog>> writeReadFile = new WriteReadFile<List<Catalog>>();
    public static List<Catalog> catalogList;

    static {
        catalogList = writeReadFile.readFile(WriteReadFile.PATH_CATALOG);
        catalogList = (catalogList == null) ? new ArrayList<>() : catalogList;
    }

    @Override
    public List<Catalog> findAll() {
        return catalogList;
    }

    @Override
    public void save(Catalog catalog) {
        catalogList.add(catalog);
        updateData();
    }

    @Override
    public void update(Catalog catalog) {
        Catalog catalogEdit = findByID(catalog.getCatalogId());
        catalogEdit.setCatalogName(catalog.getCatalogName());
        catalogEdit.setDescription(catalog.getDescription());
        updateData();
    }

    @Override
    public List<Product> delete(int id) {
        Catalog catalogDelete = findByID(id);
        catalogList.remove(catalogDelete);
        updateData();
        return null;
    }

    @Override
    public Catalog findByID(int id) {
        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogId() == id) {
                return catalog;
            }
        }
        return null;
    }

    @Override
    public void updateData() {
        writeReadFile.writeFile(WriteReadFile.PATH_CATALOG, findAll());
    }

    @Override
    public int getNewId() {
        return 0;
    }

}
