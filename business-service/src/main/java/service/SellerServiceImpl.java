package service;

import model.Seller;
import repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerRepository sellerRepository;
    @Override
    public Iterable<Seller> findAll() {
        return sellerRepository.findAll();
    }

    @Override
    public Seller findSellerByUsername(String username) {
        return sellerRepository.findSellerByAccount_Username(username);
    }

    @Override
    public Seller findSellerByEmail(String email) {
        return sellerRepository.findSellerByAccount_Email(email);
    }

    @Override
    public Seller findSellerByCertificateCode(Long certificate) {
        return sellerRepository.findSellerByCertificateCode(certificate);
    }

    @Override
    public void save(Seller seller) {
        sellerRepository.save(seller);
    }
}
