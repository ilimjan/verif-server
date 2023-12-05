package ru.tkoinform.ppkverificationserver.service;

import com.kuliginstepan.dadata.client.DadataClient;
import com.kuliginstepan.dadata.client.domain.Suggestion;
import com.kuliginstepan.dadata.client.domain.address.Address;
import com.kuliginstepan.dadata.client.domain.address.AddressRequestBuilder;
import com.kuliginstepan.dadata.client.domain.address.GeolocateRequest;
import com.kuliginstepan.dadata.client.domain.organization.Organization;
import com.kuliginstepan.dadata.client.domain.organization.OrganizationRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tkoinform.ppkverificationserver.dto.FiasAddressDto;
import ru.tkoinform.ppkverificationserver.dto.FnsDto;
import ru.tkoinform.ppkverificationserver.mapping.GlobalMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
public class FiasService {

    @Value("${dadata.client.timeout}")
    private Integer timeout;

    @Autowired
    private DadataClient client;

    @Autowired
    private GlobalMapper globalMapper;

    public List<FiasAddressDto> findAddressesByQuery(String query) {


        List<Suggestion<Address>> suggestions = client.suggestAddress(
                AddressRequestBuilder.create(query).build()
        ).collectList().block();

        List<FiasAddressDto> addresses = new ArrayList<FiasAddressDto>();
        if (suggestions != null && !suggestions.isEmpty()) {
            for (Suggestion suggestion : suggestions) {
                addresses.add(globalMapper.map((Address) suggestion.getData(), FiasAddressDto.class));
            }
        }

        return addresses;
    }

    public List<FiasAddressDto> findAddressesByGeo(Float latitude, Float longitude) {
        GeolocateRequest geolocateRequest = new GeolocateRequest(latitude, longitude);
        List<Suggestion<Address>> suggestions = client.geolocate(geolocateRequest).collectList().block();

        List<FiasAddressDto> addresses = new ArrayList<FiasAddressDto>();
        if (suggestions != null && !suggestions.isEmpty()) {
            for (Suggestion suggestion : suggestions) {
                addresses.add(globalMapper.map((Address) suggestion.getData(), FiasAddressDto.class));
            }
        }

        return addresses;
    }

    public FnsDto findFnsEntityByTin(String inn) {
        List<Suggestion<Organization>> suggestions = client
                .suggestOrganization(OrganizationRequestBuilder.create(inn).build()).collectList().block();

        if (suggestions != null && !suggestions.isEmpty()) {
            Organization organization = suggestions.get(0).getData();

            FnsDto fnsDto = new FnsDto();
            fnsDto.setInn(organization.getInn());
            fnsDto.setKpp(organization.getKpp());
            fnsDto.setOgrn(organization.getOgrn());
            fnsDto.setName(organization.getName().getFullWithOpf());

            if (organization.getAddress() != null) {
                fnsDto.setActualAddress(organization.getAddress().getValue());
                fnsDto.setLegalAddress(organization.getAddress().getValue());
            }

            if (organization.getEmails() != null) {
                StringJoiner joiner = new StringJoiner(", ");
                for (String email : organization.getEmails()) {
                    joiner.add(email);
                }
                fnsDto.setEmail(joiner.toString());
            }

            if (organization.getPhones() != null) {
                StringJoiner joiner = new StringJoiner(", ");
                for (String phone : organization.getPhones()) {
                    joiner.add(phone);
                }
                fnsDto.setPhone(joiner.toString());
            }

            return fnsDto;
        }

        return null;
    }
}
