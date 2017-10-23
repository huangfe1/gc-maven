package com.dreamer.view.goods;

import com.dreamer.domain.mall.delivery.DeliveryNote;
import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.GoodsType;
import com.dreamer.domain.mall.goods.Price;
import com.dreamer.domain.user.AgentLevel;
import com.dreamer.domain.user.User;
import com.dreamer.service.mobile.*;
import com.dreamer.view.mall.goods.GoodsDTO;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.DatatableDTO;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/goods")
public class GoodsQueryController {

    @RequestMapping(value = {"/index.html", "/search.html"}, method = RequestMethod.GET)
    public String index(
            @ModelAttribute("parameter") SearchParameter<Goods> parameter,
            HttpServletRequest request, Model model) {
        try {
            List<Goods> goods = goodsHandler.findGoods(parameter);
            WebUtil.turnPage(parameter, request);
            model.addAttribute("goods", goods);
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("产品查询失败", exp);
        }
        return "goods/goods_index";
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.GET)
    public String edit_enter(
            @ModelAttribute("parameter") SearchParameter<Goods> parameter,
            HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        if (user.isAdmin()) {
            List<AgentLevel> levels = agentLevelHandler.findAllOrderByLevel();
            Set<Price> prices = parameter.getEntity().getPrices();
            HashMap<Integer, Price> maps = new HashMap<>();
            Iterator<Price> ite = prices.iterator();
            while (ite.hasNext()) {
                Price p = ite.next();
                maps.put(p.getAgentLevel().getId(), p);
            }
            model.addAttribute("types", GoodsType.values());
            model.addAttribute("levels", levels);
            model.addAttribute("prices", maps);
            model.addAttribute("categorys", categoryHandler.findByType(1));
            return "goods/goods_edit";
        } else {
            LOG.error("非管理员身份,无产品编辑权限");
            return "common/403";
        }

    }

    @RequestMapping(value = "/detail.html", method = RequestMethod.GET)
    public String detail(
            @ModelAttribute("parameter") SearchParameter<Goods> parameter, Model model) {
        List<AgentLevel> levels = agentLevelHandler.findAll();
        Set<Price> prices = parameter.getEntity().getPrices();
        HashMap<Integer, Price> maps = new HashMap<>();
        Iterator<Price> ite = prices.iterator();
        while (ite.hasNext()) {
            Price p = ite.next();
            maps.put(p.getAgentLevel().getId(), p);
        }
        model.addAttribute("levels", levels);
        model.addAttribute("prices", maps);
        return "goods/goods_detail";

    }

    @RequestMapping(value = "/addAutyType.html", method = RequestMethod.GET)
    public String addAuthType_enter(
            @ModelAttribute("parameter") SearchParameter<Goods> parameter,
            HttpServletRequest request, Model model) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            if (user.isAdmin()) {

            } else {
                LOG.error("非管理员身份,无产品授权增加权限");
                return "common/403";
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("进入新增授权类型失败", exp);
        }
        return "authorization/authType_edit";
    }

    @RequestMapping(value = "/authGoods.html", method = RequestMethod.GET)
    public String selectGoodsEnter(
            @ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter,
            HttpServletRequest request, Model model) {
        try {
        } catch (Exception exp) {
            LOG.error("进入货物选择界面失败,", exp);
            exp.printStackTrace();
        }
        return "goods/select_authGoods";
    }

    @RequestMapping(value = "/authGoods.json", method = RequestMethod.GET)
    @ResponseBody
    public DatatableDTO<GoodsDTO> queryGoods(
            @ModelAttribute("parameter") SearchParameter<Goods> parameter,
            HttpServletRequest request) {
        DatatableDTO<GoodsDTO> dto = new DatatableDTO();
//        User user = (User) WebUtil.getCurrentUser(request);
//        Agent agent;
//        if (user.isAdmin()) {
//            agent = muteUserHandler.getMuteUser();
//        } else {
//            agent = muteUserHandler.get(user.getId());
//        }
        Map<String, Object> map = new HashedMap();
        map.put("ASC", "order");//排序
        List<Goods> goodsstem = goodsHandler.getList(map);
        List<GoodsDTO> goodss = goodsstem.stream().filter(gc -> gc.getCurrentBalance() > 0).map(gc -> {
            GoodsDTO d = new GoodsDTO();
            d.setId(gc.getId());
            d.setName(gc.getName());
            return d;
        }).skip(parameter.caculateFirstIndexForCurrentPage()).limit(parameter.rowsOfPerPage()).collect(Collectors.toList());
        parameter.setTotalRows(goodsstem.size());
        dto.setData(goodss);
        dto.setRecordsFiltered(parameter.getTotalRows());
        dto.setRecordsTotal(parameter.getTotalRows());

        return dto;
    }

    @ModelAttribute("parameter")
    public SearchParameter<Goods> preprocess(
            @RequestParam("id") Optional<Integer> id) {
        SearchParameter<Goods> parameter = new SearchParameter<>();
        Goods goods;
        if (id.isPresent()) {
            goods = goodsHandler.get(id.get());
        } else {
            goods = new Goods();
        }
        parameter.setEntity(goods);
        return parameter;
    }


    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private MuteUserHandler muteUserHandler;

    @Autowired
    private GoodsHandler goodsHandler;

    @Autowired
    private GoodsAccountHandler goodsAccountHandler;

    @Autowired
    private AgentLevelHandler agentLevelHandler;

    @Autowired
    private CategoryHandler categoryHandler;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

}
