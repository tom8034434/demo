# 1.幣別 DB 維護功能<br />
  ## i.查詢幣別資訊<br />
  uri: /query?currencyCode=USD  (Get)<br />

  ## ii.新增幣別資訊<br />
  uri: /add (Post)<br />
  {
    "currencyCode" : "TWD",
    "description" : "新台幣"
  } 
  <br />
  ## iii.修改幣別資訊<br />
  uri: /update (Put)<br />
  {
    "currencyCode" : "USD",
    "description" : "美金"
  } 
  
  ## iv. 刪除幣別資訊<br />
  uri: /delete?currencyCode=TWD (Delete)<br />

# 2. 呼叫 coindesk 的 API。<br />
  uri: /coindesk (Get)<br />

# 3. 呼叫 coindesk 的 API，並進行資料轉換，組成新 API。<br />
  uri: /coindesk/convert (Get)<br />
