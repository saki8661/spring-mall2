package shop.mtcoding.mall2.model;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository // 컴퍼넌트 스캔
public class ProductRepository {

    @Autowired //엔티티만 Autowired
    private EntityManager em;

    public ProductDTO findByIdDTO(int id){
        Query query = em.createNativeQuery("select id, name, price, qty, '설명' as des from product_tb where id = :id");
        query.setParameter("id", id);

        JpaResultMapper mapper = new JpaResultMapper();
        ProductDTO productDTO = mapper.uniqueResult(query, ProductDTO.class);
        //ProductDTO productDTO = (ProductDTO) query.getSingleResult();

        return productDTO;
    }

    @Transactional
    public void save(String name, int price, int qty){
        Query query = em.createNativeQuery("insert into product_tb(name, price, qty) values(:name, :price, :qty)");
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.executeUpdate();
    }

    public List<Product> findAll() {
        Query query = em.createNativeQuery("select * from product_tb", Product.class);
        List<Product> productList = query.getResultList();
        return productList;
    }

    public Product findById(int id) {
        Query query = em.createNativeQuery("select * from product_tb where id = :id", Product.class);
        query.setParameter("id", id);
        Product product = (Product) query.getSingleResult();
        return product;
    }

    public Product findById2(int id) {
        Query query = em.createNativeQuery("select * from product_tb where id = :id", Product.class);
        query.setParameter("id", id);
        // row가 1건
        // 1, 바나나, 1000, 50
        Object[] object = (Object[]) query.getSingleResult();
        int id2 = (int) object[0];
        String name2 = (String) object[1];
        int price2 = (int) object[2];
        int qty2 = (int) object[3];

        Product product = new Product();
        product.setId(id2);
        product.setName(name2);
        product.setPrice(price2);
        product.setQty(qty2);
        return product;
    }
    @Transactional
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from product_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public void updateById(int id, String name, int price, int qty) {
        Query query = em.createNativeQuery("update product_tb set name =:name, qty =:qty, price =:price where id = :id");
        query.setParameter("id", id);
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.executeUpdate();
    }
//    public ProductDTO finByTest(int id){
//        Query query = em.createNativeQuery("select id, name, price, qty price*qty from producet_tb where");
//        query.setParameter("id", id);
//        List<Tuple> result = query.getResultList();
//        Tuple tuple = result.get(0);
//        ProductDTO productDTO = new ProductDTO(
//                tuple.get("id", Integer.class),
//                tuple.get("name", String.class),
//                tuple.get("price", Integer.class),
//                tuple.get("qty", Integer.class),
//                tuple.get("total", BigInteger.class)
//        );
//        return productDTO;
//    }
}












