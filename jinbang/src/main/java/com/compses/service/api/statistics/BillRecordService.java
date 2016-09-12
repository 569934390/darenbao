package com.compses.service.api.statistics;

import com.compses.constants.OperationCategoryConsants;
import com.compses.constants.OrderConstants;
import com.compses.dao.base.IUserCardPwdDao;
import com.compses.dao.statistics.IBillRecordDao;
import com.compses.model.BillRecord;
import com.compses.model.TotalUserBill;
import com.compses.model.UserCardPwd;
import com.compses.service.api.base.BankCardInfoService;
import com.compses.service.common.BaseCommonService;
import com.compses.util.StringUtils;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
@Service
@Transactional
public class BillRecordService extends BaseCommonService{
    @Autowired
    private IBillRecordDao billRecordDao;
    @Autowired
    private IUserCardPwdDao userCardPwdDao;
    @Autowired
    private TotalUserBillService totalUserBillService;


    public int addCash(BillRecord billRecord,String pwd){
        //验证密码
        UserCardPwd userCardPwd = userCardPwdDao.getByUserId(billRecord.getReceivingUserId());
        if (userCardPwd.getBankPwd().equals(pwd)){
            String userId = billRecord.getReceivingUserId();
            TotalUserBill userBill = totalUserBillService.getByUserId(userId);
            if (userBill.getBalance()-billRecord.getTradingAmount()<0){
                return -1;
            }
            //提现操作
            billRecord.setOperationCategory(OperationCategoryConsants.OPERATION_CATEGORY_CASH);
            billRecord.setOperationStatus(OrderConstants.EXECUTION_STATUS_ACCEPTING);
            billRecord.setTradingDate(new Date());
            save(billRecord);
            return 1;
        }else {
            return 0;
        }
    }

    public void applyCash(BillRecord billRecord){
        //修改交易记录信息
        billRecord.setOperationStatus(OrderConstants.EXECUTION_STATUS_ACCEPTED);
        billRecordDao.update(billRecord);
        //修改余额
        String userId = billRecord.getReceivingUserId();
        TotalUserBill userBill = totalUserBillService.getByUserId(userId);
        userBill.setBalance(userBill.getBalance()-billRecord.getTradingAmount());
        totalUserBillService.update(userBill);
    }

    public void save(BillRecord billRecord){
        billRecord.setRecordId(UUIDUtils.getUUID());
        billRecordDao.saveForUUid(billRecord);
    }

    public List<BillRecord> listByCondition(String userId,String operationCategory){
        BillRecord search = new BillRecord();
        search.setReceivingUserId(userId);
        if (!StringUtils.isEmpty(operationCategory)){
            search.setOperationCategory(operationCategory);
        }
        List<BillRecord> result = loadData(search);
        return result;
    }

    public List<BillRecord> listAllRecord(String userId){
        BillRecord search = new BillRecord();
        search.setPayUserId(userId);
        search.setReceivingUserId(userId);
        List<BillRecord> result = billRecordDao.listAllRecord(search);
        return result;
    }

    public BillRecord getById(String id){
        BillRecord billRecord = new BillRecord();
        billRecord.setRecordId(id);
        billRecord= billRecordDao.selectOne(billRecord);
        return billRecord;
    }




}
