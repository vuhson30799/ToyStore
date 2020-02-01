package repository;

import model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findSellerByAccount_Username(String username);
    Seller findSellerByAccount_Email(String email);
    Seller findSellerByCertificateCode(Long certificate);
}
