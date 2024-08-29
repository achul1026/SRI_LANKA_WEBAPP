package com.sri.lanka.traffic.webapp.common.dto.invst;

import java.util.List;

import com.sri.lanka.traffic.webapp.common.entity.TlSrvyAns;
import com.sri.lanka.traffic.webapp.common.entity.TlSrvyRslt;

import lombok.Data;

@Data
public class TlSrvyAnsSaveDTO {
	
	private TlSrvyRslt tlSrvyRslt;
	private List<TlSrvyAns> tlSrvyAnsList; 
}
