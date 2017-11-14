package com.sz.quxin.addresschosedialog.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/14.
 * author:dwb
 */

public class AddressBean implements Serializable {
    private String appId;
    private int            count;
    private String         object;
    private String         retCode;
    private String         retDesc;
    private boolean        rtn;
    private String         rtnMsg;
    private String         serNo;
    private String         terminal;
    private String         version;
    private List<DataBean> data;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetDesc() {
        return retDesc;
    }

    public void setRetDesc(String retDesc) {
        this.retDesc = retDesc;
    }

    public boolean isRtn() {
        return rtn;
    }

    public void setRtn(boolean rtn) {
        this.rtn = rtn;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public String getSerNo() {
        return serNo;
    }

    public void setSerNo(String serNo) {
        this.serNo = serNo;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * cities : [{"areas":[{"area":"东城区","areaid":"110101","cityid":"110100","id":1,"smallAreas":[]},{"area":"西城区","areaid":"110102","cityid":"110100","id":2,"smallAreas":[]},{"area":"崇文区","areaid":"110103","cityid":"110100","id":3,"smallAreas":[]},{"area":"宣武区","areaid":"110104","cityid":"110100","id":4,"smallAreas":[]},{"area":"朝阳区","areaid":"110105","cityid":"110100","id":5,"smallAreas":[]},{"area":"丰台区","areaid":"110106","cityid":"110100","id":6,"smallAreas":[]},{"area":"石景山区","areaid":"110107","cityid":"110100","id":7,"smallAreas":[]},{"area":"海淀区","areaid":"110108","cityid":"110100","id":8,"smallAreas":[]},{"area":"门头沟区","areaid":"110109","cityid":"110100","id":9,"smallAreas":[]},{"area":"房山区","areaid":"110111","cityid":"110100","id":10,"smallAreas":[]},{"area":"通州区","areaid":"110112","cityid":"110100","id":11,"smallAreas":[]},{"area":"顺义区","areaid":"110113","cityid":"110100","id":12,"smallAreas":[]},{"area":"昌平区","areaid":"110114","cityid":"110100","id":13,"smallAreas":[]},{"area":"大兴区","areaid":"110115","cityid":"110100","id":14,"smallAreas":[]},{"area":"怀柔区","areaid":"110116","cityid":"110100","id":15,"smallAreas":[]},{"area":"平谷区","areaid":"110117","cityid":"110100","id":16,"smallAreas":[]},{"area":"东城区1","areaid":"110100","cityid":"110100","id":3146,"smallAreas":[]}],"city":"市辖区","cityid":"110100","id":1,"provinceid":"110000"},{"areas":[{"area":"密云县","areaid":"110228","cityid":"110200","id":17,"smallAreas":[]},{"area":"延庆县","areaid":"110229","cityid":"110200","id":18,"smallAreas":[]}],"city":"县","cityid":"110200","id":2,"provinceid":"110000"}]
         * cityNames : []
         * id : 1
         * province : 北京市
         * provinceid : 110000
         */

        private int id;
        private String           province;
        private String           provinceid;
        private List<CitiesBean> cities;
        private List<?>          cityNames;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvinceid() {
            return provinceid;
        }

        public void setProvinceid(String provinceid) {
            this.provinceid = provinceid;
        }

        public List<CitiesBean> getCities() {
            return cities;
        }

        public void setCities(List<CitiesBean> cities) {
            this.cities = cities;
        }

        public List<?> getCityNames() {
            return cityNames;
        }

        public void setCityNames(List<?> cityNames) {
            this.cityNames = cityNames;
        }

        public static class CitiesBean {

            /**
             * areas : [{"area":"东城区","areaid":"110101","cityid":"110100","id":1,"smallAreas":[]},{"area":"西城区","areaid":"110102","cityid":"110100","id":2,"smallAreas":[]},{"area":"崇文区","areaid":"110103","cityid":"110100","id":3,"smallAreas":[]},{"area":"宣武区","areaid":"110104","cityid":"110100","id":4,"smallAreas":[]},{"area":"朝阳区","areaid":"110105","cityid":"110100","id":5,"smallAreas":[]},{"area":"丰台区","areaid":"110106","cityid":"110100","id":6,"smallAreas":[]},{"area":"石景山区","areaid":"110107","cityid":"110100","id":7,"smallAreas":[]},{"area":"海淀区","areaid":"110108","cityid":"110100","id":8,"smallAreas":[]},{"area":"门头沟区","areaid":"110109","cityid":"110100","id":9,"smallAreas":[]},{"area":"房山区","areaid":"110111","cityid":"110100","id":10,"smallAreas":[]},{"area":"通州区","areaid":"110112","cityid":"110100","id":11,"smallAreas":[]},{"area":"顺义区","areaid":"110113","cityid":"110100","id":12,"smallAreas":[]},{"area":"昌平区","areaid":"110114","cityid":"110100","id":13,"smallAreas":[]},{"area":"大兴区","areaid":"110115","cityid":"110100","id":14,"smallAreas":[]},{"area":"怀柔区","areaid":"110116","cityid":"110100","id":15,"smallAreas":[]},{"area":"平谷区","areaid":"110117","cityid":"110100","id":16,"smallAreas":[]},{"area":"东城区1","areaid":"110100","cityid":"110100","id":3146,"smallAreas":[]}]
             * city : 市辖区
             * cityid : 110100
             * id : 1
             * provinceid : 110000
             */

            private String city;
            private String          cityid;
            private int             id;
            private String          provinceid;
            private List<AreasBean> areas;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCityid() {
                return cityid;
            }

            public void setCityid(String cityid) {
                this.cityid = cityid;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProvinceid() {
                return provinceid;
            }

            public void setProvinceid(String provinceid) {
                this.provinceid = provinceid;
            }

            public List<AreasBean> getAreas() {
                return areas;
            }

            public void setAreas(List<AreasBean> areas) {
                this.areas = areas;
            }

            public static class AreasBean {

                /**
                 * area : 东城区
                 * areaid : 110101
                 * cityid : 110100
                 * id : 1
                 * smallAreas : []
                 */

                private String area;
                private String  areaid;
                private String  cityid;
                private int     id;
                private List<SmallAreasBean> smallAreas;

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public String getAreaid() {
                    return areaid;
                }

                public void setAreaid(String areaid) {
                    this.areaid = areaid;
                }

                public String getCityid() {
                    return cityid;
                }

                public void setCityid(String cityid) {
                    this.cityid = cityid;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public List<SmallAreasBean> getSmallAreas() {
                    return smallAreas;
                }

                public void setSmallAreas(List<SmallAreasBean> smallAreas) {
                    this.smallAreas = smallAreas;
                }
                public static class SmallAreasBean implements Serializable{
                    private String areaid;
                    private long createtime;
                    private int id;
                    private String name;
                    private int sortorder;
                    private boolean isCheck = false;//默认选中

                    public String getAreaid() {
                        return areaid;
                    }

                    public void setAreaid(String areaid) {
                        this.areaid = areaid;
                    }

                    public long getCreatetime() {
                        return createtime;
                    }

                    public void setCreatetime(long createtime) {
                        this.createtime = createtime;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getSortorder() {
                        return sortorder;
                    }

                    public void setSortorder(int sortorder) {
                        this.sortorder = sortorder;
                    }

                    public boolean isCheck() {
                        return isCheck;
                    }

                    public void setCheck(boolean check) {
                        isCheck = check;
                    }
                }
            }
        }
    }
}
