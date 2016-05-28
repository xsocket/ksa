package com.ksa.system.initialize.convert;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.bd.BasicDataDao;
import com.ksa.model.bd.BasicData;
import com.ksa.model.bd.BasicDataType;
import com.ksa.model.bd.Partner;
import com.ksa.model.security.User;
import com.ksa.service.bd.PartnerService;
import com.ksa.system.initialize.model.KeHu;
import com.ksa.util.StringUtils;

public class PartnerConverter {
    
    // 供别的转换器使用的 合作伙伴缓存
    private static Map<String, Partner> partnerMap = new HashMap<String, Partner>( 2000 );
    public static Map<String, Partner> getPartnerMap() {
        return partnerMap;
    }

    public static void doConvert( SqlSession session ) {
        Collection<KeHu> list = getAllKeHu( session );
        Map<String, BasicData> map = getDepartmentMap();
        partnerMap.clear();
        PartnerService service = ServiceContextUtils.getService( PartnerService.class );
        
        User saler = new User();
        saler.setId( "" );
        // 保存用户
        for( KeHu kehu : list ) {
            Partner partner = new Partner();
            partner.setCode( kehu.getDaiMa() );
            partner.setName( kehu.getZhongWMC() );
            partner.setAlias( StringUtils.hasText( kehu.getTiDan() ) ? kehu.getTiDan()  : "" );
            partner.setAddress( StringUtils.hasText( kehu.getZhongWDZ() ) ? kehu.getZhongWDZ()  : "" );
            partner.setImportant( kehu.isChangYong() ? 1 : 0 );
            partner.setNote( StringUtils.hasText( kehu.getBeiZhu() ) ? kehu.getBeiZhu()  : "" );
            partner.setSaler( saler );
            if( StringUtils.hasText(  kehu.getFuKuanZQ() ) ) {
                String zhouqi = kehu.getFuKuanZQ();
                int index = zhouqi.indexOf( "天付款" );
                if( index >= 0  ) {
                    try {
                        partner.setPp( Integer.parseInt( zhouqi.substring( 0, index ) ) );
                    } catch(NumberFormatException e ) { }
                }
            }
            
            // 加入客户类型
            if( StringUtils.hasText( kehu.getLeiXing() ) && map.containsKey( kehu.getLeiXing() ) ) {
                partner.setTypeIds( new String[] { map.get( kehu.getLeiXing() ).getId() } );
            }
            service.createPartner( partner );
            partnerMap.put( partner.getCode(), partner );   // 以原表主键 Daima（现表字段Code）来记录
            if( StringUtils.hasText( partner.getName() ) && !partnerMap.containsKey( partner.getName() ) ) {
                partnerMap.put( partner.getName(), partner);
            }
            if( StringUtils.hasText( kehu.getZhongWJC() ) && !partnerMap.containsKey( kehu.getZhongWJC() ) ) {
                partnerMap.put( kehu.getZhongWJC(), partner);
            }
        }
    }
    
    public static Partner getPartner(String name) {
        return partnerMap.get( name );
    }
    
    // 获取关联来往单位类型的数据
    private static Map<String, BasicData> getDepartmentMap() {
        List<BasicData> datas = ServiceContextUtils.getService( BasicDataDao.class ).selectBasicDataByType( BasicDataType.DEPARTMENT.getId() );
        Map<String, BasicData> map = new HashMap<String, BasicData>();
        for(BasicData d : datas) {
            map.put( d.getName(), d );
        }
        return map;
    }

    private static Collection<KeHu> getAllKeHu( SqlSession session ) {
        List<KeHu> kehuList = session.selectList( "select-init-kehu" );
        // 客户 和 来往单位表有重复数据，按 DaiMa 列合并
        Map<String, KeHu> map = new HashMap<String, KeHu>();
        for(KeHu kehu : kehuList) {
            // DaiMa 是主键，在同一个表中不会重复
            map.put( kehu.getDaiMa(), kehu );
        }

        List<KeHu> danWeiList = session.selectList( "select-init-laiwdw" );
        for(KeHu danWei : danWeiList ) {
            // 重复的单位只记录其类型信息
            if( map.containsKey( danWei.getDaiMa() ) ) {
                map.get( danWei.getDaiMa() ).setLeiXing( danWei.getLeiXing() );
            } else {
                map.put( danWei.getDaiMa(), danWei );
            }
        }
        
        return map.values();
    }


}
