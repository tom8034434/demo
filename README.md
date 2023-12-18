1.幣別 DB 維護功能
  i.查詢幣別資訊
  uri: /query?currencyCode=USD  (Get)
  
  ii.新增幣別資訊
  uri: /add (Post)
  {
    "currencyCode" : "TWD",
    "description" : "新台幣"
  } 
  
  iii.修改幣別資訊
  uri: /update (Put)
  {
    "currencyCode" : "USD",
    "description" : "美金"
  } 
  
  iv. 刪除幣別資訊
  uri: /delete?currencyCode=TWD (Delete)

2. 呼叫 coindesk 的 API。
  uri: /coindesk (Get)

3. 呼叫 coindesk 的 API，並進行資料轉換，組成新 API。
  uri: /coindesk/convert (Get)
