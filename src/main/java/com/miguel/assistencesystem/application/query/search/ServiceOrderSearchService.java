package com.miguel.assistencesystem.application.query.search;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.dto.summary.SoSummaryDTO;
import com.miguel.assistencesystem.domain.enums.ServiceOrderStatus;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.ServiceOrderNotFoundException;
import com.miguel.assistencesystem.domain.valueobjects.PageRequest;
import com.miguel.assistencesystem.infrastructure.persistence.ServiceOrderJpaDAO;



@Service
@Transactional(readOnly = true)
public class ServiceOrderSearchService {
	
	private final ServiceOrderJpaDAO orderDAO;
	
	public ServiceOrderSearchService(ServiceOrderJpaDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	
	public SoSummaryDTO searchById(Long id) {
		
            return orderDAO.findById(id)
                .map(SoSummaryDTO::fromEntity)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));  
	}
	
	public SoSummaryDTO searchByProtocol(String protocol) {

		return orderDAO.findByProtocolNumber(protocol)
			    .map(SoSummaryDTO::fromEntity)
			    .orElseThrow(() ->
			        new ServiceOrderNotFoundException(
			            "Service order not found for protocol: " + protocol
			        )
			    );
	}

	public List<SoSummaryDTO> searchByDateRange(
			LocalDateTime from,
			LocalDateTime to,
			int page,
			int pageSize) {
		
        	PageRequest pr = new PageRequest(page, pageSize);
        	
            return orderDAO.findByDateRange(from, to, pr).stream()
                .map(SoSummaryDTO::fromEntity)
                .toList();     
      
	}

	public List<SoSummaryDTO> searchByStatus(ServiceOrderStatus status, int page, int pageSize){

        	PageRequest pr = new PageRequest(page, pageSize);
        	
            return orderDAO.findByStatus(status, pr).stream()
                .map(SoSummaryDTO::fromEntity)
                .toList();     
	}

	/* Use: Client says product was serviced before but can't find ID stick now.
	   Tech sends stick pic, team check the info.*/
	
	public List<SoSummaryDTO> searchByProductSerialNumber(String serialNumber, int page, int pageSize){
		
        	PageRequest pr = new PageRequest(page, pageSize);
        	
            return orderDAO.findByProductSerialNumber(serialNumber, pr).stream()
                .map(SoSummaryDTO::fromEntity)
                .toList();     		
	}

}
