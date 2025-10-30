package md.donesk.trendshop.jwt;

import lombok.AllArgsConstructor;
import md.donesk.trendshop.model.Customer;
import md.donesk.trendshop.repository.CustomerRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return UserDetailsImpl.build(customer);
    }
}
