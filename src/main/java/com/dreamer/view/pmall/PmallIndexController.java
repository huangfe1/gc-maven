package com.dreamer.view.pmall;

import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.PmallGoodsType;
import com.dreamer.service.mobile.MallGoodsHandler;
import com.dreamer.service.mobile.MallGoodsTypeHandler;
import com.dreamer.service.mobile.factory.WxConfigFactory;
import com.dreamer.util.TokenInfo;
import com.dreamer.view.pmall.dto.MallGoodsDto;
import com.dreamer.view.pmall.dto.PointsGoodsDTO;
import com.dreamer.view.pmall.dto.TypesGoodsDTO;
import com.wxjssdk.JSAPI;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = {"/pmall", "/dmz/pmall"})
public class PmallIndexController {



    /**
     * 积分商城首页展示
     *
     * @param param
     * @param ref
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/index.html", "/", "/index"}, method = RequestMethod.GET)
    public String index(@ModelAttribute("parameter") SearchParameter<PmallGoods> param) {
        return "pmall/pmall_index";
    }

    /**
     * 积分商城广告展示页面
     *
     * @param param
     * @param ref
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/show.html", "/show"}, method = RequestMethod.GET)
    public String show(@ModelAttribute("parameter") SearchParameter<PmallGoods> param, HttpServletRequest request, Model model, Integer myCode) {

        String url = ServletUriComponentsBuilder.fromRequest(request).toUriString();
        String appid = wxConfigFactory.getBaseConfig().getAppid();
        String jsonParam = JSAPI.createWxConfig(appid, TokenInfo.getJsapiTicket(appid),TokenInfo.getAccessToken(appid),url).toString();
        if(!WebUtil.isLogin(request)&&myCode!=null){
            request.getSession().setAttribute("refCode",myCode);
        }
        List<PmallGoodsType> list =mallGoodsTypeHandler.findSubType();
        if(list!=null&&!list.isEmpty()){
            Map map = new HashedMap();
            map.put("goodsType.id",list.get(0).getId());
            map.put("shelf",true);//只显示上架的
            List<PmallGoods> ms = mallGoodsHandler.getList(map);
            List<PointsGoodsDTO> dtos = new ArrayList<>();
            ms.forEach(g -> {
                if(!g.getShelf()){
                    return;
                }
                PointsGoodsDTO dto = new PointsGoodsDTO();
                dto.setId(g.getId());
                dto.setName(g.getName());
                dto.setSel(g.getSel());
                dto.setPrice(g.getPrice());
                dto.setRetailPrice(g.getRetailPrice());
                dto.setShareName(g.getShareName());
                dto.setShareDetail(g.getShareDetail());
                dto.setPrice(g.getPrice());
                dto.setSpec(g.getSpec());
                dto.setStockQuantity(g.getStockQuantity());
                //保留最高级
                String strV = g.getVouchers();
                dto.setVouchers(strV.substring(strV.length()-5, strV.length()-4));
                String imgUrl = g.getImgFile();
                String wallImgUrl = g.getWallFile();
                dto.setImgUrl(imgUrl);
                dto.setWallImgUrl(wallImgUrl);
                dtos.add(dto);
            });
            model.addAttribute("ztGoods",dtos);
        }
        model.addAttribute("jsapiParamJson", jsonParam);
        model.addAttribute("pTypes",list);
        list.remove(0);
        model.addAttribute("pType",list.get(0).getId());//首次只展示这种分类
        return "pmall/pmall_show";
    }




    /**
     * 优惠商城列表页面
     *
     * @return
     */
    @RequestMapping(value = "/view.html", method = RequestMethod.GET)
    public String pmallView(@ModelAttribute("parameter") SearchParameter<PmallGoods> param) {
        return "pmall/pmall_view";
    }




    @RequestMapping(value = "/detail.html", method = RequestMethod.GET)
    public String detail(@RequestParam("id") Integer id, HttpServletRequest request, Integer myCode, Model model) {
        try {
            PmallGoods g = mallGoodsHandler.get(id);
            PointsGoodsDTO dto = new PointsGoodsDTO();
            dto.setId(g.getId());
            dto.setPrice(g.getPrice());
            dto.setName(g.getName());
            dto.setRetailPrice(g.getRetailPrice());
            dto.setSpec(g.getSpec());
            dto.setStockQuantity(g.getStockQuantity());
            dto.setShareDetail(g.getShareDetail());
            dto.setShareName(g.getShareName());
            dto.setSel(g.getSel());
            dto.setShelf(g.getShelf());
            dto.setGoodsStandard(g.getGoodsStandards());
            //保留最高级
            String strV = g.getVouchers();
            dto.setVouchers(strV.substring(strV.length()-5, strV.length()-4));
            dto.setImgUrl(g.getImgFile());
            List<String> list = new ArrayList<>();
            if (g.getDetailImg() != null) {
                for (String str : g.getDetailImg().split("\\+")) {
                    list.add( str);
                }
            }
            dto.setDetailImgUrls(list);//添加详情页
            String url = ServletUriComponentsBuilder.fromRequest(request).toUriString();
//            HashMap jspmap = JSAPI..buildShare(wxConfigFactory.getBaseConfig(), url, TokenInfo.getJsapiTicket(wxConfigFactory.getBaseConfig().getAppid()));
            String appid = wxConfigFactory.getBaseConfig().getAppid();
            String jsonParam = JSAPI.createWxConfig(appid, TokenInfo.getJsapiTicket(appid),TokenInfo.getAccessToken(appid),url).toString();
            if(!WebUtil.isLogin(request)&&myCode!=null){
                request.getSession().setAttribute("refCode",myCode);
            }
            model.addAttribute("jsapiParamJson", jsonParam);
            model.addAttribute("goods", dto);
        } catch (Exception exp) {
            LOG.error("进入积分商城获取详情页失败，可能是绑定上级失败" + exp.getMessage());
            LOG.error("进入优惠商城商品详情页异常", exp);
            exp.printStackTrace();
        }
        return "pmall/goods_detail";
    }






    @RequestMapping(value = "/goods/query.json", method = RequestMethod.GET)
    @ResponseBody
    public List<PointsGoodsDTO> queryGoods(@ModelAttribute("parameter") SearchParameter<PmallGoods> param, HttpServletResponse response, Model model) {
//        response.setHeader("Access-Control-Allow-Origin", "*");
        param.getEntity().setShelf(true);
        List<PmallGoods> goods = mallGoodsHandler.findMallGoods(param);
        List<PointsGoodsDTO> dtos = new ArrayList<>();
        goods.forEach(g -> {
            PointsGoodsDTO dto = new PointsGoodsDTO();
            dto.setId(g.getId());
            dto.setName(g.getName());
            dto.setPrice(g.getPrice());
            dto.setSel(g.getSel());
            dto.setShareName(g.getShareName());
            dto.setShareDetail(g.getShareDetail());
            dto.setRetailPrice(g.getRetailPrice());
            dto.setSpec(g.getSpec());
            dto.setStockQuantity(g.getStockQuantity());
            //保留最高级
            String strV = g.getVouchers();
            dto.setVouchers(strV.substring(strV.length()-5, strV.length()-4));
            String imgUrl = g.getImgFile();
            String wallImgUrl = g.getWallFile();
            dto.setImgUrl(imgUrl);
            dto.setWallImgUrl(wallImgUrl);
            dtos.add(dto);
        });

        return dtos;
    }


    /**
     * 优惠商城首页加载
     *
     * @param param
     * @param gt
     * @param request
     * @return
     */
    @RequestMapping(value = "/types/goods/query.json", method = RequestMethod.GET)
    @ResponseBody
    public List<TypesGoodsDTO> queryTypesAndGoods(SearchParameter<PmallGoodsType> param) {
        PmallGoodsType pmallGoodsType = new PmallGoodsType();
        pmallGoodsType.setType(1);
        param.setEntity(pmallGoodsType);//查询二等级别
        List<PmallGoodsType> pmallGoodsTypes = mallGoodsTypeHandler.findMallGoodsTypes(param);
        List<TypesGoodsDTO> typesGoodsDTOS = new ArrayList<>();
        pmallGoodsTypes.forEach(p -> {
            TypesGoodsDTO typesGoodsDTO = new TypesGoodsDTO();
            typesGoodsDTO.setTypeId(p.getId());
            typesGoodsDTO.setTypeName(p.getName());
            List<MallGoodsDto> goodsDtos = new ArrayList<>();
            mallGoodsHandler.getList("goodsType.id",p.getId()).forEach(t -> {
               if(!t.getShelf())return;
                MallGoodsDto goodsDto = new MallGoodsDto();
                goodsDto.setGoodsId(t.getId());
                goodsDto.setGoodsName(t.getName());
                goodsDto.setImgPath(t.getImgFile());
                goodsDtos.add(goodsDto);
            });
            if (goodsDtos.size() != 0) {
                typesGoodsDTO.setGoodsDtos(goodsDtos);
                typesGoodsDTOS.add(typesGoodsDTO);
            }
        });
        return typesGoodsDTOS;
    }

    @ModelAttribute("parameter")
    public SearchParameter<PmallGoods> preprocessing() {
        SearchParameter<PmallGoods> param = new SearchParameter<PmallGoods>();
        PmallGoods goods = new PmallGoods();
        param.setEntity(goods);
        return param;
    }




    @Autowired
    private WxConfigFactory wxConfigFactory;

    @Autowired
    private MallGoodsHandler mallGoodsHandler;

    @Autowired
    private MallGoodsTypeHandler mallGoodsTypeHandler;



    private final Logger LOG = LoggerFactory.getLogger(getClass());
}
