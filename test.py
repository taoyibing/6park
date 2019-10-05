import http.client

connection = http.client.HTTPSConnection("www.cool18.com")
connection.request("GET", "/bbs4/index.php?app=forum&act=gold&p=1")
response = connection.getresponse()
print("Status: {} and reason: {}".format(response.status, response.reason))

html = response.read()
print(html.encode('utf8'))

connection.close()