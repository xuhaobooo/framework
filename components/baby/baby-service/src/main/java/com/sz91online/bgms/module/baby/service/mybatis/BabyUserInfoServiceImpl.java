package com.sz91online.bgms.module.baby.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sz91online.bgms.module.baby.dao.BabyUserInfoMapper;
import com.sz91online.bgms.module.baby.dao.BabyUserInfoMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyInvite;
import com.sz91online.bgms.module.baby.domain.BabyUserInfo;
import com.sz91online.bgms.module.baby.domain.SimpleBabyUserInfo;
import com.sz91online.bgms.module.baby.exceptions.EBabyException;
import com.sz91online.bgms.module.baby.service.BabyInviteService;
import com.sz91online.bgms.module.baby.service.BabyUserInfoService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;
import com.sz91online.common.utils.PlStringUtils;

@Service
public class BabyUserInfoServiceImpl extends DefaultSearchService<BabyUserInfo> implements BabyUserInfoService {

	@Autowired
	private BabyUserInfoMapper babyUserInfoMapper;

	@Autowired
	private BabyUserInfoMapperExt babyUserInfoMapperExt;

	@Autowired
	private BabyInviteService inviteService;

	@Override
	public ISearchableDAO getSearchMapper() {
		return babyUserInfoMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyUserInfo> getCrudMapper() {
		return babyUserInfoMapper;
	}

	@Override
	@Transactional
	public BabyUserInfo addUserInfo(SimpleBabyUserInfo userInfo) {

		BabyUserInfo saveBean = new BabyUserInfo();
		saveBean.setAddrName(userInfo.getAddrName());
		saveBean.setNote(userInfo.getNote());
		saveBean.setAddrPosX(userInfo.getAddrPosX());
		saveBean.setAddrPosY(userInfo.getAddrPosY());
		saveBean.setTel(userInfo.getTel());
		saveBean.setUserCode(userInfo.getUserCode());
		saveBean.setUserRole(userInfo.getUserRole());
		saveBean.setUserName(userInfo.getUserName());
		this.saveWithSession(saveBean, "RegistSystem");

		if (!PlStringUtils.isEmpty(userInfo.getVisitCode())) {
			// 添加邀请
			BabyInvite invite = new BabyInvite();
			invite.setUserCode(userInfo.getUserCode());
			BabyInvite resultBean = inviteService.findOne(invite);
			if (resultBean == null) {
				
				BabyUserInfo userQueryBean = new BabyUserInfo();
				userQueryBean.setTel(userInfo.getVisitCode());
				BabyUserInfo userResultBean = this.findOne(userQueryBean);
				if(userResultBean != null) {
					invite.setEnable(true);
					invite.setInviteUserCode(userResultBean.getUserCode());
					inviteService.saveWithSession(invite, "RegistSystem");
				}
			}
		}

		return saveBean;
	}

	@Override
	public SimpleBabyUserInfo findRequireApplySucc(String requireCode) {
		return babyUserInfoMapperExt.findRequireApplySucc(requireCode);
	}

}
