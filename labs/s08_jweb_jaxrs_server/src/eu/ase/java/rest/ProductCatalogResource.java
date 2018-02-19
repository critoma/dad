package eu.ase.java.rest;

import java.util.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import eu.ase.java.bean.Product;
import eu.ase.java.bean.Status;

@Path("productcatalog")
public class ProductCatalogResource {
    private static List<Product> productCatalog = null;
    public ProductCatalogResource() {
        initializeProductCatalog();
    }
    @GET
    @Path("search/category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product[] searchByCategory(@PathParam("category") String category) {
        List<Product> products = new ArrayList<Product>();
        for (Product p : productCatalog) {
            if (category != null && category.equalsIgnoreCase(p.getCategory())) {
                products.add(p);
            }
        }
        return products.toArray(new Product[products.size()]);
        //return (Product[])products.toArray();
    }
    
    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Product[] searchByName(@QueryParam("name") String name) {
        List<Product> products = new ArrayList<Product>();
        for (Product p : productCatalog) {
        //for (Product p : (Product[])productCatalog.toArray()) {
            if (name != null && name.toLowerCase().startsWith(p.getName().toLowerCase())) {
                products.add(p);
            }
        }
        return products.toArray(new Product[products.size()]);
        //return (Product[])products.toArray();
    }
    
    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Status insert(Product product) {
        productCatalog.add(product);
        return new Status("SUCCESS", "Inserted " + product.getName());
    }
    private void initializeProductCatalog() {
        if (productCatalog == null) {
            productCatalog = new ArrayList<Product>();
            productCatalog.add(new Product(1, "Keyboard", "Electronics", 29.99D));
            productCatalog.add(new Product(2, "Mouse", "Electronics", 9.95D));
            productCatalog.add(new Product(3, "17\" Monitor", "Electronics", 159.49D));
            productCatalog.add(new Product(4, "Hammer", "Hardware", 9.95D));
            productCatalog.add(new Product(5, "Screwdriver", "Hardware", 7.95D));
            productCatalog.add(new Product(6, "English Dictionary", "Books", 11.39D));
            productCatalog.add(new Product(7, "A House in Bali", "Books", 15.99D));
            productCatalog.add(new Product(8, "An Alaskan Odyssey", "Books", 799.99D));
            productCatalog.add(new Product(9, "LCD Projector", "Electronics", 1199.19D));
            productCatalog.add(new Product(10, "Smart Thermostat", "Electronics", 1199.19D));
        }
    }
}

