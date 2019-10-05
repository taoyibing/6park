import requests
from bs4 import BeautifulSoup
import re

def tid(href):
    return href and re.compile("tid=[0-9]+$").search(href)

def getTid(href):
    return re.compile("tid=([0-9]+)$").search(href).group(1)

def getBook(href):
    url = 'https://www.cool18.com/{0}'.format(href)
    resp = requests.get(url)
    soup = BeautifulSoup(resp.text, 'html.parser')
    
f = open('title','w')
for i in range(1,11):
    print('page={0}'.format(i))
    resp = requests.get('https://www.cool18.com/bbs4/index.php?app=forum&act=gold&p={0}'.format(i))
    soup = BeautifulSoup(resp.text, 'html.parser')
    for tag in soup.find_all(href=tid):
        s = '{0},{1},{2}'.format(tag.text,tag['href'],getTid(tag['href']))
        print(s, file=f)

f.close()
#print(soup.prettify())
