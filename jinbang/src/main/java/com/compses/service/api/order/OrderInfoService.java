package com.compses.service.api.order;

import com.compses.constants.OperationCategoryConsants;
import com.compses.constants.OrderConstants;
import com.compses.dao.order.IOrderInfoDao;
import com.compses.dto.*;
import com.compses.dto.UserInfo;
import com.compses.model.*;
import com.compses.redis.util.*;
import com.compses.service.api.base.AgentInfoService;
import com.compses.service.api.base.MobileUserInfoService;
import com.compses.service.api.base.UserRelaService;
import com.compses.service.api.provider.ServiceInfoService;
import com.compses.service.api.statistics.BillRecordService;
import com.compses.service.api.statistics.StatisticsSercive;
import com.compses.service.api.statistics.TotalUserBillService;
import com.compses.service.api.system.DictService;
import com.compses.service.api.system.UserInfoService;
import com.compses.service.common.BaseCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.SystemUtil;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jocelynsuebb on 16/8/1.
 */
@Service
@Transactional
public class OrderInfoService extends BaseCommonService {

    @Autowired
    private IOrderInfoDao orderInfoDao;
    @Autowired
    private ServiceInfoService serviceInfoService;
    @Autowired
    private AgentInfoService agentInfoService;
    @Autowired
    private MobileUserInfoService mobileUserInfoService;
    @Autowired
    private StatisticsSercive statisticsSercive;
    @Autowired
    private BillRecordService billRecordService;
    @Autowired
    private TotalUserBillService totalUserBillService;

    public OrderInfo save(OrderInfo orderInfo){
        orderInfo.setOrderCode(SystemUtil.makeOrderCode(10000));
        orderInfo.setBuyNum(0);
        orderInfo.setOrderStatus(OrderConstants.ORDER_CATEGORY_MEMBER);
        orderInfo.setOrderDate(new Date());
        //计算限制时间
        long currentTime = System.currentTimeMillis() ;
        currentTime +=30*60*1000;
        Date date=new Date(currentTime);
        orderInfo.setValidityPeriod(date);
        ServiceInfo serviceInfo = serviceInfoService.getById(orderInfo.getServiceId());
        orderInfo.setNativeCityName(serviceInfo.getServiceCityName());
        orderInfo.setOrderCityId(serviceInfo.getServiceCityId());
        orderInfo.setOrderType(OrderConstants.ORDER_CATEGORY_ORANGE);
        orderInfo.setOrderId(UUIDUtils.getUUID());
//        //品牌运营官
//        MobileUserInfo seller = mobileUserInfoService.getById(serviceInfo.getPublisherId());
//        AgentInfo bandZoneAgent = agentInfoService.getById(seller.getBrandAgentId());
//        orderInfo.setNativeBandId(seller.getBrandAgentId());
//        orderInfo.setNativeBandName(bandZoneAgent.getAgentName());
//        //地区运营官
//        AgentInfo areaZoneAgent = agentInfoService.getById(userRela.getAreaZoneAgentid());
//        orderInfo.setNativeCityId(userRela.getAreaZoneAgentid());
//        orderInfo.setNativeCityName(areaZoneAgent.getAgentName());
//        //中国区运营官
//        AgentInfo chinaZoneAgent = agentInfoService.getById(userRela.getChinaZoneAgentid());
//        orderInfo.setNativeTopId(userRela.getChinaZoneAgentid());
//        orderInfo.setNativeTopName(chinaZoneAgent.getAgentName());
        orderInfo.setServicePrice(orderInfo.getOrderPrice());
        orderInfoDao.saveForUUid(orderInfo);
        return orderInfo;
    }

    public Page<OrderInfo> findByUserIdAndStatus(OrderInfo orderInfo,Page<OrderInfo> page){
        Page<OrderInfo> userOrders = orderInfoDao.findUserOrdersByPage(orderInfo, page);
        for (OrderInfo order : userOrders.getResult()){
            GeoCoordinate geoCoordinate = RedisGeoUtil.geoGet(order.getPickerId());
            if (geoCoordinate!=null){
                order.setLat(geoCoordinate.getLatitude());
                order.setLng(geoCoordinate.getLongitude());
            }
            long validityDate = order.getValidityPeriod().getTime();
            long now = (new Date()).getTime();
            if (order.getOrderStatus().equals(OrderConstants.ORDER_STATUS_WAITPAY) &&  (validityDate - now)<=0){
                order.setOrderStatus(OrderConstants.ORDER_STATUS_CANCEL_BD);
                order.setCancelReason("订单已超时");
                order.setOrderEndDate(new Date());
                this.update(order);
            }
        }
        return userOrders;
    }

    public void updateRefundOrder(OrderInfo orderInfo){
        OrderInfo old = this.getOrderById(orderInfo);
        orderInfo.setOrderPrice(old.getOrderPrice());
        //修改订单信息
        update(orderInfo);
        //修改交易记录
        BillRecord billRecord = new BillRecord();
        billRecord.setOrderCode(old.getOrderCode());
        billRecord.setTradingAmount(old.getOrderPrice());
        billRecord.setTradingDate(old.getOrderDate());
        billRecord.setReceivingUserId(old.getPublisherId());
        billRecord.setPayUserId(OrderConstants.PLATFORM_ID);
        billRecord.setRoleType(OrderConstants.BENEFIT_TYPE_USER);
        billRecord.setOperationCategory(OperationCategoryConsants.OPERATION_CATEGORY_REFUND);
        billRecord.setOperationStatus(OrderConstants.EXECUTION_STATUS_ACCEPTED);
        billRecordService.save(billRecord);
        //修改余额
        TotalUserBill totalUserBill = totalUserBillService.getByUserId(old.getPublisherId());
        totalUserBill.setBalance(totalUserBill.getBalance()+old.getOrderPrice());
        totalUserBillService.update(totalUserBill);
    }


    public void update(OrderInfo orderInfo){
        OrderInfo old = this.getOrderById(orderInfo);
        orderInfo.setOrderPrice(old.getOrderPrice());
        if (orderInfo.getOrderStatus().equals(OrderConstants.ORDER_STATUS_SERVING)){
            orderInfo.setPayDate(new Date());
            orderInfo.setServiceStartDate(new Date());
        }else if (orderInfo.getOrderStatus().equals(OrderConstants.ORDER_STATUS_WAITCOMMENT)){
            ServiceInfo serviceInfo = serviceInfoService.getById(orderInfo.getServiceId());
            //修改购买数
            serviceInfo.setBuyNum(serviceInfo.getBuyNum()+1);
            serviceInfoService.update(serviceInfo);
            orderInfo.setServiceEndDate(new Date());
            //存入reids
            String serviceJson = JsonUtils.toJson(serviceInfo);
            RedisHashSetUtil.hset(orderInfo.getPickerId(), serviceInfo.getServiceCategoryName(), serviceJson);
            //分红计算
            orderInfoDao.update(orderInfo);
            orderInfo = getOrderById(orderInfo);
            statisticsSercive.updateStatisticsOrder(orderInfo);
        }else if (orderInfo.getOrderStatus().equals(OrderConstants.ORDER_STATUS_END)){
            orderInfo.setOrderEndDate(new Date());
        }
        if (!orderInfo.getOrderStatus().equals(OrderConstants.ORDER_STATUS_WAITCOMMENT)){
            orderInfoDao.update(orderInfo);
        }
        orderInfo.setServicePrice(orderInfo.getOrderPrice());
    }


    public OrderInfo getOrderById(OrderInfo orderInfo ){
        OrderInfo order = orderInfoDao.selectOne(orderInfo);
        return order;
    }

    public OrderInfo getByOrderCode(String orderCode){
        OrderInfo orderInfo = orderInfoDao.getByOrderCode(orderCode);
        return orderInfo;
    }



}
