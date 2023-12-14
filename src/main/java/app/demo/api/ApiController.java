package app.demo.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import app.demo.def.MessageConst;
import app.demo.entity.DefCurrencyEntity;
import app.demo.repository.ICurrencyRepository;
import app.demo.util.JsonUtil;

@RestController
public class ApiController {

	private final RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private ICurrencyRepository dao;

	@GetMapping("/query")
	public String queryCurrency(@RequestParam(required = false) String currencyCode) throws JsonProcessingException {
		if (currencyCode == null) {
			return JsonUtil.objToString(dao.findAll());
		}
		Optional<DefCurrencyEntity> opt = dao.findById(currencyCode);
		if (!opt.isPresent()) {
			return JsonUtil.format(currencyCode + MessageConst.IS_NOT_EXIST.getMessage());
		}
		return JsonUtil.objToString(opt.get());
	}

	@PostMapping("/add")
	public String addCurrency(@RequestBody DefCurrencyEntity defCurrencyEntity) throws JsonProcessingException {
		String currencyCode = defCurrencyEntity.getCurrencyCode();
		if (dao.findById(currencyCode).isPresent()) {
			return JsonUtil.format(currencyCode + MessageConst.ALREADY_EXIST.getMessage());
		}
		dao.save(defCurrencyEntity);
		return JsonUtil.format(MessageConst.SUCCESS);
	}

	@DeleteMapping("/delete")
	public String deleteCurrency(@RequestParam(required = true) String currencyCode) throws JsonProcessingException {
		if (!dao.findById(currencyCode).isPresent()) {
			return JsonUtil.format(currencyCode + " is not exist!");
		}
		dao.deleteById(currencyCode);
		return JsonUtil.format(MessageConst.SUCCESS);
	}

	@PutMapping("/update")
	public String udpateCurrency(@RequestBody DefCurrencyEntity defCurrencyEntity) throws JsonProcessingException {
		String currencyCode = defCurrencyEntity.getCurrencyCode();
		Optional<DefCurrencyEntity> optional = dao.findById(currencyCode);
		if (!optional.isPresent()) {
			return JsonUtil.format(currencyCode + " is not exist!");
		}
		DefCurrencyEntity updatedEntity = optional.get();
		updatedEntity.setDescription(defCurrencyEntity.getDescription());
		DefCurrencyEntity def = dao.save(updatedEntity);
		return JsonUtil.objToString(def);
	}

	@GetMapping("/coindesk")
	public String getCoinDesk() {
		String jsonStr = restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json", String.class);
		return jsonStr;
	}

	@GetMapping("/coindesk/convert")
	public String getCoinDeskAndConvert() throws ParseException {
		String jsonStr = getCoinDesk();
		JSONObject coinDeskJsonObj = new JSONObject(jsonStr);

		JSONObject resultJson = new JSONObject();
		resultJson.put("bpi", getBpiFromCoinDesk(coinDeskJsonObj));
		resultJson.put("updated", getUpdatedDate(coinDeskJsonObj, "yyyy/MM/dd HH:mm:ss"));
		return resultJson.toString();
	}

	private List<JSONObject> getBpiFromCoinDesk(JSONObject coinDeskJsonObj) {
		List<JSONObject> currencyList = new ArrayList<JSONObject>();
		JSONObject bpi = coinDeskJsonObj.getJSONObject("bpi");
		for (String key : bpi.keySet()) {
			JSONObject currencyInfo = bpi.getJSONObject(key);
			JSONObject currency = new JSONObject();
			String code = currencyInfo.getString("code");
			currency.put("code", code);
			currency.put("description", dao.findById(code).get().getDescription());
			currency.put("rate", currencyInfo.getString("rate"));
			currencyList.add(currency);
		}
		return currencyList;
	}

	private String getUpdatedDate(JSONObject coinDeskJsonObj, String dateFormat) throws ParseException {
		String orginalDateStr = coinDeskJsonObj.getJSONObject("time").getString("updated");
		SimpleDateFormat formatter2 = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.ENGLISH);
		formatter2.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = formatter2.parse(orginalDateStr);
		formatter2.applyPattern(dateFormat);
		return formatter2.format(date);
	}
}
