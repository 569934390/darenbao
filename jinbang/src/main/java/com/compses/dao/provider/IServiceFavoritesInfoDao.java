package com.compses.dao.provider;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dto.Page;
import com.compses.dto.ServiceFavoritesInfoDto;
import com.compses.model.ServiceFavoritesInfo;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public interface IServiceFavoritesInfoDao extends IBaseCommonDAO{

    public void deleteFavoriteService(ServiceFavoritesInfo serviceFavoritesInfo);

    Page<ServiceFavoritesInfoDto> listForPage(String userId,Page<ServiceFavoritesInfoDto> page);
}
