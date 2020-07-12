package com.mtons.mblog.service.task.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Sets;
import com.mtons.mblog.bo.AttackIpBo;
import com.mtons.mblog.bo.ViewLogBo;
import com.mtons.mblog.entity.bao.ViewLogEntry;
import com.mtons.mblog.service.AbstractService;
import com.mtons.mblog.service.atom.jpa.AttackIpService;
import com.mtons.mblog.service.atom.bao.ViewLogService;
import com.mtons.mblog.service.comp.IAnalyzeService;
import com.mtons.mblog.service.comp.config.impl.DiamondConfigGetServiceImpl;
import com.yueny.rapid.lang.date.DateTimeUtil;
import com.yueny.rapid.lang.util.time.DurationTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.Set;

/**
 * 发现黑名单任务， attackIpService.save 暂未生效
 *
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-08 10:57
 */
//@Service
public class FindBlackIpTask extends AbstractService implements ISecurityAction {
    @Autowired
    private ViewLogService viewLogService;
    @Autowired
    private IAnalyzeService analyzeService;
    @Autowired
    private AttackIpService attackIpService;

    /**
     * 黑名单ip发现与增加
     * 每隔 1 min 执行一次， 寻找过去一小时存在的攻击行为
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void add(){
        logger.info("黑名单ip发现与增加开始执行，执行时间:{}.", new Date());
        DurationTimer timer = new DurationTimer();

        try{
//            IPage<ViewLogEntry> page = new Page<>(0, 50);
            Pageable pageable = PageRequest.of(0, 50);
            LambdaQueryWrapper<ViewLogEntry> queryWrapper = assemblyQueryCondition();
            Page<ViewLogBo> pageResult = viewLogService.findAll(pageable, queryWrapper);

            Set<String> attackIps = Sets.newHashSet();
            if(pageResult.getContent().isEmpty()){
                logger.info("一小时内未发现攻击行为.");
            }else{
                attackIps.addAll(computer(pageResult, queryWrapper));
            }

            if(!attackIps.isEmpty()){
                DiamondConfigGetServiceImpl.getSecurityIpWhite().forEach(ip->{
                    attackIps.remove(ip);
                });
            }
            if(!attackIps.isEmpty()){
                logger.info("一小时内发现攻击行为：{} 条， ip 明细：{}.", attackIps.size(), attackIps);

                for (String attackIp : attackIps) {
                    AttackIpBo attackIpBo = new AttackIpBo();
                    attackIpBo.setClientIp(attackIp);
                    attackIpService.saveIfAbsent(attackIpBo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        logger.info("黑名单ip发现与增加执行结束，耗时时间:{} ms.", timer.duration());
    }

    /**
     * 返回此次加入黑名单的 Ip
     *
     * @param pageResult
     * @return
     */
    private Set<String> computer(Page<ViewLogBo> pageResult, LambdaQueryWrapper<ViewLogEntry> queryWrapper){
        Set<String> attackIps = Sets.newHashSet();

        computer(pageResult, attackIps);
        while(((Page) pageResult).hasNext()){
            Pageable pageable = PageRequest.of(pageResult.getNumber()+1, pageResult.getSize());
//            IPage<ViewLogEntry> page = new Page<>(pageResult.getCurrent()+1, pageResult.getSize());
            pageResult = viewLogService.findAll(pageable, queryWrapper);
            computer(pageResult, attackIps);
        }

        return attackIps;
    }

    private void computer(Page<ViewLogBo> pageResult, Set<String> attackIps){
        for (ViewLogBo entry: pageResult.getContent()) {
            if(analyzeService.isAttackUrl(entry.getResourcePath())){
                attackIps.add(entry.getClientIp());
            }
        }
    }

    /**
     * 组装查询条件
     *
     * @return
     */
    private LambdaQueryWrapper<ViewLogEntry> assemblyQueryCondition(){
        Date history = DateTimeUtil.minusMinutes(new Date(), 60);
        LambdaQueryWrapper<ViewLogEntry> queryWrapper = new QueryWrapper<ViewLogEntry>().lambda();
        //当日查询条件
        //queryWrapper.apply("date_format(column,'%Y-%m-%d') = '2008-08-08'");
        queryWrapper.ge(ViewLogEntry::getCreated, history);  // >= history
        // 已经在黑名单的ip不参与查询
//        queryWrapper.notIn(ViewLogEntry::getClientIp, null);

        return queryWrapper;
    }

    /**
     * 黑名单ip移除
     * 每天中午12点触发
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void refresh(){
        logger.info("黑名单梳理开始执行，执行时间:{}.", new Date());

//        attackIpService.findByIp()
    }
}
