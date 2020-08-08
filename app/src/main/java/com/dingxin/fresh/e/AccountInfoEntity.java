package com.dingxin.fresh.e;


public class AccountInfoEntity {


    /**
     * balance : 0.00
     * online_total_amount : 0.00
     * underline_total_amount : 0.00
     * order_num : 0
     * list1 : {"count":0,"amount":"0.00","weight":"0.000","after_service":0}
     * list2 : {"count":0,"amount":"0.00","weight":"0.000","after_service":0}
     * list3 : {"count":0,"amount":"0.00","weight":"0.000","after_service":0}
     * list4 : {"count":0,"amount":"0.00","weight":"0.000","after_service":0}
     */
    private String dis;
    private String title;
    private int color;
    private boolean is_show;

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private String balance;
    private String online_total_amount;
    private String underline_total_amount;
    private String order_num;
    private List1Bean list1;
    private List2Bean list2;
    private List3Bean list3;
    private List4Bean list4;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getOnline_total_amount() {
        return online_total_amount;
    }

    public void setOnline_total_amount(String online_total_amount) {
        this.online_total_amount = online_total_amount;
    }

    public String getUnderline_total_amount() {
        return underline_total_amount;
    }

    public void setUnderline_total_amount(String underline_total_amount) {
        this.underline_total_amount = underline_total_amount;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public List1Bean getList1() {
        return list1;
    }

    public void setList1(List1Bean list1) {
        this.list1 = list1;
    }

    public List2Bean getList2() {
        return list2;
    }

    public void setList2(List2Bean list2) {
        this.list2 = list2;
    }

    public List3Bean getList3() {
        return list3;
    }

    public void setList3(List3Bean list3) {
        this.list3 = list3;
    }

    public List4Bean getList4() {
        return list4;
    }

    public void setList4(List4Bean list4) {
        this.list4 = list4;
    }

    public static class List1Bean {
        /**
         * count : 0
         * amount : 0.00
         * weight : 0.000
         * after_service : 0
         */

        private int count;
        private String amount;
        private String weight;
        private int after_service;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public int getAfter_service() {
            return after_service;
        }

        public void setAfter_service(int after_service) {
            this.after_service = after_service;
        }
    }

    public static class List2Bean {
        /**
         * count : 0
         * amount : 0.00
         * weight : 0.000
         * after_service : 0
         */

        private int count;
        private String amount;
        private String weight;
        private int after_service;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public int getAfter_service() {
            return after_service;
        }

        public void setAfter_service(int after_service) {
            this.after_service = after_service;
        }
    }

    public static class List3Bean {
        /**
         * count : 0
         * amount : 0.00
         * weight : 0.000
         * after_service : 0
         */

        private int count;
        private String amount;
        private String weight;
        private int after_service;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public int getAfter_service() {
            return after_service;
        }

        public void setAfter_service(int after_service) {
            this.after_service = after_service;
        }
    }

    public static class List4Bean {
        /**
         * count : 0
         * amount : 0.00
         * weight : 0.000
         * after_service : 0
         */

        private int count;
        private String amount;
        private String weight;
        private int after_service;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public int getAfter_service() {
            return after_service;
        }

        public void setAfter_service(int after_service) {
            this.after_service = after_service;
        }
    }

}
