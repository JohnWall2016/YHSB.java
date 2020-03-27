package cn.yhsb.cjb.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import cn.yhsb.base.exception.BaseException;
import cn.yhsb.base.exception.OperationException;
import cn.yhsb.cjb.Configs;

/**
 * Jzfp2020
 */
public class Jzfp2020 {
    /** 扶贫数据表 */
    @MappedSuperclass
    public static abstract class FPData {
        /** 序号 */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int NO;

        /** 乡镇街 */
        private String xzj;

        /** 村社区 */
        private String csq;

        /** 地址 */
        private String address;

        /** 姓名 */
        private String name;

        /** 身份证号码 */
        private String idcard;

        /** 出生日期 */
        private String birthDay;

        /** 贫困人口 */
        private String pkrk;

        /** 贫困人口日期 */
        private String pkrkDate;

        /** 特困人员 */
        private String tkry;

        /** 特困人员日期 */
        private String tkryDate;

        /** 全额低保人员 */
        private String qedb;

        /** 全额低保人员日期 */
        private String qedbDate;

        /** 差额低保人员 */
        private String cedb;

        /** 差额低保人员日期 */
        private String cedbDate;

        /** 一二级残疾人员 */
        private String yejc;

        /** 一二级残疾人员日期 */
        private String yejcDate;

        /** 三四级残疾人员 */
        private String ssjc;

        /** 三四级残疾人员日期 */
        private String ssjcDate;

        /** 属于贫困人员 */
        private String sypkry;

        /** 居保认定身份 */
        private String jbrdsf;

        /** 居保认定身份最初日期 */
        private String jbrdsfFirstDate;

        /** 居保认定身份最后日期 */
        private String jbrdsfLastDate;

        /** 居保参保情况 */
        private String jbcbqk;

        /** 居保参保情况日期 */
        private String jbcbqkDate;

        public int getNO() {
            return NO;
        }

        public void setNO(int nO) {
            NO = nO;
        }

        public String getXzj() {
            return xzj;
        }

        public void setXzj(String xzj) {
            this.xzj = xzj;
        }

        public String getCsq() {
            return csq;
        }

        public void setCsq(String csq) {
            this.csq = csq;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(String birthDay) {
            this.birthDay = birthDay;
        }

        public String getPkrk() {
            return pkrk;
        }

        public void setPkrk(String pkrk) {
            this.pkrk = pkrk;
        }

        public String getPkrkDate() {
            return pkrkDate;
        }

        public void setPkrkDate(String pkrkDate) {
            this.pkrkDate = pkrkDate;
        }

        public String getTkry() {
            return tkry;
        }

        public void setTkry(String tkry) {
            this.tkry = tkry;
        }

        public String getTkryDate() {
            return tkryDate;
        }

        public void setTkryDate(String tkryDate) {
            this.tkryDate = tkryDate;
        }

        public String getQedb() {
            return qedb;
        }

        public void setQedb(String qedb) {
            this.qedb = qedb;
        }

        public String getQedbDate() {
            return qedbDate;
        }

        public void setQedbDate(String qedbDate) {
            this.qedbDate = qedbDate;
        }

        public String getCedb() {
            return cedb;
        }

        public void setCedb(String cedb) {
            this.cedb = cedb;
        }

        public String getCedbDate() {
            return cedbDate;
        }

        public void setCedbDate(String cedbDate) {
            this.cedbDate = cedbDate;
        }

        public String getYejc() {
            return yejc;
        }

        public void setYejc(String yejc) {
            this.yejc = yejc;
        }

        public String getYejcDate() {
            return yejcDate;
        }

        public void setYejcDate(String yejcDate) {
            this.yejcDate = yejcDate;
        }

        public String getSsjc() {
            return ssjc;
        }

        public void setSsjc(String ssjc) {
            this.ssjc = ssjc;
        }

        public String getSsjcDate() {
            return ssjcDate;
        }

        public void setSsjcDate(String ssjcDate) {
            this.ssjcDate = ssjcDate;
        }

        public String getSypkry() {
            return sypkry;
        }

        public void setSypkry(String sypkry) {
            this.sypkry = sypkry;
        }

        public String getJbrdsf() {
            return jbrdsf;
        }

        public void setJbrdsf(String jbrdsf) {
            this.jbrdsf = jbrdsf;
        }

        public String getJbrdsfFirstDate() {
            return jbrdsfFirstDate;
        }

        public void setJbrdsfFirstDate(String jbrdsfFirstDate) {
            this.jbrdsfFirstDate = jbrdsfFirstDate;
        }

        public String getJbrdsfLastDate() {
            return jbrdsfLastDate;
        }

        public void setJbrdsfLastDate(String jbrdsfLastDate) {
            this.jbrdsfLastDate = jbrdsfLastDate;
        }

        public String getJbcbqk() {
            return jbcbqk;
        }

        public void setJbcbqk(String jbcbqk) {
            this.jbcbqk = jbcbqk;
        }

        public String getJbcbqkDate() {
            return jbcbqkDate;
        }

        public void setJbcbqkDate(String jbcbqkDate) {
            this.jbcbqkDate = jbcbqkDate;
        }
    }

    @Entity
    @Table(name = "fphistorydata")
    public static class FPHistoryData extends FPData {
    }

    @Entity
    @Table(name = "fpmonthdata")
    public static class FPMonthData extends FPData {
    }

    @Entity
    @Table(name = "fprawdata")
    public static class FPRawData {
        /** 序号 */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int NO;

        /** 乡镇街 */
        private String xzj;

        /** 村社区 */
        private String csq;

        /** 地址 */
        private String address;

        /** 姓名 */
        private String name;

        /** 身份证号码 */
        private String idcard;

        /** 出生日期 */
        private String birthDay;

        /** 人员类型 */
        private String type;

        /** 类型细节 */
        private String detail;

        /** 数据月份 */
        private String date;

        public int getNO() {
            return NO;
        }

        public void setNO(int nO) {
            NO = nO;
        }

        public String getXzj() {
            return xzj;
        }

        public void setXzj(String xzj) {
            this.xzj = xzj;
        }

        public String getCsq() {
            return csq;
        }

        public void setCsq(String csq) {
            this.csq = csq;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(String birthDay) {
            this.birthDay = birthDay;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    @Entity
    @Table(name = "jbrymx")
    public static class Jbrymx {
        /** 身份证号码 */
        @Id
        private String idcard;

        /** 行政区划 */
        private String xzqh;

        /** 户籍性质 */
        private String hjxz;

        /** 姓名 */
        private String name;

        /** 性别 */
        private String sex;

        /** 出生日期 */
        private String birthDay;

        /** 参保身份 */
        private String cbsf;

        /** 参保状态 */
        private String cbzt;

        /** 缴费状态 */
        private String jfzt;

        /** 参保时间 */
        private String cbsj;

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getXzqh() {
            return xzqh;
        }

        public void setXzqh(String xzqh) {
            this.xzqh = xzqh;
        }

        public String getHjxz() {
            return hjxz;
        }

        public void setHjxz(String hjxz) {
            this.hjxz = hjxz;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(String birthDay) {
            this.birthDay = birthDay;
        }

        public String getCbsf() {
            return cbsf;
        }

        public void setCbsf(String cbsf) {
            this.cbsf = cbsf;
        }

        public String getCbzt() {
            return cbzt;
        }

        public void setCbzt(String cbzt) {
            this.cbzt = cbzt;
        }

        public String getJfzt() {
            return jfzt;
        }

        public void setJfzt(String jfzt) {
            this.jfzt = jfzt;
        }

        public String getCbsj() {
            return cbsj;
        }

        public void setCbsj(String cbsj) {
            this.cbsj = cbsj;
        }
    }

    private static SessionFactory factory;

    public static SessionFactory getFactory() {
        if (factory == null) {
            try {
                Configuration configuration = new Configuration()
                    .setProperty(Environment.DRIVER, Configs.getDBDriver())
                    .setProperty(Environment.DIALECT, Configs.getDBDialect())
                    .setProperty(Environment.URL, Configs.getDBUrl())
                    .setProperty(Environment.USER, Configs.getDBUser())
                    .setProperty(Environment.PASS, Configs.getDBPwd());

                configuration.addAnnotatedClass(FPHistoryData.class)
                    .addAnnotatedClass(FPMonthData.class)
                    .addAnnotatedClass(FPRawData.class)
                    .addAnnotatedClass(Jbrymx.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

                factory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                throw new OperationException(e);
            }
        }
        return factory;
    }

    public interface Callable {
        public void call(Session session) throws Exception;
    }

    public static void use(Callable call) {
        try (var session = getFactory().openSession()) {
            call.call(session);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new OperationException(e);
        }
    }
}