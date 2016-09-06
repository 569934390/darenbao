package com.club.web.store.domain.repository;

import com.club.web.store.domain.SalesReturnReasonDo;


public interface SalesReturnReasonRepository {
	
    public int insert(SalesReturnReasonDo salesReturnReasonDo);
    
    public int update(SalesReturnReasonDo salesReturnReasonDo);
    
    public int delet(SalesReturnReasonDo salesReturnReasonDo);
    
    
	
}
