package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ServiceProviderRepository serviceProviderRepository;

    @Autowired
    CountryRepository countryRepository;

    @Override
    public Admin register(String username, String password) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);

        adminRepository.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        Admin admin = optionalAdmin.get();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setAdmin(admin);
        serviceProvider.setName(providerName);
        admin.getServiceProviders().add(serviceProvider);

        adminRepository.save(admin);
        return admin;
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{
        Optional<ServiceProvider> optionalServiceProvider = serviceProviderRepository.findById(serviceProviderId);
        ServiceProvider serviceProvider = optionalServiceProvider.get();

        Country country =new Country();
        country.setCountryName(CountryName.valueOf(countryName));
        country.setServiceProvider(serviceProvider);

        serviceProvider.getCountryList().add(country);

        serviceProviderRepository.save(serviceProvider);
        return serviceProvider;
    }
}
