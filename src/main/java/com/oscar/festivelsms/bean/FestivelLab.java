package com.oscar.festivelsms.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class FestivelLab {
    public static FestivelLab mInstance;

    private List<Festivel> mFestivels = new ArrayList<Festivel>();

    private List<Msg> mMsgs = new ArrayList<Msg>();

    private FestivelLab() {
        mFestivels.add(new Festivel(1, "国庆节"));
        mFestivels.add(new Festivel(2, "儿童节"));
        mFestivels.add(new Festivel(3, "元旦"));
        mFestivels.add(new Festivel(4, "春秋"));
        mFestivels.add(new Festivel(5, "端午"));
        mFestivels.add(new Festivel(6, "圣诞节"));
        mFestivels.add(new Festivel(7, "七夕"));
        mFestivels.add(new Festivel(8, "母亲节"));

        mMsgs.add(new Msg(1, 1, "月光静悄悄，秋阳黄橙橙;灯笼红彤彤，佳节喜盈盈;心情美滋滋，祝福乐淘淘。国庆佳节，问候送到，愿你幸福，一切都好!"));
        mMsgs.add(new Msg(2, 1, "国富安康和谐家，中华盛世平天下，年年度度国庆日，时时刻刻喜庆时，分分秒秒送祝愿，全家欢乐无边，全天幸福欢颜，全民国庆快乐!"));
        mMsgs.add(new Msg(3, 1, "国庆的祝福最“十”在：“十”指相扣心中所爱，朋友亲人“十”意相待，前程似海“十”破天开，永远没有“十”面伏埋。国庆快乐!"));
        mMsgs.add(new Msg(4, 1, "国庆节到了，我代表包中的手机，还有网络中的留言，还有我聪明的头脑，还有我灵活的手指，再加一毛人民币，提“钱”为你送上这美好的祝愿，愿你黄金周游玩乐翻天!"));
        mMsgs.add(new Msg(5, 1, "国庆节快到了，买辆疾驰送你——太贵;请你出国旅游——铺张;约你海吃一顿——伤胃;送你一枝玫瑰——误会;给你一个热吻——分歧错误;只好短信祝你快乐——实惠!"));
        mMsgs.add(new Msg(6, 1, "佳节思亲，假日思友，思念之情在心头。真情如酒，蓦然回首，一声问候醇绵依旧。岁月相伴，友情相守，人生四季与你携手。国庆节，祝你快乐到永久!"));
        mMsgs.add(new Msg(7, 1, "锦瑟拨弦曲悠扬，牧笛迎风音绵长。玉宇澄清百花开，盛世太平千家芳。长城内外赤旗展，大江南北紫气翔。国庆良辰祝吉祥，万般如意伴君旁。"));
        mMsgs.add(new Msg(8, 1, "国庆佳节到，祝福来报到，盛放的灿烂礼花是我对你的祝福，祝你青春绽放生活倍儿棒;飘荡的缤纷彩带是我对你的祝愿，愿你梦想飞翔快乐无疆!"));
    }

    public static FestivelLab getInstance() {
        if(mInstance == null) {
            synchronized (FestivelLab.class) {
                if(mInstance == null) {
                    mInstance = new FestivelLab();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取所有节日
     * @return
     */
    public List<Festivel> getFestivels() {
        return new ArrayList<Festivel>(mFestivels);
    }

    /**
     * 通过节日id得到单个节日
     * @param fesId
     * @return
     */
    public Festivel getFestivelById(int fesId) {
        for(Festivel festivel : mFestivels) {
            if(festivel.getId() == fesId) {
                return festivel;
            }
        }
        return null;
    }

    /**
     * 得到所有短信
     * @param fesId
     * @return
     */
    public List<Msg> getMsgsByFestivelId(int fesId) {
        List<Msg> msgs = new ArrayList<Msg>();
        for(Msg msg : mMsgs) {
            if(msg.getFesId() == fesId) {
                msgs.add(msg);
            }

        }
        return msgs;
    }

    /**
     * 通过短信id得到单个短信
     * @param id
     * @return
     */
    public Msg getMsgById(int id) {
        for (Msg msg : mMsgs) {
            if(msg.getId() == id) {
                return msg;
            }
        }
        return null;
    }
}
