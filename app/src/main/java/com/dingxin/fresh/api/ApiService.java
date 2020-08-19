package com.dingxin.fresh.api;

import com.dingxin.fresh.e.AboutEntity;
import com.dingxin.fresh.e.AccountInfoEntity;
import com.dingxin.fresh.e.BannerEntity;
import com.dingxin.fresh.e.BusinessInfoEntity;
import com.dingxin.fresh.e.CancelEntity;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.CompletedEntity;
import com.dingxin.fresh.e.DetailEntity;
import com.dingxin.fresh.e.GreensEntity;
import com.dingxin.fresh.e.LoginEntity;
import com.dingxin.fresh.e.MarketEntity;
import com.dingxin.fresh.e.PrintEntity;
import com.dingxin.fresh.e.ReservationEntity;
import com.dingxin.fresh.e.SpecsEntity;
import com.dingxin.fresh.e.TargetEntity;
import com.dingxin.fresh.e.UnitEntity;
import com.dingxin.fresh.e.UploadEntity;
import com.dingxin.fresh.e.WeighFinalEntity;
import com.dingxin.fresh.e.WeightEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import okhttp3.MultipartBody;

import retrofit2.http.Body;

import retrofit2.http.GET;

import retrofit2.http.POST;


public interface ApiService {
    @GET("other/banner_list")
    Observable<BaseResponse<List<BannerEntity>>> RequestBannerList();

    @POST("member/business_info")
    Observable<BaseResponse<BusinessInfoEntity>> RequestBusinessInfoData();

    @FormUrlEncoded
    @POST("member/apk_login")
    Observable<BaseResponse<LoginEntity>> Login(@Field("username") String username, @Field("password") String password, @Field("mac") String mac);

    @POST("member/get_account_info")
    Observable<BaseResponse<AccountInfoEntity>> RequestPosData();


    @POST("member/upload")
    Observable<BaseResponse<UploadEntity>> upload(@Body MultipartBody body);

    @FormUrlEncoded
    @POST("member/update_business_info")
    Observable<BaseResponse<Object>> update(@Field("realname") String realname, @Field("mobile") String mobile, @Field("market_shop_name") String market_shop_name,
                                            @Field("market_shop_content") String market_shop_content, @Field("booth_image") String booth_image);

    @FormUrlEncoded
    @POST("member/get_weight_list")
    Observable<BaseResponse<List<WeightEntity>>> RequestWeightData(@Field("startPosition") int startPosition, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("member/order_add_weight")
    Observable<BaseResponse<WeighFinalEntity>> RequestWeight(@Field("goods_id") String goods_id, @Field("weights") String weights, @Field("order_id") String order_id, @Field("spec_id") String spec_id);

    @FormUrlEncoded
    @POST("member/cancel_order_goods")
    Observable<BaseResponse<CommonEntity>> cancel_order_goods(@Field("goods_id") String goods_id, @Field("order_id") String order_id, @Field("spec_id") String spec_id);

    @FormUrlEncoded
    @POST("member/commit_weight")
    Observable<BaseResponse<Object>> commit_weight(@Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("member/get_print_list")
    Observable<BaseResponse<List<PrintEntity>>> get_print_list(@Field("startPosition") int startPosition, @Field("pageSize") int pageSize);


    @FormUrlEncoded
    @POST("member/print")
    Observable<BaseResponse<Object>> print(@Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("neer_bazaar/list")
    Observable<BaseResponse<List<MarketEntity>>> requestMarketList(@Field("lng") String lng, @Field("lat") String lat);

    @POST("member/greens_pcate_name_become_seller")
    Observable<BaseResponse<List<TargetEntity>>> requestTargetList();

    @FormUrlEncoded
    @POST("member/bind_scale")
    Observable<BaseResponse<Object>> bind_scale(@Field("scale") String scale, @Field("name") String name);

    @FormUrlEncoded
    @POST("member/bind_ticket")
    Observable<BaseResponse<Object>> bind_pos(@Field("ticket") String scale, @Field("name") String name);

    @POST("about/safe_info")
    Observable<BaseResponse<AboutEntity>> about();

    @FormUrlEncoded
    @POST("member/become_apk_seller")
    Observable<BaseResponse<Object>> become_seller(@Field("realname") String realname, @Field("mobile") String mobile,
                                                   @Field("market_id") String market_id, @Field("tab") String tab,
                                                   @Field("market_shop_name") String market_shop_name,
                                                   @Field("market_shop_sn") String market_shop_sn, @Field("market_shop_content") String market_shop_content,
                                                   @Field("booth_image") String booth_image, @Field("card_image") String card_image);

    @POST("member/get_current_pcate")
    Observable<BaseResponse<List<CommonEntity>>> current();

    @FormUrlEncoded
    @POST("member/greens_cate_name_upload")
    Observable<BaseResponse<List<CommonEntity>>> level_class(@Field("parent_id") String parent_id);

    @FormUrlEncoded
    @POST("common/get_greens_unit")
    Observable<BaseResponse<List<UnitEntity>>> unit(@Field("id") String id);

    @FormUrlEncoded
    @POST("common/get_new_price")
    Observable<BaseResponse<CommonEntity>> new_price(@Field("id") String id, @Field("price") String price, @Field("unit_name") String unit_name, @Field("goods_weight") String goods_weight);

    @FormUrlEncoded
    @POST("member/greens_pic_list")
    Observable<BaseResponse<List<CommonEntity>>> pic_list(@Field("greens_cate_id") String greens_cate_id);

    @FormUrlEncoded
    @POST("member/greens_add")
    Observable<BaseResponse<Object>> greens_add(@Field("cid") String name, @Field("unit_name") String unit_name,
                                                @Field("title") String title, @Field("note_name") String note_name,
                                                @Field("goods_specs") String goods_specs,
                                                @Field("thumb") String thumb);

    @FormUrlEncoded
    @POST("member/greens_list")
    Observable<BaseResponse<List<GreensEntity>>> greens_list(@Field("startPosition") int startPosition, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("member/greens_spec_info")
    Observable<BaseResponse<List<SpecsEntity>>> greens_spec_info(@Field("id") String id);

    @FormUrlEncoded
    @POST("member/greens_del")
    Observable<BaseResponse<Object>> greens_del(@Field("ids") String id);

    @FormUrlEncoded
    @POST("member/sale_edit")
    Observable<BaseResponse<CommonEntity>> sale_edit(@Field("greens_id") String id);

    @FormUrlEncoded
    @POST("member/greens_edit")
    Observable<BaseResponse<Object>> greens_edit(@Field("id") String id, @Field("cid") String name, @Field("unit_name") String unit_name,
                                                 @Field("title") String title, @Field("note_name") String note_name,
                                                 @Field("goods_specs") String goods_specs,
                                                 @Field("thumb") String thumb, @Field("spec_id") String spec_id);

    @FormUrlEncoded
    @POST("member/get_finish_list")
    Observable<BaseResponse<List<CompletedEntity>>> finish_list(@Field("startPosition") int startPosition, @Field("pageSize") int pageSize);


    @POST("member/balance_detail")
    Observable<BaseResponse<List<DetailEntity>>> balance_detail();

    @FormUrlEncoded
    @POST("member/get_cash")
    Observable<BaseResponse<Object>> get_cash(@Field("money") String money);

    @FormUrlEncoded
    @POST("member/get_code")
    Observable<BaseResponse<Object>> getYz(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("member/set_greens_spec_sale")
    Observable<BaseResponse<CommonEntity>> set_greens_spec_sale(@Field("spec_id") String spec_id);

    @FormUrlEncoded
    @POST("member/set_greens_delete")
    Observable<BaseResponse<Object>> set_greens_delete(@Field("spec_id") String spec_id);

    @POST("member/change_isshow")
    Observable<BaseResponse<CommonEntity>> change_isShow();

    @FormUrlEncoded
    @POST("member/get_apk_schedule_goodslist")
    Observable<BaseResponse<ReservationEntity>> get_apk_schedule_goodslist(@Field("day") String day);

}