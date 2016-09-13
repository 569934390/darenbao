package com.compses.service.api.base;

import com.compses.dao.base.IBankCardInfoDao;
import com.compses.dao.base.IUserCardPwdDao;
import com.compses.dto.CashDetailsDto;
import com.compses.model.*;
import com.compses.service.api.statistics.BillRecordService;
import com.compses.service.common.BaseCommonService;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
@Service
@Transactional
public class BankCardInfoService extends BaseCommonService{
    @Autowired
    private IBankCardInfoDao bankCardInfoDao;
    @Autowired
    private IUserCardPwdDao userCardPwdDao;
    @Autowired
    private BillRecordService billRecordService;
    @Autowired
    private CashDetailsService cashDetailsService;

    public BankCardInfo save(BankCardInfo bankCardInfo){
        bankCardInfo.setCardId(UUIDUtils.getUUID());
        bankCardInfoDao.saveForUUid(bankCardInfo);
        return bankCardInfo;
    }

    public BankCardInfo update(BankCardInfo bankCardInfo){
        bankCardInfoDao.update(bankCardInfo);
        return bankCardInfo;
    }

    public List<BankCardInfo> getByUserId(String userId){
        BankCardInfo bankCardInfo = new BankCardInfo();
        bankCardInfo.setUserId(userId);
        List<BankCardInfo> result = bankCardInfoDao.loadData(bankCardInfo);
        List<BankCardInfo> res = new ArrayList<BankCardInfo>();
        BankCardInfo cardInfo = new BankCardInfo();
        cardInfo.setCardId(UUIDUtils.getUUID());
        cardInfo.setUserId(userId);
        cardInfo.setCardCategory("橙子银行");
        res.add(cardInfo);
        res.addAll(result);
        return res;
    }

    public BankCardInfo getById(String cardId){
        BankCardInfo bankCardInfo= new BankCardInfo();
        bankCardInfo.setCardId(cardId);
        bankCardInfo = bankCardInfoDao.selectOne(bankCardInfo);
        return bankCardInfo;
    }

    public void delete(String cardId){
        BankCardInfo bankCardInfo = new BankCardInfo();
        bankCardInfo.setCardId(cardId);
        bankCardInfoDao.delete(bankCardInfo);
    }

    public int updatePayPWD(String userId,String oldPwd,String pwd){
        UserCardPwd search = userCardPwdDao.getByUserId(userId);
        if(search==null){
            UserCardPwd userCardPwd = new UserCardPwd();
            userCardPwd.setUserId(userId);
            userCardPwd.setBankPwd(pwd);
            userCardPwd.setRelaId(UUIDUtils.getUUID());
            userCardPwdDao.saveForUUid(userCardPwd);
            return 1;
        }else {
            if (search.getBankPwd().equals(oldPwd)){
                search.setBankPwd(pwd);
                userCardPwdDao.update(search);
                return 1;
            }else{
                return 0;
            }
        }
    }

    public boolean Verify(String userId,String pwd){
        UserCardPwd userCardPwd = userCardPwdDao.getByUserId(userId);
        if (userCardPwd.getBankPwd().equals(pwd)){
            return true;
        }else {
            return false;
        }
    }

    public void forgetPWD(String userId,String pwd){
        UserCardPwd userCardPwd = userCardPwdDao.getByUserId(userId);
        userCardPwd.setBankPwd(pwd);
        userCardPwdDao.update(userCardPwd);
    }

    public boolean judgeExistPayPwd(String userId){
        UserCardPwd userCardPwd = userCardPwdDao.getByUserId(userId);
        if (userCardPwd!=null){
            return true;
        }else {
            return false;
        }
    }

    public CashDetailsDto getDetails(String recordId){
        CashDetailsDto cashDetailsDto = new CashDetailsDto();
        BillRecord billRecord = billRecordService.getById(recordId);
        String cardId = billRecord.getEnterCardId();
        BankCardInfo cardInfo = new BankCardInfo();
        cardInfo.setCardId(cardId);
        cardInfo = bankCardInfoDao.selectOne(cardInfo);
        cashDetailsDto.setDetailsStatus(billRecord.getOperationStatus());
        cashDetailsDto.setAmountPrice(billRecord.getSettlementMoney());
        cashDetailsDto.setCardNumber(cardInfo.getCardNumber());
        cashDetailsDto.setCardCategory(cardInfo.getCardCategory());
        List<CashDetails> cashDetailsList = cashDetailsService.listByConditions(billRecord.getOperationStatus());
        cashDetailsDto.setCashDetailsList(cashDetailsList);
        return cashDetailsDto;
    }


}
