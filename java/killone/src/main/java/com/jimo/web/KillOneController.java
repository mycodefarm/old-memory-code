package com.jimo.web;

import com.jimo.dto.Exposer;
import com.jimo.dto.KillExecution;
import com.jimo.dto.KillResult;
import com.jimo.entity.KillOne;
import com.jimo.enums.KillOneStateEnum;
import com.jimo.exception.KillCloseException;
import com.jimo.exception.RepeatKillException;
import com.jimo.service.KillOneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 17-5-26.
 */
@Controller
@RequestMapping("/killone")
public class KillOneController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KillOneService killOneService;

    @RequestMapping("/list")
    public String getList(Model model) {
        List<KillOne> killList = killOneService.getKillList();
        model.addAttribute("list", killList);
        return "list";
    }

    @RequestMapping(value = "/{killId}/detail", method = RequestMethod.GET)
    public String getDetail(@PathVariable("killId") Long killId, Model model) {
        if (killId == null) {
            return "redirect:/killone/list";
        }
        KillOne killOne = killOneService.getKillById(killId);
        if (killOne == null) {
            return "forward:/killone/list";
        }

        model.addAttribute("killOne", killOne);
        return "detail";
    }

    /**
     * ajax json
     *
     * @param killId
     */
    @RequestMapping(value = "/{killId}/exposor", method = RequestMethod.POST,
            produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public KillResult<Exposer> exposor(Long killId) {
        KillResult<Exposer> killResult;
        try {
            Exposer exposer = killOneService.exportKillUrl(killId);
            killResult = new KillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            killResult = new KillResult<Exposer>(false, e.getMessage());
        }
        return killResult;
    }

    @RequestMapping(value = "/{killId}/{md5}/execution",
            method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public KillResult<KillExecution> execute(
            @PathVariable("killId") Long killId,
            @PathVariable("md5") String md5,
            @CookieValue("killPhone") Long phone
    ) {
        if (phone == null) {
            return new KillResult<KillExecution>(false, "未注册");
        }
        try {
            KillExecution killExecution = killOneService.executeKill(killId, phone, md5);
            return new KillResult<KillExecution>(true, killExecution);
        } catch (KillCloseException e) {
            KillExecution killExecution = new KillExecution(killId, KillOneStateEnum.END);
            return new KillResult<KillExecution>(false, killExecution);
        } catch (RepeatKillException e) {
            KillExecution killExecution = new KillExecution(killId, KillOneStateEnum.REPEAT_KILL);
            return new KillResult<KillExecution>(false, killExecution);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new KillResult<KillExecution>(false, new KillExecution(killId, KillOneStateEnum.INNER_ERROR));
        }
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    public KillResult<Long> time() {
        Date date = new Date();
        return new KillResult<Long>(true, date.getTime());
    }
}
