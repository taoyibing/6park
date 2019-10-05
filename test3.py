import requests
from lxml.html import fromstring
import re
import queue

def hasTid(href):
    return href and re.compile("tid=[0-9]+$").search(href)

def getTid(href):
    return int(re.compile("tid=([0-9]+)$").search(href).group(1))

def getDom(tid):
    url = 'https://www.cool18.com/bbs4/index.php?app=forum&act=threadview&tid={0}'.format(tid)
    resp = requests.get(url)
    return fromstring(resp.text)

def getTitle(dom):
    return dom.cssselect('td.show_content center font b')[0].text

def getContent(dom):
    e = dom.cssselect('pre')[0]
    content = e.text
    content += '<br>'.join(e.itertext('p'))
    return content
    
def getTids(dom):
    tids = set()
    
    for link in dom.cssselect('pre a'):
        #print(link.text_content())
        if (hasTid(link.get('href'))):
            tid=getTid(link.get('href'))
            tids.add(tid)
    for link in dom.cssselect('ul li'):
        if (meetMinLength(link.text_content(),1000)):
            #print(link.text_content())
            tid=getTid(link.cssselect('a')[0].get('href'))
            tids.add(tid)
    return tids 

def meetMinLength(text, min):
    meet = False
    match = re.compile("([0-9]+) bytes").search(text)
    if (match):
        if (int(match.group(1))>min):
            meet = True
    return meet

def getBook(book_id):
    book = {}
    toc = {}
    q = queue.Queue() 
    q.put(book_id)
    visited = [book_id]
    
    f = open(str(book_id)+".html",'w')
    while(not q.empty()):
        cur = q.get()
        print(cur)
        dom=getDom(cur)
        content=getContent(dom)
        book[cur]=content
        toc[cur]=getTitle(dom)
        for tid in getTids(dom):
            if(tid not in visited):
                visited.append(tid)
                q.put(tid)

    print("<html>",file=f)
    print("<body>",file=f)
    for tid in sorted(visited):
        print("<a href=\"#{0}\">{1}</a><br>".format(tid,toc.get(tid)),file=f)
    for tid in sorted(visited):
        print("<p id={0}>".format(tid),file=f)
        print(book.get(tid), file=f)
        print("</p>",file=f)

    print("</body>",file=f)
    print("</html>",file=f)
    f.close()

#print(getTids(getDom(13927871)))
getBook(14034276)
#print(getTids(dom))
#getContent(getDom(14034279))
