package com.dingxin.fresh.e;

import java.util.List;

public class ReservationEntity {


    /**
     * order_list : [{"time":"2020-08-20 08:30:00","order_goods":[{"order_id":126,"goods_id":51,"goods_name":"坚果","guid":4707,"weight":"0.000","dada_weight":"1.000","goods_price":"0.08","final_price":"0.08","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227858994821.jpg"},{"order_id":126,"goods_id":56,"goods_name":"菠萝","guid":4707,"weight":"0.000","dada_weight":"1.000","goods_price":"11.00","final_price":"22.00","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200711/1594449658749791.jpg"}]},{"time":"2020-08-20 08:30:00","order_goods":[{"order_id":127,"goods_id":51,"goods_name":"坚果","guid":4707,"weight":"15.600","dada_weight":"1.000","goods_price":"0.07","final_price":"0.08","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227858994821.jpg"},{"order_id":127,"goods_id":56,"goods_name":"菠萝","guid":4707,"weight":"1.050","dada_weight":"1.000","goods_price":"8.90","final_price":"22.00","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200711/1594449658749791.jpg"}]},{"time":"2020-08-20 08:30:00","order_goods":[{"order_id":128,"goods_id":51,"goods_name":"坚果","guid":4707,"weight":"6.200","dada_weight":"1.000","goods_price":"0.07","final_price":"0.08","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227858994821.jpg"},{"order_id":128,"goods_id":56,"goods_name":"菠萝","guid":4707,"weight":"3.660","dada_weight":"3.000","goods_price":"8.90","final_price":"66.00","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200711/1594449658749791.jpg"}]},{"time":"2020-08-20 08:30:00","order_goods":[{"order_id":137,"goods_id":51,"goods_name":"坚果","guid":4707,"weight":"0.000","dada_weight":"1.000","goods_price":"0.08","final_price":"0.08","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227858994821.jpg"},{"order_id":137,"goods_id":56,"goods_name":"菠萝","guid":4707,"weight":"0.000","dada_weight":"1.000","goods_price":"11.00","final_price":"22.00","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200711/1594449658749791.jpg"},{"order_id":137,"goods_id":66,"goods_name":"菠萝","guid":4707,"weight":"0.000","dada_weight":"1.000","goods_price":"0.60","final_price":"0.60","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200525/1590420566780081.jpg"},{"order_id":137,"goods_id":67,"goods_name":"成品测试","guid":4707,"weight":"2.000","dada_weight":"1.000","goods_price":"1.10","final_price":"1.10","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227742888046.jpg"}]}]
     * statistics : {"dada_weight":"12.000","total_price":"134.02","weight_list":[{"goods_id":51,"goods_name":"坚果","goods_price":"0.08","dada_weight":4,"thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227858994821.jpg"},{"goods_id":56,"goods_name":"菠萝","goods_price":"11.00","dada_weight":6,"thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200711/1594449658749791.jpg"},{"goods_id":66,"goods_name":"菠萝","goods_price":"0.60","dada_weight":1,"thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200525/1590420566780081.jpg"},{"goods_id":67,"goods_name":"成品测试","goods_price":"1.10","dada_weight":1,"thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227742888046.jpg"}]}
     */

    private StatisticsBean statistics;
    private List<OrderListBean> order_list;

    public StatisticsBean getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsBean statistics) {
        this.statistics = statistics;
    }

    public List<OrderListBean> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderListBean> order_list) {
        this.order_list = order_list;
    }

    public static class StatisticsBean {
        /**
         * dada_weight : 12.000
         * total_price : 134.02
         * weight_list : [{"goods_id":51,"goods_name":"坚果","goods_price":"0.08","dada_weight":4,"thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227858994821.jpg"},{"goods_id":56,"goods_name":"菠萝","goods_price":"11.00","dada_weight":6,"thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200711/1594449658749791.jpg"},{"goods_id":66,"goods_name":"菠萝","goods_price":"0.60","dada_weight":1,"thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200525/1590420566780081.jpg"},{"goods_id":67,"goods_name":"成品测试","goods_price":"1.10","dada_weight":1,"thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227742888046.jpg"}]
         */

        private String dada_weight;
        private String total_price;
        private List<WeightListBean> weight_list;

        public String getDada_weight() {
            return dada_weight;
        }

        public void setDada_weight(String dada_weight) {
            this.dada_weight = dada_weight;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public List<WeightListBean> getWeight_list() {
            return weight_list;
        }

        public void setWeight_list(List<WeightListBean> weight_list) {
            this.weight_list = weight_list;
        }

        public static class WeightListBean {
            /**
             * goods_id : 51
             * goods_name : 坚果
             * goods_price : 0.08
             * dada_weight : 4
             * thumb : http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227858994821.jpg
             */

            private int goods_id;
            private String goods_name;
            private String goods_price;
            private int dada_weight;
            private String thumb;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public int getDada_weight() {
                return dada_weight;
            }

            public void setDada_weight(int dada_weight) {
                this.dada_weight = dada_weight;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }
        }
    }

    public static class OrderListBean {
        /**
         * time : 2020-08-20 08:30:00
         * order_goods : [{"order_id":126,"goods_id":51,"goods_name":"坚果","guid":4707,"weight":"0.000","dada_weight":"1.000","goods_price":"0.08","final_price":"0.08","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227858994821.jpg"},{"order_id":126,"goods_id":56,"goods_name":"菠萝","guid":4707,"weight":"0.000","dada_weight":"1.000","goods_price":"11.00","final_price":"22.00","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200711/1594449658749791.jpg"}]
         */

        private String time;
        private List<OrderGoodsBean> order_goods;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<OrderGoodsBean> getOrder_goods() {
            return order_goods;
        }

        public void setOrder_goods(List<OrderGoodsBean> order_goods) {
            this.order_goods = order_goods;
        }

        public static class OrderGoodsBean {
            /**
             * order_id : 126
             * goods_id : 51
             * goods_name : 坚果
             * guid : 4707
             * weight : 0.000
             * dada_weight : 1.000
             * goods_price : 0.08
             * final_price : 0.08
             * thumb : http://test.7chezhibo.com/ueditor/php/upload/image/20200627/1593227858994821.jpg
             */

            private int order_id;
            private int goods_id;
            private String goods_name;
            private int guid;
            private String weight;
            private String dada_weight;
            private String goods_price;
            private String final_price;
            private String thumb;

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getGuid() {
                return guid;
            }

            public void setGuid(int guid) {
                this.guid = guid;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getDada_weight() {
                return dada_weight;
            }

            public void setDada_weight(String dada_weight) {
                this.dada_weight = dada_weight;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getFinal_price() {
                return final_price;
            }

            public void setFinal_price(String final_price) {
                this.final_price = final_price;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }
        }
    }

}
